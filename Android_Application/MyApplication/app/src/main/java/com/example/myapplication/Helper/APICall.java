package com.example.myapplication.Helper;

import android.util.Log;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class APICall {
    private RequestQueue requestQueue;
    private Cache cache;
    private Network network;
    private String url;
    private RequestQueue.RequestFinishedListener requestFinishedListener;
    private TextView textview;

    public APICall(String url, TextView textview){
        //points to textView & url -> sets up request accordingly
        this.textview = textview;
        this.url = url;
        init();
    }

    public void start(){
        Log.d("REQUEST STARTED: ", this.url);
        requestQueue.start();
    }

    public void stop(){
        Log.d("REQUEST STOPPED: ", this.url);
        requestQueue.stop();
    }

    public void cancel(){
//        requestQueue.ca
    }



    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    //TODO: Handle response
                    Log.d("RESPONSE: ",response.toString());
                    textview.setText("Response: " + response.toString());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error

                }
            });

// Access the RequestQueue through your singleton class.
//MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

    private void init(){
        //Init cache, network and queue

        cache = new Cache() {
            @Override
            public Entry get(String key) {
                return null;
            }

            @Override
            public void put(String key, Entry entry) {

            }

            @Override
            public void initialize() {

            }

            @Override
            public void invalidate(String key, boolean fullExpire) {

            }

            @Override
            public void remove(String key) {

            }

            @Override
            public void clear() {

            }
        };

        network = new Network() {
            @Override
            public NetworkResponse performRequest(Request<?> request) throws VolleyError {
                return null;
            }
        };
        requestQueue = new RequestQueue(cache, network);

        requestFinishedListener = new RequestQueue.RequestFinishedListener() {
            @Override
            public void onRequestFinished(Request request) {
                //TODO: Handle request
                Log.d("REQUEST: ", "FINISHED");
            }
        };

        requestQueue.add(jsonObjectRequest);
        requestQueue.addRequestFinishedListener(requestFinishedListener);
    }
}
