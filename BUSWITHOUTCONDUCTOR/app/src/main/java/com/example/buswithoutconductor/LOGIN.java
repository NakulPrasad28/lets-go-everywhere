package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
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

public class LOGIN extends AppCompatActivity {
    EditText e1,e2;
    Button b1,b2;
    SharedPreferences sh;
    String uname,pswrd,url,ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1=findViewById(R.id.editTextTextPersonName3);
        e2=findViewById(R.id.editTextTextPassword);
        b1=findViewById(R.id.button3);
        b2=findViewById(R.id.button28);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sh.getString("ip","");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               uname=e1.getText().toString();
               pswrd=e2.getText().toString();
               if(uname.equals("")){
                   e1.setError("enter your uname");
                   e1.requestFocus();

               }
               else if(pswrd.equals("")){
                   e2.setError("enter your password");
                   e2.requestFocus();
               }
               else {


                   RequestQueue queue = Volley.newRequestQueue(LOGIN.this);
                   url = "http://" + ip + ":5000/logincode";

                   // Request a string response from the provided URL.
                   StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           // Display the response string.
                           Log.d("+++++++++++++++++", response);
                           try {
                               JSONObject json = new JSONObject(response);
                               String res = json.getString("task");

                               if (res.equalsIgnoreCase("success")) {
                                   String lid = json.getString("id");
                                   SharedPreferences.Editor edp = sh.edit();
                                   edp.putString("lid", lid);
                                   edp.commit();
                                   String type = json.getString("type");
                                   if (type.equals("driver")) {

                                       Intent ik = new Intent(getApplicationContext(),home_page_driver.class);
                                       startActivity(ik);
                                       Intent ik1 = new Intent(getApplicationContext(),LocationService.class);
                                       startService(ik1);

                                   } else if (type.equals("user")) {
                                       Intent ik = new Intent(getApplicationContext(), home_page_user1.class);
                                       startActivity(ik);

                                   } else {
                                       Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();

                                   }

                               } else {

                                   Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();

                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }


                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {


                           Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                       }
                   }) {
                       @Override
                       protected Map<String, String> getParams() {
                           Map<String, String> params = new HashMap<String, String>();
                           params.put("uname", uname);
                           params.put("pswd", pswrd);

                           return params;
                       }
                   };
                   queue.add(stringRequest);


               }


            }
        });
     b2.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent ik=new Intent(getApplicationContext(),Registration.class);
             startActivity(ik);
         }
     });

    }
}