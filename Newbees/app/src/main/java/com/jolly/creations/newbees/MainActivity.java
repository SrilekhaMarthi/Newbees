package com.jolly.creations.newbees;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.service.autofill.Validators.not;
import static com.google.common.base.CharMatcher.is;
import static java.util.Optional.empty;

public class MainActivity extends AppCompatActivity {
    
    private EditText inputmsg;
    private TextView othermsg;
    private String originalText;
    private String translatedText;
    private boolean connected;
    Translate translate;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        inputmsg = findViewById(R.id.inputmsg);
    
        ImageView send = findViewById(R.id.sendmsg);
    
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if (checkInternetConnection()) {
                    
                    //If there is internet connection, get translate service and start translation:
                   // getTranslateService();
                    translate();
                    
                } else {
                    
                    //If not, display "no connection" warning:
                    //translatedTv.setText(getResources().getString(R.string.no_connection));
                }
                
            }
        });
    }
    
    
    public void getTranslateService() {
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
        try (InputStream is = getResources().openRawResource(R.raw.credentials)) {
            
            //Get credentials:
            final GoogleCredentials myCredentials = GoogleCredentials.fromStream(is);
            
            //Set credentials and get translate service:
            TranslateOptions translateOptions = TranslateOptions.newBuilder().setCredentials(myCredentials).build();
            translate = translateOptions.getService();
            
        } catch (IOException ioe) {
            ioe.printStackTrace();
            
        }
    }
    
    public void translate() {
        
        //Get input text to be translated:
        originalText = inputmsg.getText().toString();
        Translation translation = translate.translate(originalText, Translate.TranslateOption.targetLanguage("tr"), Translate.TranslateOption.model("base"));
        translatedText = translation.getTranslatedText();
        
        //Translated text and original text are set to TextViews:
        //translatedTv.setText(translatedText);
        
    }
    
    public boolean checkInternetConnection() {
        
        //Check internet connection:
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        
        //Means that we are connected to a network (mobile or wi-fi)
        connected = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
        
        return connected;
    }
    
    public void sendmsg(View view) {
    
    }
}