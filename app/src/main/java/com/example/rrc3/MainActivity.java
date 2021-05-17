package com.example.rrc3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private TextView adminb;
    private EditText usr_nam;
    private EditText usr_pwd;
    private Button login;
    private FirebaseAuth mAuth;

    private ProgressBar log_prog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(this);

        adminb = (TextView) findViewById(R.id.Admin_button);
        adminb.setOnClickListener(this);

        login =(Button)findViewById(R.id.blogin);
        login.setOnClickListener(this);

        usr_nam = (EditText)findViewById(R.id.email);
        usr_pwd = (EditText)findViewById(R.id.Password);

        log_prog = (ProgressBar)findViewById(R.id.progressBar);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this,Register.class));
                break;
            case R.id.Admin_button:
                startActivity(new Intent(this,Admin_verify.class));
                break;

            case R.id.blogin:
                usrlogin();
                break;
        }

    }

    private void usrlogin(){

        String email = usr_nam.getText().toString().trim();
        String password = usr_pwd.getText().toString().trim();

        if(email.isEmpty()){
            usr_nam.setError("Email is Required");
            usr_nam.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            usr_nam.setError("Enter a valid email address");
            usr_nam.requestFocus();
            return;

        }

        if(password.isEmpty()){
            usr_pwd.setError("Password  is Required");
            usr_pwd.requestFocus();
            return;
        }
        if(password.length()<6){
            usr_pwd.setError("atleast 6 characters !");
            usr_pwd.requestFocus();
            return;
        }

        log_prog.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    startActivity(new Intent(MainActivity.this, user_profile.class));


                }else{
                    Toast.makeText(MainActivity.this, "Failed to login ", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}