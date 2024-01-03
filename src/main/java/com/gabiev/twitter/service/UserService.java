package com.gabiev.twitter.service;

import com.gabiev.twitter.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepo.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("Username %s not found".formatted(username)));
    }
}
