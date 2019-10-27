package com.example.athletica.data.facility;

import android.content.Context;
import android.location.Geocoder;

import com.example.athletica.data.user.DataManager;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class FacilityManager {
//
//    String Facilities;
//    String name;
//    String website;

    private DataManager dataManager;
    private Map<String,String> facility;
    Geocoder geocoder;


    public FacilityManager(Context context, String facilityIndex){

        facility=dataManager.readIndex(context,facilityIndex) ;
        geocoder=new Geocoder(context,Locale.getDefault());
    }

    public String getFacilities() {
        return facility.get("Facilities").replace("/", "  ");
    }

    public String getName() {
        return facility.get("name");
    }

    public String getWebsite() {
        return facility.get("website");
    }

    public double getLat(){
        return Double.parseDouble(facility.get("lat"));
    }

    public double getLong(){
        return Double.parseDouble(facility.get("long"));
    }

    public String getAddress(){
        double lat=getLat();
        double lng=getLong();

        try {
            return geocoder.getFromLocation(lat, lng, 1).get(0).getAddressLine(0);
        } catch (IOException e) {
            return "Address Could not be fetched";
        }
    }
}

