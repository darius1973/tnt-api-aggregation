package com.api.aggregation.service;
import com.api.aggregation.domain.*;
import com.api.aggregation.properties.WebServiceConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AggregationService {


    private WebServiceConfigProperties webServiceConfigProperties;

    @Autowired
    private WebClient serviceWebClient;

    public AggregationService(@Autowired WebServiceConfigProperties webServiceConfigProperties) {
        this.webServiceConfigProperties = webServiceConfigProperties;
    }

    public Flux<ApiResponse> getDataFor(List<String> countries, List<Integer> trackNumbers, List<Integer> shipmentNumbers) {
        String urlPricingItems = webServiceConfigProperties.getPricingApi() + String.join(",", countries);
        Flux<Map> callToPricingWebService = fluxCallWebServicePricing(countries, urlPricingItems);
        String urlShipmentItems = webServiceConfigProperties.getShipmentsApi() + trackNumbers.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        Flux<Map> callToShipmentsWebService = fluxCallWebServiceShipTrack(shipmentNumbers,urlShipmentItems);
        String urlTrackItems = webServiceConfigProperties.getTrackApi() + trackNumbers.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        Flux<Map> callToTracksWebService = fluxCallWebServiceShipTrack(trackNumbers, urlTrackItems);


        return Flux.zip(callToPricingWebService, callToShipmentsWebService, callToTracksWebService)
                .flatMap(dFlux ->
                        Flux.just(new ApiResponse(
                                dFlux.getT1(),
                                dFlux.getT2(),
                                dFlux.getT3()
                        ))
                );
    }

    private Flux<Map> fluxCallWebServicePricing(List<String> countries, String url) {
        return Flux.just(countries)
                .take(webServiceConfigProperties.getLimitRequestsParameter())
                .sample(Duration.ofSeconds(5))
                .flatMap(country -> serviceWebClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .onErrorReturn(nullValueForPricing(countries.toString()))

                )
                .log();
    }


    private Flux<Map> fluxCallWebServiceShipTrack(List<Integer> shipTrackNumbers, String url) {
        return Flux.just(shipTrackNumbers)
                .sample(Duration.ofSeconds(5))
                .take(webServiceConfigProperties.getLimitRequestsParameter())
                .flatMap(list -> serviceWebClient.get()
                        .uri(url)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .onErrorReturn(nullValueForShipTrack(String.valueOf(list)))
                )
                .log();
    }

    private Map<String, Double> nullValueForPricing(String value) {
        Map<String, Double> nullValueMap = new HashMap<>();
        nullValueMap.put(value, null);
        return nullValueMap;
    }

    private Map<String, String> nullValueForShipTrack(String value) {
        Map<String, String> nullValueMap = new HashMap<>();
        nullValueMap.put(value, "null");
        return nullValueMap;
    }

}

