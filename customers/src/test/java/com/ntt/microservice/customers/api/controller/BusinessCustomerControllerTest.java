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
import com.ntt.microservice.customers.api.dto.request.BusinessCustomerRequestDto;
import com.ntt.microservice.customers.api.dto.response.BusinessCustomerResponseDto;
import com.ntt.microservice.customers.service.exception.DifferentDocumentNumberException;
import com.ntt.microservice.customers.service.exception.DocumentNumberAlreadyExistsException;
import com.ntt.microservice.customers.service.handler.BusinessCustomerHandler;
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


@WebMvcTest(BusinessCustomerController.class)
class BusinessCustomerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BusinessCustomerHandler businessCustomerHandler;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testFindAll() throws Exception {
    List<BusinessCustomerResponseDto> response = List.of(new BusinessCustomerResponseDto());
    when(businessCustomerHandler.findAll()).thenReturn(response);

    mockMvc.perform(get("/business/")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testFindById() throws Exception {
    BusinessCustomerResponseDto response = new BusinessCustomerResponseDto();
    String id = "someId";
    when(businessCustomerHandler.findById(id)).thenReturn(response);

    mockMvc.perform(get("/business/" + id)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testFindByDocumentNumber() throws Exception {
    BusinessCustomerResponseDto response = new BusinessCustomerResponseDto();
    String documentNumber = "someDocumentNumber";
    when(businessCustomerHandler.findByDocumentNumber(documentNumber)).thenReturn(response);

    mockMvc.perform(get("/business/documentNumber/" + documentNumber)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testSave() throws Exception {
    BusinessCustomerResponseDto response = new BusinessCustomerResponseDto();
    BusinessCustomerRequestDto requestDto = CustomersUtils.getBusinessCustomerRequest();

    when(businessCustomerHandler.save(any(BusinessCustomerRequestDto.class))).thenReturn(response);

    mockMvc.perform(post("/business/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testUpdate() throws Exception {
    BusinessCustomerResponseDto response = new BusinessCustomerResponseDto();
    BusinessCustomerRequestDto requestDto = CustomersUtils.getBusinessCustomerRequest();
    String id = "someId";
    when(businessCustomerHandler.update(eq(id), any(BusinessCustomerRequestDto.class))).thenReturn(response);

    mockMvc.perform(put("/business/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk());
  }

  @Test
  void testDeleteById() throws Exception {
    String id = "someId";
    doNothing().when(businessCustomerHandler).deleteById(id);

    mockMvc.perform(delete("/business/" + id))
        .andExpect(status().isNoContent());
  }

  @Test
  void testSaveError400() throws Exception {
    BusinessCustomerResponseDto response = new BusinessCustomerResponseDto();
    BusinessCustomerRequestDto requestDto = CustomersUtils.getBusinessCustomerRequestErrors();

    when(businessCustomerHandler.save(any(BusinessCustomerRequestDto.class))).thenReturn(response);

    mockMvc.perform(post("/business/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testSaveErrorAlreadyExists() throws Exception {
    BusinessCustomerRequestDto requestDto = CustomersUtils.getBusinessCustomerRequest();

    when(businessCustomerHandler.save(any(BusinessCustomerRequestDto.class))).thenThrow(new DocumentNumberAlreadyExistsException());

    mockMvc.perform(post("/business/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
  }

  @Test
  void testUpdateError() throws Exception {
    BusinessCustomerRequestDto requestDto = CustomersUtils.getBusinessCustomerRequest();
    String id = "someId";
    when(businessCustomerHandler.update(eq(id), any(BusinessCustomerRequestDto.class))).thenThrow(new DifferentDocumentNumberException());

    mockMvc.perform(put("/business/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isBadRequest());
  }
}
