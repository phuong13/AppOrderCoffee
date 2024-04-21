package com.example.demo.Activity.User;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.demo.Activity.BaseActivity;
import com.example.demo.databinding.ActivityIntroBinding;

public class IntroActivity extends BaseActivity {
    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#F75564"));


    }

    private void setVariable() {
        binding.txtDangNhap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAuth.getCurrentUser()!=null){
                    startActivity(new Intent(IntroActivity.this,MainActivity.class));
                }else{
                    startActivity(new Intent(IntroActivity.this,LoginActivity.class));
                }
            }
        });
        binding.txtDangKy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntroActivity.this,SignUpActivity.class));
            }
        });
    }
}