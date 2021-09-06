package com.example.travelexpence.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.travelexpence.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase;
    ImageView back;
    Button alreadyAccount,regButton;
    TextInputLayout regName,regUsername,regEmail,regPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_bar);
        regName = findViewById(R.id.reg_name);
        regUsername= findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);
        regButton=findViewById(R.id.signUp);
        back=findViewById(R.id.back_icon);
        alreadyAccount = findViewById(R.id.alreadyAcc);
        alreadyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if(val.isEmpty()){
            regName.setError("Field cannot be empty");
            return false;
        }else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateUserName(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpaces = "^[A-Za-z]\\w{5,29}$";
        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return false;
        }else if(val.length()>=15){
            regUsername.setError("Username too long");
            return false;
        }else if(!val.matches(noWhiteSpaces)){
            regUsername.setError("Spaces Not Allowed");
            return false;
        } else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{4,}" +                // at least 4 characters
                "$";
        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }else if (val.matches(passwordVal)){
            regPassword.setError("Password is too weak");
            return false;
        } else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view){
        if(!validateEmail() | !validateName() | !validateUserName() | !validatePassword()){
            return;
        }
        mDatabase=FirebaseDatabase.getInstance().getReference();
        //get all the values
        String fullName = regName.getEditText().getText().toString();
        String userName = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userHelperClass helperClass = new userHelperClass(fullName,userName,password,email);
                    mDatabase.child("users").child(userName).setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUpScreen.this, "Registration Successfully!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }else {
                                Toast.makeText(SignUpScreen.this, "Registration Unsuccessfully Please Try Again!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }else {
                    Toast.makeText(SignUpScreen.this, "Registration Unsuccessfully Please Try Again!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }


}