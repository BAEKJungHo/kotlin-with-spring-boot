package me.baek.study.controller.response

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/response")
@RestController
class ResponseApiController {

    // 1. get 4xx
    @GetMapping
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> {
        age?.let {
            // age not null
            if (it < 20) {
                return ResponseEntity.badRequest().body("age is not null")
            }
        }?: kotlin.run {
            // age is null
            return ResponseEntity.badRequest().body("age more than 20 ")
        }

/*
        if (age == null) {
            return ResponseEntity.badRequest().body("age is not null")
        }

        if (age < 20) {
            return ResponseEntity.badRequest().body("age more than 20 ")
        }
*/

        return ResponseEntity.ok("OK")
    }

    // 2. post 200

    // 3. put 201

    // 4. delete 500
}