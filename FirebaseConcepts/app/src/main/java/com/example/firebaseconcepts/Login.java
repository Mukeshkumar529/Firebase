package com.example.firebaseconcepts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText login_email, login_psw;
    private Button btn_login;

    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_email = findViewById(R.id.login_email);
        login_psw = findViewById(R.id.login_psw);

        btn_login = findViewById(R.id.btn_login_event);

        auth = FirebaseAuth.getInstance();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email1 = login_email.getText().toString();
                String txt_psw = login_psw.getText().toString();

                loginUser(txt_email1, txt_psw);
            }
        });


    }

    private void loginUser(String txtEmail1, String txtPsw) {
        auth.createUserWithEmailAndPassword(txtEmail1, txtPsw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(Login.this, "Login Successfull",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, Home.class));
                finish();
            }
        });
    }
}