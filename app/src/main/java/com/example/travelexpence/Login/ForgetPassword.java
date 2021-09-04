package com.example.travelexpence.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.travelexpence.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

public class ForgetPassword extends AppCompatActivity {
    ImageView back;
    Button nextButton;
    TextInputLayout regEmaile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        regEmaile = findViewById(R.id.email_forget);
        back = findViewById(R.id.back_icon);
        nextButton = findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateEmail()){
                    return;
                }
                Intent intent = new Intent(ForgetPassword.this, MakeSelection.class);
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
        String val = regEmaile.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()){
            regEmaile.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmaile.setError("Invalid email address");
            return false;
        }
        else {
            regEmaile.setError(null);
            regEmaile.setErrorEnabled(false);
            return true;
        }
    }

}