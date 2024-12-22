package com.inventrymanagement.inventry.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculateBillPrice {
    private Double mrp;
    private Double gstRate;
    public Double calculate(){
        Double originalPrice=mrp;
        return calculateGst(originalPrice, gstRate);
    }
    private Double calculateGst(Double originalPrice,Double gstRate){
        Double gstPrice = (gstRate/100)*originalPrice;
        return gstPrice+originalPrice;
    }
}

