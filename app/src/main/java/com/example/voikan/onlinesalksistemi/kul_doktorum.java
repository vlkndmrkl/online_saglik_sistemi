package com.example.voikan.onlinesalksistemi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class kul_doktorum extends AppCompatActivity {
    String gelen_id,doktor_id;
    int status;
    ListView doktor_bilgi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kul_doktorum);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        doktor_bilgi=(ListView) findViewById(R.id.lstdoktor_bilgileri);
        doktor_id_bul();
    }
    public void doktor_id_bul(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/doktor_id_bul";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (status==200){
                    doktor_id=new String(response.substring(47,(response.length()-2)));
                    doktor_bilgileri_getir();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(kul_doktorum.this, "Bilinmeyen Bir Hata Olşutu", Toast.LENGTH_SHORT).show();
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
    public void doktor_bilgileri_getir() {
        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String adres = "http://mynodeapp3.herokuapp.com/doktor_bilgileri_getir";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, adres, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Mesajlar\" :[" + response + "]}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Mesajlar");
                    String randevudizi[] = new String[value.length()];
                    for (int i = 0; i < value.length(); i++) {
                        JSONObject sonuc = value.getJSONObject(i);
                        String dtarihi = ("Doğum Tarihi: " + sonuc.getString("dogumTarihi") + "\n");
                        String email = ("Email: " + sonuc.getString("email") + "\n");
                        String soyad = ("Soyad: " + sonuc.getString("lastname")+"\n");
                        String ad = ("Ad: " + sonuc.getString("name")+"\n");
                            randevudizi[i] = String.valueOf(ad + soyad + email+dtarihi);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(kul_doktorum.this, simple_list_item_1, randevudizi);
                    doktor_bilgi.setAdapter(adapter);
                } catch (JSONException e) {
                    Toast.makeText(kul_doktorum.this, "HATA"+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse=error.networkResponse;
                if (networkResponse !=null && networkResponse.statusCode==404)
                    Toast.makeText(kul_doktorum.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
                else if (networkResponse !=null && networkResponse.statusCode==500)
                    Toast.makeText(kul_doktorum.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(kul_doktorum.this, "Hata"+error, Toast.LENGTH_SHORT).show();
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
                params.put("doktor_id",doktor_id);
                return params;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
