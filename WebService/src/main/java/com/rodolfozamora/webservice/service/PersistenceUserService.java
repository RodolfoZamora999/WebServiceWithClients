package com.rodolfozamora.webservice.service;

import com.rodolfozamora.webservice.repository.RoleRepository;
import com.rodolfozamora.webservice.repository.UserRepository;
import com.rodolfozamora.webservice.model.User;
import com.rodolfozamora.webservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceUserService implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersistenceUserService(UserRepository userRepository, RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean saveUser(User user) {
        //Encode password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null)
            roleRepository.findByName("USER").ifPresent(user::setRole);

        this.userRepository.save(user);
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return this.userRepository.findById(id).orElseThrow();
    }

    @Override
    public User getUserByUserName(String userName) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public boolean deleteUser(Long id) {
        this.userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean updateUser(User user) {
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        this.userRepository.save(user);
        return true;
    }
}
