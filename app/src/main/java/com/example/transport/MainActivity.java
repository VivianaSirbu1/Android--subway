package com.example.transport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public Button button;
    private static int SPLASH_SCREEN= 5000;

    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ANIMATIONS
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image= findViewById(R.id.imageView1);
        logo=findViewById(R.id.textView1);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);

        button=findViewById(R.id.button_start);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMeniu();
            }
        });
    }

    public void openMeniu(){
        Intent intent= new Intent(MainActivity.this,Meniu.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "apel metoda onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "apel metoda onResume()");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "apel metoda onStop()");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "apel metoda onDestroy()");

    }
}