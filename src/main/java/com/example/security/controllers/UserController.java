package com.example.security.controllers;


import com.example.security.dto.RoleToUserDTO;
import com.example.security.models.AppUser;
import com.example.security.models.Role;
import com.example.security.services.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(Pageable page) {
        return ResponseEntity.ok(userService.findAllUsers(page));
    }

    @PostMapping("/user/save")
    public ResponseEntity<?> createUser(@RequestBody AppUser user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("role/save")
    public ResponseEntity<?> createRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/toUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserDTO roleUser){
        userService.addRoleToUser(roleUser.getEmail(), roleUser.getRoleName());
        return ResponseEntity.ok().build();
    }
}

