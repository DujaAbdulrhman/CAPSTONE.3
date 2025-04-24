package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Property {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column
    @NotEmpty(message = "title should not be empty")
    private String title;


    @NotEmpty(message = "description should not be empty")
    @Column
    private String description;

    @Column
    @NotEmpty(message = "type should not be empty")
    private String type;

    @NotEmpty(message = "location should not be empty")
    @Column
    private String location;


    @NotNull(message = "area size should not be empty")
    @Column
    private Double areaSize;

    //عشان نحسب متوسط سعر للعقار بحيث ماينزل السعر عنه بالعروض
    private Double propertyValue;

    @Column
    private String status;

    @Column
    @NotEmpty
    //@Pattern(regexp = "^S\\d{10}$")
    private String serialNumber;

    //اضافات عشان الايند بوينت
    @Column
    private Double annualRent =0.0 ;
    @Column(name = "accepting_offers")
    private boolean acceptingOffers;  // هذه ستكون مشكلة إذا كانت القيمة null
    @Column(name = "lease_end_date")
    private LocalDate leaseEndDate;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "property")
    private Set<Offer> offer;

    @ManyToOne
    @JsonIgnore
    @JoinColumn
    private Owner owner;



}
