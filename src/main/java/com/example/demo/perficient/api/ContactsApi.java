package com.example.demo.perficient.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.perficient.dto.Contact;
import com.example.demo.perficient.service.ContactService;

@RestController
public class ContactsApi {
	
	
	@Autowired
	ContactService contactService;
	     
	    @PostMapping(value="/contact")
	    public Contact save(@Validated @RequestBody Contact contact){
	    	
	        return contactService.save(contact);
	    }
	    
	    @GetMapping(value="/contact/id")
	    public Contact getById(@RequestBody Contact contact){
	    	
	        return contactService.getById(contact);
	    }
	    
	    @GetMapping(value="/contact/name")
	    public List<Contact> getByName(@RequestBody Contact contact){
	    	
	        return contactService.getByName(contact);
	    }

}
