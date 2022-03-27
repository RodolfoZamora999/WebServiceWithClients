package com.rodolfozamora.webservice.data;

import com.rodolfozamora.webservice.model.User;

import java.util.List;

public interface UserRepository {

    boolean saveUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    boolean deleteUser(Long id);

    boolean updateUser(User user);
}
