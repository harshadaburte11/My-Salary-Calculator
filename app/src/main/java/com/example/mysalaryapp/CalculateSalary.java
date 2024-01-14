package com.example.mysalaryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class CalculateSalary extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Spinner employeeSpinner, monthSpinner;
    private TextView haEditText, taEditText, daEditText, pfEditText;
    private EditText workingDaysEditText,searchEmp;
    private TextView totalSalaryLabel, totalSalaryTextView;
    String selectedName,selectedMonth,searchEmpName;
    Button btnCalculate,btnback,btnSearchEmp;
    private DatabaseReference mydb=FirebaseDatabase.getInstance().getReference().child("employees");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_salary);

        employeeSpinner = findViewById(R.id.employeeSpinner);
        monthSpinner = findViewById(R.id.monthSpinner);
        haEditText = findViewById(R.id.haEditText);
        taEditText = findViewById(R.id.taEditText);
        daEditText = findViewById(R.id.daEditText);
        pfEditText = findViewById(R.id.pfEditText);
        workingDaysEditText = findViewById(R.id.workingDaysEditText);
        totalSalaryLabel = findViewById(R.id.totalSalaryLabel);
        totalSalaryTextView = findViewById(R.id.totalSalaryTextView);
        mAuth = FirebaseAuth.getInstance();
        btnCalculate=findViewById(R.id.btnCalculate);
        btnback=findViewById(R.id.btnback);
        employeeSpinner.setBackgroundColor(Color.WHITE);
        searchEmp=findViewById(R.id.searchEmp);
        btnSearchEmp=findViewById(R.id.btnSearchEmp);
        btnSearchEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                searchEmpName=searchEmp.getText().toString();
                if(TextUtils.isEmpty(searchEmpName))
                {
                    Toast.makeText(CalculateSalary.this, "Enter Name of Employee For Search", Toast.LENGTH_SHORT).show();
                    loadNamesToSpinner();
                    return;
                }
                else {
                    loadNamesToSpinnerBySearch();

                }
            }
        });
        loadNamesToSpinner();
        employeeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle selection of a name from the Spinner
                if (position == 0) {
                    Toast.makeText(CalculateSalary.this, "Please Choose Employee Name", Toast.LENGTH_SHORT).show();
                } else {

                    selectedName = (String) parentView.getItemAtPosition(position);

                    mydb.orderByChild("fullName").startAt(selectedName).endAt(selectedName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Iterate through dataSnapshot to retrieve the records associated with the selected name.
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                User user = userSnapshot.getValue(User.class);
                                if (user != null) {

                                    haEditText.setText(user.getHaStr());
                                    daEditText.setText(user.getDaStr());
                                    taEditText.setText(user.getTaStr());
                                    pfEditText.setText(user.getPfStr());
                                }
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                Toast.makeText(CalculateSalary.this, "Please Select Employee Name", Toast.LENGTH_SHORT).show();
                return;
            }
        });


        // Set up the month spinner
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                // Handle selection of a name from the Spinner
                if (position == 0)
                {
                    Toast.makeText(CalculateSalary.this, "Please Choose Month", Toast.LENGTH_SHORT).show();
                    return;
                } else
                {
                    selectedMonth = (String) parentView.getItemAtPosition(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                Toast.makeText(CalculateSalary.this, "Please Choose Month", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);


        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String haStr = haEditText.getText().toString();
                String taStr = taEditText.getText().toString();
                String daStr = daEditText.getText().toString();
                String pfStr = pfEditText.getText().toString();
                String workingDaysStr = workingDaysEditText.getText().toString();
                // For demonstration, let's just display the sum of the input fields.



                if ((TextUtils.isEmpty(haStr)) || (TextUtils.isEmpty(taStr)) || (TextUtils.isEmpty(daStr)) ||
                        (TextUtils.isEmpty(pfStr)) || (TextUtils.isEmpty(workingDaysStr)) || (TextUtils.isEmpty(selectedName)) || (TextUtils.isEmpty(selectedMonth))) {
                    Toast.makeText(CalculateSalary.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    double ha = Double.parseDouble(haStr);
                    double ta = Double.parseDouble(taStr);
                    double da = Double.parseDouble(daStr);
                    double pf = Double.parseDouble(pfStr);
                    int workingDays = Integer.parseInt(workingDaysStr);
                    mydb.orderByChild("fullName").startAt(selectedName).endAt(selectedName).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Iterate through dataSnapshot to retrieve the records associated with the selected name.
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                            {
                                User user = userSnapshot.getValue(User.class);
                                if (user != null) {

                                    double bs = Double.parseDouble(user.getSalary());
                                    String email=user.getEmail();
                                    double perDaySal=bs/30;
                                    double ded;
                                    //how to calculate salary on basis of working days?
                                    if(workingDays<=3)
                                    {
                                        ded=0;
                                    }
                                    else
                                    {
                                        ded=((workingDays-3) * perDaySal);
                                    }
                                    double totalSalary =bs+ ha + ta + da - pf - ded; // A simple example calculation.
                                    empModel emp = new empModel(ha, ta, da, pf, selectedName, selectedMonth, workingDays,totalSalary,email);


                                    try {
                                        FirebaseDatabase.getInstance().getReference().child("EmpSalaryInfo").push().setValue(emp).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(CalculateSalary.this, "Record inserted Successfully", Toast.LENGTH_SHORT).show();


                                                Log.d("ded", "ded: " + ded);
                                                Log.d("User", "pf: " + pf);
                                                Log.d("User", "HA: " + ha);
                                                Log.d("User", "Da: " + da);
                                                Log.d("User", "TA: " + ta);
                                                Log.d("User", "bs: " + bs);
                                                Log.d("User", "workingDays: " + workingDays);
                                                Log.d("User", "Salary: " + perDaySal);

                                                // A simple example calculation.
                                                totalSalaryLabel.setVisibility(View.VISIBLE);
                                                totalSalaryTextView.setText("Total Salary: " + totalSalary);
                                                totalSalaryTextView.setVisibility(View.VISIBLE);
//                                            Intent intent = new Intent(CalculateSalary.this, AdminOptions.class);
//                                            startActivity(intent);
                                            }
                                        }).addOnCanceledListener(new OnCanceledListener() {
                                            @Override
                                            public void onCanceled() {
                                                Toast.makeText(CalculateSalary.this, "Error: ", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                                    } catch (Exception ee) {
                                        Toast.makeText(CalculateSalary.this, ee.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }}

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
    }


    private void loadNamesToSpinner() {

        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> names = new ArrayList<>();
                //names.add(0,"select Your Name");
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        names.add(user.getFullName());
                    }
                }

                // Populate the Spinner with names

                //
                ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(CalculateSalary.this, android.R.layout.simple_spinner_item, names);
                employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                employeeAdapter.insert("Choose Employee name", 0);
                employeeSpinner.setAdapter(employeeAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
        //on Search Employee Button
        private void loadNamesToSpinnerBySearch () {

            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    List<String> namesBySearch = new ArrayList<>();
                    //names.add(0,"select Your Name");
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                        User user = userSnapshot.getValue(User.class);
                        if (user != null)
                        {
                            if (user.getFullName() != null && user.getFullName().toLowerCase().startsWith(searchEmpName)) {
                                namesBySearch.add(user.getFullName());
                            }

                        }
                    }

                    // Populate the Spinner with names

                    //
                    ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(CalculateSalary.this, android.R.layout.simple_spinner_item, namesBySearch);
                    employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    employeeAdapter.insert("Choose Employee name", 0);
                    employeeSpinner.setAdapter(employeeAdapter);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            btnback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open the "View Salary" form or activity
                    // startActivity(new Intent(LoginOptionsActivity.this, ViewSalaryActivity.class));
                    Intent intent = new Intent(CalculateSalary.this, AdminOptions.class);
                    startActivity(intent);
                }
            });

        }

    }


