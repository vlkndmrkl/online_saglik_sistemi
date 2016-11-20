package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class giris extends AppCompatActivity {
    Button btn;
    TextView ad, sifre;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        btn = (Button) findViewById(R.id.btngiris);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deneme();
                ad = (TextView) findViewById(R.id.etxtkullanici_adi);
                sifre = (TextView) findViewById(R.id.etxtgirissifre);
            }
        });
    }
    protected void deneme() {
        final String username = ((TextView) findViewById(R.id.etxtkullanici_adi)).getText().toString();
        final String password = ((TextView) findViewById(R.id.etxtgirissifre)).getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://mynodeapp3.herokuapp.com/get_user?username="+username.toString();
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String durum=response.substring(0,5);
                if (durum.equals("false") ){
                    Toast.makeText(giris.this, "Kullanıcı adı şifre hatalı", Toast.LENGTH_SHORT).show();
                } else {
                    id=response.substring(7,31);
                    Intent intent = new Intent(giris.this,sikayet.class);
                    intent.putExtra("send_string", id.toString());
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(giris.this, "Bilinmeyen bir hata oluştu", Toast.LENGTH_LONG).show();
            }
        }) ;
        MyRequestQueue.add(MyStringRequest);
    }


}

