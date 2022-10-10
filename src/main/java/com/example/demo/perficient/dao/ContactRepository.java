package com.example.demo.perficient.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
 
import com.example.demo.perficient.dto.Contact;
 
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByFirstName(String name);
 
}
