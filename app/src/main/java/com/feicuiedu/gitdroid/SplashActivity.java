package com.feicuiedu.gitdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnEnter)
    Button btnEnter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        Toast.makeText(SplashActivity.this, "Login", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnEnter)
    public void enter() {
        Toast.makeText(SplashActivity.this, "Enter", Toast.LENGTH_SHORT).show();
    }
}