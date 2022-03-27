package com.rodolfozamora.webservice.service;

import com.rodolfozamora.webservice.data.UserRepository;
import com.rodolfozamora.webservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryUserService implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemoryUserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean saveUser(User user) {
        //Encode password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.repository.saveUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.repository.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return this.repository.getUserById(id);
    }

    @Override
    public User getUserByUserName(String userName) {
        return this.repository.getUserByUserName(userName);
    }

    @Override
    public User getUserByEmail(String email) {
        return this.repository.getUserByEmail(email);
    }

    @Override
    public boolean deleteUser(Long id) {
        return this.repository.deleteUser(id);
    }

    @Override
    public boolean updateUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.repository.updateUser(user);
    }
}
