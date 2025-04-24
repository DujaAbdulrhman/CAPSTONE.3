package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull @Column
    private Integer proposedCost;

    @Column @NotNull
    private Integer proposedYears;

    @NotEmpty @Column
    private String additionalTerm;

    @Column
    @Pattern(regexp = "Acceptied|Unacceeptable")
    private String offerStatus;

    @Column @CreatedDate
    private LocalDate orderDate;

    private LocalDateTime lastOfferTime;







    @OneToOne
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "offer_id",referencedColumnName = "id")
    @JsonIgnore
    private Property property;



    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Owner owner;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Investor investor;





}
