package com.example.demo.DTO;

import com.example.demo.Model.Offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestOffersDTO {
    private Offer highestCostOffer;
    private Offer shortestYearsOffer;

    //albatool

    //اقتراح افضل العروض لعقار معين
    private Integer offerId;
    private Double cost;
    private Integer proposedYears;
    private String additionalTerm;
    private String offerStatus;
    private LocalDate orderDate;
    private Integer investorId;
}
