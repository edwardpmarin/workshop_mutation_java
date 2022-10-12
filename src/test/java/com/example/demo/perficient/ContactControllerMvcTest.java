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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ContactsApi.class)
public class ContactControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContactService contactService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        when(contactService.save(any(Contact.class)))
                .thenReturn(new Contact(1,"Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/contact")
                        .contentType("application/json")
                        .content(asJsonString(new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com"))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber());
    }

    @Test
    void whenInValidEmail_thenReturns400() throws Exception {
        mockMvc.perform(post("/contact")
                        .contentType("application/json")
                        .content(asJsonString(new Contact("Dairo", "Quintero", "+57302336789", "dairo.testgmail.com"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInValiFirstName_thenReturns400() throws Exception {
        mockMvc.perform(post("/contact")
                        .contentType("application/json")
                        .content(asJsonString(new Contact("Da", "Quintero", "+57302336789", "dairo.test@gmail.com"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenInValidPhoneNumber_thenReturns400() throws Exception {
        mockMvc.perform(post("/contact")
                        .contentType("application/json")
                        .content(asJsonString(new Contact("Da", "Quintero", "302336789as", "dairo.test@gmail.com"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenValidEmail_GetByid() throws Exception {

        mockMvc.perform(get("/contact/id")
                        .contentType("application/json")
                        .content(asJsonString(new Contact(5))))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetByNameBodyNull() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact/name")
                        .contentType("application/json")
                        .content(asJsonString(new Contact(567) )))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGetByIdBodyNull() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/contact/id")
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
