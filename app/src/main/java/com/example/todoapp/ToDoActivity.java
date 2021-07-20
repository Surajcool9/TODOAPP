package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.adapter.TodoListAdapter;
import com.example.todoapp.databinding.ActivityToDoBinding;
import com.example.todoapp.db.model.TodoModel;
import com.example.todoapp.viewmodel.TodoListViewModel;

import java.util.List;

public class ToDoActivity extends AppCompatActivity {

    private ActivityToDoBinding binding;
    private TodoListViewModel viewModel;
    private TodoListAdapter adapter;
    public static final int ADD_TODO = 1;
    public static final int EDIT_TODO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_to_do);

        String fullName = getIntent().getStringExtra("Fullname");
        String welcome = "Welcome " + fullName + "!!";
        binding.welcomeNote.setText(welcome);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToDoActivity.this, AddEditTodo.class);
                startActivityForResult(intent, ADD_TODO);
            }
        });

        binding.recycleViewTodo.setLayoutManager(
                new LinearLayoutManager(this));
        binding.recycleViewTodo.setHasFixedSize(true);
        adapter = new TodoListAdapter();
        binding.recycleViewTodo.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(TodoListViewModel.class);
        viewModel.getAllTodo().observe(this, new Observer<List<TodoModel>>() {
            @Override
            public void onChanged(List<TodoModel> todoModels) {
                if (todoModels.size() > 0) {
                    adapter.setTodoList(todoModels);
                } else {
                    Toast.makeText(ToDoActivity.this, "No Todo Note Found Please Add todo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.delete(adapter.getTodoPos(viewHolder.getAdapterPosition()));
                Toast.makeText(ToDoActivity.this, "TODO Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recycleViewTodo);

        adapter.setItemClick(new TodoListAdapter.OnTodoClick() {
            @Override
            public void onClick(TodoModel todoModel) {
                Intent intent = new Intent(ToDoActivity.this, AddEditTodo.class);
                intent.putExtra(AddEditTodo.EDIT_ID, todoModel.todoId);
                intent.putExtra(AddEditTodo.EDIT_TITLE, todoModel.title);
                intent.putExtra(AddEditTodo.EDIT_DESC, todoModel.description);
                startActivityForResult(intent, EDIT_TODO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TODO && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTodo.EDIT_TITLE);
            String description = data.getStringExtra(AddEditTodo.EDIT_DESC);

            TodoModel todoModel = new TodoModel(title, description);
            viewModel.insert(todoModel);
            Toast.makeText(this, "TODO Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TODO && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTodo.EDIT_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "TODO can't be updated", Toast.LENGTH_SHORT).show();
            } else {
                String title = data.getStringExtra(AddEditTodo.EDIT_TITLE);
                String description = data.getStringExtra(AddEditTodo.EDIT_DESC);

                TodoModel todoModel = new TodoModel(title, description);
                todoModel.setTodoId(id);
                viewModel.update(todoModel);
                Toast.makeText(this, "TODO Updated", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "TODO not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}