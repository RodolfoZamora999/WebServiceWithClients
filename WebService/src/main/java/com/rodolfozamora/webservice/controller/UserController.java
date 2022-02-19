package com.rodolfozamora.webservice.controller;

import com.rodolfozamora.webservice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final List<User> list = new ArrayList<>();

    public UserController() {
        this.list.add(new User(1L, "fake@email.com", "Lynda", "Lagunes"));
        this.list.add(new User(2L, "fake@email.com", "Andrea", "Gimenez"));
        this.list.add(new User(3L, "fake@email.com", "John", "Hollwings"));
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        var user = this.list.stream().filter(us -> us.getId().
                equals(id)).findFirst().orElseThrow();

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody User user) {
        list.add(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        this.list.replaceAll(usr-> {
            if (usr.getId().equals(user.getId()))
                return user;
            return usr;
        });
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        this.list.removeIf(user -> user.getId().equals(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
