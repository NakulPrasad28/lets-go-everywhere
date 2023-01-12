package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class required_seat extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText e1,e2;
    Button b1;
    SharedPreferences sh;
    Spinner s7;
    String requiredseat,bookid,lastsid,stime,tidd,date;
    String[]  sstime,ttidd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_seat);
        e1=findViewById(R.id.editTextTextPersonName15);
        b1=findViewById(R.id.button25);
        s7=findViewById(R.id.spinner7);
        e2=findViewById(R.id.editTextDate2);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        bookid=getIntent().getStringExtra("tid");
        stime=getIntent().getStringExtra("stop");

        tidd=getIntent().getStringExtra("tidd");

        sstime=stime.split("=");
        ttidd=tidd.split("=");
        ArrayAdapter<String> ad=new ArrayAdapter<>(required_seat.this,android.R.layout.simple_spinner_item,sstime);
        s7.setAdapter(ad);
        s7.setOnItemSelectedListener(required_seat.this);






//        in.putExtra("tid", tid);
//        in.putExtra("stime",stime);
//        in.putExtra("stop",stop);
//        in.putExtra("tidd", tidd);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requiredseat=e1.getText().toString();
                date=e2.getText().toString();


                if (requiredseat.equals("")){
                    e1.setError("enter seat no");
                    e1.requestFocus();
                }
                else if (date.equals("")){
                    e2.setError("enter Date");
                    e2.requestFocus();
                }
                else
                {

                RequestQueue queue = Volley.newRequestQueue(required_seat.this);
                String url ="http://"+sh.getString("ip", "")+":5000/book_seat";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
                            JSONObject json=new JSONObject(response);
                            String res=json.getString("task");

                            if(res.equalsIgnoreCase("success"))
                            {
                                String bid=json.getString("bid");
                                String pr=json.getString("price");
                                Toast.makeText(getApplicationContext(),"Booking Success",Toast.LENGTH_LONG).show();


                                Intent in=new Intent(getApplicationContext(),payment.class)	;
                                in.putExtra("bid",bid);
                                in.putExtra("pr",pr);
                                startActivity(in);


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Booking Failed",Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),res,Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("bid",bookid);
                        params.put("requiredseat", requiredseat);
                        params.put("lastsid", lastsid);
                        params.put("date", date);
                        params.put("lid", sh.getString("lid","lid"));


                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);







            }}
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        lastsid=ttidd[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}