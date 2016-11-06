package com.bikash.bloodbank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DonorList extends AppCompatActivity {
    String city;
    String group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

        Bundle extras = getIntent().getExtras();

        city = extras.getString("city");
        group = extras.getString("group");

        Log.i("NAME",city);
        Log.i("NAME",group);
    }
}
