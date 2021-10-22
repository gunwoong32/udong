package com.ace.udong;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.co.bootpay.Bootpay;
import kr.co.bootpay.BootpayAnalytics;
import kr.co.bootpay.enums.Method;
import kr.co.bootpay.enums.PG;
import kr.co.bootpay.enums.UX;
import kr.co.bootpay.listener.CancelListener;
import kr.co.bootpay.listener.CloseListener;
import kr.co.bootpay.listener.ConfirmListener;
import kr.co.bootpay.listener.DoneListener;
import kr.co.bootpay.listener.ErrorListener;
import kr.co.bootpay.listener.ReadyListener;
import kr.co.bootpay.model.BootExtra;
import kr.co.bootpay.model.BootUser;


public class Pay_pay extends AppCompatActivity {

    Button test;
    ListView listViewGiven, listViewCatalog;
    private int stuck = 10;
    String price ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pay);
        setTitle("예약하러가기");

        listViewGiven = findViewById(R.id.listViewGiven);
        listViewCatalog = findViewById(R.id.listViewCatalog);

        // 초기설정 - 해당 프로젝트(안드로이드)의 application id 값을 설정합니다. 결제와 통계를 위해 꼭 필요합니다.
        // 앱에서 확인하지 말고 꼭 웹 사이트에서 확인하자. 앱의 application id 갖다 쓰면 안됨!!!

        BootpayAnalytics.init(this, "616f68b97b5ba4001f52cdb2");

        /*값 넘겨받기*/
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String tel = intent.getStringExtra("tel");
        String time = intent.getStringExtra("time");
        String date = intent.getStringExtra("date");
        String personnel = intent.getStringExtra("personnel");
        String room = intent.getStringExtra("room");
        if (room.equals("A room")){
            price = "10000";
        }else if (room.equals("B room")){
            price ="13000";
        }else if (room.equals("C room")){
            price ="18000";
        }else if (room.equals("D room")){
            price ="24000";
        }

        /*넘어온 값 토스트로 확인*/
        Toast.makeText(getApplicationContext(),name+" "+tel+" "+time+" "+date+" "+personnel+" "+room+" "+price,Toast.LENGTH_LONG).show();

        /*받은값 오른쪽 listView*/
        /*while(cursor.moveToNext())로 바꾸어 읽어와야함*/
        ArrayList<String> listViewGiven2 = new ArrayList<>();
        listViewGiven2.add(name);
        listViewGiven2.add(tel);
        listViewGiven2.add(time);
        listViewGiven2.add(date);
        listViewGiven2.add(personnel);
        listViewGiven2.add(room);

        /*왼쪽 Catalog*/
        ArrayList<String> listViewCatalog2 = new ArrayList<>();
        listViewCatalog2.add("이름");
        listViewCatalog2.add("전화번호");
        listViewCatalog2.add("시간");
        listViewCatalog2.add("날짜");
        listViewCatalog2.add("인원수");
        listViewCatalog2.add("방");

        ArrayAdapter<String> listViewGivenAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listViewGiven2);
        ArrayAdapter<String> listViewCatalogAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,listViewCatalog2);
        listViewGiven.setAdapter(listViewGivenAdapter);
        listViewCatalog.setAdapter(listViewCatalogAdapter);

        test = findViewById(R.id.test);
        test.setText(price+"원 결제하기");
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BootUser bootUser = new BootUser().setPhone("010-XXXX-XXXX"); //자기 전화번호
                BootExtra bootExtra = new BootExtra().setQuotas(new int[]{0, 2, 3});



                Bootpay.init(getFragmentManager())
                        .setApplicationId("616f68b97b5ba4001f52cdb2") // 해당 프로젝트(안드로이드)의 application id값
                        .setPG(PG.INICIS) // 결제할 PG 사
                        .setMethod(Method.PHONE) // 결제수단
                        .setContext(Pay_pay.this)
                        .setBootUser(bootUser)
                        .setBootExtra(bootExtra)
                        .setUX(UX.PG_DIALOG)
//                      .setUserPhone("010-1234-5678") // 구매자 전화번호
                        .setName(room) // 결제할 상품명
                        .setOrderId("1234") // 결제 고유번호expire_month
                        .setPrice(Integer.parseInt(price)) // 결제할 금액

                        //개발자 메뉴얼에 가면 더 추가할 수 있음 .

                        .addItem("마우's 스", 1, "ITEM_CODE_MOUSE", 100) // 주문정보에 담길 상품정보, 통계를 위해 사용
                        .addItem("키보드", 1, "ITEM_CODE_KEYBOARD", 200, "패션", "여성상의", "블라우스") // 주문정보에 담길 상품정보, 통계를 위해 사용
                        .onConfirm(new ConfirmListener() { // 결제가 진행되기 바로 직전 호출되는 함수로, 주로 재고처리 등의 로직이 수행
                            @Override
                            public void onConfirm(@Nullable String message) {

                                if (0 < stuck) Bootpay.confirm(message); // 재고가 있을 경우.
                                else Bootpay.removePaymentWindow(); // 재고가 없어 중간에 결제창을 닫고 싶을 경우
                                Log.d("confirm", message);
                            }
                        })
                        .onDone(new DoneListener() { // 결제완료시 호출, 아이템 지급 등 데이터 동기화 로직을 수행합니다
                            @Override
                            public void onDone(@Nullable String message) {
                                Log.d("done", message);
                            }
                        })
                        .onReady(new ReadyListener() { // 가상계좌 입금 계좌번호가 발급되면 호출되는 함수입니다.
                            @Override
                            public void onReady(@Nullable String message) {
                                Log.d("ready", message);
                            }
                        })
                        .onCancel(new CancelListener() { // 결제 취소시 호출
                            @Override
                            public void onCancel(@Nullable String message) {

                                Log.d("cancel", message);
                            }
                        })
                        .onError(new ErrorListener() { // 에러가 났을때 호출되는 부분
                            @Override
                            public void onError(@Nullable String message) {
                                Log.d("error", message);
                            }
                        })
                        .onClose(
                                new CloseListener() { //결제창이 닫힐때 실행되는 부분
                                    @Override
                                    public void onClose(String message) {
                                        Log.d("close", "close");
                                        Intent intent = new Intent(getApplicationContext(),Pay_insert.class);
                                        intent.putExtra("id","테스트아이디");//아이디
                                        intent.putExtra("name","홍길동");//이름
                                        intent.putExtra("tel","010-1111-2222");//번호
                                        intent.putExtra("time","10:00~12:00");//시간
                                        intent.putExtra("date","2021-10-21");//날짜
                                        intent.putExtra("personnel","3");//인원수
                                        intent.putExtra("room","A room");//방번호
                                        intent.putExtra("price",price);//가격
                                        startActivity(intent);
                                    }
                                })
                        .request();
            }
        });
    }
}
