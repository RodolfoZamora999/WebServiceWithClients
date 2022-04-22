package com.rodolfozamora.webservice.controller;

import com.rodolfozamora.webservice.model.Contact;
import com.rodolfozamora.webservice.service.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/{user}/contact")
public class ContactController {
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Contact>> getContacts(@PathVariable("user") Long userId) {
        return ResponseEntity.ok(this.contactService.getAllContacts(userId));
    }

    @GetMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> getContact(@PathVariable("user") Long userId, @PathVariable("id") Long contactId) {
        return ResponseEntity.ok(this.contactService.getContactById(userId, contactId));
    }

    @PostMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> postContact(@PathVariable("user") Long userId, @RequestBody Contact contact) {
        this.contactService.saveContact(userId, contact);
        return ResponseEntity.ok(contact);
    }

    @PutMapping(consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Contact> updateContact(@PathVariable("user") Long userId, @RequestBody Contact contact) {
        this.contactService.updateContact(userId, contact);
        return ResponseEntity.ok(contact);
    }

    @DeleteMapping(value = "/{id}", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteContact(@PathVariable("user") Long userId, @PathVariable("id") Long contactId) {
        this.contactService.deleteContact(userId, contactId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
