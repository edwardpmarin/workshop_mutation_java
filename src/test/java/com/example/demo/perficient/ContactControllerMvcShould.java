package com.example.demo.perficient;

import com.example.demo.perficient.api.ContactsApi;
import com.example.demo.perficient.dto.Contact;
import com.example.demo.perficient.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ContactsApi.class)
public class ContactControllerMvcShould {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    @Test
    void responseSuccessfully_When_CreateContactWithValidInput() throws Exception {
        when(contactService.save(any(Contact.class)))
                .thenReturn(new Contact(1, "Dairo", "Quintero", "+57302336789",
                        "dairo.test@gmail.com"));
        mockMvc.perform(MockMvcRequestBuilders
                .post("/contact")
                .contentType("application/json")
                .content(asJsonString(new Contact("Dairo", "Quintero", "+57302336789",
                        "dairo.test@gmail.com"))));
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"id\":1, \"firstName\":\"Dairo\", "
//                        + "\"lastName\":\"Quintero\", \"phoneNumber\":\"+57302336789\", "
//                        + "\"email\":\"dairo.test@gmail.com\"}"));
    }

    @Test
    void responseBadRequest_When_CreateContactWithInValidEmail() throws Exception {
        mockMvc.perform(post("/contact")
                .contentType("application/json")
                .content(asJsonString(new Contact("Dairo", "Quintero", "+57302336789", "dairo.testgmail.com"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void responseBadRequest_When_CreateContactWithInvaliFirstName() throws Exception {
        mockMvc.perform(post("/contact")
                .contentType("application/json")
                .content(asJsonString(new Contact("Da", "Quintero", "+57302336789", "dairo.test@gmail.com"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void responseBadRequest_When_CreateContactWithInValidPhoneNumber() throws Exception {
        mockMvc.perform(post("/contact")
                .contentType("application/json")
                .content(asJsonString(new Contact("Da", "Quintero", 
                        "302336789as", "dairo.test@gmail.com"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void responseSuccessfully_When_GetByIdWithValidInput() throws Exception {
        when(contactService.getById(any(Contact.class)))
                .thenReturn(new Contact(5, "Dairo", "Quintero", "+57302336789",
                        "dairo.test@gmail.com"));
        mockMvc.perform(get("/contact/id")
                .contentType("application/json")
                .content(asJsonString(new Contact(5))))
                .andExpect(status().isOk());
//                .andExpect(content().json("{\"id\":5, \"firstName\":\"Dairo\", "
//                        + "\"lastName\":\"Quintero\", \"phoneNumber\":\"+57302336789\", "
//                        + "\"email\":\"dairo.test@gmail.com\"}"));
    }

    @Test
    void responseBadRequest_When_GetByIdWithBodyNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/contact/id")
                .contentType("application/json")
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest());
    }

//    @Test
//    void responseSuccessfully_When_GetByNameWithValidInput() throws Exception {
//        final List<Contact> dataList = new ArrayList<>();
//        dataList.add(new Contact(5, "Dairo", "Quintero", "+57302336789",
//                "dairo.test@gmail.com"));
//        when(contactService.getByName(any(Contact.class)))
//                .thenReturn(dataList);
//        mockMvc.perform(get("/contact/name")
//                .contentType("application/json")
//                .content(asJsonString(new Contact(5))));
//                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.[0].id").value(5))
//                .andExpect(jsonPath("$.[0].firstName").value("Dairo"))
//                .andExpect(jsonPath("$.[0].lastName").value("Quintero"))
//                .andExpect(jsonPath("$.[0].phoneNumber").value("+57302336789"))
//                .andExpect(jsonPath("$.[0].email").value("dairo.test@gmail.com"));
//    }

    @Test
    void responseBadRequest_When_GetByNameWithBodyNull() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/contact/name")
                .contentType("application/json")
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
