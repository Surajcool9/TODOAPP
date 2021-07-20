package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.databinding.ActivityLoginBinding;
import com.example.todoapp.db.UserDB;
import com.example.todoapp.db.dao.UserDao;
import com.example.todoapp.db.model.UserModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userNameText = binding.userName.getText().toString();
                String passwordText = binding.password.getText().toString();

                if(TextUtils.isEmpty(userNameText) || TextUtils.isEmpty(passwordText))
                {
                    Toast.makeText(getApplicationContext(), "Fill all Fields", Toast.LENGTH_SHORT).show();
                } else {
                    UserDB userDB = UserDB.getUserDB(getApplicationContext());
                    UserDao userDao = userDB.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserModel userModel = userDao.login(userNameText, passwordText);
                            if(userModel == null){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credential", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                String name  = userModel.getFullName();
                                Intent i = new Intent(LoginActivity.this, ToDoActivity.class);
                                i.putExtra("Fullname", name);
                                startActivity(i);

                            }
                        }
                    }).start();
                }
            }
        });
    }
}