package com.example.demo.perficient;

import com.example.demo.perficient.dao.ContactRepository;
import com.example.demo.perficient.dto.Contact;
import com.example.demo.perficient.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ContactDataJpaShould {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    ContactRepository contactRepository;
    private ContactService contactService;

    @BeforeEach
    void setUp() {
        contactService = new ContactService(contactRepository);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @Test
    public void saveTestCorrectValues() {
        Contact contact_request = new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com");
        Contact contact_response = contactService.save(contact_request);
        assertThat(contact_response.getId()).isNotNull();
        assertThat(contact_response.getLastName()).isNotNull();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @Test
    public void getContact_When_GetByIdCorrectValues() {
        // Given
        Contact contact_request_initial = new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com");
        Contact contact_save_response = contactService.save(contact_request_initial);
        
        // When
        Contact contact_response = contactService.getById(new Contact(contact_save_response.getId()));
        
        // Then
        assertThat(contact_response.getId()).isNotNull();
        assertThat(contact_response.getFirstName()).isEqualTo(contact_request_initial.getFirstName());
        assertThat(contact_response.getLastName()).isEqualTo(contact_request_initial.getLastName());
        assertThat(contact_response.getEmail()).isEqualTo(contact_request_initial.getEmail());
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @Test
    public void getContact_When_GetByIdMissindId() {
        // Given
        Contact contact_request_initial = new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com");
        contactService.save(contact_request_initial);

        // When
        Contact contact_request = new Contact("Carlos");

        // Then
        Exception exception = assertThrows(InvalidDataAccessApiUsageException.class, () -> contactService.getById(contact_request));
        assertThat(exception.getMessage()).contains("The given id must not be null!");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @Test
    public void getContact_When_GetByNameCorrectValues() {
        // Given
        Contact contact_request_initial = new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com");
        contactService.save(contact_request_initial);
        Contact contact_request = new Contact("Dairo");

        // When
        List<Contact> contact_response = contactService.getByName(contact_request);

        // Then
        assertThat(contact_response.get(0)).isNotNull();
        assertThat(contact_response.get(0).getFirstName()).isEqualTo(contact_request_initial.getFirstName());
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @Test
    public void getContact_When_GetByNameNotMatchValues() {
        // Given
        Contact contact_request_initial = new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com");
        contactService.save(contact_request_initial);
        Contact contact_request = new Contact("Pedro");

        // When
        List<Contact> contact_response = contactService.getByName(contact_request);

        // Then
        assertTrue(contact_response.isEmpty());
        assertThat(contact_response.size()).isEqualTo(0);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @Test
    public void getContact_When_GetByNameMissingName() {
        // Given
        Contact contact_request_initial = new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com");
        contactService.save(contact_request_initial);
        Contact contact_request = new Contact(5);

        // When
        List<Contact> contact_response = contactService.getByName(contact_request);

        // Then
        assertTrue(contact_response.isEmpty());
        assertThat(contact_response.size()).isEqualTo(0);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @Test
    public void getContact_When_SaveEmailInvalid() {
        // When
        Contact contact_request = new Contact("Dairo", "Quintero", "+57302336789", "dairo.testgmail.com");
        Exception exception = assertThrows(ConstraintViolationException.class, () -> contactService.save(contact_request));
        
        // Then
        assertThat(exception.getMessage()).contains("Email inválido");
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @Test
    public void getContact_When_SaveFirstnameInvalidTestDeep() {
        // Given
        Contact contact_request = new Contact("Da", "Quintero", "+57302336789", "dairo.test@gmail.com");
        
        // When
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> contactService.save(contact_request));
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream()
                //Example to obtain other information
                //.map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(),
                //        constraintViolation.getInvalidValue(), constraintViolation.getMessage()))
                .map(constraintViolation -> String.format("%s", constraintViolation.getMessage())).collect(Collectors.toList()));
        
        // Then
        assertEquals("El nombre debe tener entre 3 y 30 caracteres", messages.iterator().next());

    }

    @ExceptionHandler(ConstraintViolationException.class)
    @Test
    public void getContact_When_SaveFirstnameInvalidTestContain() {
        // When
        Contact contact_request = new Contact("Da", "Quintero", "+57302336789", "dairo.test@gmail.com");
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> contactService.save(contact_request));

        // Then
        assertTrue(exception.getMessage().contains("El nombre debe tener entre 3 y 30 caracteres"));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @Test
    public void getContact_When_SaveNumberInvalid() {
        // Given
        Contact contact_request = new Contact("Dairo", "Quintero", "4566990", "dairo.test@gmail.com");

        // When
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> contactService.save(contact_request));
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        Set<String> messages = new HashSet<>(constraintViolations.size());
        messages.addAll(constraintViolations.stream().map(constraintViolation -> String.format("%s", constraintViolation.getMessage())).collect(Collectors.toList()));
        
        // Then
        assertEquals("El número de telefono sólo puede tener dígitos iniciando con el símbolo +", messages.iterator().next());
    }

}

