package ro.msg.learning.shop.controllers;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.services.TestService;

@RestController
@Profile("test")
@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/test")
public class TestController {
    private final TestService testService;

    @GetMapping(value = "/populate")
    public void populate() {
        testService.populate();
    }

    @GetMapping(value = "/deleteAll")
    public void deleteAll() {
        testService.deleteAll();

    }
}
