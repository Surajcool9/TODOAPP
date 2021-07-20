package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.databinding.ActivityMainBinding;
import com.example.todoapp.db.UserDB;
import com.example.todoapp.db.dao.UserDao;
import com.example.todoapp.db.model.UserModel;


public class RegisterActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel = new UserModel();
                userModel.setUserName(binding.userName.getText().toString());
                userModel.setPassword(binding.password.getText().toString());
                userModel.setFullName(binding.fullName.getText().toString());
                if(validation(userModel)) {
                    //Insert Operation using Thread
                    UserDB userDB = UserDB.getUserDB(getApplicationContext());
                    UserDao userDao = userDB.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //User Register
                           userDao.registerUser(userModel);
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(getApplicationContext(), "Successfully Registered", Toast.LENGTH_SHORT).show();
                                   binding.fullName.setText("");
                                   binding.userName.setText("");
                                   binding.password.setText("");
                               }
                           });

                        }
                    }).start();
                } else {
                    Toast.makeText(getApplicationContext(), "Fill all Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validation(UserModel userModel) {
        if(TextUtils.isEmpty(userModel.getUserName())
        || TextUtils.isEmpty(userModel.getPassword())
        || TextUtils.isEmpty(userModel.getFullName())) {
            return false;
        } else return true;
    }
}