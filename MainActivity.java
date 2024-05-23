package smu.ai.greenmate;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    CheckBox c1, c2, c3, c4, c5, c6, c7;
    Button btn;
    private SharedPreferences share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        c1 = findViewById(R.id.mon);
        c2 = findViewById(R.id.tue);
        c3 = findViewById(R.id.wedn);
        c4 = findViewById(R.id.thur);
        c5 = findViewById(R.id.fri);
        c6 = findViewById(R.id.sat);
        c7 = findViewById(R.id.sun);
        btn = findViewById(R.id.add_btn);

        share = getSharedPreferences("AlarmPreferences", MODE_PRIVATE);

        loadDay();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelectedDays();
                checkAndShowDialog();
            }
        });

        checkAndShowDialog();
    }


    private void loadDay() {
        Set<String> savedDays = share.getStringSet("selectedDays", new HashSet<String>());
        if (savedDays.contains("월요일")) {
            c1.setChecked(true);
        }
        if (savedDays.contains("화요일")) {
            c2.setChecked(true);
        }
        if (savedDays.contains("수요일")) {
            c3.setChecked(true);
        }
        if (savedDays.contains("목요일")) {
            c4.setChecked(true);
        }
        if (savedDays.contains("금요일")) {
            c5.setChecked(true);
        }
        if (savedDays.contains("토요일")) {
            c6.setChecked(true);
        }
        if (savedDays.contains("일요일")) {
            c7.setChecked(true);
        }

    }

    private void saveSelectedDays() {
        Set<String> selectedDays = new HashSet<>();
        if (c1.isChecked()) {
            selectedDays.add("월요일");
        }
        if (c2.isChecked()) {
            selectedDays.add("화요일");
        }
        if (c3.isChecked()) {
            selectedDays.add("수요일");
        }
        if (c4.isChecked()) {
            selectedDays.add("목요일");
        }
        if (c5.isChecked()) {
            selectedDays.add("금요일");
        }
        if (c6.isChecked()) {
            selectedDays.add("토요일");
        }
        if (c7.isChecked()) {
            selectedDays.add("일요일");
        }

        SharedPreferences.Editor editor = share.edit();
        editor.putStringSet("selectedDays", selectedDays);
        editor.apply();
    }

    private void checkAndShowDialog() {
        Set<String> selectedDays = share.getStringSet("selectedDays", new HashSet<String>());

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String today = "";

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                today = "월요일";
                break;
            case Calendar.TUESDAY:
                today = "화요일";
                break;
            case Calendar.WEDNESDAY:
                today = "수요일";
                break;
            case Calendar.THURSDAY:
                today = "목요일";
                break;
            case Calendar.FRIDAY:
                today = "금요일";
                break;
            case Calendar.SATURDAY:
                today = "토요일";
                break;
            case Calendar.SUNDAY:
                today = "일요일";
                break;
        }

        if (selectedDays.contains(today)) {
            showDialog(today);
        }
    }

    private void showDialog(String day) {
        String message = "오늘은 재활용 하는 " + day + " 입니다!";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("재활용 하는 날")
                .setMessage(message)
                .setPositiveButton("확인", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
