package com.absbank.service;

import com.absbank.dao.CustomerRepository;
import com.absbank.entity.Customer;
import com.absbank.model.CustomerObj;
import com.absbank.model.CustomerResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService creatorService = new CustomerServiceImpl();

    @DisplayName("A test to generate a new Nigerian Account Number")
    @Test
    void test_ToCreateA_NewCustomer_NUBAN() {
        //Mocking
        Customer creator = getMockCustomer();
        CustomerObj user = getUser();

        //Actual
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(creator);

        //Verification
        CustomerResponse userRes = creatorService.createCustomer(user);
        verify(customerRepository, times(1))
                .save(any());

        //Assert
        assertEquals(creator.getNuban(), userRes.getNuban());
    }

    private Customer getMockCustomer() {
        return Customer.builder()
                .id("1")
                .nuban("0088776692")
                .serial_number("8877669")
                .bank_code("050")
                .bank("ECOBANK NIGERIA")
                .build();
    }

    private CustomerObj getUser() {
        return CustomerObj.builder()
                .serial_number("8877669")
                .bank_code("050")
                .build();
    }
}