package me.baek.study.controller.model.http

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class UserResponse(
        var result: Result? = null,
        var description: String? = null,

//        @JsonProperty("user")
        var user: MutableList<UserRequest>? = null,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Result(
        var resultCode:String?=null,
        var resultMessage:String?=null,
)

/*
{
    "result" : {
        "result_code" : "OK",
        "result_message" : "성공"   // Snake case
    },
    "description" : "~~~~~~",
    "user" : [
        {
            "name" : "1",
            "age" : "1",
            "email" : "1",
            "phoneNumber" : ""  // Camel case
        },
        {
            "name" : "2",
            "age" : "2",
            "email" : "2",
            "phoneNumber" : ""
        }
    ]
}
*/