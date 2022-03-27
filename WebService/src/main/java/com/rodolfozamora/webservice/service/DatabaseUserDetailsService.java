package com.rodolfozamora.webservice.service;

import com.rodolfozamora.webservice.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public DatabaseUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userRepo = userService.getUserByUserName(username);
        return User.withUsername(userRepo.getName()).
                password(userRepo.getPassword()).roles(userRepo.getRoles().toArray(new String[0])).build();
    }
}
