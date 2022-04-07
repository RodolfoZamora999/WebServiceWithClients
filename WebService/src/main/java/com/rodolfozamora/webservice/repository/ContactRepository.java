package com.rodolfozamora.webservice.repository;

import com.rodolfozamora.webservice.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
