package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class home_page_driver extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_driver);
        b1=findViewById(R.id.button14);
        b2=findViewById(R.id.button15);
        b3=findViewById(R.id.button16);
        b4=findViewById(R.id.button17);
        b6=findViewById(R.id.button23);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());




        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ik = new Intent(getApplicationContext(), view_assigned_work.class);
                startActivity(ik);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ik = new Intent(getApplicationContext(), update_status.class);
                startActivity(ik);

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ik = new Intent(getApplicationContext(), send_complaint.class);
                startActivity(ik);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ik = new Intent(getApplicationContext(), view_feedback.class);
                startActivity(ik);

            }
        });
//        b5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent ik = new Intent(getApplicationContext(), .class);
//                startActivity(ik);
//
//            }
//        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ik = new Intent(getApplicationContext(), LOGIN.class);
                startActivity(ik);

            }
        });

    }
}