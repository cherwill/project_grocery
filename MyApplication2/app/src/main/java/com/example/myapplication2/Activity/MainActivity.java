package com.example.myapplication2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.example.myapplication2.DataModel.TestModel;
import com.example.myapplication2.Helper.APICall;
import com.example.myapplication2.Helper.GsonHelper;
import com.example.myapplication2.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TestModel myTestModel;
    TextView myTextView;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = findViewById(R.id.textView);

        APICall apiCall = new APICall("https://dev.tescolabs.com/locations/search", this);

        apiCall.execute();

        //TODO: get result from here











    }

    public void speak(String st) {

    }
    public void updateText(){

    }


}
