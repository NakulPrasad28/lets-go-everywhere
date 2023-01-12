package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class send_complaint extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sh;
    String complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_complaint);
        e1=findViewById(R.id.editTextTextPersonName2);
        b1=findViewById(R.id.button4);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaint = e1.getText().toString();

                if (complaint.equals("")){
                    e1.setError("enter complaint");
                e1.requestFocus();
            }
                else
                {


                RequestQueue queue = Volley.newRequestQueue(send_complaint.this);
                String url ="http://"+sh.getString("ip", "")+":5000/sendcomp";
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
                                Intent in=new Intent(getApplicationContext(),view_reply.class)	;

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
                        params.put("complaint", complaint);


                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);

            } }
        });
    }
}