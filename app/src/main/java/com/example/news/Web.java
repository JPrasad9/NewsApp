package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class Web extends AppCompatActivity {
    WebView w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        w=findViewById(R.id.web);
        Bundle extras=getIntent().getExtras();
        String s1="https://www.livemint.com/news/india/coronavirus-recoveries-in-india-rise-to-3-47-lakh-18653-new-cases-in-24-hours-11593573710450.html";
        if(extras!=null) {
            s1 = extras.getString("k1");
        }
        Log.d("detailnews", s1 );
        w.getSettings().setJavaScriptEnabled(true);
        w.loadUrl(s1);
    }
}
