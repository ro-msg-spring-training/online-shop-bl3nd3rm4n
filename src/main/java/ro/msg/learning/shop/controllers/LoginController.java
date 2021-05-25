package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/")
public class LoginController {
    @RequestMapping("login")
    public String login() {
        return "login";
    }
}
