package com.absbank.service;

import com.absbank.model.CustomerObj;
import com.absbank.model.CustomerResponse;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerObj customerObj);
}
