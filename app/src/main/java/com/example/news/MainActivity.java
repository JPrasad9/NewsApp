package com.example.news;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RelativeLayout l1;
    LinearLayout l2;
    ScrollView s;
    ImageView img;
    TextView t1,t2,t3,t4,t5;
    RequestQueue requestQueue;
    private TextToSpeech textToSpeech;
    int index=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestQueue = Volley.newRequestQueue(this);
        img=findViewById(R.id.newsimg);
        t1=findViewById(R.id.title);
//        t2=findViewById(R.id.author);
        t3=findViewById(R.id.desc);
        t4=findViewById(R.id.more);
        t5=findViewById(R.id.time);
        l1=findViewById(R.id.swipe);
        l2=findViewById(R.id.swp);
        s=findViewById(R.id.sss);


        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        Locale locale = new Locale("en", "IN");
                        int ttsLang = textToSpeech.setLanguage(locale);

                        if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                                || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "The Language is not supported!");
                        } else {
                            Log.i("TTS", "Language Supported.");
                        }
                        Log.i("TTS", "Initialization success.");
                    } else {
                        Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        start();
        l1.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if(index<38){
                    index++;
                    start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You have reached end",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if(index>0){
                    index--;
                    start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You have reached end",Toast.LENGTH_SHORT).show();

                }
            }
        });
        s.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if(index<38){
                    index++;
                    start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You have reached end",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if(index>0){
                    index--;
                    start();
                }
                else{
                    Toast.makeText(getApplicationContext(),"You have reached end",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    String webinf,data;
    public void start(){
        String url="http://newsapi.org/v2/top-headlines?country=in&apiKey=65251c7262514dd78b8178703e345342";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String tit,auth,news,imgurl,moreinf,time;
//                    JSONObject obj=response.JSONObject();
                    tit=response.getJSONArray("articles").getJSONObject(index).getString("title");
                    auth="-"+response.getJSONArray("articles").getJSONObject(index).getString("author");
                    news=response.getJSONArray("articles").getJSONObject(index).getString("description");
                    imgurl=response.getJSONArray("articles").getJSONObject(index).getString("urlToImage");
                    moreinf="For more information: "+"Click here";
                    webinf=response.getJSONArray("articles").getJSONObject(index).getString("url");
                    time="publishedAt : "+response.getJSONArray("articles").getJSONObject(index).getString("publishedAt");
//                    img.setImageURI(Uri.parse(imgurl));
                    data=tit+".."+news;
                    t1.setText(tit);
//                    t2.setText(auth);
                    t3.setText(news);
                    t4.setText(moreinf);
                    t5.setText(time);
                    Picasso.get().load(imgurl).into(img);
                    Log.d("news", imgurl);

//                    in=response.getString("staddress")+"\n";
                    Log.i("TTS", "button clicked: " + data);
                    int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null,null);

                    if (speechStatus == TextToSpeech.ERROR) {
                        Log.e("TTS", "Error in converting Text to Speech!");
                    }

                } catch (Exception e) {
                    Log.d("myapp", "something went wrong");
                    Toast.makeText(getApplicationContext(),"you reached end",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("myapp", "Something went wrong");
                Toast.makeText(getApplicationContext(),"Poor internet connection",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);


    }
    public void detailNews(View view){
//        w.getSettings().setJavaScriptEnabled(true);
//        w.loadUrl(webinf);
        Intent i=new Intent(MainActivity.this,Web.class);
        i.putExtra("k1",webinf);
        startActivity(i);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}