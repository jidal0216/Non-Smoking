package com.example.btm_menu_test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortedMeasurementsActivity extends AppCompatActivity {
    // SharedPreferences 객체 가져오기
    SharedPreferences sharedPreferences;
    List<String> combinedMeasurements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_measurements);

        // SharedPreferences 객체 가져오기
        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);

        // 측정된 값들을 받아옴
        List<Long> measurements = (List<Long>) getIntent().getSerializableExtra("measurements");

        // 기존에 저장된 정렬된 결과 가져오기
        String sortedMeasurements = sharedPreferences.getString("sortedMeasurements", "");

        combinedMeasurements = new ArrayList<>();

        // 기존 결과를 리스트에 추가
        if (!sortedMeasurements.isEmpty()) {
            String[] previousMeasurements = sortedMeasurements.split("\n");
            Collections.addAll(combinedMeasurements, previousMeasurements);
        }

        // 측정된 값들을 추가
        for (int i = 0; i < measurements.size(); i++) {
            String measurement = measurements.get(i) + "초";
            if (!combinedMeasurements.contains(measurement)) {
                combinedMeasurements.add(measurement);
            }
        }

        // 정렬 버튼 클릭 이벤트 처리
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button sortButton = findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortMeasurements();
            }
        });

        // 결과를 문자열로 변환하여 저장
        saveMeasurements();
    }

    private void sortMeasurements() {
        // 정렬을 위해 내림차순 Comparator 사용
        Collections.sort(combinedMeasurements, new Comparator<String>() {
            @Override
            public int compare(String measurement1, String measurement2) {
                long time1 = Long.parseLong(measurement1.split("초")[0]);
                long time2 = Long.parseLong(measurement2.split("초")[0]);
                return Long.compare(time2, time1);
            }
        });

        // 정렬된 결과를 문자열로 변환하여 저장
        saveMeasurements();
    }

    private void saveMeasurements() {
        // 새로운 결과를 문자열로 변환
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < combinedMeasurements.size(); i++) {
            stringBuilder.append(combinedMeasurements.get(i));
            if (i < combinedMeasurements.size() - 1) {
                stringBuilder.append("\n");
            }
        }

        // 새로운 결과 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sortedMeasurements", stringBuilder.toString());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 저장된 데이터 사용
        String updatedSortedMeasurements = sharedPreferences.getString("sortedMeasurements", "");
        if (!updatedSortedMeasurements.isEmpty()) {
            // 저장된 데이터가 있을 경우 처리
            TextView sortedMeasurementsTextView = findViewById(R.id.sortedMeasurementsTextView);
            sortedMeasurementsTextView.setText(updatedSortedMeasurements);
        } else {
            // 저장된 데이터가 없을 경우 처리
            TextView sortedMeasurementsTextView = findViewById(R.id.sortedMeasurementsTextView);
            sortedMeasurementsTextView.setText("현재 기록이 없습니다. 게임을 시작해 주세요!");
        }
    }
}