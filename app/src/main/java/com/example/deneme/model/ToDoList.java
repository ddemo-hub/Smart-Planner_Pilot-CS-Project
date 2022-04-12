package com.example.deneme.model;

import java.util.ArrayList;

public class ToDoList {

    //properties
    ArrayList<Task> tasks; 

    //constructors
    public ToDoList() {
        tasks = new ArrayList<Task>();
    }

    //methods
    public void addTask( Task t) {
        tasks.add(t);
    }

    public void removeTask( Task t) {
        tasks.remove(t);
    }

    public void completeTask() {

    }

    public void undoTask() {

    }
}