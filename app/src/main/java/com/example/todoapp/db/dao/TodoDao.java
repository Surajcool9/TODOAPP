package com.example.todoapp.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todoapp.db.model.TodoModel;

import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    void insert(TodoModel todoModel);

    @Update
    void update(TodoModel todoModel);

    @Delete
    void delete(TodoModel todoModel);

    @Query("Select * from todo_table")
    LiveData<List<TodoModel>> getAllTodo();
}
