package com.example.myapplication2.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication2.DataModel.TestModel;
import com.example.myapplication2.Helper.APICall;
import com.example.myapplication2.Helper.GsonHelper;
import com.example.myapplication2.R;

import java.util.ArrayList;
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

        final Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                promptSpeechInput();
            }
        });




        //TODO: get result from here



        }

    public void promptSpeechInput(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        );

        try {
            startActivityForResult(intent, 100);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(
                    getApplicationContext(),
                    "Speech not supported",
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                if (resultCode == -1 && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String inputString = result.get(0);
                    if (inputString.contains(" in ") && inputString.contains(" with ")) {
                        String location = inputString.split(" in ")[1].split("with")[0];
                        String facility = inputString.split("with")[1];
                        myTextView.setText(location + facility);
                        APICall apiCall = new APICall("https://dev.tescolabs.com/locations/search",location,facility, this);

                        apiCall.execute();
                    } else {
                        myTextView.setText("Couldn't find location or facillity");
                    }
//                    mainTextView.setText(location + facility)
                }
            }
        }












    public void speak(String st) {

    }
    public void updateText(){

    }


}
