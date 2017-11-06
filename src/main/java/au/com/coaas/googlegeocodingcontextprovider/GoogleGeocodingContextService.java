/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.coaas.googlegeocodingcontextprovider;

import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import java.util.List;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.joda.time.DateTime;
import org.schema.location.Distance;
import org.schema.location.GeoCoordinates;

/**
 * REST Web Service
 *
 * @author ali
 */
@Path("google/geocoder")
public class GoogleGeocodingContextService {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GoogleGeocodingContextService
     */
    public GoogleGeocodingContextService() {
    }

    /**
     * Retrieves representation of an instance of
     * au.com.coaas.googlegeocodingcontextprovider.GoogleGeocodingContextService
     *
     * @return an instance of java.lang.String
     */
    @Path("{address}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GeoCoordinates forwardGeocing(@PathParam("address") String address) {
        //TODO return proper representation object
        return GoogleGeocoder.forwardGeoCode(address);
    }

    @Path("Reverse/{lat}/{lon}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GeoCoordinates reverseGeocoding(@PathParam("lat") double lat, @PathParam("lon") double lon) {
        //TODO return proper representation object
        return GoogleGeocoder.backwardGeoCode(lat, lon);

    }

    @Path("distance/{origin_lat}/{origin_lon}/{destination_lat}/{destination_lon}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Distance distance(@PathParam("origin_lat") double origin_lat, @PathParam("origin_lon") double origin_lon,
            @PathParam("destination_lat") double destination_lat, @PathParam("destination_lon") double destination_lon,
            @DefaultValue("DRIVING") @QueryParam("mode") String mode,  @QueryParam("routeRestrictions") List<String> routeRestrictions,
            @QueryParam("arrivalTime") String arrivalTime, @QueryParam("departureTime") String departureTime) {
        //TODO return proper representation object
        DateTime arrivalDateTime = null;
        DateTime departureDateTime = null;

        if (arrivalTime != null) {
            arrivalDateTime = new DateTime(arrivalTime);
        } else if (departureTime != null) {
            departureDateTime = new DateTime(departureTime);
        } else {
            arrivalDateTime = new DateTime();
        }
        return GoogleGeocoder.distance(new LatLng(origin_lat, origin_lon), new LatLng(destination_lat, destination_lon),
                mode, routeRestrictions, arrivalDateTime, departureDateTime);
    }

    @Path("distance/{origin_address}/{destination_address}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Distance distance(@PathParam("origin_address") String origin_address, @PathParam("destination_address") String destination_address,
            @DefaultValue("DRIVING") @QueryParam("mode") String mode,  @QueryParam("routeRestrictions") List<String> routeRestrictions,
            @QueryParam("arrivalTime") String arrivalTime, @QueryParam("departureTime") String departureTime) {
        //TODO return proper representation object
        DateTime arrivalDateTime = null;
        DateTime departureDateTime = null;

        if (arrivalTime != null) {
            arrivalDateTime = new DateTime(arrivalTime);
        } else if (departureTime != null) {
            departureDateTime = new DateTime(departureTime);
        } else {
            arrivalDateTime = new DateTime();
        }
        return GoogleGeocoder.distance(origin_address, destination_address,
                mode, routeRestrictions, arrivalDateTime, departureDateTime);
    }

}
