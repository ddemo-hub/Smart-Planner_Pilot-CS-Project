package com.example.deneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.deneme.model.Category;
import com.example.deneme.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    private User user;
    private ArrayList<Category> categoriesList;
    private String[] categoriesArray;
    private ArrayList<Spinner> spinners;
    private String categoryColor;
    private String categoryTitle;
    private int categoryPriority;
    private int categoryColorCode;

    public DocumentReference documentReference;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;

    public ArrayList<String> notificationPreferencesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings );

        categoriesList = User.categories;

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        documentReference = firestore.collection("Users").document(firebaseUser.getEmail());

        spinners = new ArrayList<Spinner>();
        spinners.add(0, findViewById( R.id.spinner) );
        spinners.add(1, findViewById( R.id.spinner2) );
        spinners.add(2, findViewById( R.id.spinner3) );
        spinners.add(3, findViewById( R.id.spinner4) );
        spinners.add(4, findViewById( R.id.spinner5) );
        spinners.add(5, findViewById( R.id.spinner6) );


        for( int index = 0; index < spinners.size(); index++ ) {

            if(index < 3 ) {
                spinners.get( index ).setAdapter( new ArrayAdapter<String>( this,
                        android.R.layout.simple_spinner_dropdown_item,
                        User.colorPreferencesList ));
            } else {
                spinners.get( index ).setAdapter( new ArrayAdapter<String>( this,
                        android.R.layout.simple_spinner_dropdown_item,
                        User.notificationPreferencesList ));
            }

            spinners.get( index ).setSelection( User.preferencesIndices.get(index) );
            int finalIndex = index;
            spinners.get(index).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    User.preferencesIndices.set(finalIndex, position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        }

        Spinner categoryColorSpinner = (Spinner) findViewById( R.id.categoryColorSpinner );
        Spinner categoryPrioritySpinner = (Spinner) findViewById( R.id.categoryPrioritySpinner );

        categoryColorSpinner.setAdapter( new ArrayAdapter<String>( this,
                android.R.layout.simple_spinner_dropdown_item,
                User.colorPreferencesList));
        categoryColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryColor = User.colorPreferencesList.get(position);
                categoryColorCode = User.colorCodeList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        categoryPrioritySpinner.setAdapter( new ArrayAdapter<String>( this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.importanceLevels)));
        categoryPrioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryPriority = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void setNotificationPreferencesList(ArrayList<String> arr) {
        notificationPreferencesList = arr;
    }

    public ArrayList<String> getNotificationPreferencesList() {
        return notificationPreferencesList;
    }

    public void createCategory( View view ) {
        categoryTitle = ( (EditText) (findViewById(R.id.categoryName)) ).getText().toString();
        String title = categoryTitle;

        if( !categoryTitle.equals("")) {
            if(User.categories.size() == 0){
                User.categories.add( new Category(categoryTitle, categoryPriority, categoryColorCode));
                Toast.makeText(this, "Succesfully created category", Toast.LENGTH_LONG).show();
            } else {
                for( Category c : User.categories ) {
                    if( c.getName().equals(categoryTitle))
                        title = null;
                }
                if(title != null) {
                    User.categories.add(new Category(title, categoryPriority, categoryColorCode));
                    Toast.makeText(this, "Succesfully created category", Toast.LENGTH_LONG).show();
                }
            }
        } else
            Toast.makeText(this, "Title cannot be empty!", Toast.LENGTH_LONG).show();
        //System.out.println(categoryColor +" *********** "+ categoryPriority + "*******" + categoryTitle );
        for( Category c: User.categories)
            System.out.println( c.getName());

    }

    // Used to pass the values from arraylist to array

/*    public String[] addCategoriesToArray( ArrayList<Category> arrayList ) {
        String[] categoryArray = new String[arrayList.size()];
        for( int i = 0; i < arrayList.size(); i++ )
            categoryArray[i] = arrayList.get(i).getName();
        return categoryArray;
    }*/

    /*
    public void retrieveData() {
        documentReference = firestore.collection( "Users").document(Objects.requireNonNull( firebaseUser.getEmail() ) );
        documentReference.get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                setNotificationPreferencesList ( (ArrayList<String>) documentSnapshot.get("notificationPreferencesList"));
                System.out.println( getNotificationPreferencesList().get(0) + "1111111111111111111" );
            }
        });
    }

     */
}