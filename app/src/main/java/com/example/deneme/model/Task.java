package com.example.deneme.model;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class Task {
    //properties
    Category category;

    String title;
    String description;
    String preferredZone;

    boolean isCompleted;

    int startMinute = -1;
    int endMinute = -1;
    int startHour = -1;
    int endHour = -1;
    int timeGap;
    int priority;

    //constructors

    public Task( String title, Category category, int startHour, int startMinute, int endHour, int endMinute ) {
        this.title = title;
        this.category = category;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }
    public Task( String title, int priority, int startHour, int startMinute, int endHour, int endMinute ) {
        this.title = title;
        this.priority = priority;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
    }
    public Task(String title, Category category, int timeGap, String preferredZone) {
        this.title = title;
        this.category = category;
        this.timeGap = timeGap;
        this.preferredZone = preferredZone;
    }
    public Task(String title, int priority, int timeGap, String preferredZone) {
        this.title = title;
        this.priority = priority;
        this.timeGap = timeGap;
        this.preferredZone = preferredZone;
    }

    //methods

    public Category getCategory() {
        return category;
    }

    //@NonNull
    //@NotNull
    @Override
    public String toString() {
        if (category != null)
            return "Title: " + title + ", Priority level: " + category.getPriority() +
                    ", Category: " + category.getName() + ", " + startHour + "." + startMinute + " - " + endHour + "." + endMinute;
        else
            return "Title: " + title + ", Priority level: " + priority  + ", " + startHour + "." + startMinute + " - " + endHour + "." + endMinute;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public String getPreferredZone() {
        return preferredZone;
    }

    public int getTimeSpan() {
        return timeGap;
    }

    public int getPriority() {
        return priority;
    }

    public int getTimeGap() {
        return timeGap;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }
}
