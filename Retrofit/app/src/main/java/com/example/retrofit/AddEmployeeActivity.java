package com.example.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import APIclient.EmployeeAPI;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddEmployeeActivity extends AppCompatActivity {
    private final static  String BASE_URL="http://dummy.restapiexample.com/api/v1/";
    private EditText etName,etSalary,etEmpAge;
    private Button btnRegister,dashboard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        etName=findViewById(R.id.etName);
        etSalary=findViewById(R.id.etSalary);
        etEmpAge=findViewById(R.id.etEmpAge);
        btnRegister=findViewById(R.id.btnRegister);
        dashboard=findViewById(R.id.dashboard);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Register();
            }
        });
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEmployeeActivity.this, DashboardActivity.class));
            }
        });

    }
    private void Register(){
        String name=etName.getText().toString();
        Float salary=Float.parseFloat(etSalary.getText().toString());
        int age=Integer.parseInt(etEmpAge.getText().toString());
        EmployeeCUD employeeCUD=new EmployeeCUD(name,salary,age);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EmployeeAPI employeeAPI=retrofit.create(EmployeeAPI.class);
        Call<Void> voidCall=employeeAPI.registerEmployee(employeeCUD);
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(AddEmployeeActivity.this, "Sucessfully registered", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AddEmployeeActivity.this, "Error:"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
