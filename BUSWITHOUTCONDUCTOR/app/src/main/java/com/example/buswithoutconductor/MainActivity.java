package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button b1;
    EditText e1;
    SharedPreferences sh;
    String ip_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=findViewById(R.id.button);
        e1=findViewById(R.id.editTextTextPersonName);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip_address=e1.getText().toString();
                if (ip_address.equals("")){
                    e1.setError("enter ip address");
                    e1.requestFocus();
                }
                else {


                SharedPreferences.Editor ed=sh.edit();
                ed.putString("ip",ip_address);
                ed.commit();

                Intent i=new Intent(getApplicationContext(),LOGIN.class);
                startActivity(i);

            }}
        });

    }
}