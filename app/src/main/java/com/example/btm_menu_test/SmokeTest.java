package com.example.btm_menu_test;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SmokeTest extends AppCompatActivity {

    RadioButton[] yesButtons;
    RadioButton[] noButtons;
    Button mResultButton;


    int mCount;
    int noCount;

    boolean uncheckedQuestionExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smoke_test);

        yesButtons = new RadioButton[] {
                findViewById(R.id.radio_button_1_1),
                findViewById(R.id.radio_button_2_1),
                findViewById(R.id.radio_button_3_1),
                findViewById(R.id.radio_button_4_1),
                findViewById(R.id.radio_button_5_1),
                findViewById(R.id.radio_button_6_1),
                findViewById(R.id.radio_button_7_1),
                findViewById(R.id.radio_button_8_1),
                findViewById(R.id.radio_button_9_1),
                findViewById(R.id.radio_button_10_1),
                findViewById(R.id.radio_button_11_1),
                findViewById(R.id.radio_button_12_1)
        };

        noButtons = new RadioButton[] {
                findViewById(R.id.radio_button_1_2),
                findViewById(R.id.radio_button_2_2),
                findViewById(R.id.radio_button_3_2),
                findViewById(R.id.radio_button_4_2),
                findViewById(R.id.radio_button_5_2),
                findViewById(R.id.radio_button_6_2),
                findViewById(R.id.radio_button_7_2),
                findViewById(R.id.radio_button_8_2),
                findViewById(R.id.radio_button_9_2),
                findViewById(R.id.radio_button_10_2),
                findViewById(R.id.radio_button_11_2),
                findViewById(R.id.radio_button_12_2)
        };


        mResultButton = findViewById(R.id.submit_button);

        mResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCount = 0;
                noCount = 0;
                for (int i = 0; i < yesButtons.length; i++) {
                    if (yesButtons[i].isChecked()) {
                        mCount++;
                    }
                }

                for (int j = 0; j < noButtons.length; j++) {
                    if (noButtons[j].isChecked()) {
                        noCount++;
                    }
                }

                String resultText;

                if (mCount + noCount < 12) {
                    resultText = "체크하지 않은 문항이 존재합니다.";
                } else if (mCount <= 3) {
                    resultText = "니코틴 의존이 적지만 금연을 추천합니다.";
                } else if (mCount >= 4 && mCount <= 8) {
                    resultText = "니코틴 의존입니다. 금연이 필요합니다.";
                } else {
                    resultText = "니코틴 중독입니다. 반드시 금연이 필요합니다.";
                }



                AlertDialog.Builder builder = new AlertDialog.Builder(SmokeTest.this);
                builder.setMessage(resultText)
                        .setCancelable(false)
                        .setPositiveButton("확인", null);
                AlertDialog alert = builder.create();
                alert.show();

                // 텍스트 가운대 정렬
                int textViewId = alert.getContext().getResources().getIdentifier("android:id/message", null, null);
                TextView textView = (TextView) alert.findViewById(textViewId);
                if (textView != null) {
                    textView.setGravity(Gravity.CENTER);
                }
            }
        });
    }
}