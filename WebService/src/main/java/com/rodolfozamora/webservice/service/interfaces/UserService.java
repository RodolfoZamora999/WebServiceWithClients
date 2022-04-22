package com.rodolfozamora.webservice.service.interfaces;

import com.rodolfozamora.webservice.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    void deleteUser(Long id);

    void updateUser(User user);
}
