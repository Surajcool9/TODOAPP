package com.example.todoapp.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "todo_table")
public class TodoModel {

    @PrimaryKey(autoGenerate = true)
    public int todoId;

    public String title;

    public String description;


    public TodoModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getTodoId() {
        return todoId;
    }


    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
