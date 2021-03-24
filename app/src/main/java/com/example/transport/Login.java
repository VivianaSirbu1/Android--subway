package com.example.transport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {

    ImageView image_login;
    TextView tv_login;
    TextInputLayout txemail, txpass;
    FirebaseAuth fAuth;
    Button btn_login;
    CheckBox remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        image_login=findViewById(R.id.imagelogin);
        tv_login=findViewById(R.id.tvLogin);

        txemail= findViewById(R.id.login_email);
        txpass=findViewById(R.id.login_pass);
        btn_login= findViewById(R.id.btn_Login);
        remember= findViewById(R.id.checkremember);
        fAuth=FirebaseAuth.getInstance();

        SharedPreferences preference= getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox= preference.getString("remember","");
        if(checkbox.equals("true"))
        {
            Intent intent= new Intent(Login.this, Meniu.class);
            startActivity(intent);

        }else if(checkbox.equals("false")){
            Toast.makeText(this, "Please Sing In", Toast.LENGTH_SHORT).show();
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= txemail.getEditText().getText().toString().trim();
                String pass= txpass.getEditText().getText().toString().trim();

                if(!validateEmail() | !validatePass()){
                    return;
                }

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "Log in successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Meniu.class));
                        }else
                        {
                            Toast.makeText(Login.this, "Error!"+ Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(compoundButton.isChecked())
                {
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                    Toast.makeText(Login.this, "Checked", Toast.LENGTH_SHORT).show();

                }else if(!compoundButton.isChecked())
                {
                    SharedPreferences preferences= getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor= preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                    Toast.makeText(Login.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void callSingup(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }


    public void callForget(View view){
        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
    }

    private boolean validateEmail(){
        String val= txemail.getEditText().getText().toString().trim();
        String emailcheck="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            txemail.setError("Field can not be empty");
            return false;
        }else if(val.isEmpty())
        {
            txemail.setError("Field can not be empty");
            return false;
        }else if(!val.matches(emailcheck)){
            txemail.setError("Invalid Email!");
            return false;
        }
        else{
            txemail.setError(null);
            txemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePass(){
        String val= txpass.getEditText().getText().toString().trim();
        String checkPass="^"+".{4,}"+"$";
        if (val.isEmpty()){
            txpass.setError("Field can not be empty");
            return false;
        }else if(val.isEmpty())
        {
            txpass.setError("Field can not be empty");
            return false;
        }else if(!val.matches(checkPass)){
            txpass.setError("Password should contain 4 characters, no empty space and a special character");
            return false;
        }
        else{
            txpass.setError(null);
            txpass.setErrorEnabled(false);
            return true;
        }
    }

    public void UserConection(View view)
    {
        if(!isConnected(this))
        {
            showCustomDialog();
        }
        if(!validateEmail() | !validatePass()){
            return;
        }

        String _username= txemail.getEditText().getText().toString().trim();
        final String _pass= txpass.getEditText().getText().toString().trim();

    }

    private boolean isConnected(Login login) {
        ConnectivityManager connectivityManager= (ConnectivityManager) login.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wificonn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobconn= connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wificonn !=null && wificonn.isConnected() || (mobconn!=null && mobconn.isConnected())){
            return true;
        }else{
            return false;
        }
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder= new AlertDialog.Builder(Login.this);
        builder.setMessage("Please connect to the internet").setCancelable(false).setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}