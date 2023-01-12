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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class direct_booking extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner s1,s2;
    Button b1;
    EditText ed;
    SharedPreferences sh;
    ArrayList<String> tid,stop;
    String bid,fsid,tsid,noseates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_booking);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1=findViewById(R.id.button26);
        s1=findViewById(R.id.spinner8);
        s2=findViewById(R.id.spinner9);
        ed=findViewById(R.id.editTextTextPersonName14);
        bid=getIntent().getStringExtra("bid");

        String url ="http://"+sh.getString("ip","")+":5000/view_directbus";
        s1.setOnItemSelectedListener(direct_booking.this);
        s2.setOnItemSelectedListener(direct_booking.this);
        RequestQueue queue = Volley.newRequestQueue(direct_booking.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    tid= new ArrayList<>(ar.length());
                    stop= new ArrayList<>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        stop.add(jo.getString("stop")+" -- "+jo.getString("time"));
                        tid.add(jo.getString("time_id"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(direct_booking.this,android.R.layout.simple_spinner_item,stop);
                    s1.setAdapter(ad);
                    ArrayAdapter<String> ad1=new ArrayAdapter<>(direct_booking.this,android.R.layout.simple_spinner_item,stop);
                    s2.setAdapter(ad1);

                    // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("bid",bid);
                return params;
            }
        };


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noseates=ed.getText().toString();




                if (noseates.equals("")){
                    ed.setError("enter seat no");
                    ed.requestFocus();
                }

                else
                {

                    RequestQueue queue = Volley.newRequestQueue(direct_booking.this);
                    String url ="http://"+sh.getString("ip", "")+":5000/dbook_seat";
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
                            params.put("bid",fsid);
                            params.put("requiredseat", noseates);
                            params.put("lastsid", tsid);

                            params.put("lid", sh.getString("lid","0"));


                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);







                }








            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView==s1)
        {
            fsid=tid.get(i);
        }
        if(adapterView==s2)
        {
            tsid=tid.get(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}