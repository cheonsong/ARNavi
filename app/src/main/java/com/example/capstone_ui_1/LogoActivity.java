package com.example.capstone_ui_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoActivity extends AppCompatActivity {

    Button informationBtn, destinationBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        informationBtn = findViewById(R.id.informationButton);
        destinationBtn = findViewById(R.id.destinationButton);
        destinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DestinationActivity.class);
                startActivity(intent);
            }
        });

    }
}