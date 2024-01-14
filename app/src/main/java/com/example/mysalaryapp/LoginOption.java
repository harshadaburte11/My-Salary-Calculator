package com.example.mysalaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);

        Button loginadmbtn = findViewById(R.id.loginadmbtn);
        Button loginempbtn = findViewById(R.id.loginempbtn);

        loginadmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle "Login as Admin" options (e.g., registration or salary calculation)
                // Start the corresponding activity or fragment
                Intent intent = new Intent(LoginOption.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        loginempbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the "View Salary" form or activity
                // startActivity(new Intent(LoginOptionsActivity.this, ViewSalaryActivity.class));
                Intent intent = new Intent(LoginOption.this, LoginEmployee.class);
                startActivity(intent);
             }
        });
    }
}