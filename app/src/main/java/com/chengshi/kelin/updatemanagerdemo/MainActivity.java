package com.chengshi.kelin.updatemanagerdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chengshi.apkUpdater.Updater;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_check_update).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        new Updater.Builder(MainActivity.this).builder().check(new UpdateModel());
    }
}
