package com.example.voikan.onlinesalksistemi;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.R.layout.simple_list_item_1;

public class randevu_kontrol extends AppCompatActivity {
    Button tarih_degistir,btn11,btn1130,btn12;
    TextView tarihTextView;
    String doktor_id,gelen_id,tarih;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randevu_kontrol);
        tarihTextView=(TextView) findViewById(R.id.tarihTv);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        btn11=(Button) findViewById(R.id.btn11);
        btn1130=(Button) findViewById(R.id.btn1130);
        btn12=(Button) findViewById(R.id.btn12);
        btn12.setBackgroundColor(Color.GREEN);
        tarih_degistir=(Button) findViewById(R.id.btnTarih_degistir);
        final Calendar mcurrentTime = Calendar.getInstance();
        int yearr = mcurrentTime.get(Calendar.YEAR);
        int monthh = mcurrentTime.get(Calendar.MONTH);
        int dayy = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        final String gun=String.valueOf(dayy);
        if(gun.length()<2)
            tarihTextView.setText("0"+dayy + "." + (monthh+1)+ "."+yearr);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
        else
            tarihTextView.setText(dayy + "." + (monthh+1)+ "."+yearr);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
        doktor_id_bul();
        tarih=tarihTextView.getText().toString();
        tarih_degistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
                int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
                int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz
                DatePickerDialog datePicker;//Datepicker objemiz
                datePicker = new DatePickerDialog(randevu_kontrol.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                        // TODO Auto-generated method stub
                        String gun=String.valueOf(dayOfMonth);
                        if(gun.length()<2)
                            tarihTextView.setText( "0"+dayOfMonth + "." + (monthOfYear+1)+ "."+year);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                        else
                            tarihTextView.setText( dayOfMonth + "." + (monthOfYear+1)+ "."+year);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                    }
                },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "Ayarla",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    tarih=tarihTextView.getText().toString();
                                    randevu_durum();
                                }
                            }
                        });
                datePicker.show();
            }
        });
    }

    public void doktor_id_bul(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/doktor_id_bul";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (status==200){
                    doktor_id=new String(response.substring(47,(response.length()-2)));
                    randevu_durum();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(randevu_kontrol.this, "Bilinmeyen Bir Hata Olşutu", Toast.LENGTH_SHORT).show();
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
    public void randevu_durum(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/randevu_konrol";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                final String strJson = "{\"Saatler\" :" + response + "}";
                try {
                    JSONObject o = new JSONObject(strJson);
                    JSONArray value = o.getJSONArray("Saatler");
                    String randevudizi[] = new String[value.length()];
                    for (int i = 0; i < value.length(); i++) {
                        JSONObject sonuc = value.getJSONObject(i);
                        String saat = sonuc.getString("randevu_saati");
                            randevudizi[i] = String.valueOf(saat);
                    }
                    for (int i = 0; i < value.length(); i++) {
                        if (randevudizi[i].equals("11:00"))
                            btn11.setBackgroundColor(Color.RED);
                        else if (randevudizi[i]=="11:30")
                            btn11.setBackgroundColor(Color.RED);

                    }
                } catch (JSONException e) {
                    Toast.makeText(randevu_kontrol.this, "HATA", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(randevu_kontrol.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                status=response.statusCode;
                return super.parseNetworkResponse(response);
            }

            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("doktor_id", doktor_id);
                MyData.put("tarih", tarih);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);
    }
}
