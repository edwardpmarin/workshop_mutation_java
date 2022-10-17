package com.example.demo.perficient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.example.demo.perficient.api.ContactsApi;
import com.example.demo.perficient.dto.Contact;
import com.example.demo.perficient.service.ContactService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Random;

@SpringBootTest
class ContactsApiTestsMockitoShould {

    @InjectMocks
    private ContactsApi contactController;

    @Mock
    private ContactService contactService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testSaveContact() {
        // Given
        Contact contact_response = new Contact(1L, "Dairo", "Quintero", "", "dairo.ead@mail.com");
        Contact contact_request = new Contact("Dairo", "Quintero", "+5674667", "testmario@gmail.com");
        when(contactService.save(any(Contact.class))).thenReturn(contact_response);
        
        // When
        Contact contact_validate = contactController.save(contact_request);
        
        //Then
        // Pending to review
        verify(contactService).save(contact_request);
        assertNotNull(contact_validate);
        assertEquals(contact_response, contact_validate);

    }
}
