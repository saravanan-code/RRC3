package com.example.rrc3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private TextView rrcb,regbutton;
    private EditText rage,rname,remail,rpassword;
    private ProgressBar reprog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        rrcb = (TextView) findViewById(R.id.rrcb);
        rrcb.setOnClickListener(this);

        regbutton = (Button) findViewById(R.id.regbutton);
        regbutton.setOnClickListener(this);

        rage = (EditText) findViewById(R.id.rage);
        rname = (EditText) findViewById(R.id.rname);
        remail = (EditText) findViewById(R.id.remail);
        rpassword = (EditText) findViewById(R.id.rpassword);

        reprog = (ProgressBar) findViewById(R.id.regprog);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.rrcb:
                    startActivity(new Intent(this,MainActivity.class));
                    break;
                case R.id.regbutton:
                    regbutton();
                    break;

            }
    }

    private void regbutton() {
        String email = remail.getText().toString().trim();
        String password = rpassword.getText().toString().trim();
        String name = rname.getText().toString().trim();
        String age = rage.getText().toString().trim();

        if (name.isEmpty()){
            rname.setError("Enter your name");
            rname.requestFocus();
            return;
        }

        if (email.isEmpty()){
            rname.setError("Enter your email address");
            rname.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            remail.setError("enter a valid email address");
            remail.requestFocus();
            return;
        }

        if (age.isEmpty()){
            rname.setError("Enter your age");
            rname.requestFocus();
            return;
        }

        if (password.isEmpty()){
            rname.setError("Enter your password");
            rname.requestFocus();
            return;
        }



        reprog.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(name, age, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(Register.this,"Registered Successfully",Toast.LENGTH_LONG).show();
                                        reprog.setVisibility(View.GONE);
                                    }else{

                                        Toast.makeText(Register.this, "Registeration Unsuccessful", Toast.LENGTH_LONG).show();
                                        reprog.setVisibility(View.GONE);
                                    }

                                }
                            });


                        }else{
                            Toast.makeText(Register.this, "Registeration Unsuccessful", Toast.LENGTH_LONG).show();
                            reprog.setVisibility(View.GONE);
                        }

                    }
                });


    }

}