package com.absbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.type.UUIDBinaryType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "uuid"
    )
    @Column(updatable = false, nullable = false,
            unique = true,
            columnDefinition = "VARCHAR(32)")
    @JsonIgnore
    private String id;

    @Column(name = "nuban")
    @NotEmpty
    private String nuban;

    @Column(name = "serial_number")
    @NotNull
    private String serial_number;

    @Column(name = "bank_code")
    @NotNull
    private String bank_code;

    @Column(name = "bank")
    @NotEmpty
    private String bank;
}
