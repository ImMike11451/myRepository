package com.example.aircraftinquiry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText user,pass;
    Button login,register;
    CheckBox savepass;
    SharedPreferences shar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        savepass = findViewById(R.id.savepass);

        shar = getSharedPreferences("Loginf", Context.MODE_PRIVATE);
        loadpass();

        register.setOnClickListener(this);
        login.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.login:
                String username = user.getText().toString();
                String password = pass.getText().toString();
                if (username.equals("test01") && password.equals("123")){
                    Toast.makeText(MainActivity.this, username + " ,登录成功", Toast.LENGTH_SHORT).show();
                    if (savepass.isChecked()){
                        savePassword(username,password);
                    } else {
                        clearPassword();
                    }
                    Intent intent = new Intent(MainActivity.this,TicketInquiry.class);
                    startActivity(intent);
                }else {
                    pass.setText("");
                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register:
                Intent intent = new Intent(MainActivity.this,Register.class);
                startActivity(intent);
                finish();
                break;
        }

    }

    private void clearPassword() {

        SharedPreferences.Editor editor = shar.edit();
        editor.clear();
        editor.apply();

    }

    private void savePassword(String username, String password) {

        SharedPreferences.Editor editor = shar.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("isSaved", true);
        editor.apply();

    }

    private void loadpass() {

        boolean isSaved = shar.getBoolean("isSaved", false);
        if (isSaved) {
            String username = shar.getString("username", "");
            String password = shar.getString("password", "");
            user.setText(username);
            pass.setText(password);
            savepass.setChecked(true);

        }
    }
}