package com.example.transport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    String userID;
    TextInputLayout fullName, Username, Email, Pass;
    ImageView imageSingup;
    Button btn_done;
    TextView tv_title;
    RadioGroup radiog;
    DatePicker date;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        btn_done = findViewById(R.id.btn_signup_done);
        tv_title = findViewById(R.id.tv_signup);
        imageSingup = findViewById(R.id.imagesignup);

        radiog= findViewById(R.id.radio_group);

        date= findViewById(R.id.date_picker);
        fullName=findViewById(R.id.signup_name);
        Username=findViewById(R.id.signup_user);
        Email=findViewById(R.id.signup_email);
        Pass=findViewById(R.id.signup_pass);

        fStore= FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), Meniu.class));
            finish();
        }

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email= Email.getEditText().getText().toString().trim();
                final String pass= Pass.getEditText().getText().toString().trim();
                final String fullname=fullName.getEditText().getText().toString().trim();
                final String username= Username.getEditText().getText().toString().trim();
                Date dateDay= new Date(date.getDayOfMonth());
                Date dateMonth= new Date(date.getMonth());
                Date dateYear= new Date(date.getYear());
                final String fDate= dateDay+"/"+dateMonth+"/"+dateYear;
                RadioButton radioButton= findViewById(radiog.getCheckedRadioButtonId());
                final String radioGender= radioButton.getText().toString().toUpperCase();


                if(!validateName() | !validateUser() | !validateEmail() | !validatePass()| !validateGender()|!validateAge()){
                    return;
                }
                fAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            //send verification email
                            FirebaseUser fuser= fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(SignUp.this, "Verification Email has been sent!", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SignUp.this, "user Profile is created for"+ e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                            Toast.makeText(SignUp.this, "Error: Email not sent!!", Toast.LENGTH_SHORT).show();
                            userID= fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference= fStore.collection("users").document(userID);
                            Map<String, Object> user= new HashMap<>();
                            user.put("FullName", fullname);
                            user.put("UserName", username);
                            user.put("Email", email);
                            user.put("DataN", fDate);
                            user.put("Gender", radioGender);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(SignUp.this, "user Profile is created for", Toast.LENGTH_SHORT).show();

                                }
                            });
                            startActivity(new Intent(getApplicationContext(), Meniu.class));
                        }else
                        {
                            Toast.makeText(SignUp.this, "Error!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }

    private boolean validateName(){
        String val= fullName.getEditText().getText().toString().trim();
        if (val.isEmpty()){
            fullName.setError("Field can not be empty");
            return false;
        }
        else{
            fullName.setError(null);
            fullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUser(){
        String val= Username.getEditText().getText().toString().trim();
        String check="\\A\\w{1,20}\\z";
        if (val.isEmpty()){
            Username.setError("Field can not be empty");
            return false;
        }else if(val.length()>20)
        {
            Username.setError("Username is too big!");
            return false;
        }else if(!val.matches(check)){
            Username.setError("No spaces are allowed!");
            return false;
        }
        else{
            Username.setError(null);
            Username.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail(){
        String val= Email.getEditText().getText().toString().trim();
        String emailcheck="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (val.isEmpty()){
            Email.setError("Field can not be empty");
            return false;
        }else if(val.isEmpty())
        {
            Email.setError("Field can not be empty");
            return false;
        }else if(!val.matches(emailcheck)){
            Email.setError("Invalid Email!");
            return false;
        }
        else{
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePass(){
        String val= Pass.getEditText().getText().toString().trim();
        String checkPass="^"+".{4,}"+"$";
        if (val.isEmpty()){
            Pass.setError("Field can not be empty");
            return false;
        }else if(val.isEmpty())
        {
            Pass.setError("Field can not be empty");
            return false;
        }else if(!val.matches(checkPass)){
            Pass.setError("Password should contain 4 characters, no empty space and a special character");
            return false;
        }
        else{
            Pass.setError(null);
            Pass.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateGender(){
        if(radiog.getCheckedRadioButtonId()==-1){
            Toast.makeText(this, "Please Select Gener", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear= Calendar.getInstance().get(Calendar.YEAR);
        int Ageuser= date.getYear();
        int ValidAge= currentYear- Ageuser;
        if(ValidAge < 12)
        {
            Toast.makeText(this, "Age is not valid", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
}