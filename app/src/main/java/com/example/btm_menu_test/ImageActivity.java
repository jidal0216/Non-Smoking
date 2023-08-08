package com.example.btm_menu_test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageactivity_main);

        // 타이틀바 텍스트 설정
        getSupportActionBar().setTitle("금연 후기");

        // 타이틀바 배경색 설정
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        ImageView imageView4 = findViewById(R.id.imageView4);
        ImageView imageView5 = findViewById(R.id.imageView5);
        ImageView imageView6 = findViewById(R.id.imageView6);

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView ("https://brunch.co.kr/@mook2yo/62#comment");
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://brunch.co.kr/@mangoamigo/27");
            }
        });

        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://brunch.co.kr/@emethlee/132");
            }
        });

        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://brunch.co.kr/@i-g-young-min/64");
            }
        });

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://brunch.co.kr/@sdj6162/51");
            }
        });

        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebView("https://brunch.co.kr/@book-kingkong/178");
            }
        });
    }


    // url 받아서 LinkActivity로 전달
    private void openWebView(String url) {
        Intent intent = new Intent(this, LinkActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
