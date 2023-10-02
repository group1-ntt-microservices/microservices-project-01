package com.ntt.microservice.customers.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ntt.microservice.customers.api.dto.request.PersonalCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.PersonalCustomerResponseDto;
import com.ntt.microservice.customers.service.handler.PersonalCustomerHandler;
import com.ntt.microservice.customers.utils.CustomersUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PersonalCustomerController.class)
class PersonalCustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonalCustomerHandler personalCustomerHandler;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() throws Exception {
    List<PersonalCustomerResponseDto> response = List.of(new PersonalCustomerResponseDto());
    when(personalCustomerHandler.findAll()).thenReturn(response);

    mockMvc.perform(get("/personal/")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testFindById() throws Exception {
    PersonalCustomerResponseDto response = new PersonalCustomerResponseDto();
    String id = "someId";
    when(personalCustomerHandler.findById(id)).thenReturn(response);

    mockMvc.perform(get("/personal/" + id)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testFindByDocumentNumber() throws Exception {
    PersonalCustomerResponseDto response = new PersonalCustomerResponseDto();
    String documentNumber = "someDocumentNumber";
    when(personalCustomerHandler.findByDocumentNumber(documentNumber)).thenReturn(response);

    mockMvc.perform(get("/personal/documentNumber/" + documentNumber)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testSave() throws Exception {
    PersonalCustomerResponseDto response = new PersonalCustomerResponseDto();
    PersonalCustomerRequestDto requestDto = CustomersUtils.getPersonalCustomerRequest();

    when(personalCustomerHandler.save(any(PersonalCustomerRequestDto.class))).thenReturn(response);

    mockMvc.perform(post("/personal/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testUpdate() throws Exception {
    PersonalCustomerResponseDto response = new PersonalCustomerResponseDto();
    PersonalCustomerRequestDto requestDto = CustomersUtils.getPersonalCustomerRequest();
    String id = "someId";
    when(personalCustomerHandler.update(eq(id), any(PersonalCustomerRequestDto.class))).thenReturn(response);

    mockMvc.perform(put("/personal/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());
  }

  @Test
  void testDeleteById() throws Exception {
    String id = "someId";
    doNothing().when(personalCustomerHandler).deleteById(id);

    mockMvc.perform(delete("/personal/" + id))
        .andExpect(status().isNoContent());
  }
}
