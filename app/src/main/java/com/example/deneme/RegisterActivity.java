package com.example.deneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.deneme.databinding.ActivityRegisterBinding;
import com.example.deneme.model.Category;
import com.example.deneme.model.Date;
import com.example.deneme.model.Task;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void signUpClicked(View view)
    {
        String name = binding.nameText.getText().toString();
        String surname = binding.surnameText.getText().toString();
        String email = binding.registerEmail.getText().toString();
        String password = binding.registerPassword.getText().toString();

        if (name.equals(""))
            Toast.makeText(RegisterActivity.this,"Enter your name!",Toast.LENGTH_SHORT).show();
        else if (surname.equals(""))
            Toast.makeText(RegisterActivity.this,"Enter your surname!",Toast.LENGTH_SHORT).show();
        else if (email.equals(""))
            Toast.makeText(RegisterActivity.this,"Enter your email address!",Toast.LENGTH_SHORT).show();
        else if (password.equals(""))
            Toast.makeText(RegisterActivity.this,"Enter your password!",Toast.LENGTH_SHORT).show();
        else {
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    HashMap<String, Object> userData =  new HashMap<>();

                    ArrayList<String> arr = new ArrayList<String >();
                    arr.add(0, "Mute");
                    arr.add(1, "Vibrate");
                    arr.add(2, "Ring");
                    arr.add(3, "hello");

                    userData.put("email",email);
                    userData.put("name",name);
                    userData.put("surname",surname);
                    userData.put("image","default");
                    userData.put("createdate", FieldValue.serverTimestamp());
                    userData.put("accountDate","");
                    userData.put("categories",new ArrayList<Category>());
                    userData.put("dates",new ArrayList<Date>());
                    userData.put("courses",new ArrayList<Task>());
                    userData.put("preferencesIndices", new ArrayList<Integer>());
                    userData.put("notificationPreferencesList",arr );
                    userData.put("colorPreferencesList",new ArrayList<String>() );

                    firestore.collection("Users").document(email).set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this,"Account successfully created!",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }
                    });


                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(RegisterActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}