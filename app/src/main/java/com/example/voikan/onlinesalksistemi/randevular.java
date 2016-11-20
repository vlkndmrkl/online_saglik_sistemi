package com.example.voikan.onlinesalksistemi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
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


import static android.R.layout.simple_list_item_1;

public class randevular extends AppCompatActivity {
    EditText et;
    ListView randevu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevular);
        et=(EditText)findViewById(R.id.editTextrandevulardeneme);
        randevu = (ListView) findViewById(R.id.lstrandevular);
        getir();

    }
    protected void getir() {
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/randevu_getir";
        StringRequest MyStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Randevular\" :"+response+"}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Randevular");
                    String randevudizi[]=new String[value.length()];
                    for (int i =0; i< value.length();i++){
                        JSONObject sonuc = value.getJSONObject(i);
                        String id=("id= " + sonuc.getString("_id")+"\n");
                        String ad=("ad= " + sonuc.getString("ad")+"\n");
                        String soyad=("soyad= " + sonuc.getString("soyad")+"\n");
                        String tarih=("tarih= " + sonuc.getString("tarih")+"\n");
                        String username=("username= " + sonuc.getString("username")+"\n");
                        randevudizi[i]= String.valueOf(id+ad+soyad+tarih+username);
                    }
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(randevular.this,simple_list_item_1,randevudizi);
                    randevu.setAdapter(adapter);
                }
                catch (JSONException e) {
                    Toast.makeText(randevular.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(randevular.this, "Bilinmeyen bir hata oluştu", Toast.LENGTH_LONG).show();
            }
        }) ;
        MyRequestQueue.add(MyStringRequest);

    }
}
