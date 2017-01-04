package com.example.voikan.onlinesalksistemi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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


import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class randevular extends AppCompatActivity {
    EditText et;
    ListView randevu;
    String gelen_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevular);
        et=(EditText)findViewById(R.id.editTextrandevulardeneme);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        randevu = (ListView) findViewById(R.id.lstrandevular);
        getir();
    }
    protected void getir() {
        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String adres = "http://mynodeapp3.herokuapp.com/randevu_getir_user";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, adres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Randevular\" :" + response + "}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Randevular");
                    String randevudizi[] = new String[value.length()];
                    for (int i = 0; i < value.length(); i++) {
                        JSONObject sonuc = value.getJSONObject(i);
                        String ad = ("Ad: " + sonuc.getString("ad") + "\n");
                        String soyad = ("Soyad: " + sonuc.getString("soyad")+"\n");
                        String tarih = ("Randevu Tarihi: " + sonuc.getString("tarih")+"\n");
                        String saat = ("Randevu Saati: " + sonuc.getString("randevu_saati")+"\n");
                            randevudizi[i] = String.valueOf(ad + soyad+tarih+saat);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(randevular.this, simple_list_item_1, randevudizi);
                    randevu.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(randevular.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse=error.networkResponse;
                if (networkResponse !=null && networkResponse.statusCode==404)
                    Toast.makeText(randevular.this, "Bir Hata Oluştu Lütfen Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                else if (networkResponse !=null && networkResponse.statusCode==500)
                    Toast.makeText(randevular.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(randevular.this, "Hata"+error, Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("user_id",gelen_id);
                return params;
            }
        };
        MyRequestQueue.add(MyStringRequest);
        }
}
