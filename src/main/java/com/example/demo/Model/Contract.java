package com.example.demo.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contractId;


    @NotEmpty(message = "Cannot be Empty")
    private String ContractDocumentationPath;

    @NotEmpty(message = "Agree Cost Cannot be Empty")
    private Integer agreeCost;

    @NotEmpty(message = "using Years Cannot be Empty")
    private Integer usingYears;

    @NotEmpty(message = "start Date Cannot be Empty")
    private LocalDate startDate;

    @NotEmpty(message = "end Date Cannot be Empty")
    private LocalDate endDate;

    private LocalDate paymentDate;

    private LocalDateTime ContractDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private Owner owner;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Offer offer;

    @ManyToOne
    @JoinColumn(name ="investor_id")
    private Investor investor;


}
