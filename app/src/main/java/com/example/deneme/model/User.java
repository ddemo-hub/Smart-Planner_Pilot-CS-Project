package com.example.deneme.model;

import android.graphics.Color;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.deneme.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class User implements BilPlannerView {

    //properties

    String name;
    String surname;
    String mail;
    String image;
    String accountDate;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firestore;

    public static ArrayList<Category> categories;
    public static ArrayList<Date> dates;
    public static ArrayList<Task> courses;

    public static ArrayList<String> notificationPreferencesList;
    public static ArrayList<String> colorPreferencesList;
    public static ArrayList<Integer> colorCodeList;
    public static ArrayList<Integer> preferencesIndices;

    public DocumentReference documentReference;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;

    //Constructor
    public User (){
        load();
    }

    public User (String name, String surname, String mail, String accountDate) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.accountDate = accountDate;

        categories = new ArrayList<Category>();
        dates = new ArrayList<Date>();
        courses = new ArrayList<Task>();
        preferencesIndices = new ArrayList<Integer>();
        notificationPreferencesList = new ArrayList<String>();
        colorPreferencesList = new ArrayList<String>();
        colorCodeList = new ArrayList<Integer>();
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        documentReference = firestore.collection("Users").document(firebaseUser.getEmail());

        preferencesIndices.add(0, 4);
        preferencesIndices.add(1, 5);
        preferencesIndices.add(2, 2);
        preferencesIndices.add(3, 1);
        preferencesIndices.add(4, 0);
        preferencesIndices.add(5, 2);

        notificationPreferencesList.add(0, "Ring");
        notificationPreferencesList.add(1, "Vibrate");
        notificationPreferencesList.add(2, "Mute");

        colorPreferencesList.add(0, "Black");
        colorCodeList.add(0, Color.BLACK);
        colorPreferencesList.add(1, "Cyan");
        colorCodeList.add(1, Color.CYAN);
        colorPreferencesList.add(2, "Magenta");
        colorCodeList.add(2, Color.MAGENTA);
        colorPreferencesList.add(3, "Blue");
        colorCodeList.add(3, Color.BLUE);
        colorPreferencesList.add(4, "Red");
        colorCodeList.add(4, Color.RED);
        colorPreferencesList.add(5, "Yellow");
        colorCodeList.add(5, Color.YELLOW);

        addCategory(new Category("Homework", 1));
        addCategory(new Category("Lecture", 1));
        addCategory(new Category("Study", 2));
        addCategory(new Category("Leisure Time", 3));
        addCategory(new Category("Sport Activity", 3));
    }

    //methods
    public void save() {
        firestore.collection("Users").document(mail).update("name",name);
        firestore.collection("Users").document(mail).update("surname",surname);
        firestore.collection("Users").document(mail).update("image",image);
        firestore.collection("Users").document(mail).update("accountDate",accountDate);
        firestore.collection("Users").document(mail).update("categories",categories);
        firestore.collection("Users").document(mail).update("dates",dates);
        firestore.collection("Users").document(mail).update("courses",courses);
        firestore.collection("Users").document(mail).update("preferencesIndices", preferencesIndices);
        firestore.collection("Users").document(mail).update("notificationPreferencesList", notificationPreferencesList);
        firestore.collection("Users").document(mail).update("colorPreferencesList", colorPreferencesList);
    }

    public void load() {
        documentReference = firestore.collection("Users").document(Objects.requireNonNull(firebaseUser.getEmail()));
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                name = (String) documentSnapshot.get("name");
                surname = (String) documentSnapshot.get("surname");
                mail = (String) documentSnapshot.get("email");
                image = (String) documentSnapshot.get("image");
                accountDate = (String) documentSnapshot.get("accountDate");
                categories = (ArrayList<Category>) documentSnapshot.get("categories");
                dates = (ArrayList<Date>) documentSnapshot.get("dates");
                courses = (ArrayList<Task>) documentSnapshot.get("courses");
                preferencesIndices = (ArrayList<Integer>) documentSnapshot.get("preferencesIndices");
                notificationPreferencesList = (ArrayList<String>) documentSnapshot.get("notificationPreferencesList");
                colorPreferencesList = (ArrayList<String>) documentSnapshot.get("colorPreferencesList");

            }
        });
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setSurname( String surname ) {
        this.surname = surname;
    }

    public void setMail( String mail ) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getMail() {
        return mail;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public ArrayList<Date> getDates() {
        return dates;
    }

    public ArrayList<Task> getCourses() {
        return courses;
    }

    public void addCategory( Category category ) {
        categories.add( category );
    }

    public void deleteCategory( Category category ) {
        categories.remove( category );
    }

    public void addCourse( Task course ) {
        courses.add( course );
    }

    public void deleteCourse( Task course ) {
        courses.remove( course );
    }

    public ArrayList<String> getNotificationPreferencesList() {
        return notificationPreferencesList;
    }

    public ArrayList<String> getColorPreferencesList() {
        return colorPreferencesList;
    }

    public ArrayList<Integer> getPreferencesIndices() {
        return preferencesIndices;
    }

    public void setPreferencesIndices(int index, int value) {
        preferencesIndices.set(index, value);
    }

    @Override
    public void updateView() {

    }
}
