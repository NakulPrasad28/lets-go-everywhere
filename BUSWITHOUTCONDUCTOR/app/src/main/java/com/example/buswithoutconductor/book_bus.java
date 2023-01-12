package com.example.buswithoutconductor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class book_bus extends AppCompatActivity implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener {
    Spinner S1,S2;
    Button b1;
    SharedPreferences sh;
    String nop,routeid,rno,tid;
    ListView l1;
    String stop="",stime="",tidd="";
    ArrayList<String>route,rid,reg,stopdetails,noofseats,time,timeid,nstopdetails,nnoofseats,ntime,ntimeid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_bus);
        S1=findViewById(R.id.spinner5);
        S2=findViewById(R.id.spinner6);
        b1=findViewById(R.id.button12);
        l1=findViewById(R.id.list);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String url ="http://"+sh.getString("ip","")+":5000/viewroute";
        S1.setOnItemSelectedListener(book_bus.this);
        RequestQueue queue = Volley.newRequestQueue(book_bus.this);

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
                        route.add(jo.getString("starting_stop")+"-- "+jo.getString("ending_stop"));
                        rid.add(jo.getString("id"));


                    }

                    ArrayAdapter<String> ad=new ArrayAdapter<>(book_bus.this,android.R.layout.simple_spinner_item,route);
                    S1.setAdapter(ad);

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
                String url ="http://"+sh.getString("ip","")+":5000/search_bus_and_view_time";
                S1.setOnItemSelectedListener(book_bus.this);
                RequestQueue queue = Volley.newRequestQueue(book_bus.this);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the response string.
                        Log.d("+++++++++++++++++",response);
                        try {

                            JSONArray ar=new JSONArray(response);

                            stopdetails= new ArrayList<>(ar.length());
                            time= new ArrayList<>(ar.length());
                            noofseats= new ArrayList<>(ar.length());
                            timeid= new ArrayList<>(ar.length());

                            for(int i=0;i<ar.length();i++)
                            {
                                JSONObject jo=ar.getJSONObject(i);
                                stopdetails.add(jo.getString("stop"));
                                time.add(jo.getString("time"));
                                noofseats.add(jo.getString("no_of_seats"));
                                timeid.add(jo.getString("time_id"));


                            }

//                            ArrayAdapter<String> ad=new ArrayAdapter<>(book_bus.this,android.R.layout.simple_spinner_item,reg);
//                            S2.setAdapter(ad);

                             l1.setAdapter(new custom3(book_bus.this,stopdetails,time,noofseats));
                            l1.setOnItemClickListener(book_bus.this);

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
                        params.put("rid", routeid);
                        params.put("regno", rno);
                        return params;

                    }
                };
                queue.add(stringRequest);





            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



        if(S1==adapterView)
        {
            routeid=rid.get(i);

            String url ="http://"+sh.getString("ip","")+":5000/viewbus";
            S2.setOnItemSelectedListener(book_bus.this);
            RequestQueue queue = Volley.newRequestQueue(book_bus.this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Display the response string.
                    Log.d("+++++++++++++++++",response);
                    try {

                        JSONArray ar=new JSONArray(response);

                        reg= new ArrayList<>(ar.length());
//                    rid= new ArrayList<>(ar.length());
                        for(int i=0;i<ar.length();i++)
                        {
                            JSONObject jo=ar.getJSONObject(i);
                            reg.add(jo.getString("reg_no"));
//                        rid.add(jo.getString("id"));


                        }

                        ArrayAdapter<String> ad=new ArrayAdapter<>(book_bus.this,android.R.layout.simple_spinner_item,reg);
                        S2.setAdapter(ad);

                        // l1.setAdapter(new custom2(Monitoring_signal.this,notification,date));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            },  new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                    Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("rid", routeid);
                    return params;

                }
            };
            queue.add(stringRequest);




        }


        else{
            rno=S2.getSelectedItem().toString();

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        tid=timeid.get(i);

        nstopdetails= new ArrayList<>();
        ntime= new ArrayList<>();
        nnoofseats= new ArrayList<>();
        ntimeid= new ArrayList<>();
         stop="";
         stime="";
         tidd="";
        for(int ii=i+1;ii<stopdetails.size();ii++)
        {

            stop=stop+stopdetails.get(ii)+"-"+time.get(ii)+"=";


            tidd=tidd+timeid.get(ii)+"=";


        }
        Toast.makeText(getApplicationContext(),stop,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),tidd,Toast.LENGTH_LONG).show();



        AlertDialog.Builder ald=new AlertDialog.Builder(book_bus.this);
        ald.setTitle("Select option")
                .setPositiveButton(" Book ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
//                        RequestQueue queue = Volley.newRequestQueue(book_bus.this);
//                        String url ="http://"+sh.getString("ip", "")+":5000/book_bus";
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the response string.
//                                Log.d("+++++++++++++++++",response);
//                                try {
//                                    JSONObject json=new JSONObject(response);
//                                    String res=json.getString("task");
//                                    String bookid=json.getString("bid");
//
//                                    SharedPreferences.Editor ed=sh.edit();
//                                    ed.putString("bid",bookid);
//                                    ed.commit();
//
//
//                                    if(res.equalsIgnoreCase("success"))
//                                    {
//                                        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
                                        Intent in=new Intent(getApplicationContext(),required_seat.class)	;
                                        in.putExtra("tid", tid);
                                        in.putExtra("stime",stime);
                                        in.putExtra("stop",stop);
                                        in.putExtra("tidd", tidd);

                                        startActivity(in);
//
//                                    }
//                                    else
//                                    {
//                                        Toast.makeText(getApplicationContext()," failed",Toast.LENGTH_LONG).show();
//
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                                Toast.makeText(getApplicationContext(),"Error"+error,Toast.LENGTH_LONG).show();
//                            }
//                        }){
//                            @Override
//                            protected Map<String, String> getParams()
//                            {
//                                Map<String, String>  params = new HashMap<String, String>();
//                                params.put("lid", sh.getString("lid",""));
//                                params.put("tid", tid);
//
//
//                                return params;
//                            }
//                        };
//                        // Add the request to the RequestQueue.
//                        queue.add(stringRequest);

                    }
                })
                .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent i=new Intent(getApplicationContext(),home_page_user1.class);
                        startActivity(i);
                    }
                });

        AlertDialog al=ald.create();
        al.show();
    }
}