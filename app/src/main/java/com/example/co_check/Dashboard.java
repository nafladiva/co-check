package com.example.co_check;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Dashboard extends AppCompatActivity {

    Button buttonMulai;
    TextView TVPositif, TVSembuh, TVMeninggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_dashboard);

        TVPositif = (TextView) findViewById(R.id.TVPositif);
        TVSembuh = (TextView) findViewById(R.id.TVSembuh);
        TVMeninggal = (TextView) findViewById(R.id.TVMeninggal);

        buttonMulai = (Button) findViewById(R.id.buttonMulai);

        buttonMulai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DataDiri.class);
                startActivity(intent);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.kawalcorona.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceholderAPI placeholderAPI = retrofit.create(PlaceholderAPI.class);
        Call<List<DataPost>> call = placeholderAPI.getData();

        call.enqueue(new Callback<List<DataPost>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DataPost>> call, Response<List<DataPost>> response) {

                if (response.isSuccessful()) {
                    List<DataPost> posts = response.body();
                    TVPositif.setText("" + posts.get(0).getPositif());
                    TVSembuh.setText("" + posts.get(0).getSembuh());
                    TVMeninggal.setText("" + posts.get(0).getMeninggal());
                } else {
                    Log.d("Yo", "Boo!");
                    return;
                }
            }

            @Override
            public void onFailure(Call<List<DataPost>> call, Throwable t) {
                Log.d("Yo", t.getMessage());
            }

        });

    }
}