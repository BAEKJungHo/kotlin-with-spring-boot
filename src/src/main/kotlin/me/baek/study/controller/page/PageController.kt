package me.baek.study.controller.page

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PageController {

    @GetMapping("/index")
    fun index(): String {
        return "index.html"
    }
}