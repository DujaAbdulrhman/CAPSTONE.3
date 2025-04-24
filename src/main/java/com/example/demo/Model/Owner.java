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

import java.util.Set;

@AllArgsConstructor @Setter @Getter @NoArgsConstructor
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message="name cant be empty")
    @Column
    private String name;

    @Column (unique=true)
    @Email
    private String email;

    @NotEmpty(message="password cant be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "password must contain letters, digits and special characters")
    @Column
    private String password;

    @NotEmpty(message="phone number can't be empty")
    @Size(min=9)
    @Column
    private String phone_number;




    @OneToMany(mappedBy = "owner")
    private Set<Property> properties;
}
