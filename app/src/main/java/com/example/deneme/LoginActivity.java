package com.example.deneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.deneme.databinding.ActivityLoginBinding;
import com.example.deneme.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth auth;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();
    }

    public void signInClicked(View view)
    {

        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();


        if (email.equals("") || password.equals(""))
            Toast.makeText(this,"Enter Email and Password",Toast.LENGTH_SHORT).show();
        else
        {
            auth.signInWithEmailAndPassword( email,password ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(LoginActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    startActivity( new Intent( LoginActivity.this, MainActivity.class ) );
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull @NotNull Exception e) {
                    Toast.makeText(LoginActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void goRegister(View view)
    {
        startActivity( new Intent( LoginActivity.this, RegisterActivity.class ) );
    }


}