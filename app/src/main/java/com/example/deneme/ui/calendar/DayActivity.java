
package com.example.deneme.ui.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deneme.R;
import com.example.deneme.databinding.ActivityDayBinding;
import com.example.deneme.databinding.ActivityMainBinding;
import com.example.deneme.databinding.ActivityUserBinding;
import com.example.deneme.model.Date;
import com.example.deneme.model.Task;
import com.example.deneme.model.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DayActivity extends AppCompatActivity {

    String title;
    private ActivityDayBinding binding;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int day;
        int month;
        int year;

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        //if( extras != null) {
        //date = (Date) extras.get("date");
        day = (int) extras.get("day");
        month = (int) extras.get("month");
        year = (int) extras.get("year");
        //}

        title = day + "/" + month +"/" + year;
        //String s = date.getDay() + " " + date.getMonthName() + " " + date.getYear();

        /*for( Task t : date.getTasks() ) {
            System.out.println(t.toString());
        }*/

        int existance = -1;

        for (int i = 0; i < User.dates.size(); i++) {
            if (day == User.dates.get(i).getDay() && month == User.dates.get(i).getMonth()) {
                existance = i;
            }
        }

        if (existance == -1) {
            date = new Date(day,month,year);
            //User.dates.add(date);
            //System.out.println(User.dates.get(0).getTasks().get(0).toString() + "MMMMMMMMMMMMMMMM");
        } else {
            //User.dates.get(existance).addTask(task);
            date = User.dates.get(existance);
        }

        TextView textView;
        if(date.getTasks().size() != 0) {
            for (int i = 0; i < date.getTasks().size(); i++) {
                if (date.getTasks().get(i) != null) {
                    if (i == 0) {
                        textView = findViewById(R.id.text0);
                        textView.setText(date.getTasks().get(i).getTitle());
                    } else if (i == 1) {
                        textView = findViewById(R.id.text1);
                        textView.setText(date.getTasks().get(i).getTitle());
                    }
                    else if (i == 2) {
                        textView = findViewById(R.id.text2);
                        textView.setText(date.getTasks().get(i).getTitle());
                    }
                    else if (i == 3) {
                        textView = findViewById(R.id.text3);
                        textView.setText(date.getTasks().get(i).getTitle());
                    }
                    else if (i == 4) {
                        textView = findViewById(R.id.text4);
                        textView.setText(date.getTasks().get(i).getTitle());
                    }
                    else {
                        textView = findViewById(R.id.text5);
                        textView.setText(date.getTasks().get(i).getTitle());
                    }
                }
            }
        }



        setTitle(title);
        binding.textView19.setText(title);
    }
}
