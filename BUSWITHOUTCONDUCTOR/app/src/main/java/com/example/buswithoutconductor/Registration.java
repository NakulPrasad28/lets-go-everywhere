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
import android.widget.RadioButton;
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

public class Registration extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9,e10;
    RadioButton r1,r2,r3;
    Button b1;
    SharedPreferences sh;
    String fname,lname,dob,place,post,pincode,phone,email,gender,uname,pswrd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        e1=findViewById(R.id.editTextTextPersonName6);
        e2=findViewById(R.id.editTextTextPersonName5);
        e3=findViewById(R.id.editTextDate);
        e4=findViewById(R.id.editTextTextPersonName7);
        e5=findViewById(R.id.editTextTextPersonName8);
        e6=findViewById(R.id.editTextNumber);
        e7=findViewById(R.id.editTextNumber2);
        e8=findViewById(R.id.editTextTextEmailAddress);
        e9=findViewById(R.id.editTextTextPersonName9);
        e10=findViewById(R.id.editTextTextPassword2);
        b1=findViewById(R.id.button6);
        r1=findViewById(R.id.radioButton);
        r2=findViewById(R.id.radioButton2);
        r3=findViewById(R.id.radioButton3);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                dob=e3.getText().toString();
                place=e4.getText().toString();
                post=e5.getText().toString();
                pincode=e6.getText().toString();
                phone=e7.getText().toString();
                if(r1.isChecked())
                {
                    gender=r1.getText().toString();
                }
                else if(r2.isChecked())
                {
                    gender=r2.getText().toString();
                }
                else
                {
                    gender=r3.getText().toString();
                }
                email=e8.getText().toString();
                uname=e9.getText().toString();
                pswrd=e10.getText().toString();


                if (fname.equals("")){
                    e1.setError("enter first name");
                    e1.requestFocus();
                }
                else if (lname.equals("")){
                    e2.setError("enter last name");
                    e2.requestFocus();
                }
                else if (dob.equals("")){
                    e3.setError("enter date of birth");
                    e3.requestFocus();
                }
                else if (place.equals("")){
                    e4.setError("enter your place");
                    e4.requestFocus();
                }
                else if (post.equals(""))
                {
                    e5.setError("enter post");
                    e5.requestFocus();
                }
                else if (pincode.equals("")){
                    e6.setError("enter pin code");
                    e6.requestFocus();
                }
                else if (phone.equals("")){
                    e7.setError("enter phone");
                    e7.requestFocus();
                }
                else if (email.equals("")){
                    e8.setError("enter email ");
                    e8.requestFocus();
                }
                else if (uname.equals("")){
                    e9.setError("enter username");
                    e9.requestFocus();
                }
                else if (pswrd.equals("")){
                    e10.setError("enter password ");
                    e10.requestFocus();
                }
                else {





                RequestQueue queue = Volley.newRequestQueue(Registration.this);
                String url ="http://"+sh.getString("ip", "")+":5000/insertdriver";
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
                                Toast.makeText(getApplicationContext(),"successfully register",Toast.LENGTH_LONG).show();
                                Intent in=new Intent(getApplicationContext(),LOGIN.class)	;

                                startActivity(in);

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Username already exists",Toast.LENGTH_LONG).show();

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
                        params.put("fname", fname);
                        params.put("lanme", lname);
                        params.put("dob", dob);
                        params.put("gender", gender);
                        params.put("place", place);
                        params.put("post", post);
                        params.put("pin", pincode);
                        params.put("phone", phone);
                        params.put("email", email);
                        params.put("username", uname);
                        params.put("password", pswrd);


                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);




            } }
        });
    }
}