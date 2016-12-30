package com.example.voikan.onlinesalksistemi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private int mStatusCodegelen=0;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btnkaydol);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deneme();
            }
        });
    }

    protected void deneme() {
        final String ad = ((TextView) findViewById(R.id.etxtad)).getText().toString();
        final String soyadi = ((TextView) findViewById(R.id.etxtsoyad)).getText().toString();
        final String mail = ((TextView) findViewById(R.id.etxtadres)).getText().toString();
        final String username = ((TextView) findViewById(R.id.etxtkullanici_adi)).getText().toString();
        final String password = ((TextView) findViewById(R.id.etxtgirissifre)).getText().toString();
       // RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://mynodeapp3.herokuapp.com/add_user";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, ""+mStatusCodegelen, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+mStatusCodegelen, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                mStatusCodegelen = response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                mStatusCodegelen=volleyError.networkResponse.statusCode;
                return super.parseNetworkError(volleyError);
            }

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("name", "q");
                MyData.put("lastname", "q");
                MyData.put("email", "q");
                MyData.put("password", "q");
                MyData.put("doktor_durum", "0");
                MyData.put("doktor_id", "1");
                MyData.put("dogumTarihi;", "10-12-2016");
                return MyData;
            }
        };
        RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        MyRequestQueue.add(MyStringRequest);
    }
}

