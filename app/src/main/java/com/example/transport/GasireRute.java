package com.example.transport;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class GasireRute extends AppCompatActivity {

    final String DATE_FORMAT = "dd/MM/yyyy";

    Spinner spinner1, spinner2;

    TextView tvData;
    EditText etData;
    DatePickerDialog.OnDateSetListener setListener;
    Button btn_cauta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasire_rute);

        spinner1= (Spinner)findViewById(R.id.spinner1);
        spinner2= (Spinner)findViewById(R.id.spinner2);
        btn_cauta= findViewById(R.id.btnCauta);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(GasireRute.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.numeStatiiPlecare));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(myAdapter);

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(GasireRute.this,
                android.R.layout.simple_list_item_2, getResources().getStringArray(R.array.numeStatiiSosire));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(myAdapter);

        Button btn2 = findViewById(R.id.btnSave);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(GasireRute.this, Istoric.class);
                startActivity(intent);
            }
        });

        tvData = findViewById(R.id.tv_data);
        etData = findViewById(R.id.et_data);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(

                GasireRute.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListener,year ,month ,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
               month= month+1;
               String data = day + "/" + month+ "/" + year;
               tvData.setText(data);
            }
        };

        etData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        GasireRute.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month+1;
                        String data = day+"/"+month+"/"+year;
                        etData.setText(data);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btn_cauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String statieP= spinner1.getSelectedItem().toString().toUpperCase();
                String statieS= spinner2.getSelectedItem().toString().toUpperCase();
                //check condition
                if(statieP.equals("") && statieS.equals("")){
                    Toast.makeText(getApplicationContext(), "Enter both location", Toast.LENGTH_LONG).show();

                }else{
                    //Display track
                    DisplayTrack(statieP, statieS);
                }
            }
        });
    }

    private void DisplayTrack(String statieP, String statieS) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" + statieP + "/" + statieS);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}