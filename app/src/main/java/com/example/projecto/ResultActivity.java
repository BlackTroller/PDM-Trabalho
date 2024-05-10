package com.example.projecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class ResultActivity extends AppCompatActivity {

    TextView t1, t2, t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Result");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        t1 = findViewById(R.id.textView4);
        t2 = findViewById(R.id.textView5);
        t3 = findViewById(R.id.textView6);

        Intent i = getIntent();

        String questions = i.getStringExtra("total");
        String correct = i.getStringExtra("correct");
        String wrong = i.getStringExtra("incorrect");

        t1.setText(questions);
        t2.setText(correct);
        t3.setText(wrong);
    }
}