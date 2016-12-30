package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class sifre_degistirme extends AppCompatActivity {
    EditText eski_sifre,yeni_sifre,yeni_sifre2;
    Button degistir;
    private String gelen_id;
    int statukod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degistirme);
        eski_sifre=(EditText) findViewById(R.id.etxteski_sifre);
        yeni_sifre=(EditText) findViewById(R.id.etxtyeni_sifre);
        yeni_sifre2=(EditText) findViewById(R.id.etxtyeni_sifre_tekrar);
        Bundle extras = getIntent().getExtras();
        gelen_id = extras.getString("send_string");
        degistir=(Button)findViewById(R.id.btnsifre_degistir);
        degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (yeni_sifre.getText().toString().equals(yeni_sifre2.getText().toString())) {
                sifre_kontrol();
            }
                else{
                Toast.makeText(sifre_degistirme.this, "Girilen şifreler eşleşmiyor", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }

    protected void sifre_kontrol() {

        final String oldpassword = eski_sifre.getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/eski_sifre_kontrol";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (statukod==200){
                    sifre_degistir();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse=error.networkResponse;
                if (networkResponse !=null && networkResponse.statusCode==500)
                    Toast.makeText(sifre_degistirme.this, "Bilinmeyen Bir Hata Olşutu", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(sifre_degistirme.this, "Eski Şifreyi Yanlış Girdiniz", Toast.LENGTH_SHORT).show();
            }
         }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
               statukod=response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
        protected Map getParams() {
            Map<String, String> params = new HashMap<String, String>();
            params.put("user_id", gelen_id);
            params.put("eskiSifre", oldpassword);
            return params;
        }
    };
    MyRequestQueue.add(MyStringRequest);
    }
    protected void sifre_degistir(){
        final String password = ((EditText) findViewById(R.id.etxtyeni_sifre)).getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/sifre_guncelle";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(sifre_degistirme.this, "Şifre Değiştirildi", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(sifre_degistirme.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statukod=response.statusCode;
                return super.parseNetworkResponse(response);
            }

            @Override
            protected Map getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("yeniSifre", password.toString());
                params.put("user_id", gelen_id);
                return params;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
