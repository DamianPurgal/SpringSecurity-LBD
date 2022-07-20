package lbd.fissst.securitylbd.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public String getUser(){
        return "This is user!";
    }

    @PutMapping
    public String updateUser(){
        return "User updated!";
    }
}
