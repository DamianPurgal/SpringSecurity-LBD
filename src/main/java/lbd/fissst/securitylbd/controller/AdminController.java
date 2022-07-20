package lbd.fissst.securitylbd.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String getAdmin(){
        return "This is admin!";
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String createUser(){
        return "User created!";
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteUser(){
        return "User deleted!";
    }
}
