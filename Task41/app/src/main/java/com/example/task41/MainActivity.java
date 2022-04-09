package com.example.task41;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    // Declare widgets
    TextView textView;
    TextView textViewT;
    EditText editText;

    private String kindOfWork;
    private String preT,preW;
    private SharedPreferences sharedPreferences;
    private Handler handler;
    private Integer s,m,h;
    private Boolean Run;


    final String RUN = "TIMER_RUNNING";
    final String KIND_OF_WORK = "WORKOUT_TITLE";
    final String PRE_T = "PREV_TIME";
    final String PRE_W = "PREV_WORKOUT";
    final String SHARED_PREF = "SHARED_PREF";
    final String S = "SECONDS";
    final String M = "MINUTES";
    final String H = "HOURS";

    public void RunT()
    {
        // Creates a new Handler
        handler = new Handler();

        handler.post(new Runnable() {
            @Override

            public void run()
            {

                if (Run)
                {
                    s++;
                    if (s == 60){
                        m++;
                        s = 0;
                    }
                    if (m == 60){
                        h++;
                        m = 0;
                    }
                }


                UpdateT();


                handler.postDelayed(this, 1000);
            }
        });
    }
    private void UpdateT()
    {
        textViewT.setText(GetT());
    }
    private String GetT()
    {
        return String.format(Locale.getDefault(),
                "%02d:%02d:%02d", h, m, s);
    }
    private void UpdateText()
    {
        textView.setText("You spent " + preT + " on " + preW + " last time.");
    }



    public void Start(View view) {
        Run = true;
    }

    public void Pause(View view) {
        Run = false;
    }

    public void Stop(View view) {
        Run = false;
        SavePersistentData();
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        kindOfWork = editText.getText().toString();
        outState.putBoolean(RUN, Run);
        outState.putString(KIND_OF_WORK, kindOfWork);
        outState.putString(PRE_T, preT);
        outState.putString(PRE_W, preW);
        outState.putInt(H, h);
        outState.putInt(M, m);

        outState.putInt(S, s);


    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Run = savedInstanceState.getBoolean(RUN);
        kindOfWork = savedInstanceState.getString(KIND_OF_WORK);
        preT = savedInstanceState.getString(PRE_T);
        preW = savedInstanceState.getString(PRE_W);
        m = savedInstanceState.getInt(M);
        h = savedInstanceState.getInt(H);
        s = savedInstanceState.getInt(S);





        UpdateText();
        UpdateT();


        editText.setText(kindOfWork);

    }




    public void SavePersistentData()
    {
        kindOfWork = editText.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PRE_T, GetT());
        editor.putString(PRE_W, kindOfWork);
        editor.apply();
    }


    public void LoadPersistentData()
    {
        preT = sharedPreferences.getString(PRE_T, "pre_time");
        preW = sharedPreferences.getString(PRE_W, "pre_workout");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        s = 0;
        m = 0;
        h = 0;
        Run = false;


        textView = findViewById(R.id.textView3);
        textViewT = findViewById(R.id.textView2);
        editText = findViewById(R.id.editText);


        kindOfWork = editText.getText().toString();


        sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        LoadPersistentData();


        UpdateText();
        UpdateT();
        RunT();
    }





}

