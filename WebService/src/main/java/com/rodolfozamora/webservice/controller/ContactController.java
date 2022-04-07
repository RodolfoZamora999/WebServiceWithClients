package com.rodolfozamora.webservice.controller;

import com.rodolfozamora.webservice.model.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/{user}/contact")
public class ContactController {

    public ContactController() {

    }

    @GetMapping
    public ResponseEntity<List<Contact>> getContacts(@PathVariable("user") Long id) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable("user") Long idUser, @PathVariable("id") Long id) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<Contact> postContact(@PathVariable("user") Long idUser, @RequestBody Contact contact) {
        return ResponseEntity.ok(null);
    }

    @PutMapping
    public ResponseEntity<Contact> updateContact(@PathVariable("user") Long idUser, @RequestBody Contact contact) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable("user") Long idUser, @PathVariable("id") Long id) {
        return ResponseEntity.ok(null);
    }
}
