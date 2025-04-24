package com.example.demo.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractExtensionDTO {

    //Albatool

    //المستثمر يرسل طلب تجديد
    private Integer contractId;

    @Positive
    @NotNull
    private Double cost;


    @NotNull(message = "Extra years is required")
    @Min(value = 1, message = "At least 1 year must be added")
    private Integer extraYears;

    @NotNull(message = "Owner approval is required")
    private Boolean ownerApproval;

}
