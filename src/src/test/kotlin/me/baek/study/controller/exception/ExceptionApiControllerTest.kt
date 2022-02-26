package me.baek.study.controller.exception

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import me.baek.study.controller.model.http.UserRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap

// SpringBoot 의 모든 기능을 가져올 필요가 없음
// MVC 관련 기능만 가져오게 설정
@AutoConfigureMockMvc
@WebMvcTest
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun helloTest1() {
        mockMvc.perform(
                get("/api/exception/hello")
        ).andExpect (
            status().isOk
        ).andExpect (
            content().string("hello")
        ).andDo(print())
    }

    @Test
    fun helloTest2() {
        mockMvc.perform(
                get("/api/exception/hello")
        ).andExpect (
            status().`is`(200)
        ).andExpect {
            content().string("hello")
        }.andDo(print())
    }

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "baekjh")
        queryParams.add("age", "20")

        mockMvc.perform(
                get("/api/exception").queryParams(queryParams)
        ).andExpect (
                status().isOk
        ).andExpect (
                content().string("baekjh 20")
        ).andDo(print())
    }

    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "baekjh")
        queryParams.add("age", "9")

        mockMvc.perform(
                get("/api/exception").queryParams(queryParams)
        ).andExpect (
                status().isBadRequest
        ).andExpect (
                content().contentType("application/json")
        ).andExpect(
                jsonPath("\$.result_code").value("-10001")
        ).andExpect(
                jsonPath("\$.errors[0].field").value("age")
        ).andDo(print())
    }

    @Test
    fun postTest() {
        val userRequest = UserRequest().apply {
            this.name = "baekjh"
            this.age = 29
            this.phoneNumber = "010-1234-5678"
            this.address = "서울특별시"
            this.email = "qjxjfld13@gmail.com"
            this.createdAt = "2022-02-26 13:00:00"
        }

        val json = jacksonObjectMapper().writeValueAsString(userRequest)
        println(json)

        mockMvc.perform(
                post("/api/exception")
                        .content(json)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
        ).andExpect(
                status().isOk
        ).andExpect(
                jsonPath("\$.name").value("baekjh")
        ).andExpect(
                jsonPath("\$.age").value("29")
        ).andExpect(
                jsonPath("\$.phoneNumber").value("010-1234-5678")
        ).andExpect(
                jsonPath("\$.address").value("서울특별시")
        ).andExpect(
                jsonPath("\$.email").value("qjxjfld13@gmail.com")
        ).andDo(print())
    }
}