package com.example.todoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.todoapp.db.model.TodoModel;
import com.example.todoapp.repository.TodoRepo;

import java.util.List;

public class TodoListViewModel extends AndroidViewModel {

    private TodoRepo todoRepo;
    private LiveData<List<TodoModel>> allTodo;

    public TodoListViewModel(@NonNull Application application) {
        super(application);
        todoRepo = new TodoRepo(application);
        allTodo = todoRepo.getAllTodo();
    }

    public void insert(TodoModel todoModel){
       todoRepo.insert(todoModel);
    }

    public void update(TodoModel todoModel){
        todoRepo.update(todoModel);
    }

    public void delete(TodoModel todoModel){
        todoRepo.delete(todoModel);
    }

    public LiveData<List<TodoModel>> getAllTodo() {
        return allTodo;
    }
}
