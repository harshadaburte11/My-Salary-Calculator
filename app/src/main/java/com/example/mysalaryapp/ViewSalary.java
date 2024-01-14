package com.example.mysalaryapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ViewSalary extends AppCompatActivity {

    EditText selectMonth;
    TextView salaryResult,ha,ta,da,pf;
    String selectedMonth;
     boolean flag=true;
    private DatabaseReference mydb= FirebaseDatabase.getInstance().getReference().child("EmpSalaryInfo");

    private Spinner employeeSpinner, monthSpinner;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_salary);

        selectMonth = findViewById(R.id.selectMonth);
        salaryResult = findViewById(R.id.salaryResult);
        ha=findViewById(R.id.ha);
        ta=findViewById(R.id.ta);
        da=findViewById(R.id.da);
        pf=findViewById(R.id.pf);

        monthSpinner = findViewById(R.id.monthSpinner);
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                // Handle selection of a name from the Spinner
                if (position == 0)
                {
                    Toast.makeText(ViewSalary.this, "Please Choose Month", Toast.LENGTH_SHORT).show();
                    return;
                } else
                {
                    selectedMonth = (String) parentView.getItemAtPosition(position);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView)
            {
                Toast.makeText(ViewSalary.this, "Please Choose Month", Toast.LENGTH_SHORT).show();
                return;
            }
        });
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);


    }

    public void displaySalary(View view)
    {
        String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        flag=true;
        mydb.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Iterate through dataSnapshot to retrieve the records associated with the selected name.
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren())
                {
                    empModel user = userSnapshot.getValue(empModel.class);
                    if(selectedMonth.equals(user.getMonth()))
                    {
                            salaryResult.setText(" ₹ Total Salary :"+user.getSal());
                            ha.setText("HA :"+user.getHa()+"");
                            ta.setText("TA :"+user.getTa()+"");
                            da.setText("DA :"+user.getDa()+"");
                            pf.setText("PF :"+user.getPf()+"");
                            flag=false;
                    }

                }
                if(flag)
                {
                    Toast.makeText(ViewSalary.this, "No Record Found", Toast.LENGTH_SHORT).show();

                    salaryResult.setText(" ₹ Total Salary :");
                    ha.setText("HA :");
                    ta.setText("TA :");
                    da.setText("DA :");
                    pf.setText("PF :");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void BackToLogin()
    {
        Intent intent = new Intent(ViewSalary.this, SearchEmpID.class);
        startActivity(intent);
    }
}
