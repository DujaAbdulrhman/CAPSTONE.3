package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PropertyRecommendationDTO {
    //البتول
    //توصيه بالعقارات
    private Integer propertyId;
    private String title;
    private String location;
    private String type;
    private Double areaSize;
    private String status;
}
