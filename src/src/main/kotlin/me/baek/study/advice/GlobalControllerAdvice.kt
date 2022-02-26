package me.baek.study.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

// 특정 패키지에 적용 : @RestControllerAdvice(basePackageClasses = [ExceptionApiController::class])
// @RestControllerAdvice
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

/* Error Message Format
{
    "result_code" : "OK",
    "http_status" : "500",
    "message" : "요청이 잘못 되었습니다.",
    "path" : "/api/exception/hello",
    "timestamp" : "2020-10-02T13:00:00",
    "errors" : [
        {
            "field" : "_name",
            "message" : "5글자 이상이어야 합니다."
        }
    ]
}
 */