package com.absbank.controller;

import com.absbank.entity.Customer;
import com.absbank.model.CustomerObj;
import com.absbank.model.CustomerResponse;
import com.absbank.service.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest({"server.port=0"})
@EnableConfigurationProperties
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @RegisterExtension
    final static WireMockExtension wireMockServer
            = WireMockExtension.newInstance()
            .options(WireMockConfiguration
                    .wireMockConfig()
                    .port(8080))
            .build();

    private final ObjectMapper objectMapper
            = new ObjectMapper()
            .findAndRegisterModules()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @DisplayName("Test when create customer endpoint id called")
    @Test
    void test_createCustomerEndpoint() throws Exception{
        //Mocking
        CustomerObj user = getUser();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/customer")
                        .content(objectMapper.writeValueAsString(user))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = getCreatorsResponse();

        //Assert
        assertEquals(expectedResponse, actualResponse);
    }

    private String getCreatorsResponse() throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                CustomerResponse.builder()
                        .nuban("0088776692")
                        .serial_number("8877669")
                        .bank_code("050")
                        .bank("ECOBANK NIGERIA")
                        .build()
        );
    }

    private CustomerObj getUser() {
        return CustomerObj.builder()
                .serial_number("8877669")
                .bank_code("050")
                .build();
    }
}