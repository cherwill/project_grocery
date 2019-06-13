package com.example.myapplication2.Helper;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import com.example.myapplication2.DataModel.TestModel;
import com.example.myapplication2.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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

    public APICall(String url, Context context) {
        this.context = context;
        textView = ((Activity)context).findViewById(R.id.textView);

        textView.setText("Changed!");

        client = new OkHttpClient();
        this.url = url;

        request = new Request.Builder()
                .url(url)
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


                                TestModel tmodel = (TestModel) GsonHelper.deserialize(json.toString(), TestModel.class);

                                textView.setText(tmodel.getId().toString());
                            }
                        });
                    } catch (Exception e){

                    }


                }
            }
        });
    }

}
