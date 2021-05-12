package com.api.aggregation.service;
import com.api.aggregation.domain.*;
import com.api.aggregation.properties.WebServiceConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AggregationService {


    private WebServiceConfigProperties webServiceConfigProperties;

    private WebClient serviceWebClient;

    public AggregationService(@Autowired WebServiceConfigProperties webServiceConfigProperties) {
        this.webServiceConfigProperties = webServiceConfigProperties;
    }

    private void setServiceWebClient(WebClient webClient) {
        this.serviceWebClient = webClient;
    }

    public Mono<ApiResponse> getDataFor(List<String> countries, List<Integer> trackNumbers, List<Integer> shipmentNumbers) {
        this.setServiceWebClient(WebClient.create(webServiceConfigProperties.getHost()));
        String urlPricingItems = webServiceConfigProperties.getPricingApi() + String.join(",",countries);
        Mono<Map> callToPricingWebService = callWebService(urlPricingItems);
        String urlShipmentItems = webServiceConfigProperties.getShipmentsApi() + trackNumbers.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        Mono<Map> callToShipmentsWebService = callWebService(urlShipmentItems);
        String urlTrackItems = webServiceConfigProperties.getTrackApi() + trackNumbers.stream().map(i -> i.toString()).collect(Collectors.joining(","));
        Mono<Map>callToTrackWebService = callWebService(urlTrackItems);

        return Mono.zip(callToPricingWebService, callToShipmentsWebService, callToTrackWebService)
                .map(objects -> {
                    ApiResponse apiResponse = new ApiResponse();
                    apiResponse.setPricing(objects.getT1());
                    apiResponse.setShipments(objects.getT2());
                    apiResponse.setTrack(objects.getT3());

                    return apiResponse;
                });
    }

    private Mono callWebService(String url) {
       return serviceWebClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class)
                .log();
    }

}
