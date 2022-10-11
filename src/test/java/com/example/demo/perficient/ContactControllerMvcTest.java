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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(post("/contact")
                .contentType("application/json")
                .content(asJsonString(new Contact("Dairo", "Quintero", "+57302336789", "dairo.test@gmail.com"))))
                .andExpect(status().isOk());
    }

    @Test
    void whenInValidEmail_thenReturns400() throws Exception {
        mockMvc.perform(post("/contact")
                .contentType("application/json")
                .content(asJsonString(new Contact("Dairo", "Quintero", "+57302336789", "dairo.testgmail.com"))))
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
