package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

//تفضيلات المستثمر
//البتول
public class InvestorPreferencesDTO {

    private List<String> preferredTypes;
    private List<String> preferredLocations;
    private List<Double> preferredAreas;

}
