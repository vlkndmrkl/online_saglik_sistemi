package com.example.voikan.onlinesalksistemi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class admin_mesaj extends AppCompatActivity {
    private String gelen_id;
    private String icerik[];
    ListView liste;
    Button btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mesaj);
        liste=(ListView) findViewById(R.id.deneme);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        btn2=(Button) findViewById(R.id.btnadmin_mesaj_gonder);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesaj_gonder();
            }
        });
        btn3=(Button) findViewById(R.id.btnmesaj_goster2);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_mesaj.this,mesaj_gor.class);
                intent.putExtra("send_string", gelen_id);
                startActivity(intent);
            }
        });
        gonderilenler();
    }
    public void mesaj_gonder(){
        final String mesajiniz = ((TextView) findViewById(R.id.etxtadmine_gonder2)).getText().toString();
        final String baslik = ((TextView) findViewById(R.id.etxtsikayet_baslik2)).getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/mesaj_gonder";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(admin_mesaj.this, "Mesajınız Gönderildi", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(admin_mesaj.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        }) {

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("gonderen_id", gelen_id);
                MyData.put("alici_id", "58449060754d4c16041d898f");
                MyData.put("baslik", baslik);
                MyData.put("mesaj", mesajiniz);
                MyData.put("okundu", "0");
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    public void gonderilenler() {
        com.android.volley.RequestQueue MyRequestQueue = Volley.newRequestQueue(getApplicationContext());
        String adres = "http://mynodeapp3.herokuapp.com/gonderilen_mesajlar";
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
                        String mesaj = ("Mesaj: " + sonuc.getString("mesaj")+ "\n");
                        String mesaj2 = ("Mesaj: " + sonuc.getString("mesaj")+ "\n");
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(admin_mesaj.this, simple_list_item_1, randevudizi);
                    liste.setAdapter(adapter);
                    liste.setOnItemClickListener(new admin_mesaj.ListClickHandler());
                } catch (JSONException e) {
                    Toast.makeText(admin_mesaj.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse=error.networkResponse;
                if (networkResponse !=null && networkResponse.statusCode==404)
                    Toast.makeText(admin_mesaj.this, "Mesaj Bulunamadı", Toast.LENGTH_SHORT).show();
                else if (networkResponse !=null && networkResponse.statusCode==500)
                    Toast.makeText(admin_mesaj.this, "Bilinmeyen Bir Hata Oluştu Tekrar Deneyiniz", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(admin_mesaj.this, "Hata"+error, Toast.LENGTH_SHORT).show();
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
    public class ListClickHandler implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3) {
            AlertDialog.Builder builder = new AlertDialog.Builder(admin_mesaj.this);
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
