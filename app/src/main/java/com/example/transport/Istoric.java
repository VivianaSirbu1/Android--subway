package com.example.transport;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Istoric extends AppCompatActivity {

    ListView listView;
    ArrayList<String> list;
    Button btn_ist, btn_DB;
    EditText et_statieplecare, et_id;
    EditText et_statiesosire, et_ruta;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric);

        listView=findViewById(R.id.lv_istoric);
        btn_ist=findViewById(R.id.btn_saveist);
        btn_DB= findViewById(R.id.btn_BD);
        et_statieplecare=findViewById(R.id.et_statieplecare);
        et_statiesosire=findViewById(R.id.et_statiesosire);
        et_id= findViewById(R.id.et_id);
        et_ruta= findViewById(R.id.et_ruta);

        list = new ArrayList<String>();
        arrayAdapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        btn_ist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
              String numestatiep= et_statieplecare.getText().toString();
              String numestaties= et_statiesosire.getText().toString();
              String numeid= et_id.getText().toString();
              String numeruta=et_ruta.getText().toString();

              list.add("Id"+numeid+" statia de plecare: "+numestatiep+" destinatie: "+ numestaties+ " ruta:"+ numeruta);
              listView.setAdapter(arrayAdapter);
              arrayAdapter.notifyDataSetChanged();

            }
        });

        btn_DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Metrou m= new Metrou(et_ruta.getText().toString(),et_statieplecare.getText().toString(), et_statiesosire.getText().toString());

                MetrouDB metrouDB= MetrouDB.getInstanta(getApplicationContext());
                Random random = new Random();
                Conductor conductor1= new Conductor(random.nextInt(30)+1,"Sirbu Viviana","vivianasirbu@yahoo.com","feminin","27/07/1999");
                Conductor conductor2= new Conductor(random.nextInt(30)+1,"Oana Andra","andraoana2499@gmail.com","feminin","24/09/1999");

                metrouDB.getConsuctoriDao().insert(conductor1);
                metrouDB.getConsuctoriDao().insert(conductor2);
                metrouDB.getMetrouDao().insert(m);

                List<Metrou> listaMetrouri= metrouDB.getMetrouDao().getAll();
                if(listaMetrouri!=null)
                    for(Metrou metrou: listaMetrouri)
                        Toast.makeText(getApplicationContext(), metrou.toString(), Toast.LENGTH_LONG).show();
            }
        });





    }
}