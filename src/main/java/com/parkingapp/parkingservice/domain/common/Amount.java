package com.parkingapp.parkingservice.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.money.CurrencyUnit;

@Data
@AllArgsConstructor
public class Amount {
    CurrencyUnit currency;
    int cents;
}
