package com.example.deneme.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.example.deneme.R;
import com.example.deneme.model.DailyCalendar;
import com.example.deneme.model.Date;
import com.example.deneme.model.Task;
import com.example.deneme.model.User;
import com.example.deneme.model.Category;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class HomeActivity extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //TEST
        Date testDate = new Date(1, 8, 2021);
        testDate.addTask(new Task ("Sleep", new Category("Sleep", 2, Color.MAGENTA), 0, 0, 8, 0));
        testDate.addTask(new Task ("CS Lab", new Category("Class", 1, Color.BLUE), 3, "Morning"));
        testDate.addTask(new Task ("Lunch", new Category("Food", 2, Color.GREEN), 13, 2, 14, 2));
        testDate.addTask(new Task ("Math Work", new Category("Homework", 1, Color.RED), 14, 2, 17, 0));
        testDate.addTask(new Task ("Workout", new Category("Physical Activity", 3, Color.DKGRAY), 17, 0, 19, 3));
        testDate.addTask(new Task ("Rest", new Category("Resting", 3, Color.LTGRAY), 19, 3, 20, 3));
        testDate.addTask(new Task ("Dinner", new Category("Food", 2, Color.GREEN), 20, 3, 21, 3));
        testDate.addTask(new Task ("CS Course", new Category("Homework", 1, Color.RED), 21, 3, 23, 3));
        User.dates.add(testDate);
        
        Date today;
        
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String time = df.format(Calendar.getInstance().getTime());
        
        int index = 0;
        for (int i = 0; i < User.dates.size(); i++) {
            if (time.equals(User.dates.get(i).toString())) {
                index = i;   
            }
        }

        today = User.dates.get(index);
        DailyCalendar todaysCalendar = new DailyCalendar(today);
        todaysCalendar.createSchedule();
        todaysCalendar.createClock();


        for (int i = 0; i < todaysCalendar.getTasks().size(); i++) {
            System.out.println(todaysCalendar.getTasks().get(i));
        }

        tasks = todaysCalendar.getTasks();
        tasks.remove(tasks.size() - 1);
        pieChart = findViewById(R.id.activity_home_piechart);
        setupPieChart();
        loadPieChartData();


        ArrayList<Task> checkList = (ArrayList<Task>) tasks.clone();
        Iterator<Task> checkListIterator = checkList.iterator();
        while(checkListIterator.hasNext()) {
            if (checkListIterator.next().getTitle().equals("Free")) {
                checkListIterator.remove();
            }
        }

        TextView textView;
        for (int i = 0; i < checkList.size(); i++) {
            if (i == 0) {
                textView = findViewById(R.id.task0);
                textView.setText(checkList.get(i).getTitle());
            } else if (i == 1) {
                textView = findViewById(R.id.task1);
                textView.setText(checkList.get(i).getTitle());
            } else if (i == 2) {
                textView = findViewById(R.id.task2);
                textView.setText(checkList.get(i).getTitle());
            } else if (i == 3) {
                textView = findViewById(R.id.task3);
                textView.setText(checkList.get(i).getTitle());
            } else if (i == 4) {
                textView = findViewById(R.id.task4);
                textView.setText(checkList.get(i).getTitle());
            } else if (i == 5) {
                textView = findViewById(R.id.task5);
                textView.setText(checkList.get(i).getTitle());
            } else if (i == 6) {
                textView = findViewById(R.id.task6);
                textView.setText(checkList.get(i).getTitle());
            } else if (i == 7) {
                textView = findViewById(R.id.task7);
                textView.setText(checkList.get(i).getTitle());
            }
        }
    }

    
    public void setupPieChart() {
        pieChart.setDrawHoleEnabled(false);
        pieChart.setUsePercentValues(false);
        pieChart.setEntryLabelTextSize(10);
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);

    }
   
    public void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStartHour() < 10 && tasks.get(i).getEndHour() < 10) {
                entries.add(new PieEntry(Float.parseFloat(calculatePercentage(tasks.get(i))), tasks.get(i).getTitle() +
                    " \n" + "0" + tasks.get(i).getStartHour() + "." + tasks.get(i).getStartMinute() + "0" + " - " +
                        "0" + tasks.get(i).getEndHour() + "." + tasks.get(i).getEndMinute() + "0"));
            } else if (tasks.get(i).getStartHour() < 10 && tasks.get(i).getEndHour() >= 10) {
                entries.add(new PieEntry(Float.parseFloat(calculatePercentage(tasks.get(i))), tasks.get(i).getTitle() +
                    " \n" + "0" + tasks.get(i).getStartHour() + "." + tasks.get(i).getStartMinute() + "0" + " - " +
                        tasks.get(i).getEndHour() + "." + tasks.get(i).getEndMinute() + "0"));            
            } else if (tasks.get(i).getStartHour() >= 10 && tasks.get(i).getEndHour() >= 10) {
                entries.add(new PieEntry(Float.parseFloat(calculatePercentage(tasks.get(i))), tasks.get(i).getTitle() +
                    " \n" + tasks.get(i).getStartHour() + "." + tasks.get(i).getStartMinute() + "0" + " - " +
                        tasks.get(i).getEndHour() + "." + tasks.get(i).getEndMinute() + "0"));                      
            } else {
                entries.add(new PieEntry(Float.parseFloat(calculatePercentage(tasks.get(i))), tasks.get(i).getTitle() +
                    " \n" + tasks.get(i).getStartHour() + "." + tasks.get(i).getStartMinute() + "0" + " - " +
                        "0" + tasks.get(i).getEndHour() + "." + tasks.get(i).getEndMinute() + "0"));            
            }
        }

        //Colors

        ArrayList<Integer> colors = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getCategory() != null) {
                colors.add(tasks.get(i).getCategory().getChartColor());
            } else if (tasks.get(i).getCategory() == null) {
                colors.add(Color.GRAY);
            }
        }

        PieDataSet dataSet = new PieDataSet(entries, "Tasks");
        dataSet.setColors(colors);
        
        /*
        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }
        */

        //dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(34);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(0f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
     

    public static String calculatePercentage(Task task) {
        int time = 0;
        String timeString;
        if (task.getStartHour() <= task.getEndHour()) {
            time = (task.getEndHour() - task.getStartHour()) * 6;
            if (task.getStartMinute() <= task.getEndMinute()) {
                time = time + task.getEndMinute() - task.getStartMinute();
            } else if (task.getStartMinute() > task.getEndMinute()) {
                time = time - (task.getStartMinute() - task.getEndMinute());
            }
        } else if (task.getStartHour() > task.getEndHour()) {
            int endHour = task.getEndHour() + 24;
            time = (endHour - task.getStartHour()) * 6;
            if (task.getStartMinute() <= task.getEndMinute()) {
                time = time + task.getEndMinute() - task.getStartMinute();
            } else if (task.getStartMinute() > task.getEndMinute()) {
                time = time - (task.getStartMinute() - task.getEndMinute());
            }
        }
        time = time * 100 / 144;
        timeString = Integer.toString(time) + "f";
        return timeString;
    }
}
