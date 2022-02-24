package me.baek.study.controller.get

import me.baek.study.controller.model.http.UserRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/api")
@RestController
class GetApiController {

    @GetMapping("/hello")
    fun hello() : ResponseEntity<String> {
        return ResponseEntity.ok("success")
    }

    @RequestMapping(value = ["/request-mapping"], method = [RequestMethod.GET])
    fun requestMapping(): String {
        return "request-Mapping"
    }

    @GetMapping("/get-mapping/path-variable/{name}")
    fun pathVariable(@PathVariable name: String): String {
        return name
    }

    // @PathVariable 속성 value, name 동일
    @GetMapping("/get-mapping/path-variable/{name}/{age}")
    fun pathVariable2(@PathVariable(value = "name") _name: String, @PathVariable(name = "age") age: Int): String {
        val name = "kotlin"
        return _name + " " + age
    }

    @GetMapping("/get-mapping/query-param")
    fun queryParam(
            @RequestParam name: String,
            @RequestParam age: Int
    ): String {
        return name + " " + age;
    }

    @GetMapping("/get-mapping/query-param/object")
    fun queryParamObject(userRequest: UserRequest): UserRequest {
        println(userRequest)
        return userRequest
    }

    @GetMapping("/get-mapping/query-param/map")
    fun queryParamMap(@RequestParam map: Map<String, Any>): Map<String, Any> {
        return map
    }
}