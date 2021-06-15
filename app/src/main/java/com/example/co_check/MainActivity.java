package com.example.co_check;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private int questionValue = 0;
    private int questionNumber = 0;
    private int chooseNo = 0;
    TextView NoQuestion, questionTV;
    Button questionYes, questionNo;
    JSONObject obj;
    JSONArray pertanyaan, jawaban;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        NoQuestion = (TextView) findViewById(R.id.NoQuestion);
        questionTV = (TextView) findViewById(R.id.questionTV);
        questionYes = (Button) findViewById(R.id.questionYes);
        questionNo = (Button) findViewById(R.id.questionNo);

        Intent intent = getIntent();
        String nama = intent.getStringExtra("Nama");

        try {
            // get JSONObject from JSON file
            this.obj = new JSONObject(loadJSONFromAsset());
            this.pertanyaan = obj.getJSONArray("pertanyaan");
            this.jawaban = obj.getJSONArray("jawaban");
            NoQuestion.setText("Pertanyaan " + (questionNumber + 1));
            questionTV.setText(getQuestion(pertanyaan));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        questionYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addQuestionValue();
                nextQuestion();
                NoQuestion.setText("Pertanyaan " + (questionNumber + 1));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            questionTV.setText(getQuestion(pertanyaan));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    if(getQuestion(pertanyaan).equals("")) {
                        finish();
                        Intent intent = new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("Nama", nama);
                        intent.putExtra("resultValue", getResult(jawaban, questionValue));
                        intent.putExtra("resultImage", getImage(jawaban, questionValue));
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        questionNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
                addNumberNo();
                NoQuestion.setText("Pertanyaan " + (questionNumber + 1));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            questionTV.setText(getQuestion(pertanyaan));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                try {
                    if(questionValue > 0 ) {
                        Log.d("QuestionValue", "value: "+questionValue);
                        finish();
                        Intent intent = new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("Nama", nama);
                        intent.putExtra("resultValue", getResult(jawaban, questionValue));
                        intent.putExtra("resultImage", getImage(jawaban, questionValue));
                        startActivity(intent);
                    }

                    if(chooseNo == 2 || getQuestion(pertanyaan).equals("")) {
                        Log.d("QuestionDone", "HABIS Tidak");
                        finish();
                        Intent intent = new Intent(getApplicationContext(), Result.class);
                        intent.putExtra("Nama", nama);
                        intent.putExtra("resultValue", getResult(jawaban, questionValue));
                        intent.putExtra("resultImage", getImage(jawaban, questionValue));
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("cocheck.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String getQuestion(JSONArray jsonArray) throws JSONException {
        String pertanyaan = "";

        if(this.questionNumber != 0) {
            for(int i=1; i<jsonArray.length(); i++) {
                int value = jsonArray.getJSONObject(i).getInt("value");
                if(questionValue == value) {
                    pertanyaan = jsonArray.getJSONObject(i).getString("isi");
                    break;
                }
            }
        } else {
            pertanyaan = jsonArray.getJSONObject(0).getString("isi");
        }

        return pertanyaan;
    }

    public void restartQuestion() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void addQuestionValue() {
        this.questionValue++;
    }

    public void nextQuestion() {
        this.questionNumber++;
    }

    public void addNumberNo() {
        this.chooseNo++;
    }

    public String getResult(JSONArray jsonArray, int resultValue) throws JSONException {
        String jawaban = "";

        for(int i=0; i<jsonArray.length(); i++) {
            int value = jsonArray.getJSONObject(i).getInt("value");
            if(resultValue == value) {
                jawaban = jsonArray.getJSONObject(i).getString("isi");
                break;
            }
        }

        return jawaban;
    }


    public String getImage(JSONArray jsonArray, int resultValue) throws JSONException {
        String image_uri = "";

        for(int i=0; i<jsonArray.length(); i++) {
            int value = jsonArray.getJSONObject(i).getInt("value");
            if(resultValue == value) {
                image_uri = jsonArray.getJSONObject(i).getString("image");
                break;
            }
        }

        return image_uri;
    }
}