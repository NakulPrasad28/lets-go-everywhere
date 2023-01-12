package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class search_bus_and_view_time extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {
    Spinner s1;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    ArrayList<String>route,rid,busname,time,stop,latitude,longitude;
    String routeid,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus_and_view_time);
        s1=findViewById(R.id.spinner2);
        b1=findViewById(R.id.button7);
        l1=findViewById(R.id.lv1);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        String url = "http://" + sh.getString("ip", "") + ":5000/viewroute";
        RequestQueue queue = Volley.newRequestQueue(search_bus_and_view_time.this);
        s1.setOnItemSelectedListener(search_bus_and_view_time.this);
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
                        route.add(jo.getString("starting_stop")+"--"+jo.getString("ending_stop"));
                        rid.add(jo.getString("id"));




                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<String>(search_bus_and_view_time.this,android.R.layout.simple_spinner_item,route);
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
                String url2 ="http://"+sh.getString("ip", "") + ":5000/viewtime2";
                RequestQueue queue1 = Volley.newRequestQueue(search_bus_and_view_time.this);

                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url2,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);
                            rid= new ArrayList<>();
                            route= new ArrayList<>();
                            busname= new ArrayList<>();
                            time=new ArrayList<>();
                            stop=new ArrayList<>();
                            latitude=new ArrayList<>();
                            longitude=new ArrayList<>();

                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                rid.add(jo.getString("route_id"));
                                busname.add(jo.getString("bus_name"));
                                time.add(jo.getString("time"));
                                stop.add(jo.getString("stop"));
                                latitude.add(jo.getString("latitude"));
                                longitude.add(jo.getString("longitude"));


                            }

                            // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                            //lv.setAdapter(ad);

                            l1.setAdapter(new custom3(search_bus_and_view_time.this,busname,time,stop));
                            l1.setOnItemClickListener(search_bus_and_view_time.this);

                        } catch (Exception e) {
                            Log.d("=========", e.toString());
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(search_bus_and_view_time.this, "err"+error, Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("rid", routeid);

                        return params;
                    }
                };
                queue1.add(stringRequest1);


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        routeid=rid.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String url = "http://maps.google.com/maps?q="+latitude.get(i)+","+longitude.get(i);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,  Uri.parse(url));
        startActivity(intent);
    }
}