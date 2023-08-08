package com.example.btm_menu_test;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class EggSub extends AppCompatActivity implements View.OnTouchListener {
    private static final String PREF_NAME = "GamePrefs";
    private static final String TOUCH_COUNT_KEY = "TouchCount";
    private ImageView eggImageView;
    private TextView touchCountTextView;
    private int touchCount;
    private boolean touching = false;
    private MediaPlayer crackSoundPlayer;
    private MediaPlayer crackSoundPlayer1;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.egg_main);
        eggImageView = findViewById(R.id.eggImageView);
        touchCountTextView = findViewById(R.id.touchCountTextView);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        // 이전에 저장된 touchCount 값을 복원
        touchCount = sharedPreferences.getInt(TOUCH_COUNT_KEY, 1000);
        touchCountTextView.setText(String.valueOf(touchCount));
        eggImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator shakeAnimation = ObjectAnimator.ofFloat(eggImageView, "translationX", -20f, 20f);
                shakeAnimation.setDuration(100);
                shakeAnimation.setInterpolator(new AccelerateInterpolator());
                shakeAnimation.setRepeatCount(5);
                shakeAnimation.setRepeatMode(ObjectAnimator.REVERSE);
                shakeAnimation.start();
                --touchCount;
                touchCountTextView.setText(String.valueOf(touchCount));
                if (touchCount == 0) {
                    eggImageView.setImageResource(R.drawable.badegg);
                    crackSoundPlayer1.start();
                    eggImageView.setOnTouchListener(null); // 터치 이벤트 비활성화
                    touchCountTextView.setText("계란을 터치하시면\n" + "다시 시작됩니다.");
                    crackSoundPlayer1.start();
                } else if (touchCount <= 0) {
                    touchCount = 1000;
                    touchCountTextView.setText(String.valueOf(touchCount));
                }

                // 변경된 touchCount 값을 SharedPreferences에 저장
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(TOUCH_COUNT_KEY, touchCount);
                editor.apply();
                updateEggImage();
            }
        });
        crackSoundPlayer = MediaPlayer.create(this, R.raw.crack_sound);
        crackSoundPlayer1 = MediaPlayer.create(this, R.raw.crack_finish);
        updateEggImage();
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (!touching) {
                touching = true;
                touchCountTextView.setText(String.valueOf(touchCount));
                touchCount--;
                // 효과음 재생
                crackSoundPlayer.start();
                // 화면 흔들림 효과
                ObjectAnimator shakeAnimation = ObjectAnimator.ofFloat(eggImageView, "translationX", -20f, 20f);
                shakeAnimation.setDuration(2000);
                shakeAnimation.setInterpolator(new AccelerateInterpolator());
                shakeAnimation.setRepeatCount(2);
                shakeAnimation.setRepeatMode(ObjectAnimator.REVERSE);
                shakeAnimation.start();
                if (touchCount == 0) {
                    eggImageView.setImageResource(R.drawable.badegg);
                    crackSoundPlayer1.start();
                    eggImageView.setOnTouchListener(null); // 터치 이벤트 비활성화
                    touchCountTextView.setText("계란을 터치하시면\n" + "다시 시작됩니다.");
                    crackSoundPlayer1.start();
                }
                // 변경된 touchCount 값을 SharedPreferences에 저장
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(TOUCH_COUNT_KEY, touchCount);
                editor.apply();
                updateEggImage();
            }
        } else if (action == MotionEvent.ACTION_UP) {
            touching = false;
        }
        return true;
    }
    private void updateEggImage() {
        if (touchCount == 1000) {
            eggImageView.setImageResource(R.drawable.goodegg);
            crackSoundPlayer.start();
        } else if (touchCount >= 700) {
            eggImageView.setImageResource(R.drawable.normalegg1);
            crackSoundPlayer.start();
        } else if (touchCount >= 400) {
            eggImageView.setImageResource(R.drawable.normalegg2);
            crackSoundPlayer.start();
        } else if (touchCount >= 1) {
            crackSoundPlayer.start();
        }
    }
}