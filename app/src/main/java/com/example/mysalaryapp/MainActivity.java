package com.example.mysalaryapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        signInButton = findViewById(R.id.signInButton);
        Button searchEmployeeButton = findViewById(R.id.searchEmployeeButton);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the Registration Activity
                Intent intent = new Intent(MainActivity.this, LoginOption.class);
                startActivity(intent);
            }
        });

        searchEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(MainActivity.this, SearchEmpID.class);
                startActivity(intent);

            }
        });
    }
}