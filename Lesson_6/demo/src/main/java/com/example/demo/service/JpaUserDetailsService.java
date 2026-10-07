package com.example.demo.service;

import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final AppUserRepository users;

    public JpaUserDetailsService(AppUserRepository users) {
        this.users = users;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = users.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Mappa roller -> authorities
        var authorities = u.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority(r.getName())) // t.ex. "ROLE_ADMIN"
                .collect(Collectors.toSet());

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getUsername())
                .password(u.getPassword()) // måste vara BCRYPT-hash
                .authorities(authorities)
                .accountExpired(!u.isAccountNonExpired())
                .accountLocked(!u.isAccountNonLocked())
                .credentialsExpired(!u.isCredentialsNonExpired())
                .disabled(!u.isEnabled())
                .build();
    }
}