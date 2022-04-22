package com.rodolfozamora.webservice.service.interfaces;

import com.rodolfozamora.webservice.model.Contact;

import java.util.List;

public interface ContactService {
    void saveContact(Long userId, Contact contact);

    List<Contact> getAllContacts(Long userId);

    Contact getContactById(Long userId, Long id);

    void updateContact(Long userId, Contact contact);

    void deleteContact(Long userId, Long id);
}
