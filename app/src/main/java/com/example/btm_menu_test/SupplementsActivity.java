package com.example.btm_menu_test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SupplementsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplements_main);

        // 타이틀바 텍스트 설정
        getSupportActionBar().setTitle("금연 보조제");

        // 타이틀바 배경색 설정
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        ImageView imageView5 = findViewById(R.id.imageView5);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView ("https://www.cnuhh.com/health/medicine/info.cs?act=view&infoId=829&searchKeyword=&searchCondition=&category2=3&pageIndex=1");
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView ("https://smartstore.naver.com/chip-and-chip/products/7564558562?NaPm=ct%3Dli60rfk8%7Cci%3Da70c9eff9ca81518f456791fb261963f99d40c84%7Ctr%3Dsls%7Csn%3D4554870%7Chk%3D06025a99fee15c489d5d73858d26614bdb63af8f");
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView ("https://smartstore.naver.com/ocean5000/products/6697546635?NaPm=ct%3Dli7bmbyg%7Cci%3D9559315c46d173f514084bdf3f1281e7bdf1c4e0%7Ctr%3Dsls%7Csn%3D1242488%7Chk%3D4993c2d4b9620cdea8c1b952ac054813fee1b672");
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView ("https://he-is.co.kr/product/detail.html?product_no=49&cate_no=54&display_group=1&cafe_mkt=naver_ks&mkt_in=Y&ghost_mall_id=naver&ref=naver_open&NaPm=ct%3Dli7brflk%7Cci%3Decd6deda867292e15b2bc0f3055ffb24a7dfe985%7Ctr%3Dsls%7Csn%3D5326864%7Chk%3D0ea6032599dbe4dca1d5414a7fbe2f5dca05716c");
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView ("https://smartstore.naver.com/andrewchocousa/products/7840513444?NaPm=ct%3Dli790a5k%7Cci%3Dd8d17b4a872cdb890565c7e9709754d235ef3608%7Ctr%3Dsls%7Csn%3D664346%7Chk%3D765941d419315448a2feea178a9e498dfcd74641");
            }
        });

    }

    // url 받아서 Linkactivity로 전달
    private void openWebView(String url) {
        Intent intent = new Intent(this, LinkActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}