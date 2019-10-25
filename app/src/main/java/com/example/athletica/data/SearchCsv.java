package com.example.athletica.data;

import android.content.Context;
import android.util.Log;

import com.example.athletica.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchCsv {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    Context context;


    public SearchCsv(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }


    //    This function is used to fetch facilitiy records matching input string
    public List<Map> readDataAll(String str) {
        List<Map> dataList = new ArrayList<>();
        //int count=0;
        InputStream is = context.getResources().openRawResource(R.raw.sports);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {

            reader.readLine();


            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");


                if (tokens[3].contains(str) || tokens[4].contains(str)) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("index", tokens[0]);
                    map.put("long", tokens[1]);
                    map.put("lat", tokens[2]);
                    map.put("name", tokens[3]);
                    map.put("Facilities", tokens[4]);
                    map.put("zip", tokens[5]);
                    map.put("website", tokens[6]);

                    dataList.add(map);
                }
            }
        } catch (IOException e) {
            Log.wtf("My activity ", "error");
        }
        return dataList;
    }


    //    This function is used to get a particular record based on the index provided as a parameter

    public Map<String, String> readIndex(String index) {
        Map<String, String> data = new HashMap<>();
        InputStream is = context.getResources().openRawResource(R.raw.sports);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line = "";
        try {

            reader.readLine();


            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split(",");

                if (tokens[0].equals(index)) {

                    data.put("index", tokens[0]);
                    data.put("long", tokens[1]);
                    data.put("lat", tokens[2]);
                    data.put("name", tokens[3]);
                    data.put("Facilities", tokens[4]);
                    data.put("zip", tokens[5]);
                    data.put("website", tokens[6]);


                    return data;

                }

            }
        } catch (IOException e) {
            Log.wtf("My activity ", "error");
        }
        return null;
    }
}

