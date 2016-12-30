package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class gunluk_bilgiler extends AppCompatActivity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunluk_bilgiler);
        btn1=(Button) findViewById(R.id.btngunluk_kaydet);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayit();
            }
        });
    }
    protected void kayit() {
        final String b_tansiyon = ((TextView) findViewById(R.id.etxtbuyuk_tansiyon)).getText().toString();
        final String k_tansiyon = ((TextView) findViewById(R.id.etxtkucuk_tansiyon)).getText().toString();
        final String seker_orani = ((TextView) findViewById(R.id.etxtseker_orani)).getText().toString();
        final String icilen_su_miktari = ((TextView) findViewById(R.id.etxticilen_su)).getText().toString();
        final String adim_sayisi = ((TextView) findViewById(R.id.etxtadim_sayisi)).getText().toString();
        final SimpleDateFormat bicim=new SimpleDateFormat("dd.M.yyyy");
        final Date tarih=new Date();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://mynodeapp3.herokuapp.com/gunluk_bilgi_ekle";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(gunluk_bilgiler.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(gunluk_bilgiler.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("_id;", "583c070cb1fd4b0b6461cfdc");
                MyData.put("b_tansiyon;", b_tansiyon);
                MyData.put("k_tansiyon;", k_tansiyon);
                MyData.put("seker_orani;", seker_orani);
                MyData.put("su_miktari;", icilen_su_miktari);
                MyData.put("adim_sayisi;", adim_sayisi);
                MyData.put("tarih;", bicim.format(tarih).toString());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
