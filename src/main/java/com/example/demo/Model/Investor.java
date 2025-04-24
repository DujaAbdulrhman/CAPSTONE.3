package com.example.demo.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Investor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name cant be empty")
    @Size(min = 5,message = "the name length should be 5 letters at lest ")
    private String name;

    @Column(unique = true)
    @Email
    private String email;

    @Column
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "password must contain letters, digits and special characters")
    @NotEmpty(message = "password cant be empty")
    private String password;

    @Column
    @NotEmpty(message = "phone number is required")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with 0 and be exactly 10 digits")
    private String phone_number;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "investor")
    private Set<Offer> offer;


    @ManyToOne
    @JoinColumn(name = "contract_contract_id")
    private Contract contract;

}
