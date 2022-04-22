package com.rodolfozamora.webservice.service.interfaces;

import com.rodolfozamora.webservice.model.User;

import java.util.List;

public interface UserService {
    boolean saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    boolean deleteUser(Long id);

    boolean updateUser(User user);
}
