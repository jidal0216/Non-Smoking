package com.example.btm_menu_test;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAlarmActivity extends AppCompatActivity {
    private long startTime;
    private NotificationCompat.Builder notificationBuilder;
    private Handler handler;
    private Runnable updateNotificationRunnable;
    private RadioGroup radioGroup;
    private Button startButton, cancelButton;
    private TextView titleTextView;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private NotificationManager notificationManager;

    private List<String> quotes;

    private boolean isAlarmSet = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        titleTextView = findViewById(R.id.titleTextView);
        radioGroup = findViewById(R.id.radioGroup);
        startButton = findViewById(R.id.startButton);
        cancelButton = findViewById(R.id.cancelButton);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // 알림 채널 설정 (Android 8.0 이상에서 필요)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channel_id";
            CharSequence channelName = "channel_name";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription("channel_description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);

            notificationManager.createNotificationChannel(channel);
        }

        quotes = new ArrayList<>();
        quotes.add("큰 목표를 이루고 싶으면 허락을 구하지 마라.");
        quotes.add("일반적인 것을 잃을 위험을 감수하지 않으면 평범한 것에 만족해야 한다.");
        quotes.add("추구할 수 있는 용기가 있다면 우리의 모든 꿈은 이뤄질 수 있다.");
        quotes.add("기다리는 사람에게 좋은 일이 생기지만, 찾아나서는 사람에게는 더 좋은 일이 생긴다.");
        quotes.add("열정을 잃지 않고 실패에서 실패로 걸어가는 것이 성공이다.");
        quotes.add("기회는 일어나는 것이 아니라 만들어내는 것이다.");
        quotes.add("나는 실패한 게 아니다. 나는 잘 되지 않는 방법 1만 가지를 발견한 것이다.");
        quotes.add("성공적인 삶의 비밀은 무엇을 하는 게 자신의 운명인지 찾아낸 다음 그걸 하는 것이다.");
        quotes.add("지옥을 겪고 있다면 계속 겪어 나가라.");
        quotes.add("괴로운 시련처럼 보이는 것이 뜻밖의 좋은 일일 때가 많다.");
        quotes.add("잘못된 것들을 쫓아다니는 것을 그만두면 옳은 일들이 당신을 따라잡을 기회가 생긴다.");
        quotes.add("매일 당신을 두렵게 만드는 일을 하나씩 하라.");
        quotes.add("놀라운 일을 하려고 노력조차 하지 않을 거면 살아 있어서 뭐하나.");
        quotes.add("인생이란 자신을 찾는 것이 아니라 자신을 만드는 것이다.");
        quotes.add("지식이란 당신이 뭘 할 수 있는지 아는 것이다. 지혜란 하지 않아야 할 때를 아는 것이다.");
        quotes.add("당신의 문제가 문제가 아니라 당신의 반응이 문제다");
        quotes.add("당신이 세상을 바꿀 수 없다고 말하는 사람에는 두 종류가 있다. 시도하기를 두려워하는 사람들, 당신이 성공할까 봐 두려워하는 사람들.");
        quotes.add("나는 내가 더 노력할수록 운이 더 좋아진다는 걸 발견했다.");
        quotes.add("모든 성취의 시작점은 갈망이다.");
        quotes.add("성공은 매일 반복한 작은 노력들의 합이다.");
        quotes.add("사람들은 동기 부여는 오래가지 않는다고 말한다. 목욕도 마찬가지다. 그래서 매일 하라고하는 것이다.");
        quotes.add("성공으로 가는 길과 실패로 가는 길은 거의 똑같다.");
        quotes.add("실패에서부터 성공을 만들어 내라. 좌절과 실패는 성공으로 가는 가장 확실한 디딤돌이다.");
        quotes.add("당신의 인생을 스스로 설계하지 않으면 다른 사람의 계획에 빠져들 가능성이 크다. 남들이 당신을 위해 계획해 놓은 것? 많지 않다.");
        quotes.add("패배의 공포가 승리의 짜릿함보다 커지게 하지 마라.");
        quotes.add("성공이란 절대 실수를 하지 않는 게 아니라 같은 실수를 두 번 하지 않는 것에 있다.");
        quotes.add("진짜 어려움은 극복할 수 있다. 정복할 수 없는 것은 상상 속의 어려움들뿐이다.");
        quotes.add("성취의 크기는 목표를 이루기 위해 당신이 극복해야 했던 장애물의 크기로 잰다.");
        quotes.add("실패는 성공을 맛내는 양념이다.");
        quotes.add("낭비한 시간에 대한 후회는 더 큰 시간 낭비이다.");
        quotes.add("어제로 돌아갈 수 없다. 왜냐하면 나는 어제와는 다른 사람이 되었기 때문이다.");
        quotes.add("해야할 일은 과감히 하라. 결심한 일은 반드시 실행하라.");
        quotes.add("최대한의 삶을 살고, 최대한 긍정적인 것에 집중하자.");
        quotes.add("최선을 다하고 있다고 말해봤자 소용없다. 필요한 일을 함에 있어서는 반드시 성공해야 한다.");
        quotes.add("동기 부여가 당신을 시작하게 한다. 습관이 당신을 계속 움직이게 한다.");


        // 알림 해제 액션을 처리하는 코드 추가
        handleNotificationAction(getIntent());

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1) {
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "알람이 설정되지 않았습니다.", Snackbar.LENGTH_SHORT).show();
                    return; // 라디오 버튼이 선택되지 않은 경우 함수 종료
                }

                RadioButton radioButton = findViewById(selectedId);
                int interval = getIntervalFromRadioButtonId(selectedId);
                startAlarm(interval);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAlarmSet) {
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "알람이 설정되지 않았습니다.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                cancelAlarm();
            }
        });
    }
    private void handleNotificationAction(Intent intent) {
        if (intent != null && intent.getAction() != null) {
            if (intent.getAction().equals("CANCEL_ALARM_ACTION")) {
                // 알림 해제를 위한 PendingIntent 초기화
                Intent newIntent = new Intent(this, MyAlarmActivity.class);
                newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                cancelAlarm();
            }
        }
    }
    private int getIntervalFromRadioButtonId(int radioButtonId) {
        if (radioButtonId == R.id.interval2min) {
            return 2;
        } else if (radioButtonId == R.id.interval5min) {
            return 5;
        } else if (radioButtonId == R.id.interval15min) {
            return 15;
        } else if (radioButtonId == R.id.interval30min) {
            return 30;
        } else if (radioButtonId == R.id.interval1hour) {
            return 60;
        } else if (radioButtonId == R.id.interval2hours) {
            return 120;
        } else if (radioButtonId == R.id.interval12hours) {
            return 720;
        } else if (radioButtonId == R.id.interval24hours) {
            return 1440;
        } else {
            return 0;
        }
    }

    private void startAlarm(int interval) {
        Intent intent = new Intent(this, MyAlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        // 타이머 초기화
        handler = new Handler(Looper.getMainLooper());
        startTime = SystemClock.elapsedRealtime();

        // 일정 시간마다 알림 내용 업데이트
        updateNotificationRunnable = new Runnable() {
            @Override
            public void run() {
                // 랜덤한 인용구 선택
                Random random = new Random();
                int quoteIndex = random.nextInt(quotes.size());
                String quote = quotes.get(quoteIndex);

                String contentText = quote;
                updateNotificationContent(contentText);

                // 예약하지 않고 현재 상태 유지
                handler.postDelayed(this, interval * 60 * 1000);

            }
        };

        // 상단바에 알림 표시
        showNotification(interval);

        // 첫 번째 업데이트 예약
        handler.postDelayed(updateNotificationRunnable, interval * 60 * 1000);

        isAlarmSet = true;
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);

        View view = findViewById(android.R.id.content);
        Snackbar.make(view, interval + "분마다 알람이 설정되었습니다.", Snackbar.LENGTH_SHORT).show();
    }

    private void cancelAlarm() {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();

            // 상단바 알림 제거
            notificationManager.cancel(1);
            // Handler 제거
            if (handler != null) {
                handler.removeCallbacks(updateNotificationRunnable);
                handler = null;
            }
            isAlarmSet = false;
            startButton.setEnabled(true);
            cancelButton.setEnabled(false);

            notificationManager.cancelAll();
        View view = findViewById(android.R.id.content);
        Snackbar.make(view, "알람이 해제되었습니다.", Snackbar.LENGTH_SHORT).show();


    }

    private void showNotification(int interval) {
        startTime = getIntent().getLongExtra("startTime", 0);
        long currentTime = System.currentTimeMillis();
        // showNotification 메서드 내부 수정
        long elapsedTime = currentTime - startTime;
        long elapsedDays = elapsedTime / (24 * 60 * 60 * 1000); // Calculate elapsed days
        // Randomly select a quote
        Random random = new Random();
        int quoteIndex = random.nextInt(quotes.size());
        String quote = quotes.get(quoteIndex);

        Intent intent = new Intent(this, MyAlarmActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("위대한 도전을 하신지 " + elapsedDays + "일 되었습니다.\n" + interval + "분마다 금연하실 수 있게 도와드려요!")
                .setSmallIcon(R.drawable.no_smoking)
                .setContentIntent(pendingIntent)
                .setChannelId("channel_id");

        Notification notification = notificationBuilder.build();

        notificationManager.notify(1, notification);
    }
    private void updateNotificationContent(String contentText) {
        // 내용 업데이트
        notificationBuilder.setContentText(contentText);

        // 알림 업데이트
        notificationManager.notify(1, notificationBuilder.build());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNotificationAction(intent);
    }
}