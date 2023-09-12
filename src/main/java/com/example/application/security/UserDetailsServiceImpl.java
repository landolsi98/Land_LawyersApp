package com.example.application.security;

import com.example.application.backend.entity.User;
import com.example.application.backend.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("no user present with this username" + username);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
                   getAuthorities(user));
        }

    }
    private static List<GrantedAuthority> getAuthorities(User user) {
        System.out.println("  msajel wabeni " + user.getAuthority().getRol());
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getAuthority().getRol()));
    }
}

