package com.ntt.microservice.customers.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ntt.microservice.customers.api.dto.response.CustomerResponseDto;
import com.ntt.microservice.customers.service.exception.CustomerNotFoundException;
import com.ntt.microservice.customers.service.handler.CustomerHandler;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerHandler customerHandler;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() throws Exception {
    List<CustomerResponseDto> response = List.of(new CustomerResponseDto());
    when(customerHandler.findAll()).thenReturn(response);

    mockMvc.perform(get("/")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testFindById() throws Exception {
    CustomerResponseDto response = new CustomerResponseDto();
    String id = "someId";
    when(customerHandler.findById(id)).thenReturn(response);

    mockMvc.perform(get("/{id}", id)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testFindByDocumentNumber() throws Exception {
    CustomerResponseDto response = new CustomerResponseDto();
    String documentNumber = "someDocumentNumber";
    when(customerHandler.findByDocumentNumber(documentNumber)).thenReturn(response);

    mockMvc.perform(get("/documentNumber/{documentNumber}", documentNumber)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testFindByIdError() throws Exception {
    String id = "someId";
    when(customerHandler.findById(id)).thenThrow(new CustomerNotFoundException());

    mockMvc.perform(get("/{id}", id)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }
}
