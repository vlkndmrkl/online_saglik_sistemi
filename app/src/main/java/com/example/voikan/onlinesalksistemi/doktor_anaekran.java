package com.example.voikan.onlinesalksistemi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class doktor_anaekran extends AppCompatActivity {
    ImageButton gecis1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doktor_anaekran);
        gecis1=(ImageButton) findViewById(R.id.imgbtndoktor_hasta_bilgileri);
        gecis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent istek = new Intent(getApplicationContext(), doktor_bilgiler.class);
                startActivity(istek);
            }
        });
    }
}
