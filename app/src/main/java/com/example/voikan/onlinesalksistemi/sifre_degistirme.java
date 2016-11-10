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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class sifre_degistirme extends AppCompatActivity {
    EditText eski_sifre,yeni_sifre,yeni_sifre2;
    Button degistir;
    String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_degistirme);
        eski_sifre=(EditText) findViewById(R.id.etxteski_sifre);
        yeni_sifre=(EditText) findViewById(R.id.etxtyeni_sifre);
        yeni_sifre2=(EditText) findViewById(R.id.etxtyeni_sifre_tekrar);
        Bundle extras = getIntent().getExtras();
        value = extras.getString("send_string");
        final String eskisifre=((EditText) findViewById(R.id.etxtyeni_sifre)).getText().toString();
        final String eskisifre2=((EditText) findViewById(R.id.etxtyeni_sifre_tekrar)).getText().toString();
        degistir=(Button)findViewById(R.id.btnsifre_degistir);
        degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (eskisifre.equals(eskisifre2)) sifre_degistir();
            }
        });
    }

    protected void sifre_degistir() {
        final String password = ((EditText) findViewById(R.id.etxtyeni_sifre)).getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://mynodeapp3.herokuapp.com/sifre_guncelle?id="+value.toString()+"&sifre="+password.toString();
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(sifre_degistirme.this, "Şifre Değiştirme İşlemi Başarı İle Gerçekleşti", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sifre_degistirme.this, "Bilinmeyen bir hata oluştu", Toast.LENGTH_LONG).show();
            }
        }) ;
        MyRequestQueue.add(MyStringRequest);
    }
}
