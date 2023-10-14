package com.example.firebaseconcepts;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button login, register;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.btn_login);


        register.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this,Register.class));
            finish();
        });

        login.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        });


        }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

        if(current_user != null){
            startActivity(new Intent(MainActivity.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}
