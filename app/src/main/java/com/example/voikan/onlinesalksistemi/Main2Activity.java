package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {
    ImageButton gecis,gecis2,gecis3,gecis4,gecis5,gecis6;
    private String gelen_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Bundle extras = getIntent().getExtras();
        gelen_id= extras.getString("send_string");
        gecis= (ImageButton) findViewById(R.id.imgbtngunluk);
        gecis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),gunluk_bilgiler.class);
                intent.putExtra("send_string", gelen_id);
                startActivity(intent);
            }
        });
        gecis2= (ImageButton) findViewById(R.id.imgbtndoktorum);
        gecis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),kul_doktorum.class);
                intent.putExtra("send_string", gelen_id);
                startActivity(intent);
            }
        });
        gecis3= (ImageButton) findViewById(R.id.imageButton2);
        gecis3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),kullanici_islem_anaekran.class);
                intent.putExtra("send_string", gelen_id);
                startActivity(intent);
            }
        });
        gecis4= (ImageButton) findViewById(R.id.imgbtnsikayet);
        gecis4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),sikayet.class);
                intent.putExtra("send_string", gelen_id);
                startActivity(intent);
            }
        });
        gecis5= (ImageButton) findViewById(R.id.imgbtnrandevu);
        gecis5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),randevular.class);
                intent.putExtra("send_string", gelen_id);
                startActivity(intent);
            }
        });
        gecis6= (ImageButton) findViewById(R.id.imageButton);
        gecis6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),randevu_kontrol.class);
                intent.putExtra("send_string", gelen_id);
                startActivity(intent);
            }
        });
    }
}
