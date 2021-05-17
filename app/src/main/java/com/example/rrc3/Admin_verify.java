package com.example.rrc3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Admin_verify extends AppCompatActivity implements View.OnClickListener{

    private TextView adreg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_verify);

        adreg = (TextView)findViewById(R.id.reg_admin_b);
        adreg.setOnClickListener(this);





    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_admin_b:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }
}
