package com.example.transport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Meniu extends AppCompatActivity {

    ImageButton logo_image;
    CardView card_rute, card_login, card_info, card_program;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meniu);

        card_rute=findViewById(R.id.card_ruta);
        card_rute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Meniu.this, GasireRute.class);
                startActivity(intent);
            }
        });

        card_login=findViewById(R.id.card_login);
        card_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Meniu.this, Login.class);
                startActivity(intent);
            }
        });

        card_info=findViewById(R.id.card_info);
        card_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Meniu.this, Istoric.class);
                startActivity(intent);
            }
        });


        card_program=findViewById(R.id.card_program);
        card_program.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Meniu.this, Locatii.class);
                startActivity(intent);
            }
        });

        logo_image= findViewById(R.id.image_ParseXML);
        logo_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Meniu.this, JSONActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Logout(View view)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }

}