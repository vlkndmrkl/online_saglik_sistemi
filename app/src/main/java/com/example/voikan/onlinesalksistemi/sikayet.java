package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class sikayet extends AppCompatActivity {

    Button btn;
    EditText et;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sikayet);
        btn = (Button) findViewById(R.id.buttonrandevugetirme);
        et=(EditText) findViewById(R.id.editTextrandevugetirme);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getir();
            }
        });
    }
    protected void getir() {
        final TextView txt= ((TextView) findViewById(R.id.etxtkullanici_adi));
        et=(EditText) findViewById(R.id.editTextrandevugetirme);
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/randevu_getir";
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Randevular\" :"+response+"}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Randevular");
                    StringBuilder sb = new StringBuilder();
                    for (int i =0; i< value.length();i++){
                        JSONObject sonuc = value.getJSONObject(i);
                        sb.append("id= " + sonuc.getString("_id")+"\n");
                        sb.append("ad= " + sonuc.getString("ad")+"\n");
                        sb.append("soyad= " + sonuc.getString("soyad")+"\n");
                        sb.append("tarih= " + sonuc.getString("tarih")+"\n");
                        sb.append("username= " + sonuc.getString("username")+"\n\n\n");
                    }
                    et.setText(sb.toString());
                } catch (JSONException e) {
                    Toast.makeText(sikayet.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sikayet.this, "Bilinmeyen bir hata oluştu", Toast.LENGTH_LONG).show();
            }
        }) ;
        MyRequestQueue.add(MyStringRequest);
    }
}
