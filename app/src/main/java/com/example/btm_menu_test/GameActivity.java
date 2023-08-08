package com.example.btm_menu_test;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // 타이틀바 텍스트 설정
        getSupportActionBar().setTitle("유틸리티");

        // 타이틀바 배경색 설정
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        Button imageButton1 = (Button) findViewById(R.id.egggame_btn);
        Button imageButton2 = (Button) findViewById(R.id.egggame_btn2);
        Button imageButton3 = (Button) findViewById(R.id.egggame_btn3);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EggSub.class);
                startActivity(intent);
            }
        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SmokeTest.class);
                startActivity(intent);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MicTestActivity.class);
                startActivity(intent);
            }
        });
    }
}