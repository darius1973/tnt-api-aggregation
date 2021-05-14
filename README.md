# tnt-api-aggregation

maven & Spring boot application using WebFlux
 - makes non-blocking calls to 3 services and collects data
 - can be runned from  intelij idea as spring boot application 
   or : 
 - mvn clean install
 and then in target folder:
 
 java -jar api-aggregation-service-1.0-SNAPSHOT-exec.jar
 
Sample request:

http://localhost:8081/aggregation?pricing=NL,CN,AA&track=109347263,123456891&shipments=109347263,123456891

response body:

[{"pricing":{"AA":10.421339234058324,"CN":2.777629685168337,"NL":23.89804979016227},
"shipments":{"109347263":["box","pallet","box"],"123456891":["envelope"]},"track":{"109347263":"IN TRANSIT","123456891":"NEW"}}]

The most logic is in the AggregationService.For every subsequent service there is a separate flux , which are then regroupped using Flux.zip.
This way the result ( held in Apiresponse) is created.


