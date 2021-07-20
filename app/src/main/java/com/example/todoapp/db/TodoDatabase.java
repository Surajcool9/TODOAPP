package com.example.todoapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todoapp.db.dao.TodoDao;
import com.example.todoapp.db.model.TodoModel;

@Database(entities = {TodoModel.class}, version = 1)
public abstract class TodoDatabase extends RoomDatabase {

    private static TodoDatabase todoDatabase;
    private static final String dbName = "todo_database";

    public static synchronized  TodoDatabase getInstance(Context context){

        if(todoDatabase == null) {
            todoDatabase = Room.databaseBuilder(context,TodoDatabase.class,dbName)
                    .fallbackToDestructiveMigration()
                    .build();
         }
        return todoDatabase;
    }
    public abstract TodoDao todoDao();
}
