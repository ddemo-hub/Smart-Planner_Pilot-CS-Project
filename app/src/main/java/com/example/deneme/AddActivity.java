package com.example.deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.example.deneme.databinding.ActivityAddBinding;
import com.example.deneme.databinding.ActivityLoginBinding;
import com.example.deneme.model.Category;
import com.example.deneme.model.Date;
import com.example.deneme.model.Task;
import com.example.deneme.model.User;

import java.time.LocalTime;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    //properties
    private ActivityAddBinding binding;
    private ArrayList<Category> categoriesList;
    private User user;
    boolean timeSwitchBoolean;

    private int startHour;
    private int startMinute;
    private int endHour;
    private int endMinute;
    private int timeAllocation;
    private String preferredZone;
    private String title;
    private String category;
    private int importance;
    private Category categoryObject;
    private Task task;
    private int day;
    private int month;
    private int year;
    private Date date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate( getLayoutInflater() );
        View view = binding.getRoot();
        setContentView(view);

        Intent i = getIntent();
        Bundle extras = i.getExtras();
        if( extras != null) {
            day = (int) extras.get("day");
            month = (int) extras.get("month");
            year = (int) extras.get("year");
        }

        this.setTitle(day + "/"+ month + "/" + year);

        findViewById(R.id.timeAllocation).setEnabled(false);
        findViewById(R.id.addActivityImportance).setEnabled(false);
        findViewById(R.id.preferredZone).setEnabled(false);

        ( (Switch) findViewById( R.id.timeSwitch ) ).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked)
                    timeSwitchBoolean = false;
                else
                    timeSwitchBoolean = true;

                findViewById(R.id.startHourEntry).setEnabled(timeSwitchBoolean);
                findViewById(R.id.startMinuteEntry).setEnabled(timeSwitchBoolean);
                findViewById(R.id.endHourEntry).setEnabled(timeSwitchBoolean);
                findViewById(R.id.endMinuteEntry).setEnabled(timeSwitchBoolean);
                findViewById(R.id.timeAllocation).setEnabled(isChecked);
                findViewById(R.id.preferredZone).setEnabled(isChecked);
            }
        });

        ( (Switch) findViewById( R.id.categorySwitch ) ).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if( isChecked){
                    findViewById(R.id.addActivityImportance).setEnabled(true);
                    findViewById(R.id.addActivityCategory).setEnabled(false);
                }
                else {
                    findViewById(R.id.addActivityCategory).setEnabled(true);
                    findViewById(R.id.addActivityImportance).setEnabled(false);
                }
            }
        });

        categoriesList = User.categories;

        //creating the dropdown menu for Category
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item,
                addCategoriesToArray( categoriesList ) );
        binding.addActivityCategory.setAdapter( myAdapter );

        //creating the dropdown menu for Importance
        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray( R.array.importanceLevels ) );
        binding.addActivityImportance.setAdapter( myAdapter2 );

        //creating the dropdown menu for Zone
        ArrayAdapter<String> myAdapter3 = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray( R.array.preferredZone ) );
        binding.preferredZone.setAdapter( myAdapter3 );
    }

    public void createTask( View v ) {
        title = binding.addActivityTitle.getText().toString();
        category = binding.addActivityCategory.getSelectedItem().toString();

        if( binding.timeSwitch.isChecked() ) {
            timeAllocation = Integer.parseInt(( ( EditText ) findViewById(R.id.timeAllocation)).getText().toString());
            preferredZone = ((Spinner) findViewById(R.id.preferredZone)).getSelectedItem().toString();
        } else {
            startHour = Integer.parseInt(( binding.startHourEntry).getText().toString());
            startMinute = Integer.parseInt(( binding.startMinuteEntry).getText().toString());
            endHour = Integer.parseInt(( binding.endHourEntry).getText().toString());
            endMinute = Integer.parseInt(( binding.endMinuteEntry).getText().toString());
        }

        if( binding.categorySwitch.isChecked() ) {
            String priorityString = binding.addActivityImportance.getSelectedItem().toString();
            if( priorityString.equalsIgnoreCase("first priority"))
                importance = 1;
            else if( priorityString.equalsIgnoreCase("second priority"))
                importance = 2;
            else if( priorityString.equalsIgnoreCase("third priority"))
                importance = 3;
        } else {
            category = binding.addActivityCategory.getSelectedItem().toString();
        }

        int index = -1;

        for( int i = 0; i < categoriesList.size(); i++ ){
            if( categoriesList.get(i).getName().equalsIgnoreCase( category ) )
                index = i;
        }
        categoryObject = categoriesList.get( index );

        if( binding.categorySwitch.isChecked() && binding.timeSwitch.isChecked() ) {
            task = new Task( title, importance, timeAllocation, preferredZone );
        } else if( binding.categorySwitch.isChecked() && !binding.timeSwitch.isChecked() ) {
            task = new Task( title, importance, startHour, startMinute, endHour, endMinute );
        } else if( !binding.categorySwitch.isChecked() && binding.timeSwitch.isChecked() ){
            task = new Task( title, categoryObject, timeAllocation, preferredZone );
        } else {
            task = new Task( title, categoryObject, startHour, startMinute, endHour, endMinute );
        }

        //System.out.println(task.toString());

        int existance = -1;
        for (int i = 0; i < User.dates.size(); i++) {
            if (day == User.dates.get(i).getDay() && month == User.dates.get(i).getMonth()) {
                existance = i;
            }
        }

        if (existance == -1) {
            date = new Date(day,month,year);
            date.addTask(task);
            User.dates.add(date);
            //System.out.println(User.dates.get(0).getTasks().get(0).toString() + "MMMMMMMMMMMMMMMM");
        } else if (existance != -1) {
            User.dates.get(existance).addTask(task);
        }

        finish();
    }

    // Used to pass the values from arraylist to array
    public String[] addCategoriesToArray( ArrayList<Category> arrayList ) {
        String[] categoryArray = new String[arrayList.size()];
        for( int i = 0; i < arrayList.size(); i++ )
            categoryArray[i] = arrayList.get(i).getName();
        return categoryArray;
    }
}
