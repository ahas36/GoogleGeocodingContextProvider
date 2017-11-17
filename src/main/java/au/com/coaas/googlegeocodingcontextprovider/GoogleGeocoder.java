/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package au.com.coaas.googlegeocodingcontextprovider;

import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.schema.common.QuantitativeValue;
import org.schema.location.Distance;
import org.schema.location.GeoCoordinates;

/**
 *
 * @author ali
 */
public class GoogleGeocoder {

   // public static final String GOOGLE_API_KEY = "AIzaSyAY-4RA1p8EumvdQ9jx1mrNuiHHh7CWKeY";
    
    public static final String GOOGLE_API_KEY = "AIzaSyAkH1CfXFHYSvnOMLdFPxkKKS0Msx-3FbI"; 

    private static final GeoApiContext context = new GeoApiContext.Builder()
            .apiKey(GOOGLE_API_KEY)
            .build();

    public static GeoCoordinates forwardGeoCode(String address) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    address).await();
            GeoCoordinates geo = new GeoCoordinates();
            GeocodingResult r = results[0];
            geo.setAddress(r.formattedAddress);
            geo.setLatitude(r.geometry.location.lat);
            geo.setLongitude(r.geometry.location.lng);
            return geo;
        } catch (ApiException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Distance distance(LatLng origin, LatLng des, String mode, List<String> routeRestrictions, DateTime arrivalTime, DateTime departureTime) {
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context).
                origins(origin).
                destinations(des).
                mode(TravelMode.valueOf(mode));
        for (String routeRestriction : routeRestrictions) {
            req.avoid(RouteRestriction.valueOf(routeRestriction));
        }
        if (arrivalTime != null) {
            req.arrivalTime(arrivalTime);
        } else if (departureTime != null) {
            req.departureTime(departureTime);
        }
        try {
            DistanceMatrix trix = req.await();
            Distance distance = new Distance();
            DistanceMatrixElement element = trix.rows[0].elements[0];
            distance.setDistance(new QuantitativeValue(String.valueOf(element.distance.inMeters), "m", element.distance.humanReadable));
            distance.setDuration(new QuantitativeValue(String.valueOf(element.duration.inSeconds), "sec", element.duration.humanReadable));
            if (element.durationInTraffic != null) {
                distance.setDurationInTraffic(new QuantitativeValue(String.valueOf(element.durationInTraffic.inSeconds), "sec", element.durationInTraffic.humanReadable));
            }
            if (element.fare != null) {
                distance.setFare(new QuantitativeValue(String.valueOf(element.fare.value), element.fare.currency.getCurrencyCode(), element.fare.toString()));
            }
            return distance;
        } catch (ApiException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Distance distance(String origin, String des, String mode, List<String> routeRestrictions, DateTime arrivalTime, DateTime departureTime) {
        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context).
                origins(origin).
                destinations(des).
                mode(TravelMode.valueOf(mode));
        for (String routeRestriction : routeRestrictions) {
            req.avoid(RouteRestriction.valueOf(routeRestriction));
        }
        if (arrivalTime != null) {
            req.arrivalTime(arrivalTime);
        } else if (departureTime != null) {
            req.departureTime(departureTime);
        }
        try {
            DistanceMatrix trix = req.await();
            Distance distance = new Distance();
            DistanceMatrixElement element = trix.rows[0].elements[0];
            distance.setDistance(new QuantitativeValue(String.valueOf(element.distance.inMeters), "m", element.distance.humanReadable));
            distance.setDuration(new QuantitativeValue(String.valueOf(element.duration.inSeconds), "sec", element.duration.humanReadable));
            if (element.durationInTraffic != null) {
                distance.setDurationInTraffic(new QuantitativeValue(String.valueOf(element.durationInTraffic.inSeconds), "sec", element.durationInTraffic.humanReadable));
            }
            if (element.fare != null) {
                distance.setFare(new QuantitativeValue(String.valueOf(element.fare.value), element.fare.currency.getCurrencyCode(), element.fare.toString()));
            }
            return distance;
        } catch (ApiException | InterruptedException | IOException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static GeoCoordinates backwardGeoCode(double lat, double lon) {
        try {
            GeocodingResult[] results = GeocodingApi.reverseGeocode(context,
                    new LatLng(lat, lon)).await();
            GeocodingResult r = results[0];
            GeoCoordinates geo = new GeoCoordinates();
            geo.setAddress(r.formattedAddress);
            geo.setLatitude(r.geometry.location.lat);
            geo.setLongitude(r.geometry.location.lng);
            return geo;
        } catch (ApiException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GoogleGeocoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void main(String[] args) {
        GeoCoordinates forwardGeoCode = GoogleGeocoder.forwardGeoCode("Wellington Rd, Clayton VIC 3800");
        System.out.println(forwardGeoCode.toString());

        System.out.println(GoogleGeocoder.backwardGeoCode(Double.valueOf(forwardGeoCode.getLatitude()), Double.valueOf(forwardGeoCode.getLongitude())).toString());

    }
}
