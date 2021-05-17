package com.example.rrc3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Admin_registration extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private TextView ad_banner,ad_reg_b;
    private EditText adname,adage,ademail,adpassword,adphone;
    private ProgressBar adprog;
    private FirebaseAuth mAuth;
    public Spinner ad_gender,ad_bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        mAuth = FirebaseAuth.getInstance();

        ad_gender = findViewById(R.id.Gender_ad_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad_gender.setAdapter(adapter);
        ad_gender.setOnItemSelectedListener(this);

        ad_bg = findViewById(R.id.BG__ad_spinner);
        ArrayAdapter<CharSequence> badapter = ArrayAdapter.createFromResource(this, R.array.bloodGroups, android.R.layout.simple_spinner_item);
        badapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ad_bg.setAdapter(badapter);
        ad_bg.setOnItemSelectedListener(this);

        ad_banner = (TextView)findViewById(R.id.adb);
        ad_banner.setOnClickListener(this);
        ad_reg_b = (Button) findViewById(R.id.ad_reg_bt);
        ad_reg_b.setOnClickListener(this);

        adname = (EditText) findViewById(R.id.AD_name);
        adage = (EditText) findViewById(R.id.AD_age);
        ademail = (EditText) findViewById(R.id.AD_email);
        adpassword = (EditText) findViewById(R.id.AD_password);
        adphone = (EditText) findViewById(R.id.AD_phone);
        adprog = (ProgressBar) findViewById(R.id.ad_prog);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.adb:
                startActivity(new Intent(this,Admin_verify.class));
                break;
            case R.id.ad_reg_bt:
                ad_reg_b();
                break;

        }

    }

    private void ad_reg_b() {

        String email = ademail.getText().toString().trim();
        String password = adpassword.getText().toString().trim();
        String name = adname.getText().toString().trim();
        String age = adage.getText().toString().trim();
        String Gender = ad_gender.getSelectedItem().toString().trim();
        String bloodGroup = ad_bg.getSelectedItem().toString().trim();
        String mobile = adphone.getText().toString().trim();

        if (mobile.isEmpty()){
            adphone.setError("Enter your Mobile Number");
            adphone.requestFocus();
            return;
        }

        if (name.isEmpty()){
            adname.setError("Enter your name");
            adname.requestFocus();
            return;
        }

        if (email.isEmpty()){
            ademail.setError("Enter your email address");
            ademail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            ademail.setError("enter a valid email address");
            ademail.requestFocus();
            return;
        }

        if (age.isEmpty()){
            adage.setError("Enter your age");
            adage.requestFocus();
            return;
        }

        if (password.isEmpty()){
            adpassword.setError("Enter your password");
            adpassword.requestFocus();
            return;
        }

        adprog.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Admin admin = new Admin (name, age, email, Gender, mobile, bloodGroup );
                            FirebaseDatabase.getInstance().getReference("Admin")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(admin).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(Admin_registration.this, "Admin Registerd Successfully", Toast.LENGTH_LONG).show();
                                    }else{

                                        Toast.makeText(Admin_registration.this, "Registeration Unsuccessful", Toast.LENGTH_LONG).show();
                                        adprog.setVisibility(View.GONE);
                                    }

                                }
                            });
                        }else{
                                Toast.makeText(Admin_registration.this, "Registeration Unsuccessful", Toast.LENGTH_LONG).show();
                                adprog.setVisibility(View.GONE);
                        }

                    }
                });



    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner)parent;
        Spinner Gender_spinner = (Spinner)parent;
        if(spinner.getId() == R.id.BG__ad_spinner)
        {
            Toast.makeText(parent.getContext(), "", Toast.LENGTH_LONG).show();
        }
        if(Gender_spinner.getId() == R.id.Gender_ad_spinner)
        {
            Toast.makeText(parent.getContext(), "", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}