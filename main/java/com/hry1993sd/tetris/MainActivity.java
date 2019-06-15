package com.hry1993sd.tetris;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public static float gridSpace;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        float dip = 28f;
        Resources r = getResources();
        gridSpace = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
    }

    public void onClickSinglePlay(View view) {
        Intent intent = new Intent(view.getContext(), SinglePlayActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onClickSkillCombination(View view) {
        Intent intent = new Intent(view.getContext(), SkillCombinationActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onClickPlayWithAI(View view) {
        Intent intent = new Intent(view.getContext(), PlayWithAIActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onClickPlayWithPeople(View view) {
        Intent intent = new Intent(view.getContext(), LocalNetworkActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onClickSetting(View view) {
        Intent intent = new Intent(view.getContext(), SettingActivity.class);
        view.getContext().startActivity(intent);
    }
}
