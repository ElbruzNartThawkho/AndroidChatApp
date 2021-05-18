package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    private Button signin,signup;
    public  void init()
    {
        signin=(Button) findViewById(R.id.signin);
        signup=(Button) findViewById(R.id.signup);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intentLogin);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intentLogin);
            }
        });
    }
}