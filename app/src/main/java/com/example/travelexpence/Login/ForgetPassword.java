package com.example.travelexpence.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.travelexpence.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassword extends AppCompatActivity {
    ImageView back;
    Button nextButton;
    TextInputLayout regEmail;
    ProgressBar progressBar;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        progressBar = findViewById(R.id.progress_bar);
        auth = FirebaseAuth.getInstance();
        regEmail = findViewById(R.id.email_forget);
        back = findViewById(R.id.back_icon);
        nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmail()){
                    return;
                }
                resetPassword();
                Intent intent = new Intent(ForgetPassword.this, code_page.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPassword.this, LoginScreen.class);
                startActivity(intent);
            }
        });

    }
    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString().trim();
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

    private void resetPassword(){
        String email = regEmail.getEditText().getText().toString().trim();
            progressBar.setVisibility(View.VISIBLE);
            auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgetPassword.this, "Check your email to reset your password!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(ForgetPassword.this, "Invalid Email!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });
    }

}