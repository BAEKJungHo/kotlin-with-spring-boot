package me.baek.study.controller.put

import me.baek.study.controller.model.http.Result
import me.baek.study.controller.model.http.UserRequest
import me.baek.study.controller.model.http.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RequestMapping("/api")
@RestController
class PutApiController {

    @PutMapping("/put-mapping")
    fun postMapping(): String {
        return "put-mapping"
    }

    @RequestMapping(path = ["/request-mapping"], method = [RequestMethod.PUT])
    fun requestMapping(): String {
        return "request-mapping - put - method"
    }

    @PutMapping("/put-mapping/object")
    fun putMappingObject(
            @Valid @RequestBody userRequest : UserRequest,
            bindingResult: BindingResult
    ): ResponseEntity<String> {
        if(bindingResult.hasErrors()) {
            // 500 Error
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                val field = it as FieldError
                val message = it.defaultMessage
                msg.append(field.field).append(message).append("\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }
        return ResponseEntity.ok("OK")
        
        // apply pattern 사용 -> 자기 자신 리턴
/*
        // 1. UserResponse
        return UserResponse().apply {
            // 2. result
            this.result = Result().apply {
                this.resultCode = "OK"
                this.resultMessage = "성공"
            }
        }.apply {
            // 3. description
            this.description = "~~~~~";
        } .apply {
            // 4. user mutable list
            val userList = mutableListOf<UserRequest>()
            userList.add(userRequest)

            userList.add(UserRequest().apply {
                this.name = "a"
                this.age = 10
                this.email = "a@gmail.com"
                this.address = "a address"
                this.phoneNumber = "010-1234-5678"
            })

            userList.add(UserRequest().apply {
                this.name = "a"
                this.age = 20
                this.email = "a@gmail.com"
                this.address = "a address"
                this.phoneNumber = "010-1111-2222"
            })

            this.user = userList
        }
 */
    }
}