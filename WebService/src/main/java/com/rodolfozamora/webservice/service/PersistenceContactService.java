package com.rodolfozamora.webservice.service;

import com.rodolfozamora.webservice.model.Contact;
import com.rodolfozamora.webservice.repository.ContactRepository;
import com.rodolfozamora.webservice.repository.UserRepository;
import com.rodolfozamora.webservice.service.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersistenceContactService implements ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public PersistenceContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveContact(Long userId, Contact contact) {
        var user = this.userRepository.findById(userId).orElseThrow();
        contact.setUser(user);
        this.contactRepository.save(contact);
    }

    @Override
    public List<Contact> getAllContacts(Long userId) {
        var user = this.userRepository.findById(userId).orElseThrow();
        return this.contactRepository.findAllByUser(user).orElseThrow();
    }

    @Override
    public Contact getContactById(Long userId, Long id) {
        var user = this.userRepository.findById(userId).orElseThrow();
        return this.contactRepository.findByUserAndId(user, id).orElseThrow();
    }

    @Override
    public void updateContact(Long userId, Contact contact) {
        var user = this.userRepository.findById(userId).orElseThrow();
        this.contactRepository.findByUserAndId(user, contact.getId()).orElseThrow();
        contact.setUser(user);
        this.contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Long userId, Long id) {
        var user = this.userRepository.findById(userId).orElseThrow();
        var contact = this.contactRepository.findByUserAndId(user, id).orElseThrow();
        this.contactRepository.delete(contact);
    }
}
