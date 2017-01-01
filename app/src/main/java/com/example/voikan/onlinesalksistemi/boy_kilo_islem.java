package com.example.voikan.onlinesalksistemi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class boy_kilo_islem extends AppCompatActivity {

    EditText etboy,etkilo;
    TextView tvboy,tvkilo;
    Button btnkaydet;
    int status;
    String gelen_id,doktor_id_gelen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boy_kilo_islem);
        etboy=(EditText) findViewById(R.id.etxtboy);
        etkilo=(EditText) findViewById(R.id.etxtkilo);
        tvboy=(TextView) findViewById(R.id.boygoster);
        tvkilo=(TextView) findViewById(R.id.kilogoster);
        tvboy.setText("--");
        tvkilo.setText("--");
        btnkaydet=(Button) findViewById(R.id.btnboy_kilo_kaydet);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        kayıt_getir();
        btnkaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etboy!=null && etkilo !=null)
                    id_bul();
            }
        });
    }

    protected void kayıt_getir(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/boy_kilo_getir";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Randevular\" :" + response + "}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Randevular");
                    for (int i = 0; i < value.length(); i++) {
                        JSONObject sonuc = value.getJSONObject(i);
                        String boy = (sonuc.getString("boy"));
                        String kilo = (sonuc.getString("kilo"));
                        if (boy!=null && kilo !=null){
                            tvboy.setText(boy);
                            tvkilo.setText(kilo);
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(boy_kilo_islem.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(boy_kilo_islem.this, "Bilinmeyen Bir Hata Olşutu", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("user_id", gelen_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
    protected void id_bul(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/doktor_id_bul";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (status==200){
                    doktor_id_gelen=new String(response.substring(47,(response.length()-2)));
                    kaydet(doktor_id_gelen);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(boy_kilo_islem.this, "Bilinmeyen Bir Hata Olşutu", Toast.LENGTH_SHORT).show();
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
    protected void kaydet(final String doktor_id){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/boy_kilo_kaydet";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(boy_kilo_islem.this, "Kayıt İşlemi Başarılı", Toast.LENGTH_SHORT).show();
                tvkilo.setText(etkilo.getText().toString());
                tvboy.setText(etboy.getText().toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(boy_kilo_islem.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("user_id", gelen_id);
                MyData.put("doktor_id", doktor_id);
                MyData.put("boy", etboy.getText().toString());
                MyData.put("kilo", etkilo.getText().toString());
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
