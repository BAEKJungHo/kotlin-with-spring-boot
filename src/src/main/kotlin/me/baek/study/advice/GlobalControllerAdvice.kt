package me.baek.study.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// 특정 패키지에 적용 : @RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])
@RestControllerAdvice
class GlobalControllerAdvice {

    @ExceptionHandler(value = [IndexOutOfBoundsException::class])
    fun indexOutOfBounds(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Index Out Of Bounds Error")
    }

    @ExceptionHandler(value = [RuntimeException::class])
    fun exception(e: RuntimeException): String {
        return "Server Error"
    }
}