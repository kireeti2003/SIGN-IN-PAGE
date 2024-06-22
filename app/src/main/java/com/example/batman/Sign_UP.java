package com.example.batman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.batman.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_UP extends AppCompatActivity {

    EditText signupemail,signuppa,signup_confpa;
    TextView loginred;
    Button sighupbt;
    String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        signupemail=findViewById(R.id.signup_email);
//        signupname=findViewById(R.id.signup_name);
        signuppa=findViewById(R.id.signup_pa);
        sighupbt=findViewById(R.id.signup_button);
        signup_confpa=findViewById(R.id.signup_confpa);

        loginred=findViewById(R.id.loginRedirectText);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        loginred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_UP.this, MainActivity.class);
                startActivity(intent);
            }
        });
        sighupbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Performauth();
            }
        });
    }



    private void Performauth() {

        String email=signupemail.getText().toString();
//        String name=signupname.getText().toString();
        String password=signuppa.getText().toString();
        String conf_password=signup_confpa.getText().toString();

        if(!email.matches(EMAIL_PATTERN)){
            signupemail.setError("Please check your email");

        }else if(password.isEmpty()||password.length()<6) {
            signuppa.setError("Minimum 6 letters");
        } else if (!password.equals(conf_password)) {
            signup_confpa.setError("Password is doen't match");

        }else{

            progressDialog.setMessage("Please wait while registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(Sign_UP.this, "Registration complete", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Sign_UP.this, MainActivity.class);
                        startActivity(intent);

                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(Sign_UP.this, ""+task.getException(), Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }


    }


}