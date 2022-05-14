package com.example.security.services;

import com.example.security.interfaces.IAppUser;
import com.example.security.models.AppUser;
import com.example.security.models.Role;
import com.example.security.repository.IRoleRepository;
import com.example.security.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AppUserService implements IAppUser, UserDetailsService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving user: " + user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role: " + role.getRoleName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String roleName) {
        Optional<AppUser> currentUser = Optional.ofNullable(userRepository.findByEmail(email));
        Role role = roleRepository.findByRoleName(roleName);
        currentUser.get().getRoles().add(role);
    }

    @Override
    public Optional<AppUser> findUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    @Override
    public Page<AppUser> findAllUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser currentUser = userRepository.findByEmail(email);
        if (currentUser == null) {
            log.error("User not found: " + email);
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        currentUser.getRoles().forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
                });
        return new User(currentUser.getEmail(), currentUser.getPassword(), authorities);
    }
}
