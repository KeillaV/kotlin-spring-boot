package br.dio.cursos.kotlinspringboot.exception

import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidException(exception: MethodArgumentNotValidException): ResponseEntity<ExceptionDetails> {
        val errors: MutableMap<String, String> = HashMap()
        exception.bindingResult.allErrors.stream().forEach {
            error: ObjectError ->
            val fieldName: String = (error as FieldError).field
            val message: String? = error.defaultMessage
            errors[fieldName] = message!!
        }

        return ResponseEntity(
            ExceptionDetails(
                title = "Validation Error",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value(),
                exception = exception.javaClass.simpleName,
                details = errors
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(DataAccessException::class)
    fun handleDataAccessException(exception: DataAccessException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity(
            ExceptionDetails(
                title = "Data Access Error",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.CONFLICT.value(),
                exception = exception.javaClass.simpleName,
                details = mutableMapOf(exception.cause.toString() to exception.message!!)
            ), HttpStatus.CONFLICT
        )
    }

    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(exception: BusinessException): ResponseEntity<ExceptionDetails> {
        return ResponseEntity(
            ExceptionDetails(
                title = "Business Error",
                timestamp = LocalDateTime.now(),
                status = HttpStatus.BAD_REQUEST.value(),
                exception = exception.javaClass.simpleName,
                details = mutableMapOf(exception.cause.toString() to exception.message!!)
            ), HttpStatus.BAD_REQUEST
        )
    }
}