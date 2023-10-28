package com.app.primeshow.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.primeshow.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginbtn.setOnClickListener(v -> {
            if(binding.userName.getText().toString().isEmpty() || binding.userPassword.getText().toString().isEmpty()){
                Toast.makeText(LoginActivity.this,"Please fill your username and password",Toast.LENGTH_SHORT).show();
            } else if (binding.userName.getText().toString().equals("test") && binding.userPassword.getText().toString().equals("test")) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this,"Wrong credentials",Toast.LENGTH_SHORT).show();
            }
        });
    }
}