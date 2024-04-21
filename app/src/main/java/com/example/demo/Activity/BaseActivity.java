package com.example.demo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.demo.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class BaseActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    public FirebaseDatabase database;
    public String TAG="OCHA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database =FirebaseDatabase.getInstance();
        mAuth =FirebaseAuth.getInstance();
    }
}