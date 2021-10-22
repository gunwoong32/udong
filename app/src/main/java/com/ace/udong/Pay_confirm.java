package com.ace.udong;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Pay_confirm extends AppCompatActivity {

    ListView listView1, listView3;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_confirm);
        //액션바
        ActionBar ab = getSupportActionBar();
        ab.setTitle("우리 동네 합주실");

        listView1 = findViewById(R.id.listView1);
        listView3 = findViewById(R.id.listView3);

        myDBHelper = new MyDBHelper(this);

        //외부 레이아웃 파일 설정
        // 어디에 분리시켜놓은 layout 파일을 넣을지 결정!
        LinearLayout layout1 = findViewById(R.id.home);
        LinearLayout layout2 = findViewById(R.id.search);
        LinearLayout layout3 = findViewById(R.id.chat);
        LinearLayout layout4 = findViewById(R.id.mypage);


        // 탭호스트
        TabHost tabHost = findViewById(R.id.tabhost);
        tabHost.setup();

        //이미지뷰
        //1
        ImageView iv1 = new ImageView(this);
        iv1.setImageResource(R.drawable.home);
        iv1.setLayoutParams(new ViewGroup.LayoutParams(370,270));
        iv1.setPadding(50,15,80,50);
        //2
        ImageView iv2 = new ImageView(this);
        iv2.setImageResource(R.drawable.search);
        iv2.setLayoutParams(new ViewGroup.LayoutParams(370,270));
        iv2.setPadding(50,15,80,50);
        //3
        ImageView iv3 = new ImageView(this);
        iv3.setImageResource(R.drawable.chat);
        iv3.setLayoutParams(new ViewGroup.LayoutParams(370,270));
        iv3.setPadding(50,15,80,50);
        //4
        ImageView iv4 = new ImageView(this);
        iv4.setImageResource(R.drawable.mypage);
        iv4.setLayoutParams(new ViewGroup.LayoutParams(370,270));
        iv4.setPadding(50,15,80,50);

        //각탭별 설정
        TabHost.TabSpec tabSpecHome = tabHost.newTabSpec("home").setIndicator(iv1);
        tabSpecHome.setContent(R.id.home);
        tabHost.addTab(tabSpecHome);

        /*SELECT*/
        /*sql문 추후에 mid로 변경??*/

        SQLiteDatabase sqlDB = myDBHelper.getReadableDatabase();
        //String name = edtName.getText().toString();
        //String number = edtNumber.getText().toString();
        String sql = "select * from reservation where name = '" + "홍길동" + "'";
        //하나 가져오기
        //select * from groupTBL2 where gName = 'a'

       // String sql = "select * from reservation";
        Cursor cursor = sqlDB.rawQuery(sql,null);

        /*리스트뷰 목록명(왼쪽)*/
        ArrayList<String> listView2 = new ArrayList<>();
        ArrayList<String> listView4 = new ArrayList<>();
        listView4.add("이름");
        listView4.add("전화번호");
        listView4.add("시간");
        listView4.add("날짜");
        listView4.add("인원");
        listView4.add("방");
        listView4.add("가격");

        while(cursor.moveToNext()){
            //cursor.moveToNext() 첫번째 행을 가리키면서 있는지 없는지 체크
            //있으면 true 리턴
            //각 열에 있는 값들을 인덱스로 꺼내오면 된다.
            //인덱스는 0부터 시작
            //String result = cursor.getString(0) + " : " + cursor.getString(1);
            // strName += cursor.getString(0);
            //strNumber += cursor.getString(1);
            //Log.d("sqlite3DML",result);
            // result2 += result + " ";
            listView2.add(cursor.getString(0));
            listView2.add(cursor.getString(1));
            listView2.add(cursor.getString(2));
            listView2.add(cursor.getString(3));
            listView2.add(cursor.getString(4));
            listView2.add(cursor.getString(5));
            listView2.add(cursor.getString(6));
        }
        cursor.close();
        sqlDB.close();

        //Log.d("sqlite3DML",result2);
        ArrayAdapter<String> listViewAdapter1 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listView2);
        listView1.setAdapter(listViewAdapter1);
        ArrayAdapter<String> listViewAdapter2 = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listView4);
        listView3.setAdapter(listViewAdapter2);

        TabHost.TabSpec tabSpecSearch = tabHost.newTabSpec("search").setIndicator(iv2);
        tabSpecSearch.setContent(R.id.search);
        tabHost.addTab(tabSpecSearch);

        TabHost.TabSpec tabSpecChat = tabHost.newTabSpec("chat").setIndicator(iv3);
        tabSpecChat.setContent(R.id.chat);
        tabHost.addTab(tabSpecChat);

        TabHost.TabSpec tabSpecMypage = tabHost.newTabSpec("mypage").setIndicator(iv4);
        tabSpecMypage.setContent(R.id.mypage);
        tabHost.addTab(tabSpecMypage);

        tabHost.setCurrentTab(0);


    } //oncreate
} // class