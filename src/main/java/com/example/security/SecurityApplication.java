package com.example.security;

import com.example.security.models.AppUser;
import com.example.security.models.Role;
import com.example.security.services.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runData(AppUserService userService){
        return args -> {
            userService.saveRole(new Role(null, "USER"));
            userService.saveRole(new Role(null, "ADMIN"));
            userService.saveRole(new Role(null, "SuperAdmin"));

            userService.saveUser(new AppUser(null, "John", "john@gmail.com", "john1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Rei", "rei@gmail.com", "rei1234", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Luna", "luna@gmail.com", "luna27", new ArrayList<>()));

            userService.addRoleToUser("rei@gmail.com", "ADMIN");
            userService.addRoleToUser("john@gmail.com", "SuperAdmin");
            userService.addRoleToUser("john@gmail.com", "ADMIN");
            userService.addRoleToUser("luna@gmail.com", "USER");
        };
    }
}
