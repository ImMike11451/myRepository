package com.example.aircraftinquiry;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class TicketInquiry extends AppCompatActivity implements View.OnClickListener {

    EditText from,to,data;
    Button search,select;
    SharedPreferences shar;
    String fromtext,totext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_inquiry);

        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        data = findViewById(R.id.data);
        search = findViewById(R.id.search);
        select = findViewById(R.id.select);

        shar = getSharedPreferences("FTtext", Context.MODE_PRIVATE);

        loadText();

        search.setOnClickListener(this);
        select.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select:
                showDataPic();
                Toast.makeText(TicketInquiry.this, "日期选择器", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                fromtext = from.getText().toString();
                totext = to.getText().toString();
                saveText(fromtext,totext);
                if (fromtext.trim().isEmpty() || totext.trim().isEmpty() || data.getText().toString().trim().isEmpty()){
                    Toast.makeText(TicketInquiry.this, "请检查出发地，到达地，日期是否填写完整", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(TicketInquiry.this, "搜索成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TicketInquiry.this,TicketList.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("data",data.getText().toString().trim());
                    bundle.putString("from",fromtext.trim());
                    bundle.putString("to",totext.trim());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }


    private void showDataPic() {

        // 获取当前日期
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 创建并显示 DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                TicketInquiry.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 用户选择的日期
                        String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
                        // 将日期显示在输入框中
                        data.setText(selectedDate);
                    }
                },
                year, month, day
        );
        datePickerDialog.show();

    }

    private void loadText() {

        boolean isSaved = shar.getBoolean("isSaved",false);
        if (isSaved){
            from.setText(shar.getString("fromtext",""));
            to.setText(shar.getString("totext",""));
        }

    }

    private void saveText(String from,String to) {

        SharedPreferences.Editor editor = shar.edit();
        editor.putString("fromtext",from);
        editor.putString("totext",to);
        editor.putBoolean("isSaved",true);
        editor.apply();

    }

}