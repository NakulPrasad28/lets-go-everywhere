package com.example.buswithoutconductor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class payment extends AppCompatActivity {
    Spinner s1;
    EditText e1,e2,e3,e4;
    Button b1;
    String price;
    SharedPreferences sh;
    String acc_no,ifsc,pin,seat_required,banks;
    String bank[]={"SBI","kotak","punjab national bank","canara","indian bank","gramin bank"};
    String bid;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        s1=findViewById(R.id.spinner4);
        e1=findViewById(R.id.editTextTextPersonName10);
        e2=findViewById(R.id.editTextTextPersonName11);
        e3=findViewById(R.id.editTextTextPersonName12);
        e4=findViewById(R.id.editTextTextPersonName13);
        b1=findViewById(R.id.button11);
        tv=findViewById(R.id.textView58);
        ArrayAdapter<String > ad= new ArrayAdapter<String>(payment.this, android.R.layout.simple_spinner_item,bank);
        s1.setAdapter(ad);

        bid=getIntent().getStringExtra("bid");
        price=getIntent().getStringExtra("pr");
        tv.setText("Total Price   "+price);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banks=s1.getSelectedItem().toString();
                acc_no=e1.getText().toString();
                ifsc=e2.getText().toString();
                pin=e3.getText().toString();
                seat_required=e4.getText().toString();

                if (acc_no.equals("")){
                    e1.setError("enter acc no");
                    e1.requestFocus();
                }
                else if (ifsc.equals("")){
                    e2.setError("enter ifsc code");
                    e2.requestFocus();
                }
                else if (pin.equals("")){
                    e3.setError("enter pin");
                    e3.requestFocus();
                }
                else if (seat_required.equals("")){
                    e4.setError("seat required");
                    e4.requestFocus();
                }
                else{



                RequestQueue queue = Volley.newRequestQueue(payment.this);
                String url ="http://"+sh.getString("ip", "")+":5000/payment";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {



                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {
                            JSONObject json=new JSONObject(response);
                            String res=json.getString("task");

                            if(res.equalsIgnoreCase("ok"))
                            {
                                Toast.makeText(getApplicationContext(),"successfully added",Toast.LENGTH_LONG).show();
                                Intent in=new Intent(getApplicationContext(),home_page_user1.class)	;

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


                        params.put("bank", banks);
                        params.put("acc_no", acc_no);
                        params.put("ifsc", ifsc);
                        params.put("pin", pin);
                        params.put("bid", bid);
                        params.put("price", price);



                        return params;
                    }
                };
                // Add the request to the RequestQueue.
                queue.add(stringRequest);


            } }
        });

    }
}