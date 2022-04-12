package com.example.deneme.model;

import android.graphics.Color;

public class Category {

    //properties

    String name;
    int priority;
    int chartColor;

    //constructors

    public Category( String title, int priority) {
        name = title;
        this.priority = priority;
        //chartColor = color;
    }

    public Category(String title, int priority, int color) {
        name = title;
        this.priority = priority;
        chartColor = color;
    }

    //methods

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public int getChartColor() {
        return chartColor;
    }
}
