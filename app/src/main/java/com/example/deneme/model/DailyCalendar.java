package com.example.deneme.model;

import java.util.ArrayList;
import java.util.Collections;

public class DailyCalendar extends Calendar{

    //properties
    Date date;
    ArrayList<Task> tasks;
    ArrayList<Task> activities;
    ArrayList<Task> toDo;
    Task[][] day = new Task[24][6];

    //Constructor

    public DailyCalendar(Date date) {
        this.date = date;
        activities = new ArrayList<Task>();
        toDo = new ArrayList<Task>();
        tasks = date.getTasks();
    }

    //methods

    public void createSchedule() {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getStartHour() != -1) {
                if (tasks.get(i).getStartHour() > tasks.get(i).getEndHour()) {
                    int endHour = tasks.get(i).getEndHour() + 24;
                    for (int j = tasks.get(i).getStartHour(); j <= endHour; j++) {
                        for (int k = 0; k < 6; k++) {
                            if (day[j%24][k] == null) {
                                day[j%24][k] = tasks.get(i);
                            }
                        }
                    }
                    for (int j = (tasks.get(i).getStartMinute() - 1); j > 0; j--) {
                        if (day[tasks.get(i).getStartHour()][j].equals(tasks.get(i))) {
                            day[tasks.get(i).getStartHour()][j] = null;
                        }
                    }
                    if (tasks.get(i).getStartMinute() != 0) {
                        if (day[tasks.get(i).getStartHour()][0].equals(tasks.get(i))) {
                            day[tasks.get(i).getStartHour()][0] = null;
                        }
                    }
                    for (int j = tasks.get(i).getEndMinute(); j <6; j++) {
                        if (day[tasks.get(i).getEndHour()][j].equals(tasks.get(i))) {
                            day[tasks.get(i).getEndHour()][j] = null;
                        }
                    }
                    if (tasks.get(i).getEndMinute() == 0) {
                        if (day[tasks.get(i).getEndHour()][0] == null || day[tasks.get(i).getEndHour()][0].equals(tasks.get(i))) {
                            day[tasks.get(i).getEndHour()][0] = null;
                        }
                    }
                } else if (tasks.get(i).getStartHour() == tasks.get(i).getEndHour()) {
                    for (int j = tasks.get(i).getStartMinute(); j <= tasks.get(i).getEndMinute(); j++) {
                        day[tasks.get(i).getStartHour()][j] = tasks.get(i);
                    }
                } else if (tasks.get(i).getStartHour() < tasks.get(i).getEndHour()) {
                    for (int j = tasks.get(i).getStartHour(); j <= tasks.get(i).getEndHour(); j++) {
                        for (int k = 0; k < 6; k++) {
                            if (day[j][k] == null) {
                                day[j][k] = tasks.get(i);
                            }
                        }
                    }
                    for (int j = (tasks.get(i).getStartMinute() - 1); j > 0; j--) {
                        if (day[tasks.get(i).getStartHour()][j].equals(tasks.get(i))) {
                            day[tasks.get(i).getStartHour()][j] = null;
                        }
                    }
                    if (tasks.get(i).getStartMinute() != 0) {
                        if (day[tasks.get(i).getStartHour()][0].equals(tasks.get(i))) {
                            day[tasks.get(i).getStartHour()][0] = null;
                        }
                    }
                    for (int j = tasks.get(i).getEndMinute(); j <6; j++) {
                        if (day[tasks.get(i).getEndHour()][j].equals(tasks.get(i))) {
                            day[tasks.get(i).getEndHour()][j] = null;
                        }
                    }
                    if (tasks.get(i).getEndMinute() == 0) {
                        if (day[tasks.get(i).getEndHour()][0] == null || day[tasks.get(i).getEndHour()][0].equals(tasks.get(i)) ) {
                            day[tasks.get(i).getEndHour()][0] = null;
                        }
                    }
                }
            } else if (tasks.get(i).getStartHour() == -1) {
                activities.add(tasks.get(i));
            }
        }

        sortActivities(activities, 0, (activities.size() - 1));

        boolean free = true;
        boolean done = false;
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getPreferredZone().equals("Morning")) {
                if (!done) {
                    for (int j = 5; j <= (12 - activities.get(i).getTimeSpan()); j++) {
                        if (!done) {
                            for (int k = 0; k < 6; k++) {
                                if (!done) {
                                    if (day[j][k] == null) {
                                        for (int l = j; l < (j + activities.get(i).getTimeSpan()); l++) {
                                            if (!done) {
                                                for (int m = k; m < 6; m++) {
                                                    if (day[l][m] != null) {
                                                        free = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (free && day[j + activities.get(i).getTimeSpan()][k] != null) {
                                            free = false;
                                        }
                                        if (free) {
                                            activities.get(i).setStartHour(j);
                                            activities.get(i).setStartMinute(k);
                                            activities.get(i).setEndHour(j + activities.get(i).getTimeSpan());
                                            activities.get(i).setEndMinute(k);
                                            this.addActivity(activities.get(i), j, k, (j + activities.get(i).getTimeSpan()), k);
                                            done = true;
                                        }
                                        free = true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (activities.get(i).getPreferredZone().equals("Afternoon")) {
                if (!done) {
                    for (int j = 12; j <= (17 - activities.get(i).getTimeSpan()); j++) {
                        if (!done) {
                            for (int k = 0; k < 6; k++) {
                                if (!done) {
                                    if (day[j][k] == null) {
                                        for (int l = j; l < (j + activities.get(i).getTimeSpan()); l++) {
                                            if (!done) {
                                                for (int m = k; m < 6; m++) {
                                                    if (day[l][m] != null) {
                                                        free = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (free && day[j + activities.get(i).getTimeSpan()][k] != null) {
                                            free = false;
                                        }
                                        if (free) {
                                            activities.get(i).setStartHour(j);
                                            activities.get(i).setStartMinute(k);
                                            activities.get(i).setEndHour(j + activities.get(i).getTimeSpan());
                                            activities.get(i).setEndMinute(k);
                                            this.addActivity(activities.get(i), j, k, (j + activities.get(i).getTimeSpan()), k);
                                            done = true;
                                        }
                                        free = true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (activities.get(i).getPreferredZone().equals("Evening")) {
                if (!done) {
                    for (int j = 17; j <= (21 - activities.get(i).getTimeSpan()); j++) {
                        if (!done) {
                            for (int k = 0; k < 6; k++) {
                                if (!done) {
                                    if (day[j][k] == null) {
                                        for (int l = j; l < (j + activities.get(i).getTimeSpan()); l++) {
                                            if (!done) {
                                                for (int m = k; m < 6; m++) {
                                                    if (day[l][m] != null) {
                                                        free = false;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                        if (free && day[j + activities.get(i).getTimeSpan()][k] != null) {
                                            free = false;
                                        }
                                        if (free) {
                                            activities.get(i).setStartHour(j);
                                            activities.get(i).setStartMinute(k);
                                            activities.get(i).setEndHour(j + activities.get(i).getTimeSpan());
                                            activities.get(i).setEndMinute(k);
                                            this.addActivity(activities.get(i), j, k, (j + activities.get(i).getTimeSpan()), k);
                                            done = true;
                                        }
                                        free = true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            done = false;
        }

    }

    public void addActivity(Task theTask, int startHour, int startMinute, int endingHour, int endMinute) {
        if (startHour > endingHour) {
            int endHour = endingHour + 24;
            for (int j = startHour; j <= endHour; j++) {
                for (int k = 0; k < 6; k++) {
                    if (day[j%24][k] == null) {
                        day[j%24][k] = theTask;
                    }
                }
            }
            for (int j = (startMinute - 1); j > 0; j--) {
                if (day[startHour][j].equals(theTask)) {
                    day[startHour][j] = null;
                }
            }
            if (startMinute != 0) {
                if (day[startHour][0].equals(theTask)) {
                    day[startHour][0] = null;
                }
            }
            for (int j = endMinute; j < 6; j++) {
                if (day[endingHour][j].equals(theTask)) {
                    day[endingHour][j] = null;
                }
            }
            if (endMinute == 0) {
                if (day[endingHour][0] == null || day[endingHour][0].equals(theTask)) {
                    day[endingHour][0] = null;
                }
            }
        } else if (startHour == endingHour) {
            for (int j = startMinute; j <= endMinute; j++) {
                day[startHour][j] = theTask;
            }
        } else if (startHour < endingHour) {
            for (int j = startHour; j <= endingHour; j++) {
                for (int k = 0; k < 6; k++) {
                    if (day[j][k] == null) {
                        day[j][k] = theTask;
                    }
                }
            }
            for (int j = (startMinute - 1); j > 0; j--) {
                if (day[startHour][j].equals(theTask)) {
                    day[startHour][j] = null;
                }
            }
            if (startMinute != 0) {
                if (day[startHour][0].equals(theTask)) {
                    day[startHour][0] = null;
                }
            }
            for (int j = endMinute; j <6; j++) {
                if (day[endingHour][j].equals(theTask)) {
                    day[endingHour][j] = null;
                }
            }
            if (endMinute == 0) {
                if (day[endingHour][0] == null || day[endingHour][0].equals(theTask)) {
                    day[endingHour][0] = null;
                }
            }
        }
    }

    public static void sortActivities (ArrayList<Task> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            sortActivities(arr, begin, partitionIndex-1);
            sortActivities(arr, partitionIndex+1, end);
        }
    }

    public static int partition (ArrayList<Task> arr, int begin, int end) {
        int pivot;
        if (arr.get(end).getCategory() != null) {
            pivot = arr.get(end).getCategory().getPriority();
        }
        else {
            pivot = arr.get(end).getPriority();
        }

        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j).getCategory() != null) {
                if (arr.get(j).getCategory().getPriority() <= pivot) {
                    i++;

                    Collections.swap(arr, i, j);
                }
            } else if (arr.get(j).getCategory() == null) {
                if (arr.get(j).getPriority() <= pivot) {
                    i++;

                    Collections.swap(arr, i, j);
                }
            }
        }

        Collections.swap(arr, i+1, end);

        return i+1;
    }

    public static void sortActivitiesByStart (ArrayList<Task> arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partitionForStart(arr, begin, end);

            sortActivitiesByStart(arr, begin, partitionIndex-1);
            sortActivitiesByStart(arr, partitionIndex+1, end);
        }
    }

    public static int partitionForStart (ArrayList<Task> arr, int begin, int end) {
        int pivot;
        pivot = arr.get(end).getStartHour();

        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr.get(j).getStartHour() < pivot) {
                i++;

                Collections.swap(arr, i, j);
            } else if (arr.get(j).getStartHour() == pivot) {
                if (arr.get(j).getStartMinute() < arr.get(end).getStartMinute()) {
                    i++;

                    Collections.swap(arr, i, j);
                }
            }
        }

        Collections.swap(arr, i+1, end);

        return i+1;
    }


    public void createClock() {
        int startHour = -1;
        int startMinute = -1;
        int endHour = -1;
        int endMinute = -1;

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 6; j++) {
                if (j != 0) {
                    if (day[i][j] == null && day[i][j-1] != null) {
                        startHour = i;
                        startMinute = j;
                    }
                } else if (j == 0 && i != 0) {
                    if (day[i][j] == null && day[i - 1][5] != null) {
                        startHour = i;
                        startMinute = j;
                    }
                } else if (j == 0 && i == 0) {
                    if (day[i][j] == null && day[i][j + 1] == null) {
                        startHour = i;
                        startMinute = j;
                    }
                }

                if (startHour != -1) {
                    if (j != 5) {
                        if (day[i][j] == null && day[i][j + 1] != null) {
                            endHour = i;
                            endMinute = j;
                            tasks.add(new Task("Free", 10, startHour, startMinute, endHour, endMinute));
                            startHour = -1;
                            startMinute = -1;
                            endHour = -1;
                            endMinute = -1;
                        }
                    } else if (j == 5) {
                        if (day[i][j] == null && i != 23 && day[i + 1][0] != null) {
                            endHour = i;
                            endMinute = j;
                            tasks.add(new Task("Free", 10, startHour, startMinute, endHour, endMinute));
                            startHour = -1;
                            startMinute = -1;
                            endHour = -1;
                            endMinute = -1;
                        } else if (day[i][j] == null && i == 23) {
                            endHour = i;
                            endMinute = j;
                            tasks.add(new Task("Free", 10, startHour, startMinute, endHour, endMinute));
                            startHour = -1;
                            startMinute = -1;
                            endHour = -1;
                            endMinute = -1;
                        }
                    }
                }
            }
        }
        sortActivitiesByStart(tasks, 0, (tasks.size() - 1));
    }

    public void createLinearChart() {
        sortActivitiesByStart(tasks, 0, (tasks.size() - 1));
    }

    public void completeTask() {

    }

    public Task[][] getDay() {
        return day;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    @Override
    public void switchToNext() {

    }

    @Override
    public void switchToPrevious() {

    }
}
