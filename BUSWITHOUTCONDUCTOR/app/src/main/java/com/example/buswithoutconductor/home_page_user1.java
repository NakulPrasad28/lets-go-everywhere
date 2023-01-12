package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class home_page_user1 extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7,b8;
    SharedPreferences sh;

    protected static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_user1);
        b1=findViewById(R.id.button13);
        b2=findViewById(R.id.button19);
        b3=findViewById(R.id.button20);
        b4=findViewById(R.id.button21);
        b5=findViewById(R.id.button22);
        b6=findViewById(R.id.button24);
        b7=findViewById(R.id.button18);
        b8=findViewById(R.id.button27);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ik = new Intent(getApplicationContext(), search_bus_and_view_time.class);
                startActivity(ik);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {
                    Intent in = new Intent(ACTION_SCAN);
                    in.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(in, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(home_page_user1.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
                }


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

                Intent ik = new Intent(getApplicationContext(), send_feedback.class);
                startActivity(ik);

            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent ik = new Intent(getApplicationContext(), LOGIN.class);
                startActivity(ik);

            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), view_time.class);
                startActivity(ik);

            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), book_bus.class);
                startActivity(ik);

            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ik = new Intent(getApplicationContext(), view_booking_details.class);
                startActivity(ik);

            }
        });



    }





    protected Dialog showDialog(final home_page_user1 qrscan, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        // TODO Auto-generated method stub
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(qrscan);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    qrscan.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                if (contents.equals("")) {
                    Toast.makeText(getApplicationContext(), "invalid", Toast.LENGTH_LONG).show();

                } else {
                    //Toast.makeText(getApplicationContext(), contents, Toast.LENGTH_LONG).show();


                    Intent i = new Intent(getApplicationContext(), direct_booking.class);
                    i.putExtra("bid", contents);
                    startActivity(i);
                }
            }
        }


    }



}