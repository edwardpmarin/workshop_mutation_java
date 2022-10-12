package com.example.demo.perficient;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
class ContactsApiTestsMockito {

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
        Contact contact_response = new Contact(1L, "Dairo", "Quintero", "", "dairo.ead@mail.com");
        Contact contact_request = new Contact("Dairo", "Quintero", "+5674667", "testmario@gmail.com");
        when(contactService.save(any(Contact.class))).thenReturn(contact_response);
        Contact contact_validate = contactController.save(contact_request);
        //revisar
        verify(contactService).save(contact_request);
        Assert.notNull(contact_validate.getId());
        assertEquals(contact_response, contact_validate);

    }
}
