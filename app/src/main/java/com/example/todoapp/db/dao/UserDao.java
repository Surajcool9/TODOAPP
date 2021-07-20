package com.example.todoapp.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.todoapp.db.model.UserModel;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserModel userModel);

    @Query("SELECT * FROM users where userName=(:userName) and password=(:password)")
    UserModel login(String userName, String password);
}
