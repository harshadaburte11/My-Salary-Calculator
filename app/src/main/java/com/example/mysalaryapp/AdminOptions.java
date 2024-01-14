package com.example.mysalaryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        Button registeremp = findViewById(R.id.registeremp);
        Button calculatesalary = findViewById(R.id.calculatesalary);


        registeremp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the Registration Activity
                Intent intent = new Intent(AdminOptions.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        calculatesalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement the search employee ID functionality
                Intent intent = new Intent(AdminOptions.this, CalculateSalary.class);
                startActivity(intent);
            }
        });


    }
}