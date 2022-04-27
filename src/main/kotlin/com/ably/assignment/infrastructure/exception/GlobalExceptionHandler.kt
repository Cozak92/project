package com.ably.assignment.infrastructure.exception

import com.ably.assignment.infrastructure.exception.model.ErrorResponse
import com.ably.assignment.infrastructure.exception.model.Error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest


@RestControllerAdvice
class GlobalExceptionHandler {
    private fun makeErrorResponse(errors: MutableList<Error>, request: HttpServletRequest): ErrorResponse =
        ErrorResponse().apply {
            resultCode = "FAIL"
            httpStatus = HttpStatus.BAD_REQUEST.value().toString()
            httpMethod = request.method
            message = "요청에 에러가 발생하였습니다."
            path = request.requestURI.toString()
            timestamp = LocalDateTime.now()
            this.errors = errors
        }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()
        e.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                field = (errorObject as FieldError).field
                message = errorObject.defaultMessage
                value = errorObject.rejectedValue
            }
            errors.add(error)
        }
        val errorResponse = makeErrorResponse(errors, request)


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class,
        IllegalAccessException::class,
        IllegalStateException::class,
        IllegalArgumentException::class)
    fun exceptionHandler(
        e: HttpMessageNotReadableException,
        request: HttpServletRequest,
    ): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()

        val error = Error().apply {
            message = e.message
        }
        errors.add(error)

        val errorResponse = makeErrorResponse(errors, request)


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)

    }


}