package com.api.aggregation.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("web-service-config")
public class WebServiceConfigProperties {

    private String host;
    private String shipmentsApi;
    private String pricingApi;
    private String trackApi;
}
