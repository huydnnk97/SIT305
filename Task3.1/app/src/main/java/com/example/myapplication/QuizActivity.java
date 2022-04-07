package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class QuizActivity extends AppCompatActivity {

    String questions[] = {
            "Keywords is used to define interfaces in Java?",
            "Correct way of importing an entire package ‘pkg’?",
            "Return type of Constructors?",
            "Package stores all the standard java classes?",
            "Class String's method is used to compare two String objects for their equality?"
    };
    String answers[] = {"interface","import pkg.*","Don't have return type","java","equals()"};
    String opt[] = {
            "Interface", "interface", "intf",
            "Import pkg.", "import pkg.*", "import pkg.",
            "int", "void", "Don't have return type",
            "lang", "java", "util",
            "equals()", "isequal()", "Isequal()"
    };
    String name;
    TextView welcomeName;
    int progressNumber=1;
    Button submitBtn;
    int isClickedButton=0;
    TextView numberProgress;
    TextView numberQuestion;
    TextView question;
    TextView answer1;
    TextView answer2;
    TextView answer3;
    ProgressBar progressBar;
    int score=0;


    public void checkAnswer(int current){
        if(answer1.isSelected()) {
            if (!answer1.getText().toString().equals(answers[current - 1])) {
                answer1.setBackgroundColor(Color.parseColor("#FF0000"));

            }
            else{score=score+1;}

            return;
        }
        if(answer2.isSelected()) {
            if (!answer2.getText().toString().equals(answers[current - 1])) {
                answer2.setBackgroundColor(Color.parseColor("#FF0000"));

            }
            else{score=score+1;}
            return;
        }
        if(answer3.isSelected()) {
            if (!answer3.getText().toString().equals(answers[current - 1])) {
                answer3.setBackgroundColor(Color.parseColor("#FF0000"));

            }
            else{score=score+1;}
            return;


        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        welcomeName=findViewById(R.id.textViewa);
        submitBtn=findViewById(R.id.button2);
        numberProgress=findViewById(R.id.progressNumberView);
        numberQuestion=findViewById(R.id.textViewb);
        numberQuestion.setText("Question 1");
        question=findViewById(R.id.textViewc);
        question.setText(questions[0]);
        answer1=findViewById(R.id.textViewd);
        answer2=findViewById(R.id.textViewe);
        answer3=findViewById(R.id.textViewf);
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setProgress(progressNumber);
        answer1.setBackgroundResource(R.drawable.border);
        answer2.setBackgroundResource(R.drawable.border);
        answer3.setBackgroundResource(R.drawable.border);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setSelected(true);
                answer2.setSelected(false);
                answer3.setSelected(false);
            }
        });
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setSelected(false);
                answer2.setSelected(true);
                answer3.setSelected(false);
            }
        });
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setSelected(false);
                answer2.setSelected(false);
                answer3.setSelected(true);
            }
        });

        answer1.setText(opt[0]);
        answer2.setText(opt[1]);
        answer3.setText(opt[2]);

        Intent intent=getIntent();
        name=intent.getStringExtra("yourname");
        welcomeName.setText("Welcome"+name+"!");

        Timer delayTime=new Timer(false);
        delayTime.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        welcomeName.setVisibility(View.GONE);
                    }
                });
            }
        },3000);

        answer2.setSelected(false);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isClickedButton==0){
                    if(answer1.isSelected()||answer2.isSelected()||answer3.isSelected()){
                        checkAnswer(progressNumber);
                        submitBtn.setText("Next");
                        isClickedButton=1;

                        if(answer1.getText().toString().equals(answers[progressNumber-1])){
                            answer1.setBackgroundColor(Color.GREEN);
                        }
                        if(answer2.getText().toString().equals(answers[progressNumber-1])){
                            answer2.setBackgroundColor(Color.GREEN);
                        }
                        if(answer3.getText().toString().equals(answers[progressNumber-1])){
                            answer3.setBackgroundColor(Color.GREEN);
                        }
                    }

                }
                else if(isClickedButton==1){
                    if(progressNumber==5){
                        Intent intent1=new Intent(getApplicationContext(),ResultActivity.class);
                        intent1.putExtra("yourname",name);
                        intent1.putExtra("score",String.valueOf(score));

                        startActivity(intent1);
                        finish();
                    }
                    else {
                        submitBtn.setText("Sumbit");
                        isClickedButton = 0;
                        progressNumber = progressNumber + 1;
                        numberProgress.setText(String.valueOf(progressNumber) + "/5");
                        numberQuestion.setText("Question " + String.valueOf(progressNumber));
                        question.setText(questions[progressNumber - 1]);
                        answer1.setText(opt[3 * (progressNumber - 1)]);
                        answer2.setText(opt[3 * (progressNumber - 1) + 1]);
                        answer3.setText(opt[3 * (progressNumber - 1) + 2]);
                        answer1.setBackgroundResource(R.drawable.border);
                        answer2.setBackgroundResource(R.drawable.border);
                        answer3.setBackgroundResource(R.drawable.border);
                        answer1.setSelected(false);
                        answer2.setSelected(false);
                        answer3.setSelected(false);
                        progressBar.setProgress(progressNumber);
                    }
                }

            }
        });
    }

}