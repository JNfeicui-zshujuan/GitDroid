package com.feicuiedu.gitdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.feicuiedu.gitdroid.common.ActivityUtils;
import com.feicuiedu.gitdroid.login.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {
    @Bind(R.id.btnLogin)
    Button btnLogin;
    @Bind(R.id.btnEnter)
    Button btnEnter;
    private ActivityUtils utils ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        utils =new ActivityUtils(this);
    }

    @OnClick(R.id.btnLogin)
    public void login() {
        utils.startActivity(LoginActivity.class);
        Toast.makeText(SplashActivity.this, "Login", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnEnter)
    public void enter() {
        Toast.makeText(SplashActivity.this, "Enter", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}