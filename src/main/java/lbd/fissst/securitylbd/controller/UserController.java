package lbd.fissst.securitylbd.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    @Operation(summary = "Get user information", description = "get user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin')")
    public String getUser(){
        return "This is user!";
    }

    @PutMapping
    @Operation(summary = "Update user account", description = "Update user")
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyAuthority('user:edit', 'admin')")
    public String updateUser(){
        return "User updated!";
    }
}
