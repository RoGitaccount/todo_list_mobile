// filepath: /mnt/c/Users/r6717/AndroidStudioProjects/TodoList/app/src/main/java/com/example/todolist/View/ActivityAdd.java
package com.example.todolist.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.Model.Task;
import com.example.todolist.R;
import com.example.todolist.ViewModel.TaskViewModel;
import com.example.todolist.databinding.ActivityAddBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class ActivityAdd extends AppCompatActivity {

    private ActivityAddBinding binding;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        // Initialize DatePicker from layout
        Calendar today = Calendar.getInstance();
        binding.DateSelector.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                (view, year, month, day) -> {
                    String msg = "Date Sélectionnée: " + day + "/" + (month + 1) + "/" + year;
                    Toast.makeText(ActivityAdd.this, msg, Toast.LENGTH_SHORT).show();
                }
        );

        // Add task button click listener
        binding.btnAddTask.setOnClickListener(v -> {
            String title = binding.inputTaskTitle.getText().toString();
            String description = binding.inputTaskDescription.getText().toString();
            int day = binding.DateSelector.getDayOfMonth();
            int month = binding.DateSelector.getMonth() + 1;
            int year = binding.DateSelector.getYear();
            String deadline = day + "/" + month + "/" + year;
            String status = "En cours";

            if (title.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                Task newTask = new Task(title, description, status, deadline);
                taskViewModel.addTask(newTask);

                Toast.makeText(this, "Tâche ajoutée", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ActivityAdd.this, ActivityTodo.class));
                finish();
            }
        });

        // redirections des menus
        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu) {
                startActivity(new Intent(this, ActivityTodo.class));
                return true;
            } else if (item.getItemId() == R.id.add) {
                return true;
            }
            return false;
        });
    }
}