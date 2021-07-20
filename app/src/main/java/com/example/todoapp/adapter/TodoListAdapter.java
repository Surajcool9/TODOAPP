package com.example.todoapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.databinding.TodoListViewBinding;
import com.example.todoapp.db.model.TodoModel;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoHolder> {

    private List<TodoModel> todoList = new ArrayList<>();
    private OnTodoClick onTodoClick;

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.todo_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {

        TodoModel todoModel = todoList.get(position);
        holder.binding.todoTitle.setText(todoModel.title);
        holder.binding.todoDesc.setText(todoModel.description);

        holder.binding.todoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTodoClick != null && position != RecyclerView.NO_POSITION) {
                    onTodoClick.onClick(todoList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void setTodoList(List<TodoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public TodoModel getTodoPos(int position) {
        return todoList.get(position);
    }

    class TodoHolder extends RecyclerView.ViewHolder{

        private TodoListViewBinding binding;

        public TodoHolder(@NonNull TodoListViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface  OnTodoClick {
        void onClick(TodoModel todoModel);
    }

    public void setItemClick(OnTodoClick onTodoClick) {
        this.onTodoClick = onTodoClick;
    }

}
