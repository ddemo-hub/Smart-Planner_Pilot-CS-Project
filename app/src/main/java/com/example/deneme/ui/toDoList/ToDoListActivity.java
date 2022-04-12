package com.example.deneme.ui.toDoList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.deneme.R;
import com.example.deneme.model.Date;
import com.example.deneme.model.Task;
import com.example.deneme.model.User;

import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    private User user;
    private ArrayList<Date> dates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

    }


}