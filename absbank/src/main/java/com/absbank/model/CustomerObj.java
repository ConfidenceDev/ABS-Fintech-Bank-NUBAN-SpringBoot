package com.absbank.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerObj {

    @Size(max = 9)
    @NotBlank(message = "Serial number is required")
    private String serial_number;

    @NotBlank(message = "Bank code is required")
    private String bank_code;
}
