package com.absbank.controller;

import com.absbank.model.CustomerObj;
import com.absbank.model.CustomerResponse;
import com.absbank.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@Log4j2
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Operation(
            description = "Create customer method",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Create a new Bank Account Number",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "code: 201, status: success"
                                            )
                                    }
                            )
                    )
            }
    )
    @PostMapping("/customer")
    public ResponseEntity<CustomerResponse> createCustomer(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                    mediaType = "application/json",
                    examples = {
                            @ExampleObject(
                                    value = "serial_number: 89776688, bank_code: 050"
                            ),
                    })
    ) @Validated @RequestBody CustomerObj customerObj){
        log.info("Sending details for computation");
        CustomerResponse res = customerService.createCustomer(customerObj);

        log.info("NUBAN created");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public String hello(){
        return "Hello World!";
    }
}
