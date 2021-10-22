package com.ace.udong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PayConfirmTest1 extends AppCompatActivity {

    Button btnSelectTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_confirm_test1);
        btnSelectTest = findViewById(R.id.btnSelectTest);


        btnSelectTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Pay_confirm.class);
                startActivity(intent);
            }
        });
    }
}