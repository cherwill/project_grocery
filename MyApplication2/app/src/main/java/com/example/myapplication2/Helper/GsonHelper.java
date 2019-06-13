package com.example.myapplication2.Helper;

import android.util.Log;

import com.example.myapplication2.DataModel.TestModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHelper {


    public static Object deserialize(String jsonString, Class<?> className){
        //Create desired class file and direct this method towards it
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonString, className);

    }
}
