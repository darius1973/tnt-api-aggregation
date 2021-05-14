package com.api.aggregation.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private Map<String,Double> pricing;
    private Map<String,ArrayList> shipments;
    private Map<String, String> track;
}
