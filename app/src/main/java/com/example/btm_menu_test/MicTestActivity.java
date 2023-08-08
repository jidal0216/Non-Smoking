package com.example.btm_menu_test;

import static java.util.concurrent.TimeUnit.SECONDS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.tomer.fadingtextview.FadingTextView;

import java.util.ArrayList;
import java.util.List;

public class MicTestActivity extends AppCompatActivity {
    // 기록할 리스트 추가
    private List<Long> measurements = new ArrayList<>();
    private long startTime;
    Intent intent;
    SpeechRecognizer mRecognizer;
    Button sttBtn;
    final int PERMISSION = 1;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mictest);

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        if ( Build.VERSION.SDK_INT >= 30 ){
            // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        sttBtn = findViewById(R.id.sttStart);

// sttResult 버튼 클릭 시 이벤트 처리
        Button sttResultBtn = findViewById(R.id.sttResult);
        sttResultBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("조용한 곳에서 '아' 또는 '어'를 최대한 길게 내보세요!\n현재 자신의 폐 상태를 측정합니다.")
                    .setPositiveButton("확인", (dialog, which) -> {
                        // 확인 버튼을 눌렀을 때 수행할 동작 추가
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        });

        intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");
        sttBtn.setOnClickListener(v -> {
            mRecognizer=SpeechRecognizer.createSpeechRecognizer(MicTestActivity.this);
            mRecognizer.setRecognitionListener(listener);
            mRecognizer.startListening(intent);
        });

        // sttAward 버튼 클릭 시 이벤트 처리
        Button sttAwardBtn = findViewById(R.id.sttAward);
        sttAwardBtn.setOnClickListener(v -> {

            // 정렬된 값을 다음 창으로 전달하여 화면 이동
            Intent intent = new Intent(MicTestActivity.this, SortedMeasurementsActivity.class);
            intent.putExtra("measurements", new ArrayList<>(measurements));
            startActivity(intent);
        });


    }

    private final RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            View view = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(view, "폐활량 측정을 시작합니다. \n있는 힘껏 버티세요!", Snackbar.LENGTH_SHORT);
            snackbar.show();
            startTime = System.currentTimeMillis();
        }
        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) / 1000; // 밀리초를 초로 변환

            View view = findViewById(android.R.id.content);
            Snackbar snackbar;

            if (duration < 10) {
                snackbar = Snackbar.make(view, "총 측정 시간: " + duration + "초\n폐기능이 정상에 못미칩니다.", Snackbar.LENGTH_SHORT);
            } else if (duration < 20) {
                snackbar = Snackbar.make(view, "총 측정 시간: " + duration + "초\n폐기능이 정상입니다.", Snackbar.LENGTH_SHORT);
            } else if (duration < 30) {
                snackbar = Snackbar.make(view, "총 측정 시간: " + duration + "초\n운동선수의 폐입니다.", Snackbar.LENGTH_SHORT);
            } else {
                snackbar = Snackbar.make(view, "총 측정 시간: " + duration + "초\n사람이 아니라 로봇이네요.", Snackbar.LENGTH_SHORT);
            }

            snackbar.show();
            measurements.add(duration);
        }

        @Override
        public void onError(int error) {

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                case SpeechRecognizer.ERROR_CLIENT:
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                case SpeechRecognizer.ERROR_NETWORK:
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                case SpeechRecognizer.ERROR_NO_MATCH:
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                case SpeechRecognizer.ERROR_SERVER:
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                default:
                    break;
            }
        }

        @Override
        public void onResults(Bundle results) {}

        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    };
}

