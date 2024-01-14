package com.example.mysalaryapp;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignIn;


    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        auth = FirebaseAuth.getInstance();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if ((TextUtils.isEmpty(email))) {
                    Toast.makeText(LoginActivity.this, "Enter The email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Enter The Password", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(LoginActivity.this, "enter valid email", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 8) {
                    Toast.makeText(LoginActivity.this, "Password need to be longer then 8 character", Toast.LENGTH_SHORT).show();
                } else {

                    if(email.equals("admin11@gmail.com")&&password.equals("admin123"))
                    {
                        Toast.makeText(LoginActivity.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, AdminOptions.class);
                                    startActivity(intent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Email and Password does not match", Toast.LENGTH_SHORT).show();
                    }


//                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()){
//                                try {
//                                    Intent intent = new Intent(LoginActivity.this, AdminOptions.class);
//                                    startActivity(intent);
//                                    finish();
//                                }catch (Exception e) {
//                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            }else {
//                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                    });
                }
            }
      });


                    }

                }






