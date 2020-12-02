# WhereIs POC

The main idea of this POC is testing a combination of kafka, spring-boot and angular in application to receive georefence data via websocket and display in a openlayers like 
a RTS (Real Time System). All stack is configured to run over docker minimizing headaches associate with the enviroment configurations.

Here you will find the application of kafka + spring-boot monitoring the location of your device or any service able to send geolocation via REST endpoint, as soon this data 
arrives a consumer sends to websocket. The angular application connect to websocke an add a feature (geojson definition to geometry + data) at a layer on map and
it is displayed on page.
