package me.baek.study.controller.exception

import me.baek.study.controller.model.http.UserRequest
import me.baek.study.error.Error
import me.baek.study.error.ErrorResponse
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Validated
@RequestMapping("/api/exception")
@RestController
class ExceptionApiController {

    @GetMapping("/hello")
    fun hello(): String {
        val list = mutableListOf<String>()
//        val temp = list[0] // IndexOutOfBoundsException
        return "hello"
    }

    @GetMapping
    fun get(
            @NotBlank
            @Size(min = 2, max = 6)
            @RequestParam name: String,

            @Min(10)
            @RequestParam age: Int
    ): String {
        println(name)
        println(age)
        return name + " " + age
    }

    @PostMapping
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        return userRequest
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun methodArgumentNotValidException(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errors = mutableListOf<Error>()
        e.bindingResult.allErrors.forEach { errorObject ->
            val error = Error().apply {
                this.field = (errorObject as FieldError).field
                this.message = errorObject.defaultMessage
                this.value = errorObject.rejectedValue
            }
            errors.add(error)
        }

        // 2. ErrorResponse
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "-10001"
            this.httpStatus = BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI.toString()
            this.timeStamp = LocalDateTime.now()
            this.errors = errors
        }

        // 3. ResponseEntity
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun constraintViolationException(e: ConstraintViolationException, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        // 1. 에러 분석
        val errors = mutableListOf<Error>()

        e.constraintViolations.forEach {
            val field = it.propertyPath.last().name // propertyPath 마지막이 field
            val message = it.message
            val error = Error().apply {
                this.field = field
                this.message = message
                this.value = it.invalidValue
            }
            errors.add(error)
        }

        // 2. ErrorResponse
        val errorResponse = ErrorResponse().apply {
            this.resultCode = "-10001"
            this.httpStatus = BAD_REQUEST.value().toString()
            this.httpMethod = request.method
            this.message = "요청에 에러가 발생하였습니다."
            this.path = request.requestURI.toString()
            this.timeStamp = LocalDateTime.now()
            this.errors = errors
        }

        // 3. ResponseEntity
        return ResponseEntity.status(BAD_REQUEST).body(errorResponse)
    }
}