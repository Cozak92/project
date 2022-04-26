package com.ably.assignment.infrastructure.cloudsql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.SQLException
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
import javax.servlet.annotation.WebListener
import javax.sql.DataSource


@WebListener("Creates a connection pool that is stored in the Servlet's context for later use.")
class ConnectionPoolContextListener : ServletContextListener {
    private fun createConnectionPool(): DataSource {
        // [START cloud_sql_mysql_servlet_create]
        // Note: For Java users, the Cloud SQL JDBC Socket Factory can provide authenticated connections
        // which is preferred to using the Cloud SQL Proxy with Unix sockets.
        // See https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory for details.

        // The configuration object specifies behaviors for the connection pool.
        val config = HikariConfig()

        // The following URL is equivalent to setting the config options below:
        // jdbc:mysql:///<DB_NAME>?cloudSqlInstance=<INSTANCE_CONNECTION_NAME>&
        // socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=<DB_USER>&password=<DB_PASS>
        // See the link below for more info on building a JDBC URL for the Cloud SQL JDBC Socket Factory
        // https://github.com/GoogleCloudPlatform/cloud-sql-jdbc-socket-factory#creating-the-jdbc-url

        // Configure which instance and what database user to connect with.
        config.jdbcUrl = String.format("jdbc:mysql://$DB_NAME")
        config.username = DB_USER // e.g. "root", "mysql"
        config.password = DB_PASS // e.g. "my-password"
        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory")
        config.addDataSourceProperty(
            "cloudSqlInstance",
            INSTANCE_CONNECTION_NAME
        )

        // The ipTypes argument can be used to specify a comma delimited list of preferred IP types
        // for connecting to a Cloud SQL instance. The argument ipTypes=PRIVATE will force the
        // SocketFactory to connect with an instance's associated private IP.
        config.addDataSourceProperty("ipTypes", "PUBLIC,PRIVATE")

        // ... Specify additional connection properties here.
        // [START_EXCLUDE]

        // [START cloud_sql_mysql_servlet_limit]
        // maximumPoolSize limits the total number of concurrent connections this pool will keep. Ideal
        // values for this setting are highly variable on app design, infrastructure, and database.
        config.maximumPoolSize = 5
        // minimumIdle is the minimum number of idle connections Hikari maintains in the pool.
        // Additional connections will be established to meet this value unless the pool is full.
        config.minimumIdle = 5
        // [END cloud_sql_mysql_servlet_limit]

        // [START cloud_sql_mysql_servlet_timeout]
        // setConnectionTimeout is the maximum number of milliseconds to wait for a connection checkout.
        // Any attempt to retrieve a connection from this pool that exceeds the set limit will throw an
        // SQLException.
        config.connectionTimeout = 10000 // 10 seconds
        // idleTimeout is the maximum amount of time a connection can sit in the pool. Connections that
        // sit idle for this many milliseconds are retried if minimumIdle is exceeded.
        config.idleTimeout = 600000 // 10 minutes
        // [END cloud_sql_mysql_servlet_timeout]

        // [START cloud_sql_mysql_servlet_backoff]
        // Hikari automatically delays between failed connection attempts, eventually reaching a
        // maximum delay of `connectionTimeout / 2` between attempts.
        // [END cloud_sql_mysql_servlet_backoff]

        // [START cloud_sql_mysql_servlet_lifetime]
        // maxLifetime is the maximum possible lifetime of a connection in the pool. Connections that
        // live longer than this many milliseconds will be closed and reestablished between uses. This
        // value should be several minutes shorter than the database's timeout value to avoid unexpected
        // terminations.
        config.maxLifetime = 1800000 // 30 minutes
        // [END cloud_sql_mysql_servlet_lifetime]

        // [END_EXCLUDE]

        // Initialize the connection pool using the configuration object.
        // [END cloud_sql_mysql_servlet_create]
        return HikariDataSource(config)
    }

    @Throws(SQLException::class)
    private fun createTable(pool: DataSource?) {
        // Safely attempt to create the table schema.
        pool!!.connection.use { conn ->
            val stmt = ("CREATE TABLE IF NOT EXISTS votes ( "
                    + "vote_id SERIAL NOT NULL, time_cast timestamp NOT NULL, candidate CHAR(6) NOT NULL,"
                    + " PRIMARY KEY (vote_id) );")
            conn.prepareStatement(stmt).use { createTableStatement -> createTableStatement.execute() }
        }
    }

    override fun contextDestroyed(event: ServletContextEvent) {
        // This function is called when the Servlet is destroyed.
        val pool = event.servletContext.getAttribute("my-pool") as HikariDataSource
        pool?.close()
    }

    override fun contextInitialized(event: ServletContextEvent) {
        // This function is called when the application starts and will safely create a connection pool
        // that can be used to connect to.
        val servletContext = event.servletContext
        var pool = servletContext.getAttribute("my-pool") as DataSource
        if (pool == null) {
            pool = createConnectionPool()
            servletContext.setAttribute("my-pool", pool)
        }
        try {
            createTable(pool)
        } catch (ex: SQLException) {
            throw RuntimeException(
                "Unable to verify table schema. Please double check the steps"
                        + "in the README and try again.",
                ex
            )
        }
    }

    companion object {
        // Saving credentials in environment variables is convenient, but not secure - consider a more
        // secure solution such as https://cloud.google.com/kms/ to help keep secrets safe.
        private val INSTANCE_CONNECTION_NAME = "ably-assignment"
        private val DB_USER = "root"
        private val DB_PASS = "!tlstmdgur11"
        private val DB_NAME = "ably"
    }
}