package com.example.demo.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerOfferCountDTO {
    private Integer ownerId;
    private String ownerName;
    private Integer numberOfOffers;
}
