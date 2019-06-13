package com.example.myapplication2.Helper;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.example.myapplication2.DataModel.TestModel;
import com.example.myapplication2.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICall {
    private String url;
    private Context context;
    private TextView textView;
    private TestModel tmodel;
    OkHttpClient client;
    Request request;
    private TextToSpeech tts;

    public APICall(String url, Context context) {
        this.context = context;
        textView = ((Activity)context).findViewById(R.id.textView);

        textView.setText("Changed!");

        client = new OkHttpClient();
        this.url = url;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(this.url).newBuilder();
        urlBuilder.addQueryParameter("offset", "0");
        urlBuilder.addQueryParameter("limit", "10");
        urlBuilder.addQueryParameter("sort", "near:chingford"); //location buzzword
        urlBuilder.addQueryParameter("facilities", "WHEELCHAIR_ACCESS"); //access buzzword
        String uri = urlBuilder.build().toString();

        request = new Request.Builder()
                .url(uri)
                .addHeader("Ocp-Apim-Subscription-Key","47db0bfc5032433cbf31ab78415fe4b1")
                .build();



    }

    //TODO: GET THIS TO RETURN THE RESULT
    public void execute(){
        final Object result;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String resStr = response.body().string().toString();
                    try {
                        final JSONObject json = new JSONObject(resStr);
                        final String myResponse = response.body().toString();
                        textView.post(new Runnable() {
                            @Override
                            public void run() {


                                final TestModel tmodel = (TestModel) GsonHelper.deserialize(json.toString(), TestModel.class);

                                textView.setText(tmodel.results[0].location.name);

                                tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                                    @Override
                                    public void onInit(int i) {
                                        tts.setLanguage(Locale.US);
                                        tts.speak(tmodel.results[0].location.name, TextToSpeech.QUEUE_ADD, null);
                                    }
                                });
                            }
                        });
                    } catch (Exception e){

                    }


                }
            }
        });
    }

}
