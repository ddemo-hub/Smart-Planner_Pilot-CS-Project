package com.example.deneme.ui.calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.example.deneme.AddActivity;
import com.example.deneme.R;
import com.example.deneme.model.Date;
import com.example.deneme.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private User user;
    private ArrayList<Date> dates;
    private int myDay;
    private int myMonth;
    private int myYear;
    private Date date;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_calendar );

        dates = User.dates;

        calendarView = ( CalendarView ) findViewById( R.id.calendarView );
        calendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                myDay = dayOfMonth;
                myMonth = month + 1;
                myYear = year;
            }
        });

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.addActivityButton);
        myFab.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent( CalendarActivity.this, AddActivity.class );
                intent.putExtra( "day", myDay );
                intent.putExtra( "month", myMonth );
                intent.putExtra( "year", myYear );
                startActivity( intent );
            }
        });
    }

    public void launchDailyCalendar( View v ) {



        Intent intent = new Intent( CalendarActivity.this, DayActivity.class );
        //intent.putExtra( "date", date);
        intent.putExtra( "day", myDay );
        intent.putExtra( "month", myMonth );
        intent.putExtra( "year", myYear );
        startActivity( intent );
    }
}