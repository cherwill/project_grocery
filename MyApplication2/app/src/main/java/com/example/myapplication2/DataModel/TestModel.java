package com.example.myapplication2.DataModel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestModel {

    public Result[] results;


    public class Result {
        public MyLocation location;
    }

    public class MyLocation {
        public String name;
    }

}
