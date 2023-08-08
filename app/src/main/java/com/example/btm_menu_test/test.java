package com.example.btm_menu_test;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import android.graphics.Color;

public class test extends AppCompatActivity {
    private LinearLayout effectContainer;
    private long startTime;
    public static final String TIMER_PREFS = "timer_prefs";
    public static final String START_TIME_KEY = "start_time_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SharedPreferences prefs = getSharedPreferences(TIMER_PREFS, MODE_PRIVATE);
        startTime = prefs.getLong(START_TIME_KEY, 0);

        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - startTime;
        double timeInSeconds = (double) timeDifference / 1000;
        long timeInMinutes = (long) (timeInSeconds / 60);

        effectContainer = findViewById(R.id.effectContainer);

        displayEffectsForQuitTime(timeInMinutes);
    }
    private void displayEffectsForQuitTime(long quitTimeInMinutes) {
        Log.d("QuitTime", "Quit Time in Minutes: " + quitTimeInMinutes);
        String[] effects = {
                "20분: 심박수와 혈압이 떨어집니다.",
                "12시간: 혈액의 일산화탄소 수치가 정상으로 떨어집니다.",
                "12주: 폐 기능이 회복되며, 혈액 순환이 좋아집니다.",
                "9달: 기침과 호흡 곤란이 줄어듭니다.",
                "1년: 심장병의 위험은 흡연자의 절반 정도로 줄어듭니다.",
                "5년: 뇌졸중에 걸릴 위험이 비 흡연자와 같아집니다.",
                "10년: 폐암의 위험이 줄어듭니다.",
                "15년: 심장병에 걸릴 위험이 비 흡연자와 동일해집니다.",
                "20년: 대사증후군의 위험성이 완전히 사라지게 됩니다."
        };

        LinearLayout headerLayout = new LinearLayout(this);
        headerLayout.setOrientation(LinearLayout.HORIZONTAL);
        headerLayout.setGravity(Gravity.CENTER);

        TextView headerTextView = new TextView(this);
        headerTextView.setText("기간별 금연 효과");
        headerTextView.setTextSize(24);
        headerTextView.setPadding(16, 16, 16, 32);
        headerTextView.setTypeface(Typeface.DEFAULT_BOLD);
        headerTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        headerLayout.addView(headerTextView);

        LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        headerLayoutParams.setMargins(0, 16, 0, 32);

        effectContainer.addView(headerLayout, headerLayoutParams);

        for (int i = 0; i < effects.length; i++) {
            LinearLayout effectLayout = new LinearLayout(this);
            effectLayout.setOrientation(LinearLayout.HORIZONTAL);

            LinearLayout imageProgressLayout = new LinearLayout(this);
            imageProgressLayout.setOrientation(LinearLayout.VERTICAL);
            imageProgressLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            ImageView imageView = new ImageView(this);
            imageView.setImageResource(getImageResource(i));

            int imageSize = 145;
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(imageSize, imageSize);
            imageView.setLayoutParams(imageParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setMax(100);
            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progress_bar_drawable, getTheme()));
            progressBar.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f
            ));

            imageProgressLayout.addView(imageView);
            imageProgressLayout.addView(progressBar);

            TextView effectTextView = new TextView(this);
            effectTextView.setText(effects[i]);
            effectTextView.setTextSize(15);
            effectTextView.setPadding(16, 16, 16, 16);
            effectTextView.setTypeface(Typeface.SERIF);
            effectTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            effectLayout.addView(imageProgressLayout);
            effectLayout.addView(effectTextView);

            long currentQuitTime = getQuitTimeInMinutes(i);
            long nextQuitTime = getQuitTimeInMinutes(i + 1);

            if (quitTimeInMinutes < currentQuitTime) {
                progressBar.setProgress(0);
                progressBar.getProgressDrawable().setColorFilter(Color.GRAY, android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (quitTimeInMinutes >= currentQuitTime && (nextQuitTime == 0 || quitTimeInMinutes < nextQuitTime)) {
                long timeRemaining = quitTimeInMinutes - currentQuitTime;
                double progressPercentage = (double) timeRemaining / (nextQuitTime - currentQuitTime) * 100;
                progressBar.setProgress((int) progressPercentage);
            } else {
                progressBar.setProgress(100);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 40, 0, 40);

            effectContainer.addView(effectLayout, layoutParams);
        }
    }
    private long getQuitTimeInMinutes(int index) {
        switch (index) {
            case 0:
                return 20;  // 20분
            case 1:
                return 12 * 60;  // 12시간 (시간 -> 분 변환)
            case 2:
                return 12 * 7 * 24 * 60;  // 12주 (주 -> 분 변환)
            case 3:
                return 9 * 30 * 24 * 60;  // 9달 (달 -> 분 변환)
            case 4:
                return 365 * 24 * 60;  // 1년 (년 -> 분 변환)
            case 5:
                return 5 * 365 * 24 * 60;  // 5년 (년 -> 분 변환)
            case 6:
                return 10 * 365 * 24 * 60;  // 10년 (년 -> 분 변환)
            case 7:
                return 15 * 365 * 24 * 60;  // 15년 (년 -> 분 변환)
            case 8:
                return 20 * 365 * 24 * 60;  // 20년 (년 -> 분 변환)
            default:
                return 0;
        }
    }
    private int getImageResource(int index) {
        switch (index) {
            case 0:
                return R.drawable.image1;
            case 1:
                return R.drawable.image2;
            case 2:
                return R.drawable.image3;
            case 3:
                return R.drawable.image4;
            case 4:
                return R.drawable.image5;
            case 5:
                return R.drawable.image6;
            case 6:
                return R.drawable.image7;
            case 7:
                return R.drawable.image8;
            case 8:
                return R.drawable.image9;
            default:
                return 0;
        }
    }
}