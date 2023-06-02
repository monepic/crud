package com.monepic.crud.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("validation")
public class ValidationController {


    @PostMapping("/email")
    public boolean isEmailValid(@RequestParam("email") String email) {
        return email.length() % 2 == 0;
    }
}
