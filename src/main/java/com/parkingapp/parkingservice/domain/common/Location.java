package com.parkingapp.parkingservice.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Location {
    private BigDecimal latitude;
    private BigDecimal longitude;
}
