package com.api.aggregation;

import com.api.aggregation.domain.ApiResponse;
import com.api.aggregation.properties.WebServiceConfigProperties;
import com.api.aggregation.service.AggregationService;
import com.api.aggregation.validators.ValidCountryCode;
import com.api.aggregation.validators.ValidShipmentTrackNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Validated
public class Controller {

    @Autowired
    private AggregationService aggregationService;

    @GetMapping("/aggregation")
    @ResponseBody
    public Mono<ApiResponse> getAggregatedDataFor(@RequestParam("pricing") @ValidCountryCode List<String> countries,
                                                  @RequestParam("track") @ValidShipmentTrackNumber List<Integer> trackNumbers,
                                                  @RequestParam("shipments") @ValidShipmentTrackNumber List<Integer> shipmentNumbers) {

        return aggregationService.getDataFor(countries,
                trackNumbers,
                shipmentNumbers);
    }
}
