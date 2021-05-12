package com.api.aggregation.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class ShipmentNumber {

    @Pattern(message="invalid shipment number",regexp = "^\\\\d{9}$")
    private int shipmentNumber;
}
