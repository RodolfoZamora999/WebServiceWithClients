package com.rodolfozamora.webservice.data;

import com.rodolfozamora.webservice.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryUserRepository implements UserRepository {
    private final List<User> repo = new ArrayList<>();

    @Override
    public boolean saveUser(User user) {
        return repo.add(user);
    }

    @Override
    public List<User> getAllUsers() {
        return this.repo;
    }

    @Override
    public User getUserById(Long id) {
        return repo.stream().filter(user -> user.getId().equals(id)).
                findFirst().orElseThrow();
    }

    @Override
    public User getUserByUserName(String userName) {
        return repo.stream().filter(user -> user.getName().equals(userName)).
                findFirst().orElseThrow();
    }

    @Override
    public User getUserByEmail(String email) {
        return repo.stream().filter(user -> user.getEmail().equals(email)).
                findFirst().orElseThrow();
    }

    @Override
    public boolean deleteUser(Long id) {
        return this.repo.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public boolean updateUser(User user) {
        if (this.repo.removeIf(original -> original.getId().equals(user.getId()))) {
            this.repo.add(user);
            return true;
        }
        return false;
    }
}
