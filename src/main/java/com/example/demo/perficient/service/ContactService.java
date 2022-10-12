package com.example.demo.perficient.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.perficient.dao.ContactRepository;
import com.example.demo.perficient.dto.Contact;

@Service
public class ContactService {

    ContactRepository dao;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.dao = contactRepository;
    }


    public Contact save(Contact contact) {

        return dao.saveAndFlush(contact);
    }

    public Contact getById(Contact contact) {
        return dao.getReferenceById(contact.getId());
    }

    public List<Contact> getByName(Contact contact) {
        return dao.findByFirstName(contact.getFirstName());
    }
}
