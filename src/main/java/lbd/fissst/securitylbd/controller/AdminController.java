package lbd.fissst.securitylbd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    @Operation(summary = "Get administrator information", description = "Get admin")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String getAdmin(){
        return "This is admin!";
    }

    @PostMapping
    @Operation(summary = "Create user account", description = "Create user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String createUser(){
        return "User created!";
    }

    @DeleteMapping
    @Operation(summary = "Delete user account", description = "Delete user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('admin')")
    public String deleteUser(){
        return "User deleted!";
    }
}
