package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class kullanici_islem_anaekran extends AppCompatActivity {
    ImageButton btn,btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_islem_anaekran);
        btn= (ImageButton) findViewById(R.id.imgbtnsifre_degistir);
        btn2= (ImageButton) findViewById(R.id.imgbtnilac_islemleri);
        btn3= (ImageButton) findViewById(R.id.imgbtnboy_kilo_islem);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent istek = new Intent(getApplicationContext(), sifre_degistirme.class);
                startActivity(istek);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent istek = new Intent(getApplicationContext(), ilac_islemleri.class);
                startActivity(istek);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent istek = new Intent(getApplicationContext(), boy_kilo_islem.class);
                startActivity(istek);
            }
        });
    }
}
