package com.example.aircraftinquiry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.aircraftinquiry.AllBean.Row;
import com.example.aircraftinquiry.AllBean.Ticketbean;
import com.example.aircraftinquiry.BasAdapterRepeat.TicketsBas;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TicketList extends AppCompatActivity implements View.OnClickListener {

    String TAG;
    TextView datatime;
    ListView listview;
    Context context = this;
    TicketsBas bas;
    List<Row> rows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        datatime = findViewById(R.id.Datatime);
        listview = findViewById(R.id.ticketlistview);
        Button timetop = findViewById(R.id.timetop);
        Button timebuttom = findViewById(R.id.timebuttom);
        Button moneytop = findViewById(R.id.moneytop);
        Button moneybuttom = findViewById(R.id.moneybuttom);

        timetop.setOnClickListener(this);
        timebuttom.setOnClickListener(this);
        moneytop.setOnClickListener(this);
        moneybuttom.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            String from = bundle.getString("from");
            String to = bundle.getString("to");
            String data = bundle.getString("data");

            getSupportActionBar().setTitle(from + " -> " + to);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            datatime.setText("日期: " + data);

            Request request = new Request
                    .Builder()
                    .get()
                    .url("http://10.35.249.105:10011/prod-api/api/bus/order/list?")
                    .addHeader("Authorization","eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjMyOTQzZWRlLWQ1MjItNGMxZC1hOTQ4LWMwMDgxZjZkY2RmNyJ9.AuAmR1ZA-mpj6vGemx_zi6XKQKYzAszMZp8qQwnengbVwIAFnsT8vUIiryeqVp9Q9jr6qTP0gHvLH0uCy77-WQ")
                    .build();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("TAG", "onFailure: " + e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    Gson gson = new Gson();
                    Ticketbean ticket = gson.fromJson(response.body().string(),Ticketbean.class);
                    rows = ticket.getRows();
                    bas = new TicketsBas(rows,TicketList.this);
                    runOnUiThread(() -> {
                        listview.setAdapter(bas);
                    });

                    //Log.e("TAG", "onResponse: " + response.body().string());
                }
            });

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.timetop:
                sortByTime(true);
                break;
            case R.id.timebuttom:
                sortByTime(false);
                break;
            case R.id.moneytop:
                sortByMoney(true);
                break;
            case R.id.moneybuttom:
                sortByMoney(false);
                break;
        }

    }

    private void sortByMoney(boolean b) {

        Collections.sort(rows, new Comparator<Row>() {
            @Override
            public int compare(Row row1, Row row2) {
                return b ? Double.compare(row1.getPrice(),row2.getPrice())
                        : Double.compare(row2.getPrice(),row1.getPrice());
            }
        });
        bas.notifyDataSetChanged();

    }

    private void sortByTime(boolean b) {

        Collections.sort(rows, new Comparator<Row>() {
            @Override
            public int compare(Row row1, Row row2) {
                return b ? row1.getCreateTime().compareTo(row2.getCreateTime())
                        : row2.getCreateTime().compareTo(row1.getCreateTime());
            }
        });
        bas.notifyDataSetChanged();
    }


}