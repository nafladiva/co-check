package com.example.co_check;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

public class DataDiri extends AppCompatActivity {

    EditText ETNama, ETUmur, ETDaerah;
    Button buttonMulai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_data_diri);

        ETNama = (EditText) findViewById(R.id.ETNama);
        ETUmur = (EditText) findViewById(R.id.ETUmur);
        ETDaerah = (EditText) findViewById(R.id.ETDaerah);

        buttonMulai = (Button) findViewById(R.id.buttonMulai);
    }

    public void getData(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        String nama = ETNama.getText().toString();
        intent.putExtra("Nama", nama);
        startActivity(intent);
    }
}