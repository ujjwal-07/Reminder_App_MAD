package com.example.uvv_reminder_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.Year;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView tvOnceTime, tvonceDate, tvRepeatingTime;
    private ImageButton ibOnceTime, ibOnceDate, ibRepeatingTime;
    private EditText etOnceMessage, etRepeatingMessage;
    private Button btnsetOnceAlarm, btnSetRepeatingAlarm, btnCancelRepeatingAlarm;

    private AlarmReceive alarmReceive;
    private int mYear,mMonth,mDay,mhour,mMinute;
    private int mHourRepeat, mMinuteRepaet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOnceTime = findViewById(R.id.tv_once_time);
        tvonceDate = findViewById(R.id.tv_once_date);
        tvRepeatingTime = findViewById(R.id.tv_repeating_time);
        ibOnceTime = findViewById(R.id.ib_once_time);
        ibOnceDate = findViewById(R.id.ib_once_date);
        ibRepeatingTime = findViewById(R.id.ib_repeating_time);
        etOnceMessage = findViewById(R.id.et_once_message);
        etRepeatingMessage = findViewById(R.id.et_repeating_message);
        btnsetOnceAlarm = findViewById(R.id.btn_set_once_alarm);
        btnSetRepeatingAlarm = findViewById(R.id.btn_set_repeating_alarm);
        btnCancelRepeatingAlarm = findViewById(R.id.btn_cancel_repeating_alarm);

        alarmReceive =  new AlarmReceive();

        Calendar calendar = Calendar.getInstance();

        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        mhour = calendar.get(Calendar.HOUR_OF_DAY);
        mMinute = calendar.get(Calendar.MINUTE);

        mHourRepeat = mhour;
        mMinuteRepaet = mMinute;

        ibOnceDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        tvonceDate.setText(String.format("%04d-%02d-%02d",year,month+1,dayOfMonth));
                        mYear = year;
                        mMinute = month;
                        mDay = dayOfMonth;
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
        ibOnceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        tvOnceTime.setText(String.format("%02d:%02d",hourOfDay, minute));
                        mhour = hourOfDay;
                        mMinute = minute;
                    }
                }, mhour, mMinute,true);
                timePickerDialog.show();
            }
        });
        ibRepeatingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        tvRepeatingTime.setText(String.format("%02d:%02d",hourOfDay, minute));
                        mHourRepeat = hourOfDay;
                        mMinuteRepaet = minute;
                    }
                }, mHourRepeat, mMinuteRepaet,true);
                timePickerDialog.show();
            }
        });
        btnsetOnceAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvonceDate.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this,"Date is empty",Toast.LENGTH_SHORT).show();

                }else if(tvOnceTime.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this,"Time is empty",Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(etOnceMessage.getText().toString())){
                   etOnceMessage.setError("Message can't be empty!");

                }else{
                    alarmReceive.setOneTimeAlarm(MainActivity.this, AlarmReceive.TYPE_ONE_TIME,
                            tvonceDate.getText().toString(),tvOnceTime.getText().toString(),
                            etOnceMessage.getText().toString());
                }
            }
        });
        btnSetRepeatingAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvRepeatingTime.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this,"Time is empty",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(etRepeatingMessage.getText().toString())){
                    etRepeatingMessage.setError("Message can't be empty!");
                }else{
                    alarmReceive.setRepeatingAlarm(MainActivity.this,AlarmReceive.TYPE_REPEATING,
                            tvRepeatingTime.getText().toString(),
                            etRepeatingMessage.getText().toString());
                }
            }
        });

        btnCancelRepeatingAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alarmReceive.isAlarmSet(MainActivity.this, AlarmReceive.TYPE_REPEATING)) {
                    tvRepeatingTime.setText("");
                    etRepeatingMessage.setText("");
                    alarmReceive.cancelAlarm(MainActivity.this, AlarmReceive.TYPE_REPEATING);
                }
            }
        });
        }
}