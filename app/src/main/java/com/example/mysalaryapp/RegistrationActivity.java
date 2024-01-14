package com.example.mysalaryapp;//package com.example.mysalaryapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mysalaryapp.R;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.auth.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText emailEditText;
    private EditText mobileEditText;
    private EditText addressEditText,salary, haEditText, taEditText, daEditText, pfEditText;
    private EditText designationEditText,selectMonth;
    private Button signUpButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mydb;
    Calendar calendar;
   String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
String selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        mydb=FirebaseDatabase.getInstance().getReference().child("employees");
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        addressEditText = findViewById(R.id.addressEditText);
        signUpButton = findViewById(R.id.signUpButton);
        selectMonth=findViewById(R.id.selectMonth);
        calendar = Calendar.getInstance();
        salary=findViewById(R.id.salary);
        fullNameEditText = findViewById(R.id.fullNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        addressEditText = findViewById(R.id.addressEditText);
        designationEditText= findViewById(R.id.designationEditText);
        haEditText = findViewById(R.id.ha);
        taEditText = findViewById(R.id.ta);
        daEditText = findViewById(R.id.da);
        pfEditText = findViewById(R.id.pf);

        signUpButton = findViewById(R.id.signUpButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = fullNameEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String mobile = mobileEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String designation = designationEditText.getText().toString();
                String esalary=salary.getText().toString();

                String haStr = haEditText.getText().toString();
                String taStr = taEditText.getText().toString();
                String daStr = daEditText.getText().toString();
                String pfStr = pfEditText.getText().toString();
                // For demonstration, let's just display the sum of the input fields.

                // Perform validation for each field
                if ((TextUtils.isEmpty(fullName))||(TextUtils.isEmpty(username))||(TextUtils.isEmpty(password))||
                        (TextUtils.isEmpty(email))||(TextUtils.isEmpty(mobile))||(TextUtils.isEmpty(address))||(TextUtils.isEmpty(designation))||(TextUtils.isEmpty(haStr))||(TextUtils.isEmpty(taStr))||(TextUtils.isEmpty(daStr))||(TextUtils.isEmpty(pfStr))) {
                    Toast.makeText(RegistrationActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                }
                else if (!email.matches(emailPattern)) {
                    Toast.makeText(RegistrationActivity.this, "enter valid email", Toast.LENGTH_SHORT).show();
                }else if(password.length()<8){
                    Toast.makeText(RegistrationActivity.this, "Password need to be longer then 8 character", Toast.LENGTH_SHORT).show();
                }else{



                    FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String userId = mAuth.getCurrentUser().getUid();
                                       // Toast.makeText(RegistrationActivity.this, "UID :" + userId, Toast.LENGTH_SHORT).show();
//                                        double ha = Double.parseDouble(haStr);
//                                        double ta = Double.parseDouble(taStr);
//                                        double da = Double.parseDouble(daStr);
//                                        double pf = Double.parseDouble(pfStr);
                                        User user = new User(fullName, username, email, password, mobile, address, designation, esalary, selectedDate,haStr,taStr,daStr,pfStr);
                                       try {
                                           mydb.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                               @Override
                                               public void onComplete(@NonNull Task<Void> task) {
                                                   Toast.makeText(RegistrationActivity.this, "Record inserted Successfully", Toast.LENGTH_SHORT).show();
                                                   Intent intent = new Intent(RegistrationActivity.this, AdminOptions.class);
                                                   startActivity(intent);
                                               }
                                           }).addOnCanceledListener(new OnCanceledListener() {
                                               @Override
                                               public void onCanceled()
                                               {
                                                   Toast.makeText(RegistrationActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                               }
                                           });
                                       }
                                       catch (Exception ee)
                                       {
                                           Toast.makeText(RegistrationActivity.this, ee.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                           }
                                    else {
                                        Toast.makeText(RegistrationActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
       });




    }
    public void showDatePicker(View view) {
         DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Handle the selected date here
                 selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                // Now you can use the selectedDate as needed
                selectMonth.setText(selectedDate);
            }};

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                onDateSetListener,
                2023, 0, 1
        );
        datePickerDialog.show();
    }
}


