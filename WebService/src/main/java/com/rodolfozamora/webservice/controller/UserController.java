package com.rodolfozamora.webservice.controller;

import com.rodolfozamora.webservice.model.User;
import com.rodolfozamora.webservice.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(this.userService.getUserById(id));
    }

    @PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> postUser(@RequestBody User user) {
        this.userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        this.userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
