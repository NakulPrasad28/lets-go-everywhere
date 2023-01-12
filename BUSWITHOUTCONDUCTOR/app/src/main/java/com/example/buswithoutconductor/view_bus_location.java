package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

public class view_bus_location extends AppCompatActivity implements AdapterView.OnItemSelectedListener   {
    Spinner s1;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> route,rid,bus,regno;
    String url,riid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bus_location);
        s1=findViewById(R.id.spinner3);
        b1=findViewById(R.id.button8);
        l1=findViewById(R.id.l2);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url ="http://"+sh.getString("ip","")+":5000/viewroute";
        s1.setOnItemSelectedListener(view_bus_location.this);
        RequestQueue queue = Volley.newRequestQueue(view_bus_location.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);

                    route= new ArrayList<>(ar.length());
                    rid= new ArrayList<>(ar.length());
                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        route.add(jo.getString("NAME")+"  "+jo.getString("NAME"));
                        rid.add(jo.getString("ID"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(view_bus_location.this,android.R.layout.simple_spinner_item,route);
                    s1.setAdapter(ad);

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
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String url = "http://" + sh.getString("ip", "") + ":5000/viewbuslocation";
                RequestQueue queue = Volley.newRequestQueue(view_bus_location.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            bus= new ArrayList<>();
                            regno= new ArrayList<>();



                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                bus.add(jo.getString("bus_name"));
                                regno.add(jo.getString("reg_no"));



                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            l1.setAdapter(new custom2(view_bus_location.this,bus,regno));
//                    l1.setOnItemClickListener(viewuser.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(view_bus_location.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();

                        return params;
                    }
                };
                queue.add(stringRequest);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        riid=rid.get(i);


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}