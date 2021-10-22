package com.ace.udong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pay_insert extends AppCompatActivity {

    Button btnInsert3;
    MyDBHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_insert);
        btnInsert3 = findViewById(R.id.btnInsert3);
        MyDBHelper myDBHelper = new MyDBHelper(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String tel = intent.getStringExtra("tel");
        String time = intent.getStringExtra("time");
        String date = intent.getStringExtra("date");
        String personnel = intent.getStringExtra("personnel");
        String room = intent.getStringExtra("room");
        String price = intent.getStringExtra("price");

        btnInsert3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqlDB = myDBHelper.getWritableDatabase();
                String sql = "insert into reservation values('"+name+"', '"+ tel + "', '"+ time + "', '"+ date + "', '"+ personnel + "', '"+ room + "', '"+ price + "')";
                sqlDB.execSQL(sql);
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(),PayConfirmTest1.class);
                startActivity(intent);

            }
        });//btnInsert3
    }
}