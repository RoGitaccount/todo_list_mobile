package com.example.todolist.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.Model.Task;
import com.example.todolist.R;
import com.example.todolist.ViewModel.TaskViewModel;
import com.example.todolist.databinding.ActivityEditBinding;

public class ActivityEdit extends AppCompatActivity {

    private ActivityEditBinding binding;
    private TaskViewModel taskViewModel;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        Intent intent = getIntent();
        if (intent != null) {
            taskId = intent.getStringExtra("taskId");
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String deadline = intent.getStringExtra("deadline");
            String status = intent.getStringExtra("status");

            binding.inputEditTaskTitle.setText(title);
            binding.inputEditTaskDescription.setText(description);
            // Set the date in DatePicker
            String[] dateParts = deadline.split("/");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int year = Integer.parseInt(dateParts[2]);
            binding.editDateSelector.updateDate(year, month, day);

            // Set the status in Spinner
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.liste_statuts, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerStatus.setAdapter(adapter);
            if (status != null) {
                int spinnerPosition = adapter.getPosition(status);
                binding.spinnerStatus.setSelection(spinnerPosition);
            }
        }

        binding.btnEditTask.setOnClickListener(v -> {
            String title = binding.inputEditTaskTitle.getText().toString();
            String description = binding.inputEditTaskDescription.getText().toString();
            int day = binding.editDateSelector.getDayOfMonth();
            int month = binding.editDateSelector.getMonth() + 1;
            int year = binding.editDateSelector.getYear();
            String deadline = day + "/" + month + "/" + year;
            String status = binding.spinnerStatus.getSelectedItem().toString();

            if (title.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                Task updatedTask = new Task(title, description, status, deadline);
                taskViewModel.updateTask(taskId, updatedTask);

                Toast.makeText(this, "Tâche mise à jour", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ActivityEdit.this, ActivityTodo.class));
                finish();
            }
        });
    }
}