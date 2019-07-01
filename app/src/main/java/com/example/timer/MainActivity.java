package com.example.timer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private TextView timerText;
    private EditText edit;
    private Button Start_T, reset_T;
    private CountDownTimer C_Timer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = (TextView) findViewById(R.id.textView);
        edit = (EditText) findViewById(R.id.editText2);
        Start_T = (Button) findViewById(R.id.button);
        reset_T = (Button) findViewById(R.id.button2);
        setListeners();
    }

    private void setListeners()
    {
        Start_T.setOnClickListener(this);
        reset_T.setOnClickListener(this);
    }

    private void StopTime()
    {
        if (C_Timer != null)
        {
            C_Timer.cancel();
            C_Timer = null;
        }
    }

    private void StartTime(int noMin)
    {
        C_Timer = new CountDownTimer(noMin, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                long milli = millisUntilFinished;
                String tm = String.format("%02d:%02d:%02d",
                        TimeUnit.MILLISECONDS.toHours(milli),
                        TimeUnit.MILLISECONDS.toMinutes(milli) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milli)),
                        TimeUnit.MILLISECONDS.toSeconds(milli) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milli)));
                timerText.setText(tm);
            }

            @Override
            public void onFinish()
            {
                timerText.setText("FINISH");
                C_Timer = null;
                Start_T.setText(getString(R.string.Start_T));
            }
        }.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button:
                if(C_Timer == null)
                {
                    String get_Min = edit.getText().toString();
                    if (!get_Min.equals("") && get_Min.length() > 0)
                    {
                        int noMin = Integer.parseInt(get_Min) * 60 * 1000;
                        StartTime(noMin);
                        Start_T.setText(getString(R.string.Stop_T));
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Please input in minutes", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    StopTime();
                    Start_T.setText("Restart");
                }
                break;
            case R.id.button2:
                StopTime();
                Start_T.setText(getString(R.string.Start_T));
                timerText.setText(getString(R.string.Time_Format));
                break;
        }
    }
}
