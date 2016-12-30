package com.example.voikan.onlinesalksistemi;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.RequestQueue;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class mesaj_gor extends AppCompatActivity {

    ListView mesajlar;
    private String icerik[];

    public void setGelen_id(String gelen_id) {
        this.gelen_id = gelen_id;
    }

    private String gelen_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesaj_gor);
        mesajlar=(ListView) findViewById(R.id.lstmesaj_gor);
        Intent inten = getIntent();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            setGelen_id(bundle.getString("send_string"));
            mesaj_getir(gelen_id);
        }
    }
    public void mesaj_getir(final String gelen) {
        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String adres = "http://mynodeapp3.herokuapp.com/mesaj_getir";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, adres, new Response.Listener<String>() {
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
                        String id = ("id: " + sonuc.getString("_id") + "\n");
                        String baslik = ("Konu: " + sonuc.getString("baslik") + "\n");
                        String mesaj = ("Mesaj: " + sonuc.getString("mesaj"));
                        String mesaj2 = ("Mesaj: " + sonuc.getString("mesaj"));
                        if (mesaj.length()>22) {
                            randevudizi[i] = String.valueOf(id + baslik + mesaj.substring(0, 22) + "...");
                            randevudizi2[i] = String.valueOf(baslik + mesaj2);
                        }
                        else {
                            randevudizi[i] = String.valueOf(id + baslik + mesaj);
                            randevudizi2[i] = String.valueOf(baslik + mesaj2);
                        }
                    }
                    icerik=randevudizi2;
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mesaj_gor.this, simple_list_item_1, randevudizi);
                    mesajlar.setAdapter(adapter);
                    mesajlar.setOnItemClickListener(new ListClickHandler());
                } catch (JSONException e) {
                    Toast.makeText(mesaj_gor.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse=error.networkResponse;
                if (networkResponse !=null && networkResponse.statusCode==404)
                    Toast.makeText(mesaj_gor.this, "Mesaj Bulunamadı", Toast.LENGTH_SHORT).show();
                else if (networkResponse !=null && networkResponse.statusCode==500)
                    Toast.makeText(mesaj_gor.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(mesaj_gor.this, "Hata"+error, Toast.LENGTH_SHORT).show();
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
                params.put("user_id",gelen);
                return params;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    public class ListClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mesaj_gor.this);
            builder.setTitle("Mesaj Detayları");
            builder.setMessage(icerik[position]);
            builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }
}
