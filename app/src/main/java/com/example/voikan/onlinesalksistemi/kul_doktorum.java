package com.example.voikan.onlinesalksistemi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
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

public class kul_doktorum extends AppCompatActivity {
    String gelen_id;
    ListView doktor_bilgi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kul_doktorum);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        doktor_bilgi=(ListView) findViewById(R.id.lstdoktor_bilgileri);
        doktor_bilgileri_getir();
    }

    public void doktor_bilgileri_getir() {
        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String adres = "http://mynodeapp3.herokuapp.com/gonderilen_mesajlar";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, adres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Doktor\" :" + response + "}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Doktor");
                    String randevudizi[] = new String[value.length()];
                    for (int i = 0; i < value.length(); i++) {
                        JSONObject sonuc = value.getJSONObject(i);
                        String email = ("E-Mail: " + sonuc.getString("email") + "\n");
                        String ad = ("Ad: " + sonuc.getString("name") + "\n");
                        String soyad = ("Soyad: " + sonuc.getString("lastname")+ "\n");
                        String dogum_tarihi = ("Doğum Tarihi: " + sonuc.getString("dogumTarihi")+ "\n");
                            randevudizi[i] = String.valueOf(email + ad + soyad+dogum_tarihi);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(kul_doktorum.this, simple_list_item_1, randevudizi);
                    doktor_bilgi.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(kul_doktorum.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(kul_doktorum.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
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
                params.put("user_id", gelen_id);
                return params;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
