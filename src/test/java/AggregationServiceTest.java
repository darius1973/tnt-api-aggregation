import com.api.aggregation.domain.ApiResponse;
import com.api.aggregation.properties.WebServiceConfigProperties;
import com.api.aggregation.service.AggregationService;
import io.swagger.models.HttpMethod;
import org.junit.After;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AggregationServiceTest {

    private WebServiceConfigProperties webServiceConfigProperties = createWebServiceConfigurationProperties();
    private AggregationService aggregationService = new AggregationService(webServiceConfigProperties);
    private ClientAndServer clientAndServer = new ClientAndServer();

    @After
    public void reset() {
        clientAndServer.reset();
    }

    @Test
    public void testAggregationServiceCalls() {

        HttpRequest expectedPricingServiceRequest = HttpRequest.request()
                .withMethod(HttpMethod.GET.name())
                .withPath("/pricing?q=NL,CN");
        this.clientAndServer.when(expectedPricingServiceRequest)
                .respond(HttpResponse.response()
                        .withBody("{\"NL\": 14.242090605778  " +
                                   "\"CN\": 20.503467806384}")
                        .withContentType(MediaType.APPLICATION_JSON)
                );

        HttpRequest expectedShipmentsServiceRequest = HttpRequest.request()
                .withMethod(HttpMethod.GET.name())
                .withPath("/shipments?q=109347263,123456891");
        this.clientAndServer.when(expectedShipmentsServiceRequest)
                .respond(HttpResponse.response()
                        .withBody("{\"109347263\" : [\"box\",\"pallet\"]," +
                                  "\"123456891\" : [\"envelope\"]}")
                        .withContentType(MediaType.APPLICATION_JSON)
                );

        HttpRequest expectedTrackingsServiceRequest = HttpRequest.request()
                .withMethod(HttpMethod.GET.name())
                .withPath("/track?q=109347263,123456891");
        this.clientAndServer.when(expectedTrackingsServiceRequest)
                .respond(HttpResponse.response()
                        .withBody("{\"109347263\" : \"NEW\"," +
                                "\"123456891\" : \"COLLECTING\"}")
                        .withContentType(MediaType.APPLICATION_JSON)
                );


        List<String> countries = Arrays.asList("NL","CN");
        List<Integer> shipmentNumbers = Arrays.asList(109347263, 123456891);
        StepVerifier.create(this.aggregationService.getDataFor(countries, shipmentNumbers, shipmentNumbers))
                .assertNext(apiResponse->assertValues(apiResponse));
    }

    private void assertValues(ApiResponse apiResponse) {
        assertThat(apiResponse.getPricing().get("NL")).isEqualTo(14.242090605778);
        assertThat(apiResponse.getPricing().get("CN")).isEqualTo(20.503467806384);
        assertThat(apiResponse.getShipments().get("109347263")).isEqualTo(Arrays.asList("box","pallet"));
        assertThat(apiResponse.getShipments().get("123456891")).isEqualTo(Arrays.asList("envelope"));
        assertThat(apiResponse.getTrack().get("109347263")).isEqualTo("NEW");
        assertThat(apiResponse.getTrack().get("123456891")).isEqualTo("COLLECTING");
    }

    private WebServiceConfigProperties createWebServiceConfigurationProperties() {
        WebServiceConfigProperties properties = new WebServiceConfigProperties();
        properties.setHost("http://localhost:8080");
        properties.setPricingApi("/pricing?q=");
        properties.setTrackApi("/track?q=");
        properties.setShipmentsApi("/shipments?q=");

        return properties;
    }
}
