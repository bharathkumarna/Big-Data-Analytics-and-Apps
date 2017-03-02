package com.example.bharath.imageclassification;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void DT(View v) {
        Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
        webPageIntent.setData(Uri.parse("http://192.168.1.146:8080/get_dt"));

        try {
            startActivity(webPageIntent);
        } catch (ActivityNotFoundException ex) {

        }
    }
    public void RF(View v) {
        Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
        webPageIntent.setData(Uri.parse("http://192.168.1.146:8081/get_rf"));

        try {
            startActivity(webPageIntent);
        } catch (ActivityNotFoundException ex) {

        }
    }
    public void CL(View v) {
        Intent webPageIntent = new Intent(Intent.ACTION_VIEW);
        webPageIntent.setData(Uri.parse("http://192.168.1.146:8082/get_img"));

        try {
            startActivity(webPageIntent);
        } catch (ActivityNotFoundException ex) {

        }
    }
}
