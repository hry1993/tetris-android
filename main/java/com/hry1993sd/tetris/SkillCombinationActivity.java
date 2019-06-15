package com.hry1993sd.tetris;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SkillCombinationActivity extends AppCompatActivity {

    Button resetbutton,skillbutton1,skillbutton2,skillbutton3,skillbutton4,skillbutton5,skillbutton6,skillbutton7,skillbutton8,skillbutton9,skillbutton10,skillbutton11,skillbutton12,skillbutton13,skillbutton14,skillbutton15;
    ProgressBar bar;
    TextView minbar;
    Skill[] Skills = new Skill[15];
    SharedPreferences sharedPreferences;
    boolean[] combinationrecord = new boolean[15];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_combination);

        skillbutton1 = findViewById(R.id.skill1);
        skillbutton2 = findViewById(R.id.skill2);
        skillbutton3 = findViewById(R.id.skill3);
        skillbutton4 = findViewById(R.id.skill4);
        skillbutton5 = findViewById(R.id.skill5);
        skillbutton6 = findViewById(R.id.skill6);
        skillbutton7 = findViewById(R.id.skill7);
        skillbutton8 = findViewById(R.id.skill8);
        skillbutton9 = findViewById(R.id.skill9);
        skillbutton10 = findViewById(R.id.skill10);
        skillbutton11 = findViewById(R.id.skill11);
        skillbutton12 = findViewById(R.id.skill12);
        skillbutton13 = findViewById(R.id.skill13);
        skillbutton14 = findViewById(R.id.skill14);
        skillbutton15 = findViewById(R.id.skill15);

        bar = findViewById(R.id.progressBar);
        minbar = findViewById(R.id.minbar);
        bar.setMax(15);

        Skills[0] = new Skill(skillbutton1, false, 3,minbar,this,bar);
        Skills[1] = new Skill(skillbutton2, false, 4,minbar,this,bar);
        Skills[2] = new Skill(skillbutton3, false, 5,minbar,this,bar);
        Skills[3] = new Skill(skillbutton4, false, 3,minbar,this,bar);
        Skills[4] = new Skill(skillbutton5, false, 4,minbar,this,bar);
        Skills[5] = new Skill(skillbutton6, false, 5,minbar,this,bar);
        Skills[6] = new Skill(skillbutton7, false, 3,minbar,this,bar);
        Skills[7] = new Skill(skillbutton8, false, 4,minbar,this,bar);
        Skills[8] = new Skill(skillbutton9, false, 5,minbar,this,bar);
        Skills[9] = new Skill(skillbutton10, false, 3,minbar,this,bar);
        Skills[10] = new Skill(skillbutton11, false, 4,minbar,this,bar);
        Skills[11] = new Skill(skillbutton12, false, 5,minbar,this,bar);
        Skills[12] = new Skill(skillbutton13, false, 5,minbar,this,bar);
        Skills[13] = new Skill(skillbutton14, false, 8,minbar,this,bar);
        Skills[14] = new Skill(skillbutton15, false, 10,minbar,this,bar);

        sharedPreferences = getSharedPreferences("Skill",0);
        for (int i = 0;i < 15; i++) {
            combinationrecord[i] = sharedPreferences.getBoolean("skill" + i,false);
        }

        resetbutton = findViewById(R.id.reset);
        resetbutton.post(new Runnable() {
            @Override
            public void run() {
                resetbutton.performClick();
            }
        });
    }

    public void onClickSkill1(View view) {
        Skills[0].onClick(view);
    }

    public void onClickSkill2(View view) {
        Skills[1].onClick(view);
    }

    public void onClickSkill3(View view) {
        Skills[2].onClick(view);
    }

    public void onClickSkill4(View view) {
        Skills[3].onClick(view);
    }

    public void onClickSkill5(View view) {
        Skills[4].onClick(view);
    }

    public void onClickSkill6(View view) {
        Skills[5].onClick(view);
    }

    public void onClickSkill7(View view) {
        Skills[6].onClick(view);
    }

    public void onClickSkill8(View view) {
        Skills[7].onClick(view);
    }

    public void onClickSkill9(View view) {
        Skills[8].onClick(view);
    }

    public void onClickSkill10(View view) {
        Skills[9].onClick(view);
    }

    public void onClickSkill11(View view) {
        Skills[10].onClick(view);
    }

    public void onClickSkill12(View view) {
        Skills[11].onClick(view);
    }

    public void onClickSkill13(View view) {
        Skills[12].onClick(view);
    }

    public void onClickSkill14(View view) {
        Skills[13].onClick(view);
    }

    public void onClickSkill15(View view) {
        Skills[14].onClick(view);
    }

    public void save(View view) {
        for (int i = 0; i < 15;i++) {
            combinationrecord[i] = Skills[i].clicked;
            sharedPreferences.edit().putBoolean("skill" + i,combinationrecord[i]).apply();
        }
    }

    public void reset(View view) {
        int point = 0;
        for (int i = 0;i < 15;i++) {
            if (combinationrecord[i]) {
                point = point + Skills[i].value;
                Skills[i].clicked = true;
                Skills[i].skillbutton.setBackgroundTintList(ColorStateList.valueOf(0xffffffff));
            } else {
                Skills[i].clicked = false;
                Skills[i].skillbutton.setBackgroundTintList(ColorStateList.valueOf(0xffcccccc));
            }
        }
        bar.setProgress(point);
        minbar.setText(String.valueOf(point));
    }

    public void cancel(View view) {
        Intent intent = new Intent(view.getContext(), MainActivity.class);
        view.getContext().startActivity(intent);
    }
}
