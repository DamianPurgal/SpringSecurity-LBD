package lbd.fissst.securitylbd.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getAdmin(){
        return "This is admin!";
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createUser(){
        return "User created!";
    }

    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteUser(){
        return "User deleted!";
    }
}
