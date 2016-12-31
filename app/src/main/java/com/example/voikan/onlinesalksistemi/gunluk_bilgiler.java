package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class gunluk_bilgiler extends AppCompatActivity {
    Button btn1;

    public void setGelen_id(String gelen_id) {
        this.gelen_id = gelen_id;
    }

    public void setDoktor_id_gelen(String doktor_id_gelen) {
        this.doktor_id_gelen = doktor_id_gelen;
    }

    private String gelen_id;
    private String doktor_id_gelen;
    private int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gunluk_bilgiler);
        Bundle extras = getIntent().getExtras();
        setGelen_id(extras.getString("send_string"));
        //this.gelen_id = extras.getString("send_string");
        btn1=(Button) findViewById(R.id.btngunluk_kaydet);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_bul();
            }
        });
    }
    public void id_bul(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/doktor_id_bul";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (status==200){
                    setDoktor_id_gelen(response.substring(47,(response.length()-2)));
                    kayit(doktor_id_gelen);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(gunluk_bilgiler.this, "Bilinmeyen Bir Hata Olşutu", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                status=response.statusCode;
                return super.parseNetworkResponse(response);
            }

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("user_id", gelen_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    protected void kayit(final String doktorun_id) {
        final String b_tansiyon = ((TextView) findViewById(R.id.etxtbuyuk_tansiyon)).getText().toString();
        final String k_tansiyon = ((TextView) findViewById(R.id.etxtkucuk_tansiyon)).getText().toString();
        final String seker_orani = ((TextView) findViewById(R.id.etxtseker_orani)).getText().toString();
        final String icilen_su_miktari = ((TextView) findViewById(R.id.etxticilen_su)).getText().toString();
        final String adim_sayisi = ((TextView) findViewById(R.id.etxtadim_sayisi)).getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/gunluk_bilgi_ekle";
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
                MyData.put("user_id", gelen_id);
                MyData.put("b_tansiyon", b_tansiyon);
                MyData.put("k_tansiyon", k_tansiyon);
                MyData.put("seker_orani", seker_orani);
                MyData.put("su_miktari", icilen_su_miktari);
                MyData.put("adim_sayisi", adim_sayisi);
                MyData.put("doktor_id", doktorun_id);
                return MyData;
            }
        };
       MyRequestQueue.add(MyStringRequest);
    }
}
