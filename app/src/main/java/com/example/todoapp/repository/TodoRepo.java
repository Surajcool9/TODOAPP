package com.example.todoapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.todoapp.db.TodoDatabase;
import com.example.todoapp.db.dao.TodoDao;
import com.example.todoapp.db.model.TodoModel;

import java.util.List;

public class TodoRepo {

    private TodoDao todoDao;
    private LiveData<List<TodoModel>> allTodo;

    public TodoRepo(Application application) {
        TodoDatabase db = TodoDatabase.getInstance(application);
        todoDao = db.todoDao();
        allTodo = todoDao.getAllTodo();
    }

    public void insert(TodoModel todoModel){
        new InsertTodoAsyncTask(todoDao).execute(todoModel);
    }

    public void update(TodoModel todoModel){
        new UpdateTodoAsyncTask(todoDao).execute(todoModel);
    }

    public void delete(TodoModel todoModel){
        new DeleteTodoAsyncTask(todoDao).execute(todoModel);
    }

    public LiveData<List<TodoModel>> getAllTodo() {
        return allTodo;
    }


    private static class InsertTodoAsyncTask extends AsyncTask<TodoModel, Void, Void> {

        private TodoDao todoDao;

        public InsertTodoAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(TodoModel... todoModels) {
            todoDao.insert(todoModels[0]);
            return null;
        }
    }

    private static class UpdateTodoAsyncTask extends AsyncTask<TodoModel, Void, Void> {

        private TodoDao todoDao;

        public UpdateTodoAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(TodoModel... todoModels) {
            todoDao.update(todoModels[0]);
            return null;
        }
    }

    private static class DeleteTodoAsyncTask extends AsyncTask<TodoModel, Void, Void> {

        private TodoDao todoDao;

        public DeleteTodoAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(TodoModel... todoModels) {
            todoDao.delete(todoModels[0]);
            return null;
        }
    }

}
