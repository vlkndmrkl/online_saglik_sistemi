package com.example.voikan.onlinesalksistemi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_list_item_checked;

public class MainActivity extends AppCompatActivity {

    private int mStatusCodegelen=0,indis;
    private String icerik[];
    Button btn1;
    Spinner doktor_adlari;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = (Button) findViewById(R.id.btnkaydol);
        doktor_adlari=(Spinner) findViewById(R.id.spndoktor_adlari);
        doktor_id_bulma();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullanici_kayit(icerik[indis]);
            }
        });
    }
    public void doktor_id_bulma(){
        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String adres = "http://mynodeapp3.herokuapp.com/doktor_listesi";
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, adres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Mesajlar\" :" + response + "}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Mesajlar");
                    String randevudizi[] = new String[value.length()];
                    String randevudizi2[] = new String[value.length()];
                    for (int i = 0; i < value.length(); i++) {
                        JSONObject sonuc = value.getJSONObject(i);
                        String id = (sonuc.getString("_id"));
                        String ad = (sonuc.getString("name") + " ");
                        String soyad = (sonuc.getString("lastname"));
                        randevudizi[i] = String.valueOf(ad+soyad);
                        randevudizi2[i] = String.valueOf(id);
                    }
                    icerik=randevudizi2;
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,simple_list_item_checked,randevudizi);
                    doktor_adlari.setAdapter(adapter);
                    doktor_adlari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            indis=position;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse=error.networkResponse;
                if (networkResponse !=null && networkResponse.statusCode==404)
                    Toast.makeText(MainActivity.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz ", Toast.LENGTH_SHORT).show();
                else if (networkResponse !=null && networkResponse.statusCode==500)
                    Toast.makeText(MainActivity.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Hata"+error, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<String, String>();
                headers.put("Content-Type","application/json");

                return headers;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    public void kullanici_kayit(final String id_kayit_user) {
        final String ad = ((TextView) findViewById(R.id.etxtad)).getText().toString();
        final String soyadi = ((TextView) findViewById(R.id.etxtsoyad)).getText().toString();
        final String username = ((TextView) findViewById(R.id.etxtkullanici_adi)).getText().toString();
        final String password = ((TextView) findViewById(R.id.etxtgirissifre)).getText().toString();
        final String dTarihi = ((TextView) findViewById(R.id.etxtdogum_tarihi)).getText().toString();
        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://mynodeapp3.herokuapp.com/add_user";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, ""+mStatusCodegelen, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
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
                MyData.put("name", ad);
                MyData.put("lastname", soyadi);
                MyData.put("email", username);
                MyData.put("password", password);
                MyData.put("doktor_durum", "0");
                MyData.put("doktor_id", id_kayit_user);
                MyData.put("dogumTarihi;", dTarihi);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}