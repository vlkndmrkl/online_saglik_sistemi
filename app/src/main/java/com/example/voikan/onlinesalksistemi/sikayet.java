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

import java.util.HashMap;
import java.util.Map;

public class sikayet extends AppCompatActivity {
    TextView tv;
    Button btn,btn2;
    private String value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sikayet);
        Bundle extras = getIntent().getExtras();
        value= extras.getString("send_string");
        btn=(Button) findViewById(R.id.btnsikayet_gonder);
        btn2 = (Button) findViewById(R.id.btnmesaj_goster);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesaj_gonder();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent istek = new Intent(getApplicationContext(), mesaj_gor.class);
                startActivity(istek);
            }
        });
    }
    public void mesaj_gonder(){
        final String mesajiniz = ((TextView) findViewById(R.id.etxtsikayet_mesaj)).getText().toString();
        final String baslik = ((TextView) findViewById(R.id.etxtsikayet_baslik)).getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "https://mynodeapp3.herokuapp.com/mesaj_gonder";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(sikayet.this, "Mesajınız Gönderildi", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sikayet.this, "Mesaj Gönderilemedi", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("id", value);
                MyData.put("baslik", baslik);
                MyData.put("mesaj", mesajiniz);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

}
