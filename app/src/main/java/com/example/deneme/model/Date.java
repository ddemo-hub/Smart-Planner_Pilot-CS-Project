package com.example.deneme.model;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Date implements Serializable{

    //properties
    private int day;
    private int month;
    private int year;
    private String dayName;
    private String monthName;
    public  ArrayList<Task> todaysTasks;
    private ArrayList<String> days;
    private ArrayList<String> months;

    //constructors
    public Date( int day, int month, int year ) {
        this.day = day;
        this.month = month;
        this.year = year;

        months = new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        monthName = months.get( month - 1 );
        todaysTasks = new ArrayList<Task>();
    }

    //methods
    public void addTask( Task task ) {
        todaysTasks.add( task );
    }

    public void deleteTask( Task task ) {
        todaysTasks.remove( task );
    }

    public ArrayList<Task> getTasks() {
        return todaysTasks;
    }

    public void integrateCourses() {
        // TO - DO
    }

    public int getDay(){
        return day;
    }

    public int getMonth(){
        return month;
    }

    public int getYear(){
        return year;
    }

    public String getMonthName() {
        return months.get(month - 1);
    }

    /*
    public ArrayList<Task> getCourses() {
        ArrayList<Task> courses = new ArrayList<Task>();
        for( Task t: todaysTasks ) {
            if (t.getCategory().getType().equalsIgnoreCase("course"))
                courses.add(t);
        }
        return courses;
    }*/

    @NotNull
    public String toString( String s ) {
        if (day < 10 && month < 10) {
            return Integer.toString(year) + "0" + Integer.toString(month) + "0" + Integer.toString(day); 
        } else if (day >= 10 && month < 10) {
            return Integer.toString(year) + "0" + Integer.toString(month) + Integer.toString(day); 
        } else if (day < 10 && month >= 10) {
            return Integer.toString(year) + Integer.toString(month) + "0" + Integer.toString(day); 
        } else if (day >= 10 && month >= 10) {
            return Integer.toString(year) + Integer.toString(month) + Integer.toString(day); 
        }
        return null;
    }

}
