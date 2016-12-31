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

import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class sikayet extends AppCompatActivity {
    TextView tv;
    Button btn,btn2;
    private String gelen_id,doktor_id;
    private int status,statuskodmesajgonder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sikayet);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        btn=(Button) findViewById(R.id.btnsikayet_gonder);
        btn2 = (Button) findViewById(R.id.btnmesaj_goster);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_bul();
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
    public void id_bul(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/doktor_id_bul";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (status==200){
                    doktor_id=new String(response.substring(47,(response.length()-2)));
                    mesaj_gonder();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Toast.makeText(sikayet.this, "Bilinmeyen Bir Hata Olşutu", Toast.LENGTH_SHORT).show();
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
    protected void mesaj_gonder(){
        final String mesajiniz = ((TextView) findViewById(R.id.etxtsikayet_mesaj)).getText().toString();
        final String baslik = ((TextView) findViewById(R.id.etxtsikayet_baslik)).getText().toString();
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/mesaj_gonder";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(sikayet.this, "Mesajınız Gönderildi", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(sikayet.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                statuskodmesajgonder=response.statusCode;
                return super.parseNetworkResponse(response);
            }

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("gonderen_id", gelen_id);
                MyData.put("alici_id", doktor_id);
                MyData.put("baslik", baslik);
                MyData.put("mesaj", mesajiniz);
                MyData.put("okundu", "0");
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }

}
