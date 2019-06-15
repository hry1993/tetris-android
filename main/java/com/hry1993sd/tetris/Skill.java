package com.hry1993sd.tetris;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Skill {
    public Button skillbutton;
    public boolean clicked;
    private ProgressBar bar;
    private TextView minbar;
    public int value;
    private Context context;

    public Skill(Button skillbutton, boolean clicked, int value, TextView minbar, Context context, ProgressBar bar) {
        this.skillbutton = skillbutton;
        this.clicked = clicked;
        this.value = value;
        this.minbar = minbar;
        this.context = context;
        this.bar = bar;
    }

    public void onClick(View view) {
        int point = Integer.parseInt(minbar.getText().toString());
        if (!clicked) {
            if ((point + value) <= 15) {
                point = point + value;
                bar.setProgress(point);
                minbar.setText(String.valueOf(point));
                skillbutton.setBackgroundTintList(ColorStateList.valueOf(0xffffffff));
                clicked = true;
            } else {
                Toast.makeText(context, "Beyond max points.", Toast.LENGTH_LONG).show();
            }
        } else {
            clicked = false;
            point = point - value;
            minbar.setText(String.valueOf(point));
            bar.setProgress(point);
            skillbutton.setBackgroundTintList(ColorStateList.valueOf(0xffcccccc));
        }
    }
}
