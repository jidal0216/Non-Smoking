package com.example.btm_menu_test;

import static java.util.concurrent.TimeUnit.SECONDS;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tomer.fadingtextview.FadingTextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //EffectActivity 관련
    private LinearLayout effectContainer;
    // 여기까지 EffectActivity 과련

    // 리설트 액티비티 관련
    private TextView savingsTextView;
    private TextView lifespanTextView;
    private TextView timeSavedTextView;
    // 여기까지 리설트액티비티 관련

    // HomeActivity 관련
    private TextView textViewSavings;
    private TextView textViewLifespanIncrease;
    private TextView textViewTimeGained;
    private TextView textViewDaysSmokeFree;
    private TextView textViewTotalCigarettesSmoked;
    private TextView textViewLifespanReduction;
    private TextView textViewTimeLoss;
    private TextView textViewSmokingCessationPeriod;
    private TextView textViewSavingMoney;
    private DatabaseHelper databaseHelper;
    private Handler handler;
    private Runnable timerRunnable;
    private long startTime;
    private static final String TIMER_PREFS = "timer_prefs";
    private static final String START_TIME_KEY = "start_time_key";

    // 여기까지 HomeActivity 관련
    //Shake 관련
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private boolean isShaking = false;


    private ImageButton imageButton2;
    private ImageButton btn_test;

    //여기까지 Shake 관련

    private BottomNavigationView bottomNavigationView;   //    하단 메뉴바 관련

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // FaddingTextView
        FadingTextView FTV = (FadingTextView) findViewById(R.id.fadingTextView);
        FTV.setTimeout(7, SECONDS);


              // "테스트버튼" 데이터 전달버튼
       /* btn_test = findViewById(R.id.btn_test);

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent btn_test = new Intent(MainActivity.this, test.class);
                btn_test.putExtra("averageSmokingAmount", averageSmokingAmount);
                btn_test.putExtra("cigaretteCount", cigaretteCount);
                btn_test.putExtra("averageSmokingTime", averageSmokingTime);
                btn_test.putExtra("cigarettePrice", cigarettePrice);
                startActivity(btn_test);
                }
            });*/

        btn_test = findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btn_test = new Intent(MainActivity.this, test.class);
                startActivity(btn_test);
            }
        });

        imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long startTime1 = System.currentTimeMillis();
                Intent intent = new Intent(MainActivity.this, MyAlarmActivity.class);
                intent.putExtra("startTime", startTime1);
                startActivity(intent);
            }
        });

        // 여기부터 Shake 기능
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        // 여기까지 Shake 기능

        //    하단 메뉴바 관련
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.menu_item1) {
                    startActivity(new Intent(MainActivity.this, CalenderActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_item2) {
                    startActivity(new Intent(MainActivity.this, TipActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_item3) {
                    startActivity(new Intent(MainActivity.this, GameActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.menu_item4) {
                    startActivity(new Intent(MainActivity.this, MissionActivity.class));
                    return true;
                }

                return false;
            }
        });
        //    여기까지 하단 메뉴바 관련

        // HomeActivity 관련
        textViewSavings = findViewById(R.id.textViewSavings);
        textViewLifespanIncrease = findViewById(R.id.textViewLifespanIncrease);
        textViewTimeGained = findViewById(R.id.textViewTimeGained);
        textViewDaysSmokeFree = findViewById(R.id.textViewDaysSmokeFree);
        textViewTotalCigarettesSmoked = findViewById(R.id.textViewTotalCigarettesSmoked);
        textViewLifespanReduction = findViewById(R.id.textViewLifespanReduction);
        textViewTimeLoss = findViewById(R.id.textViewTimeLoss);
        textViewSmokingCessationPeriod = findViewById(R.id.textViewsmokingcessationperiod);
        textViewSavingMoney = findViewById(R.id.textViewSavingMoney);
        databaseHelper = new DatabaseHelper(this);
        handler = new Handler();
        // SharedPreferences에서 시작 시간을 복원합니다.
        SharedPreferences prefs = getSharedPreferences(TIMER_PREFS, MODE_PRIVATE);
        startTime = prefs.getLong(START_TIME_KEY, System.currentTimeMillis());
        calculateStats();
        startTimer();

        // 여기까지 HomeActivity 관련
    }

    // Shake 기능
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        if ((Math.abs(x) > 18 || Math.abs(y) > 18 || Math.abs(z) > 18) && !isShaking) {
            isShaking = true;
            RandomMotivation();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        handler.postDelayed(timerRunnable, 1000); // 임시 ( 데이터값 실시간 출력 )
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
        handler.removeCallbacks(timerRunnable); // 임시 ( 데이터값 실시간 출력 )
    }

    private void RandomMotivation() {
        String[] motivation = {
                "큰 목표를 이루고 싶으면 허락을 구하지 마라.",
                "일반적인 것을 잃을 위험을 감수하지 않으면 평범한 것에 만족해야 한다.",
                "추구할 수 있는 용기가 있다면 우리의 모든 꿈은 이뤄질 수 있다.",
                "기다리는 사람에게 좋은 일이 생기지만, 찾아나서는 사람에게는 더 좋은 일이 생긴다.",
                "열정을 잃지 않고 실패에서 실패로 걸어가는 것이 성공이다.",
                "기회는 일어나는 것이 아니라 만들어내는 것이다.",
                "나는 실패한 게 아니다. 나는 잘 되지 않는 방법 1만 가지를 발견한 것이다.",
                "성공적인 삶의 비밀은 무엇을 하는 게 자신의 운명인지 찾아낸 다음 그걸 하는 것이다.",
                "지옥을 겪고 있다면 계속 겪어 나가라.",
                "괴로운 시련처럼 보이는 것이 뜻밖의 좋은 일일 때가 많다.",
                "잘못된 것들을 쫓아다니는 것을 그만두면 옳은 일들이 당신을 따라잡을 기회가 생긴다.",
                "매일 당신을 두렵게 만드는 일을 하나씩 하라.",
                "놀라운 일을 하려고 노력조차 하지 않을 거면 살아 있어서 뭐하나.",
                "인생이란 자신을 찾는 것이 아니라 자신을 만드는 것이다.",
                "지식이란 당신이 뭘 할 수 있는지 아는 것이다. 지혜란 하지 않아야 할 때를 아는 것이다.",
                "당신의 문제가 문제가 아니라 당신의 반응이 문제다",
                "당신이 세상을 바꿀 수 없다고 말하는 사람에는 두 종류가 있다. 시도하기를 두려워하는 사람들, 당신이 성공할까 봐 두려워하는 사람들.",
                "나는 내가 더 노력할수록 운이 더 좋아진다는 걸 발견했다.",
                "모든 성취의 시작점은 갈망이다.",
                "성공은 매일 반복한 작은 노력들의 합이다.",
                "사람들은 동기 부여는 오래가지 않는다고 말한다. 목욕도 마찬가지다. 그래서 매일 하라고하는 것이다.",
                "성공으로 가는 길과 실패로 가는 길은 거의 똑같다.",
                "실패에서부터 성공을 만들어 내라. 좌절과 실패는 성공으로 가는 가장 확실한 디딤돌이다.",
                "당신의 인생을 스스로 설계하지 않으면 다른 사람의 계획에 빠져들 가능성이 크다. 남들이 당신을 위해 계획해 놓은 것? 많지 않다.",
                "패배의 공포가 승리의 짜릿함보다 커지게 하지 마라.",
                "성공이란 절대 실수를 하지 않는 게 아니라 같은 실수를 두 번 하지 않는 것에 있다.",
                "진짜 어려움은 극복할 수 있다. 정복할 수 없는 것은 상상 속의 어려움들뿐이다.",
                "성취의 크기는 목표를 이루기 위해 당신이 극복해야 했던 장애물의 크기로 잰다.",
                "실패는 성공을 맛내는 양념이다.",
                "낭비한 시간에 대한 후회는 더 큰 시간 낭비이다.",
                "어제로 돌아갈 수 없다. 왜냐하면 나는 어제와는 다른 사람이 되었기 때문이다.",
                "해야할 일은 과감히 하라. 결심한 일은 반드시 실행하라.",
                "최대한의 삶을 살고, 최대한 긍정적인 것에 집중하자.",
                "최선을 다하고 있다고 말해봤자 소용없다. 필요한 일을 함에 있어서는 반드시 성공해야 한다.",
                "동기 부여가 당신을 시작하게 한다. 습관이 당신을 계속 움직이게 한다."
        };

        Random random = new Random();
        int index = random.nextInt(motivation.length);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("포기하지 마세요!");
        builder.setMessage(motivation[index]);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isShaking = false;
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // 여기까지 Shake 기능

    // 여기부터 HomeActivity 관련
    @Override
    protected void onStop() {
        super.onStop();

        // 앱이 종료될 때 시작 시간을 SharedPreferences에 저장합니다.
        SharedPreferences prefs = getSharedPreferences(TIMER_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(START_TIME_KEY, startTime);
        editor.apply();
    }

    private void calculateStats() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // 데이터베이스에서 흡연 정보 가져오기
        Cursor cursor = db.rawQuery("SELECT * FROM smoking ORDER BY id DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int averageSmokingAmount = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_AVERAGE_SMOKING_AMOUNT));
            @SuppressLint("Range") int cigaretteCount = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CIGARETTE_COUNT));
            @SuppressLint("Range") int cigarettePrice = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_CIGARETTE_PRICE));
            @SuppressLint("Range") String smokingStartDate = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_SMOKING_START_DATE));
            @SuppressLint("Range") int averageSmokingTime = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_AVERAGE_SMOKING_TIME));

      /*      // "테스트버튼" 데이터 전달버튼
            btn_test = findViewById(R.id.btn_test);

            btn_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent btn_test = new Intent(MainActivity.this, test.class);
                    btn_test.putExtra("averageSmokingAmount", averageSmokingAmount);
                    btn_test.putExtra("cigaretteCount", cigaretteCount);
                    btn_test.putExtra("averageSmokingTime", averageSmokingTime);
                    btn_test.putExtra("cigarettePrice", cigarettePrice);
                    startActivity(btn_test);
                }
            });
*/
            // 통계 계산하기
            int daysSmokeFree = calculateDaysSmokeFree(smokingStartDate);
            double savings = calculateSavings(cigaretteCount, cigarettePrice, averageSmokingAmount, daysSmokeFree);
            double lifespanIncrease = calculateLifespanIncrease(averageSmokingAmount);
            double timeGained = calculateTimeGained(averageSmokingAmount, averageSmokingTime);
            int totalCigarettesSmoked = calculateTotalCigarettesSmoked(daysSmokeFree, averageSmokingAmount);
            double lifespanReduction = calculateLifespanReduction(averageSmokingAmount, daysSmokeFree);
            long timeLoss = calculateTimeLoss(totalCigarettesSmoked,averageSmokingTime);
            long timeDifference = calculateTimeDifference();
            double savingMoney = calculateSavingMoney(cigarettePrice, cigaretteCount, averageSmokingAmount);



            // 값 포맷팅하기

            NumberFormat numberFormat = NumberFormat.getInstance();
            String formattedTimeDifference = formatTimeDifference(timeDifference);

            // TextView에 계산된 값 설정하기
            textViewSavings.setText(formatSavings(savings)) ;  //돈 쓴 금액
            textViewLifespanIncrease.setText(formatLifespanIncrease(lifespanIncrease) );  // 예상 수명 증가
            textViewTimeGained.setText(formatTimeGained(timeGained)); //흡연 중단으로 얻은 시간
            textViewDaysSmokeFree.setText(numberFormat.format(daysSmokeFree) + " 일"); //총 흡연 기간
            textViewTotalCigarettesSmoked.setText(numberFormat.format(totalCigarettesSmoked) + " 개비"); //총 흡연량
            textViewLifespanReduction.setText(formatLifespanReduction((long) lifespanReduction));  //흡연으로 인한 수명 감소
            textViewTimeLoss.setText(formatTimeLoss(timeLoss));  // 흡연으로 인한 시간 손실
            textViewSmokingCessationPeriod.setText(formattedTimeDifference); // 금연 기간
            textViewSavingMoney.setText(formatSavings(savingMoney)); // 절약 금액
        }

        cursor.close();
        db.close();
    }

    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                long timeDifference = currentTime - startTime;
                String formattedTimeDifference = formatTimeDifference(timeDifference);
                textViewSmokingCessationPeriod.setText(formattedTimeDifference);
                calculateStats();


                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(timerRunnable, 1000);
    } // 총 흡연 기간
    private int calculateDaysSmokeFree(String smokingStartDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date startDate = dateFormat.parse(smokingStartDate);
            Date currentDate = new Date();
            long timeDifference = currentDate.getTime() - startDate.getTime();
            int daysSmokeFree = (int) TimeUnit.MILLISECONDS.toDays(timeDifference);
            return daysSmokeFree > 0 ? daysSmokeFree : 1; // 최소 1일로 설정
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 돈 쓴 금액
    private double calculateSavings(int cigaretteCount, int cigarettePrice, int averageSmokingAmount,int daysSmokeFree) {
        double packPrice = (double) cigarettePrice / cigaretteCount;
        double dailySavings = packPrice * averageSmokingAmount ;
        double UsePrice = dailySavings * daysSmokeFree ;
        return UsePrice;
    }
    //돈 쓴 금액 단위 변경 + 값 포매팅
    private String formatSavings(double savings) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(savings) + " 원";
    }
    // 예상 수명 증가
    private double calculateLifespanIncrease(int averageSmokingAmount) {
        long OneDaySmoke = averageSmokingAmount ;
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - startTime;
        double timeInSeconds = (double) timeDifference / 1000  ;
        double lifespanIncrease = timeInSeconds * OneDaySmoke * 1 / 1440;  // in seconds

        // 수명 증가가 수명 감소보다 크면 수명 감소 값으로 변경
        double lifespanReduction = calculateLifespanReduction(averageSmokingAmount, (int) timeInSeconds);
        if (lifespanIncrease > lifespanReduction) {
            lifespanIncrease = lifespanReduction;
            System.out.println("흡연으로 인한 수명 손실이 거의 다 회복되었습니다.고생하셨습니다.");
        }

        return lifespanIncrease;
    }


    // 수명 증가 포맷팅
    private String formatLifespanIncrease(double lifespanIncrease) {
        long totalSeconds = (long) lifespanIncrease;
        long days = TimeUnit.SECONDS.toDays(totalSeconds);
        long hours = TimeUnit.SECONDS.toHours(totalSeconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60;
        long seconds = totalSeconds % 60;

        return String.format(Locale.getDefault(), "%d일 %d시간 %d분 %d초", days, hours, minutes, seconds);
    }

    // 금연으로 얻은 시간
    private double calculateTimeGained(int averageSmokingAmount, int averageSmokingTime) {
        long smokeDayTime = averageSmokingAmount * averageSmokingTime ;
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - startTime;
        double timeInSeconds = (double) timeDifference /1000 ;
        return smokeDayTime * timeInSeconds / 1440 ;  // in seconds
    }

    // 금연으로 얻은 시간 포맷팅
    private String formatTimeGained(double timeGained) {
        long totalSeconds = (long) timeGained;
        long days = TimeUnit.SECONDS.toDays(totalSeconds);
        long hours = TimeUnit.SECONDS.toHours(totalSeconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60;
        long seconds = totalSeconds % 60;

        return String.format(Locale.getDefault(), "%d일 %d시간 %d분 %d초", days, hours, minutes, seconds);
    }

    // 총 담배 개수
    private int calculateTotalCigarettesSmoked(int daysSmokeFree, int averageSmokingAmount) {
        return daysSmokeFree * averageSmokingAmount;
    }
    // 흡연으로 인한 수명 감소
    private double calculateLifespanReduction(int averageSmokingAmount, int daysSmokeFree) {
        return averageSmokingAmount * 11 * daysSmokeFree;
    }

    // 수명 감소 포맷팅
    private String formatLifespanReduction(double lifespanReduction) {
        long totalMinutes = (long) lifespanReduction;
        long days = TimeUnit.MINUTES.toDays(totalMinutes);
        long hours = TimeUnit.MINUTES.toHours(totalMinutes) % 24;
        long minutes = totalMinutes % 60;

        return String.format(Locale.getDefault(), "%d일 %d시간 %d분", days, hours, minutes);
    }

    // 시간 손실
    private long calculateTimeLoss(int totalCigarettesSmoked ,int averageSmokingTime) {
        return  averageSmokingTime * totalCigarettesSmoked ;
    }

    // 흡연으로 인한 시간 손실 포매팅
    private String formatTimeLoss(long timeLoss) {
        long totalMinutes = (long) timeLoss;
        long days = TimeUnit.MINUTES.toDays(timeLoss);
        long hours = TimeUnit.MINUTES.toHours(timeLoss) % 24;
        long minutes = totalMinutes % 60;
        return String.format(Locale.getDefault(), "%d일 %d시간 %d분", days, hours, minutes);
    }

    //금연 기간
    private long calculateTimeDifference() {
        long currentTime = System.currentTimeMillis();
        return currentTime - startTime;
    }
    //금연 기간 포매팅
    private String formatTimeDifference(long timeDifference) {
        long days = TimeUnit.MILLISECONDS.toDays(timeDifference);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifference) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference) % 60;

        return String.format(Locale.getDefault(), "%d일 %d시간 %d분 %d초", days, hours, minutes, seconds);
    }
    //절약금액
    private double calculateSavingMoney(int cigarettePrice, int cigaretteCount, int averageSmokingAmount) {
        double packPrice = (double) cigarettePrice / cigaretteCount;
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - startTime;
        double timeInSeconds = (double) timeDifference / 1000 ;

        // 시간이 흐를수록 절약 금액이 증가하도록 계산
        double savingMoney = packPrice * averageSmokingAmount / 86400 * timeInSeconds;

        return savingMoney;
    }

    // 흡연 정보 수정 화면으로 이동
    public void onButtonEditProfileClicked(View view) {
        databaseHelper.clearSavedData();

        Intent intent = new Intent(this, SmokeActivity.class);
        startActivity(intent);

    }

    public void onButtonQuitSmokingClicked(View view) {
        // 금연하기 버튼 동작 처리
    }
    //데이터 초기화 메서드
    private void clearSavedData() {


        // 시작 시간 재설정
        startTime = System.currentTimeMillis();

        // 예상 수명 증가 , 금연으로 얻은 시간, 금연 기간 초기화 후 텍스트 뷰 업데이트
        String formattedTimeDifference = formatTimeDifference(0);
        textViewSmokingCessationPeriod.setText(formattedTimeDifference);
        textViewLifespanIncrease.setText(formatLifespanIncrease(0.0));
        textViewTimeGained.setText(formatTimeGained(0.0));
    }
    // "금연 실패" 버튼을 클릭했을 때 호출되는 메서드
    public void onClearDataButtonClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("금연 초기화")
                .setMessage("금연 데이터를 초기화하시겠습니까?")
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // "예"를 선택한 경우
                        Toast.makeText(getApplicationContext(), "금연 데이터가 초기화 됩니다.", Toast.LENGTH_SHORT).show();
                        // 금연 데이터 초기화
                        clearSavedData();
                        Toast.makeText(getApplicationContext(), "이번에는 꼭 금연 하시길 바랍니다.", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("아니요", null)
                .show();



    }
    // 여기까지 HomeActivity 관련


}