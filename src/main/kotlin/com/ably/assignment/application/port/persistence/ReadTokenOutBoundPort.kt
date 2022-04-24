package com.ably.assignment.application.port.persistence

interface ReadTokenOutBoundPort {
    fun sidExists(sid:String): Boolean
}