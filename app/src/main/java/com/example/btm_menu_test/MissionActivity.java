package com.example.btm_menu_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.annotation.Dimension;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.Timer;
import java.util.TimerTask;

public class MissionActivity extends AppCompatActivity {
    // 적용법 = progressbar 에서 max 부분과 private long trophy의 배열값을 같게 해야함
    private long[] trophyTimes = {3600000,        // 1시간
            10800000,    // 3시간
            86400000,   // 1일
            259200000,   // 3일
            604800000,   // 7일
            1209600000, // 14일
            1814400000 // 21일
    };

    private SharedPreferences sp;

    private long ChallengeTime;


    //private Button btn_reset;   // 도전과제 작동 테스트용

    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5, progressBar6, progressBar7 ;
    private TextView progressBarText1, progressBarText2, progressBarText3, progressBarText4, progressBarText5, progressBarText6, progressBarText7;

    private CardView cardView1, cardView2, cardView3, cardView4, cardView5, cardView6, cardView7, cardView8;

    private ImageView trophy1, trophy2, trophy3, trophy4, trophy5, trophy6, trophy7, trophy8;



    int num = 0; // 도전과제 숫자



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.misson);

        // 타이틀바 텍스트 설정
        getSupportActionBar().setTitle("도전 과제");

        // 타이틀바 배경색 설정
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        trophy1 = findViewById(R.id.imageView1);
        trophy2 = findViewById(R.id.imageView2);
        trophy3 = findViewById(R.id.imageView3);
        trophy4 = findViewById(R.id.imageView4);
        trophy5 = findViewById(R.id.imageView5);
        trophy6 = findViewById(R.id.imageView6);
        trophy7 = findViewById(R.id.imageView7);
        trophy8 = findViewById(R.id.imageView8);
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar4 = findViewById(R.id.progressBar4);
        progressBar5 = findViewById(R.id.progressBar5);
        progressBar6 = findViewById(R.id.progressBar6);
        progressBar7 = findViewById(R.id.progressBar7);
        progressBarText1 = findViewById(R.id.progressBarText1);
        progressBarText2 = findViewById(R.id.progressBarText2);
        progressBarText3 = findViewById(R.id.progressBarText3);
        progressBarText4 = findViewById(R.id.progressBarText4);
        progressBarText5 = findViewById(R.id.progressBarText5);
        progressBarText6 = findViewById(R.id.progressBarText6);
        progressBarText7 = findViewById(R.id.progressBarText7);
        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);
        cardView5 = findViewById(R.id.cardView5);
        cardView6 = findViewById(R.id.cardView6);
        cardView7 = findViewById(R.id.cardView7);
        cardView8 = findViewById(R.id.cardView8);


        //SharedPreferences 설정
        sp = getSharedPreferences("ChallengeTime", MODE_PRIVATE);


        //  도전과제 작동 테스트용
        /*btn_reset = findViewById(R.id.btn_reset);
        // 데이터 초기화 + 트로피 dark로 전환
        btn_reset.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ChallengeTime = System.currentTimeMillis();
                sp.edit().putLong("ChallengeGetTime",ChallengeTime).apply();
                //trophy2.setImageResource(R.drawable.icon_dark);
                //trophy3.setImageResource(R.drawable.icon_dark);
                //trophy4.setImageResource(R.drawable.icon_dark);
                num = 0;
            }
        });*/

        // 그림에 설명 추가
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "금연을 시작하신것을 축하합니다!", Toast.LENGTH_SHORT).show();
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "총 금연 1시간차 성공 트로피입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "총 금연 3시간차 성공 트로피입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "총 금연 1일차 성공 트로피입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "총 금연 3일차 성공 트로피입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "총 금연 7일차 성공 트로피입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "총 금연 14일차 성공 트로피입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "총 금연 21일차 성공 트로피입니다.", Toast.LENGTH_SHORT).show();
            }
        });


        //"금연일수" + "금연시작" 도전과제 시작
        startSmokingChallenge();
    }

    private void startSmokingChallenge() {
        //앱 실행시 "금연시작" 트로피가 생성됩니다.
        trophy1.setImageResource(R.drawable.icon_bright);

        //"금연일수" 트로피를 시간마다 설정한 부분입니다.

        ChallengeTime = sp.getLong("ChallengeGetTime", 0);

        if(ChallengeTime == 0){
            ChallengeTime = System.currentTimeMillis();
            sp.edit().putLong("ChallengeGetTime",ChallengeTime).apply();
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long time = System.currentTimeMillis() - ChallengeTime; // 현재시간 - 시작시간
                progressBar1.setProgress((int) time);
                progressBar2.setProgress((int) time);
                progressBar3.setProgress((int) time);
                progressBar4.setProgress((int) time);
                progressBar5.setProgress((int) time);
                progressBar6.setProgress((int) time);
                progressBar7.setProgress((int) time);
                if(num == 0 && time > trophyTimes[0]){ // tropthyTimes = 배열값
                    trophy2.setImageResource(R.drawable.mission1_bright);
                    progressBar1.setVisibility(View.INVISIBLE);
                    progressBarText1.setTextColor(Color.parseColor("#8BD11A"));
                    num = 1;
                }

                if(num == 1 && time > trophyTimes[1]){
                    trophy3.setImageResource(R.drawable.mission2_bright);
                    progressBar2.setVisibility(View.INVISIBLE);
                    progressBarText2.setTextColor(Color.parseColor("#8BD11A"));
                    num = 2;
                }

                if(num == 2 && time > trophyTimes[2]){
                    trophy4.setImageResource(R.drawable.mission3_bright);
                    progressBar3.setVisibility(View.INVISIBLE);
                    progressBarText3.setTextColor(Color.parseColor("#8BD11A"));
                    num = 3;
                }

                if(num == 3 && time > trophyTimes[3]){
                    trophy5.setImageResource(R.drawable.mission4_bright);
                    progressBar4.setVisibility(View.INVISIBLE);
                    progressBarText4.setTextColor(Color.parseColor("#8BD11A"));
                    num = 4;
                }

                if(num == 4 && time > trophyTimes[4]){
                    trophy6.setImageResource(R.drawable.mission5_bright);
                    progressBar5.setVisibility(View.INVISIBLE);
                    progressBarText5.setTextColor(Color.parseColor("#8BD11A"));
                    num = 5;
                }

                if(num == 5 && time > trophyTimes[5]){
                    trophy7.setImageResource(R.drawable.mission6_bright);
                    progressBar6.setVisibility(View.INVISIBLE);
                    progressBarText6.setTextColor(Color.parseColor("#8BD11A"));
                    num = 6;
                }

                if(num == 6 && time > trophyTimes[6]){
                    trophy8.setImageResource(R.drawable.mission7_bright);
                    progressBar7.setVisibility(View.INVISIBLE);
                    progressBarText7.setTextColor(Color.parseColor("#8BD11A"));
                    num = 7;
                }

            }
        },0,1000);
    }
}
