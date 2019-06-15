package com.hry1993sd.tetris;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LocalNetworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_network);
    }
    public void server(View view) {
        Intent intent = new Intent(view.getContext(), PlayWithPeopleActivityServer.class);
        view.getContext().startActivity(intent);
    }
    public void client(View view) {
        Intent intent = new Intent(view.getContext(), PlayWithPeopleActivityClient.class);
        view.getContext().startActivity(intent);
    }
}
