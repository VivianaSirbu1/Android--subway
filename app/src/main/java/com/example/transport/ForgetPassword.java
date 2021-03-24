package com.example.transport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassword extends AppCompatActivity {
    TextInputLayout txforget;
    Button btn_forget;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        txforget= findViewById(R.id.txforgetpass);
        btn_forget=findViewById(R.id.btn_forgetpass);
        fAuth=FirebaseAuth.getInstance();
    }

    public void callForget2(View view){
        if(!validateEmail())
        {
            return;

        }
        else {
            //sent resent link
            String mail= txforget.getEditText().getText().toString();
            fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {
                                                                            Toast.makeText(ForgetPassword.this, "Link sent to your email!", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }

            ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ForgetPassword.this, "Error! Reset link is not sent!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private boolean validateEmail(){
        String val= txforget.getEditText().getText().toString().trim();
        String emailcheck="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            txforget.setError("Field can not be empty");
            return false;
        }else if(val.isEmpty())
        {
            txforget.setError("Field can not be empty");
            return false;
        }else if(!val.matches(emailcheck)){
            txforget.setError("Invalid Email!");
            return false;
        }
        else{
            txforget.setError(null);
            txforget.setErrorEnabled(false);
            return true;
        }
    }
}