package com.ace.udong;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TestSend extends AppCompatActivity {

    //예약하러가기 > intent로 값만 넘김
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_send);

        btnSend = findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Pay_pay.class);
                intent.putExtra("id","테스트아이디");//아이디
                intent.putExtra("name","홍길동");//이름
                intent.putExtra("tel","010-1111-2222");//번호
                intent.putExtra("time","10:00~12:00");//시간
                intent.putExtra("date","2021-10-21");//날짜
                intent.putExtra("personnel","3");//인원수
                intent.putExtra("room","A room");//방번호
                startActivity(intent);
            }
        });
    }
}