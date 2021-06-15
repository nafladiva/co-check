package com.example.co_check;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class Result extends AppCompatActivity {

    TextView TVNama, result;
    Button buttonRestart;
    ImageView imageResult;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_result);

        TVNama = (TextView) findViewById(R.id.TVNama);
        result = (TextView) findViewById(R.id.result);

        imageResult = (ImageView) findViewById(R.id.imageResult);
        buttonRestart = (Button) findViewById(R.id.buttonRestart);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("Nama");
        String questionResult = intent.getStringExtra("resultValue");
        String image_uri = intent.getStringExtra("resultImage");

        TVNama.setText(nama + ", kamu harus ...");
        result.setText(questionResult);

        int imageResource = getResources().getIdentifier(image_uri, null, getPackageName());

        @SuppressLint("UseCompatLoadingForDrawables") Drawable res = getResources().getDrawable(imageResource);
        imageResult.setImageDrawable(res);

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });
    }
}