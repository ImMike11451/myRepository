package com.example.aircraftinquiry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText user,pass;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register = findViewById(R.id.register);
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pass.length() >= 8 && pass.length() <= 16){
                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}