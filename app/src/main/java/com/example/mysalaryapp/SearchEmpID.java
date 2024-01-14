package com.example.mysalaryapp;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
 import java.util.ArrayList;
import java.util.List;

public class SearchEmpID extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Spinner spinner;
    private TextView uname, pass;
    private EditText emailEdtText, searchEmp;
    private String empEmail, searchEmpName;
    private Button btnSearchEmp;
    private DatabaseReference mydb = FirebaseDatabase.getInstance().getReference().child("employees");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_emp_id);

        mAuth = FirebaseAuth.getInstance();
        uname = findViewById(R.id.uname);
        pass = findViewById(R.id.pass);
        emailEdtText = findViewById(R.id.emailEdtText);
        spinner = findViewById(R.id.nameSpinner);
        spinner.setBackgroundColor(Color.WHITE);
        Button searchButton = findViewById(R.id.searchButton);
        loadNamesToSpinner();
        searchEmp = findViewById(R.id.searchEmp);
        btnSearchEmp = findViewById(R.id.btnSearchEmp);
        btnSearchEmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEmpName = searchEmp.getText().toString();
                if (TextUtils.isEmpty(searchEmpName)) {
                    Toast.makeText(SearchEmpID.this, "Enter Name of Employee For Search", Toast.LENGTH_SHORT).show();
                    loadNamesToSpinner();
                    return;
                } else {
                    loadNamesToSpinnerBySearch();

                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Handle selection of a name from the Spinner
                if (position == 0) {
                    Toast.makeText(SearchEmpID.this, "Please Choose Your Name", Toast.LENGTH_SHORT).show();
                } else {
                    String selectedName = (String) parentView.getItemAtPosition(position);

                }
                // Fetch records associated with the selected name and display them.
                // You can implement this in the "okButton" click listener.
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where nothing is selected (if needed).
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empEmail = emailEdtText.getText().toString();
                // Fetch and display records associated with the selected name.
                String selectedName = (String) spinner.getSelectedItem();
                fetchAndDisplayRecords(selectedName);
            }
        });


    }


    private void fetchAndDisplayRecords(String selectedName) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("employees");

        usersRef.orderByChild("fullName").startAt(selectedName).endAt(selectedName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Iterate through dataSnapshot to retrieve the records associated with the selected name.
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        // Display user information or perform actions as needed.
//                        Log.d("User", "Name: " + user.getFullName());
//                        Log.d("User", "Username: " + user.getUsername());
//                        Log.d("User", "Email: " + user.getEmail());
//                        Log.d("User", "Email: " + user.getPassword());
//                        uname.setText("Username: " + user.getUsername());
//                        pass.setText("Password: " + user.getPassword());
//                        uname.setVisibility(View.VISIBLE);
//                        pass.setVisibility(View.VISIBLE);
                        if (empEmail.equals(user.getEmail())) {
                            uname.setText("Username: " + user.getUsername());
                            pass.setText("Password: " + user.getPassword());
                            uname.setVisibility(View.VISIBLE);
                            pass.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(SearchEmpID.this, "Record not Found", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors as needed
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchEmpID.this, android.R.layout.simple_spinner_item, names);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                adapter.insert("Choose your name", 0);

                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadNamesToSpinnerBySearch() {

        mydb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> namesBySearch = new ArrayList<>();
                //names.add(0,"select Your Name");
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {

                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        if (user.getFullName() != null && user.getFullName().toLowerCase().startsWith(searchEmpName)) {
                            namesBySearch.add(user.getFullName());
                        }

                    }
                }

                // Populate the Spinner with names

                //
                ArrayAdapter<String> employeeAdapter = new ArrayAdapter<>(SearchEmpID.this, android.R.layout.simple_spinner_item, namesBySearch);
                employeeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                employeeAdapter.insert("Choose Employee name", 0);
                spinner.setAdapter(employeeAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}