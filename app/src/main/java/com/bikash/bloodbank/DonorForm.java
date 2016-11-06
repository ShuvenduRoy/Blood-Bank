package com.bikash.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class DonorForm extends AppCompatActivity {
    Spinner cityChoice;
    Spinner groupChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_form);

        cityChoice = (Spinner) findViewById(R.id.dropdownCity);

        String[] citis = new String[]{"Khulna", "Dhaka", "Chittagong"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, citis);
        cityChoice.setAdapter(adapter);


        groupChoice = (Spinner) findViewById(R.id.dropdownGroup);
        String[] group = new String[]{"O+","O-", "A+", "B+","A-", "B-", "AB+", "AB-"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, group);
        groupChoice.setAdapter(adapter1);
    }
}
