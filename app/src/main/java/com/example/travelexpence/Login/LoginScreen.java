package com.example.travelexpence.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.Common.main_screen;
import com.example.travelexpence.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginScreen extends AppCompatActivity {
    Button buttonSignUp,forgetPassword,login;
    TextInputLayout Email,Password;
    private FirebaseAuth mAuth;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);


        progressBar = findViewById(R.id.progress_bar);
        buttonSignUp = findViewById(R.id.signUpButton);
        forgetPassword=findViewById(R.id.forget_Password);

        Password = findViewById(R.id.password);
        Email = findViewById(R.id.email);

        login = findViewById(R.id.go);
        mAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, ForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, SignUpScreen.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private Boolean validateEmail(){
        String val = Email.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            Email.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            Email.setError("Invalid email address");
            return false;
        }
        else {
            Email.setError(null);
            Email.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = Password.getEditText().getText().toString();
        String passwordVal = "^" +
                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                "(?=\\S+$)" +            // no white spaces
                ".{4,}" +                // at least 4 characters
                "$";
        if(val.isEmpty()){
            Password.setError("Field cannot be empty");
            return false;
        }else if (val.matches(passwordVal)){
            Password.setError("Password is too weak");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }

    public void userLogin(View view){

        if(!validateEmail() | !validatePassword()){
            return;
        }
        String email = Email.getEditText().getText().toString().trim();
        String password = Password.getEditText().getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // redirect to user Profile
                        Intent intent = new Intent(LoginScreen.this, main_screen.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LoginScreen.this,"Login Successful",Toast.LENGTH_LONG).show();

                        progressBar.setVisibility(View.GONE);

                    }else{
                        Toast.makeText(LoginScreen.this,"Login failed! Please check your password or Email",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
            }
        });




    }
}