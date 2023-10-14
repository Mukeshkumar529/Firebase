package com.example.firebaseconcepts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText register_email, register_psw;
    private Button btn_register_event;

    private FirebaseAuth auth;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register_email = findViewById(R.id.register_email);
        register_psw = findViewById(R.id.register_psw);

        btn_register_event = findViewById(R.id.btn_register_event);

        auth = FirebaseAuth.getInstance();


     btn_register_event.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             String txt_email = register_email.getText().toString();
             String txt_password = register_psw.getText().toString();

             if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                 Toast.makeText(Register.this,"User is Validation is not Corrects",Toast.LENGTH_SHORT).show();
             }
             else if(txt_password.length()<6){
                 Toast.makeText(Register.this,"User is Validation is not Corrects",Toast.LENGTH_SHORT).show();
             }
             else {
                 registerUser(txt_email, txt_password);
             }
         }
     });

    }

    private void registerUser(String txtEmail, String txtPassword) {
        auth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(Register.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Register.this,"User Registered Successfull",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this,Home.class));
                    finish();
                }
                else {
                    Toast.makeText(Register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}