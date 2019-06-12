package com.example.myapplication.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.Helper.APICall;
import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    APICall myAPICall;
    TextView myTextView;
    String url = "https://reqres.in/api/users?page=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView = this.findViewById(R.id.myTextView);

        myAPICall = new APICall(url, myTextView);
        myAPICall.start();


    }
}
