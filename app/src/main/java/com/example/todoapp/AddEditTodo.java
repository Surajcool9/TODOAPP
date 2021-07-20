package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.todoapp.databinding.ActivityAddEditTodoBinding;

public class AddEditTodo extends AppCompatActivity {

    private ActivityAddEditTodoBinding binding;
    public static final String EDIT_ID = "EDIT_ID";
    public static final String EDIT_TITLE = "EDIT_TITLE";
    public static final String EDIT_DESC = "EDIT_DESC";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_edit_todo);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24);
        Intent data = getIntent();
        if(data.hasExtra(EDIT_ID)){
            setTitle("Edit Todo");
            binding.titleTodo.setText(data.getStringExtra(EDIT_TITLE));
            binding.descTodo.setText(data.getStringExtra(EDIT_DESC));
        } else {
            setTitle("Add Todo");
        }

        binding.saveTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTodo();
            }
        });

    }

    private void saveTodo() {
        String title = binding.titleTodo.getText().toString();
        String description = binding.descTodo.getText().toString();

        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Please insert title and description", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra(EDIT_TITLE, title);
            intent.putExtra(EDIT_DESC, description);
            int id = getIntent().getIntExtra(EDIT_ID, -1);
            if(id != -1) {
                intent.putExtra(EDIT_ID, id);
            }
            setResult(RESULT_OK, intent);
            finish();
        }
    }

}