package com.example.security.interfaces;

import com.example.security.models.AppUser;
import com.example.security.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IAppUser {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    Optional<AppUser> findUserByEmail(String email);
    Page<AppUser> findAllUsers(Pageable page);
}
