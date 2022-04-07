package com.rodolfozamora.webservice.service;

import com.rodolfozamora.webservice.model.Contact;
import com.rodolfozamora.webservice.model.User;

import java.util.List;

public interface ContactService {
    boolean saveContact(Long userId, Contact contact);

    List<Contact> getAllContacts(Long userId);

    User getContactById(Long userId, Long id);

    boolean updateContact(Long userId, Contact contact);

    boolean deleteContact(Long userId, Long id);
}
