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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class update_status extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    Spinner s1;
    Button b1;
    SharedPreferences sh;
    String si[]={"yes","no"};
    String stts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);
        s1=findViewById(R.id.spinner);
        b1=findViewById(R.id.button5);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ArrayAdapter<String> ad=new ArrayAdapter<>(update_status.this,android.R.layout.simple_list_item_1,si);
        s1.setAdapter(ad);






        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stts=s1.getSelectedItem().toString();
                RequestQueue queue = Volley.newRequestQueue(update_status.this);
                String url ="http://"+sh.getString("ip", "")+":5000/updatestatus";
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
                                Toast.makeText(getApplicationContext(),"successfully added",Toast.LENGTH_LONG).show();
                                Intent in=new Intent(getApplicationContext(),home_page_driver.class)	;

                                startActivity(in);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext()," failed",Toast.LENGTH_LONG).show();

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
                        params.put("lid", sh.getString("lid",""));

                        params.put("status", stts);



                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        stts=s1.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}