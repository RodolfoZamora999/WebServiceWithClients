package com.rodolfozamora.webservice.repository;

import com.rodolfozamora.webservice.model.Contact;
import com.rodolfozamora.webservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<List<Contact>> findAllByUser(User user);

    Optional<Contact> findByUserAndId(User user, Long id);
}
