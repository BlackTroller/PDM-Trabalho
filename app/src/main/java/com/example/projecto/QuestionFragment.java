package com.example.projecto;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Objects;
import java.util.Random;

import model.Question;

public class QuestionFragment extends Fragment {

    private Button btn1, btn2, btn3, btn4;
    private TextView t1_question;

    private boolean isPaused = false;

    int points;
    int totalAnswers;
    int rightAnswers;
    int wrongAnswers;
    String docId;
    int newPoints = 0;
    int total = 0;
    int correct = 0;
    int wrong = 0;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public QuestionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (user != null) {
            String email = user.getEmail();
            db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                docId = document.getId();
                                points = Objects.requireNonNull(document.getLong("points")).intValue();
                                totalAnswers = Objects.requireNonNull(document.getLong("totalAnswers")).intValue();
                                rightAnswers = Objects.requireNonNull(document.getLong("rightAnswers")).intValue();
                                wrongAnswers = Objects.requireNonNull(document.getLong("wrongAnswers")).intValue();
                            }
                        }
                    });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mContentView = inflater.inflate(R.layout.fragment_question, container, false);
        t1_question = mContentView.findViewById(R.id.question);
        TextView timerTxt = mContentView.findViewById(R.id.timerTxt);
        btn1 = mContentView.findViewById(R.id.btn1);
        btn2 = mContentView.findViewById(R.id.btn2);
        btn3 = mContentView.findViewById(R.id.btn3);
        btn4 = mContentView.findViewById(R.id.btn4);

        updateQuestion();
        reverseTimer(3600, timerTxt);
        return mContentView;
    }

    public static int getRandomNumber(){
        int max = 12;
        int min = 0;
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    private void updateQuestion(){
        total ++;
        if(total > 4){
            //open the result activity

            db.collection("users")
                    .document(docId)
                    .update("points", (points + newPoints), "totalAnswers", (totalAnswers + total - 1), "rightAnswers", (rightAnswers + correct), "wrongAnswers", (wrongAnswers + wrong));

            Intent i = new Intent(QuestionFragment.this.getActivity(), ResultActivity.class);
            i.putExtra("total", String.valueOf(total - 1));
            i.putExtra("correct", String.valueOf(correct));
            i.putExtra("incorrect", String.valueOf(wrong));
            startActivity(i);

        }
        else{
            reference = FirebaseDatabase.getInstance().getReference().child("Questions").child(String.valueOf(getRandomNumber()));
            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Question question = dataSnapshot.getValue(Question.class);

                    t1_question.setText(question.getQuestion());
                    btn1.setText(question.getOption1());
                    btn2.setText(question.getOption2());
                    btn3.setText(question.getOption3());
                    btn4.setText(question.getOption4());

                    btn1.setOnClickListener(v -> {
                        if(btn1.getText().toString().equals(question.getAnswer()))
                        {
                            btn1.setBackgroundColor(Color.GREEN);
                            newPoints = newPoints + 3;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion();
                            },1500);
                        }
                        else{
                            //answer is wrong...... We will find the correct answer, and make it green
                            wrong++;
                            btn1.setBackgroundColor(Color.RED);

                            if(btn2.getText().toString().equals(question.getAnswer()))
                            {
                                btn2.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn3.getText().toString().equals(question.getAnswer()))
                            {
                                btn3.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn4.getText().toString().equals(question.getAnswer()))
                            {
                                btn4.setBackgroundColor(Color.GREEN);
                            }


                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            },1500);
                        }
                    });

                    btn2.setOnClickListener(view -> {
                        if(btn2.getText().toString().equals(question.getAnswer()))
                        {
                            btn2.setBackgroundColor(Color.GREEN);
                            newPoints = newPoints + 3;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion();
                            },1500);
                        }
                        else{
                            //answer is wrong...... We will find the correct answer, and make it green
                            wrong++;
                            btn2.setBackgroundColor(Color.RED);

                            if(btn1.getText().toString().equals(question.getAnswer()))
                            {
                                btn1.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn3.getText().toString().equals(question.getAnswer()))
                            {
                                btn3.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn4.getText().toString().equals(question.getAnswer()))
                            {
                                btn4.setBackgroundColor(Color.GREEN);
                            }


                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            },1500);
                        }
                    });

                    btn3.setOnClickListener(view -> {
                        if(btn3.getText().toString().equals(question.getAnswer()))
                        {
                            btn3.setBackgroundColor(Color.GREEN);
                            newPoints = newPoints + 3;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion();
                            },1500);
                        }
                        else{
                            //answer is wrong...... We will find the correct answer, and make it green
                            wrong++;
                            btn3.setBackgroundColor(Color.RED);

                            if(btn1.getText().toString().equals(question.getAnswer()))
                            {
                                btn1.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn2.getText().toString().equals(question.getAnswer()))
                            {
                                btn2.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn4.getText().toString().equals(question.getAnswer()))
                            {
                                btn4.setBackgroundColor(Color.GREEN);
                            }

                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            },1500);
                        }
                    });

                    btn4.setOnClickListener(view -> {
                        if(btn4.getText().toString().equals(question.getAnswer()))
                        {
                            btn4.setBackgroundColor(Color.GREEN);
                            newPoints = newPoints + 3;
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                correct++;
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));

                                updateQuestion();
                            },1500);
                        }
                        else{
                            //answer is wrong...... We will find the correct answer, and make it green
                            wrong++;
                            btn4.setBackgroundColor(Color.RED);

                            if(btn1.getText().toString().equals(question.getAnswer()))
                            {
                                btn1.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn2.getText().toString().equals(question.getAnswer()))
                            {
                                btn2.setBackgroundColor(Color.GREEN);
                            }
                            else if(btn3.getText().toString().equals(question.getAnswer()))
                            {
                                btn3.setBackgroundColor(Color.GREEN);
                            }


                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                btn1.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn2.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn3.setBackgroundColor(Color.parseColor("#03A9F4"));
                                btn4.setBackgroundColor(Color.parseColor("#03A9F4"));
                                updateQuestion();
                            },1500);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    public void reverseTimer(int seconds, TextView tv){
        new CountDownTimer(seconds * 1000 + 1000, 1000) {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                int hour = minutes / 60;
                seconds = seconds % 60;


                if (isPaused) {
                    cancel();
                } else {
                    tv.setText(String.format("%02d", hour) + ":" + String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
                }
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {

                tv.setText("Completed");
                Intent myIntent = new Intent(QuestionFragment.this.getActivity(), ResultActivity.class);
                myIntent.putExtra("total", String.valueOf(total));
                myIntent.putExtra("correct", String.valueOf(correct));
                myIntent.putExtra("wrong", String.valueOf(wrong));

                db.collection("users")
                        .document(docId)
                        .update("points", (points + newPoints), "totalAnswers", (totalAnswers + total - 1), "rightAnswers", (rightAnswers + correct), "wrongAnswers", (wrongAnswers + wrong));

                startActivity(myIntent);
            }
        }.start();

    }

    @Override
    public void onResume() {
        isPaused = false;
        super.onResume();
    }

    @Override
    public void onPause() {
        isPaused = true;
        super.onPause();
    }

}