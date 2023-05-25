package com.absbank.service;

import com.absbank.constant.BankList;
import com.absbank.dao.CustomerRepository;
import com.absbank.entity.Customer;
import com.absbank.exception.CustomerException;
import com.absbank.model.CustomerObj;
import com.absbank.model.CustomerResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@Log4j2
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private static final int[] seed = {3, 7, 3, 3, 7, 3, 3, 7, 3, 3, 7, 3, 3, 7, 3};
    private static final int serialNumLength = 9;

    @Override
    public CustomerResponse createCustomer(CustomerObj customerObj) {
        log.info("Computation Started");
        Map<String, String> banks = BankList.banks();
        log.info(banks);

        if (!customerObj.getSerial_number().matches("^\\d*$")
                || !customerObj.getBank_code().matches("^\\d*$")) {
            throw new CustomerException("Values must contain only digits",
                    "BAD_REQUEST", 400);
        }

        customerObj.setBank_code(String.format("%03d", Integer.parseInt(customerObj.getBank_code())));

        log.info(customerObj);
        String bank = Optional.of(banks.get(customerObj.getBank_code()))
                .orElseThrow(() -> new CustomerException("Code not recognized as a Nigerian commercial bank code",
                        "BAD_REQUEST", 400));

        log.info("Bank: " + bank);
        Long nuban_long = createNubanWithSerial(customerObj.getSerial_number(), customerObj.getBank_code());
        String nuban = String.format("%010d", nuban_long);

        Customer customer = Customer.builder()
                .nuban(nuban)
                .serial_number(customerObj.getSerial_number())
                .bank_code(customerObj.getBank_code())
                .bank(bank)
                .build();

        customerRepository.save(customer);

        log.info("Customer: " + customer);
        return CustomerResponse.builder()
                .nuban(customer.getNuban())
                .serial_number(customer.getSerial_number())
                .bank_code(customer.getBank_code())
                .bank(customer.getBank())
                .build();
    }

    private Long createNubanWithSerial(String serialNumber, String bank_code) {
        String nuban = serialNumber + checkDigit(serialNumber, bank_code);
        return Long.parseLong(nuban);
    }

    /*
        Checking the last digit for authenticity
     */
    private int checkDigit(String serialNumber, String bankCode) {
        if (serialNumber.length() > serialNumLength) {
            throw new CustomerException("Serial number should be at most " + serialNumLength + " digits long",
                    "BAD_REQUEST", 400);
        }

        serialNumber = String.format("%09d", Integer.parseInt(serialNumber));
        bankCode = bankCode.length() == 5
                ? String.format("9%d", Integer.parseInt(bankCode))
                : String.format("%06d", Integer.parseInt(bankCode));

        log.info(bankCode + " : " + serialNumber);

        String cipher_str = bankCode + serialNumber;
        String[] cipher = cipher_str.split("");

        log.info(cipher_str + " : Evaluation the cipher: " + Arrays.toString(cipher));
        int[] cipher_int = Arrays.stream(cipher)
                .mapToInt(Integer::parseInt)
                .toArray();

        int sum = 0;
        for (int i = 0; i < seed.length; i++) {
            sum += cipher_int[i] * seed[i];
        }

        log.info("SUM: " + sum);
        sum %= 10;
        int check = 10 - sum;

        log.info("Returning the digit: " + check);
        return check == 10 ? 0 : check;
    }
}
