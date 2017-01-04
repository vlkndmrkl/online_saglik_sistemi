package com.example.voikan.onlinesalksistemi;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
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
    Button tarih_degistir,btn11,btn1130,btn12,btn1230,btn13,btn1330;
    TextView tarihTextView,yazi;
    String doktor_id;
    String gelen_id;

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    String tarih;
    String saat;
    String randevu_tarih_kayıt_icin;
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
        btn1230=(Button) findViewById(R.id.btn1230);
        btn13=(Button) findViewById(R.id.btn13);
        btn1330=(Button) findViewById(R.id.btn1330);
        yazi=(TextView) findViewById(R.id.txtacıklamarandevu);

        yazi.setTextColor(Color.RED);
        btn12.setBackgroundColor(Color.GREEN);
        btn11.setBackgroundColor(Color.GREEN);
        btn1130.setBackgroundColor(Color.GREEN);
        btn1230.setBackgroundColor(Color.GREEN);
        btn13.setBackgroundColor(Color.GREEN);
        btn1330.setBackgroundColor(Color.GREEN);

        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)btn11.getBackground()).getColor();
                if (color ==-16711936) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(randevu_kontrol.this);
                    builder.setTitle("Randevu Detayları");
                    builder.setMessage("Randevu Tarihi: "+tarihTextView.getText().toString() +"\nRandevu Saati: 11:00 \nRandevuyu onaylıyor musunuz?");
                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saat="11:00";
                            randevu_tarih_kayıt_icin=tarihTextView.getText().toString();
                            randevu_kayıt_olustur();
                    }
                });
                builder.show();
                }
            }
        });
        btn1130.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)btn1130.getBackground()).getColor();
                if (color ==-16711936) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(randevu_kontrol.this);
                    builder.setTitle("Randevu Detayları");
                    builder.setMessage("Randevu Tarihi: " + tarihTextView.getText().toString() + "\nRandevu Saati: 11:30 \nRandevuyu onaylıyor musunuz?");

                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saat = "11:30";
                            randevu_tarih_kayıt_icin = tarihTextView.getText().toString();
                            randevu_kayıt_olustur();
                        }
                    });
                    builder.show();
                }
            }
        });
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)btn12.getBackground()).getColor();
                if (color ==-16711936) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(randevu_kontrol.this);
                    builder.setTitle("Randevu Detayları");
                    builder.setMessage("Randevu Tarihi: " + tarihTextView.getText().toString() + "\nRandevu Saati: 12:00 \nRandevuyu onaylıyor musunuz?");

                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saat = "12:00";
                            randevu_tarih_kayıt_icin = tarihTextView.getText().toString();
                            randevu_kayıt_olustur();
                        }
                    });
                    builder.show();
                }
            }
        });
        btn1230.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)btn1230.getBackground()).getColor();
                if (color ==-16711936) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(randevu_kontrol.this);
                    builder.setTitle("Randevu Detayları");
                    builder.setMessage("Randevu Tarihi: " + tarihTextView.getText().toString() + "\nRandevu Saati: 12:30 \nRandevuyu onaylıyor musunuz?");

                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saat = "12:30";
                            randevu_tarih_kayıt_icin = tarihTextView.getText().toString();
                            randevu_kayıt_olustur();
                        }
                    });
                    builder.show();
                }
            }
        });
        btn13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)btn13.getBackground()).getColor();
                if (color ==-16711936) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(randevu_kontrol.this);
                    builder.setTitle("Randevu Detayları");
                    builder.setMessage("Randevu Tarihi: " + tarihTextView.getText().toString() + "\nRandevu Saati: 13:00 \nRandevuyu onaylıyor musunuz?");

                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saat = "13:00";
                            randevu_tarih_kayıt_icin = tarihTextView.getText().toString();
                            randevu_kayıt_olustur();
                        }
                    });
                    builder.show();
                }
            }
        });
        btn1330.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ((ColorDrawable)btn1330.getBackground()).getColor();
                if (color ==-16711936) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(randevu_kontrol.this);
                    builder.setTitle("Randevu Detayları");
                    builder.setMessage("Randevu Tarihi: " + tarihTextView.getText().toString() + "\nRandevu Saati: 13:30 \nRandevuyu onaylıyor musunuz?");

                    builder.setNegativeButton("İPTAL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saat = "13:30";
                            randevu_tarih_kayıt_icin = tarihTextView.getText().toString();
                            randevu_kayıt_olustur();
                        }
                    });
                    builder.show();
                }
            }
        });


        tarih_degistir=(Button) findViewById(R.id.btnTarih_degistir);
        final Calendar mcurrentTime = Calendar.getInstance();
        int yearr = mcurrentTime.get(Calendar.YEAR);
        int monthh = mcurrentTime.get(Calendar.MONTH);
        int dayy = mcurrentTime.get(Calendar.DAY_OF_MONTH);
        final String gun=String.valueOf(dayy);
        if(gun.length()<2)
            tarihTextView.setText("0"+dayy + "." + (monthh+1)+ "."+yearr);
        else
            tarihTextView.setText(dayy + "." + (monthh+1)+ "."+yearr);
        doktor_id_bul();
        setTarih(tarihTextView.getText().toString());
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
                        if(gun.length()<2) {
                            tarihTextView.setText("0" + dayOfMonth + "." + (monthOfYear + 1) + "." + year);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                            setTarih(tarihTextView.getText().toString());
                            randevu_durum();

                        }
                            else {
                            tarihTextView.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);//Ayarla butonu tıklandığında textview'a yazdırıyoruz
                            setTarih(tarihTextView.getText().toString());
                            randevu_durum();
                        }
                        }
                },year,month,day);//başlarken set edilcek değerlerimizi atıyoruz
                datePicker.setTitle("Tarih Seçiniz");
                datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "Ayarla",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == DialogInterface.BUTTON_POSITIVE) {
                                    setTarih(tarihTextView.getText().toString());
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
        btn12.setBackgroundColor(Color.GREEN);
        btn11.setBackgroundColor(Color.GREEN);
        btn1130.setBackgroundColor(Color.GREEN);
        btn1230.setBackgroundColor(Color.GREEN);
        btn13.setBackgroundColor(Color.GREEN);
        btn1330.setBackgroundColor(Color.GREEN);

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
                        if (randevudizi[i].equals("11:30"))
                            btn1130.setBackgroundColor(Color.RED);
                        if (randevudizi[i].equals("12:00"))
                            btn12.setBackgroundColor(Color.RED);
                        if (randevudizi[i].equals("12:30"))
                            btn1230.setBackgroundColor(Color.RED);
                        if (randevudizi[i].equals("13:00"))
                            btn13.setBackgroundColor(Color.RED);
                        if (randevudizi[i].equals("13:30"))
                            btn1330.setBackgroundColor(Color.RED);
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
    public void randevu_kayıt_olustur(){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(this);
        String url = "http://mynodeapp3.herokuapp.com/randevu_ekle";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(randevu_kontrol.this, "Randevu Oluşturuldu", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(randevu_kontrol.this, "Bilinmeyen Bir Hata Oluştu", Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("user_id", gelen_id);
                MyData.put("tarih", randevu_tarih_kayıt_icin);
                MyData.put("randevu_saati", saat);
                MyData.put("doktor_id", doktor_id);
                return MyData;
            }
        };
        MyRequestQueue.add(MyStringRequest);

    }
}