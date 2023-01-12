package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class view_booking_details extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    ArrayList<String> booking_id,bus_name,time,date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking_details);
        l1=findViewById(R.id.lst);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url = "http://" + sh.getString("ip", "") + ":5000/view_booking_details";
        RequestQueue queue = Volley.newRequestQueue(view_booking_details.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {

                    JSONArray ar=new JSONArray(response);
                    booking_id= new ArrayList<>();
                    bus_name= new ArrayList<>();
                    time= new ArrayList<>();
                    date= new ArrayList<>();


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
                        booking_id.add(jo.getString("booking_id"));
                        bus_name.add(jo.getString("bus_name"));
                        time.add(jo.getString("time"));
                        date.add(jo.getString("date"));




                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new custom4(view_booking_details.this,booking_id,bus_name,time,date));
                    l1.setOnItemClickListener(view_booking_details.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(view_booking_details.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid",""));

                return params;
            }
        };
        queue.add(stringRequest);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent in=new Intent(getApplicationContext(),view_qr_code.class);
        in.putExtra("bid",booking_id.get(i));
        startActivity(in);
    }
}