package com.jolly.creations.newbees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ChooseLocation extends AppCompatActivity {
    String add="",url1="";
    String pilat,pilon;
    private WebView wv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);
    
        wv1=(WebView)findViewById(R.id.webView);
        wv1.setWebViewClient(new MyBrowser());
        
                String url = "https://www.google.com/maps/";
            
                wv1.getSettings().setLoadsImagesAutomatically(true);
                wv1.getSettings().setJavaScriptEnabled(true);
                wv1.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                wv1.loadUrl(url);
                
        url1 = wv1.getOriginalUrl();
    
    
        if(url.length()>45) {
    
           
            String coords = "";
    
            int pos1 = url.indexOf("/@");
            String rightpart = url.substring(pos1 + ("/@".length()));
            int pos2 = rightpart.indexOf("/");
            //Log.e("food_reg_map", pos1+"-----"+rightpart+"-----"+pos2);
            
            if (pos2 > 0) {
                coords = rightpart.substring(0, pos2);
                String[] split = coords.split(",");
                pilat = split[0];
                pilon = split[1];
                Log.e("food_reg_map", pilat + "-----" + pilon);
            } else {
                String[] split = rightpart.split(",");
                pilat = split[0];
                pilon = split[1];
                Log.e("food_reg_map", pilat + "-----" + pilon);
            }
    
    
            Toast.makeText(getApplicationContext(), "Lat" + pilat, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Lon" + pilon, Toast.LENGTH_SHORT).show();
    
            getAddress(Double.parseDouble(pilat), Double.parseDouble(pilon));
        }
    }
    
    public String getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            
            //String add = obj.getAddressLine(0);
            //add = add + "\n" + obj.getCountryName();
            //add = add + "\n" + obj.getCountryCode();
            //add = add + "\n" + obj.getAdminArea();
            add = add + "\n" + obj.getPostalCode();
            //add = add + "\n" + obj.getSubAdminArea();
            //add = add + "\n" + obj.getLocality();
            //add = add + "\n" + obj.getSubThoroughfare();
            
            //Log.v("IGA", "Address" + add);
            Toast.makeText(this, "Address=>" + add, Toast.LENGTH_SHORT).show();
    
            return obj.getPostalCode();
            
            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            return "602001";
        }
        
    }
    
    public void nextact(View view) {
        SharedPreferences sharedPref = getSharedPreferences("newbees", MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString("locapin",getAddress(Double.parseDouble(pilat), Double.parseDouble(pilon)));
        startActivity(new Intent(ChooseLocation.this,MainActivity.class));
    }
    
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}