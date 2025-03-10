package com.example.todolist.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.todolist.Model.Task;
import com.example.todolist.ViewModel.TaskViewModel;
import com.example.todolist.databinding.ActivityAddBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.todolist.R; // Importez le fichier R pour accéder aux identifiants de menu

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
        DatePicker datePicker = binding.DateSelector;

        // Get today's date using Calendar instance
        Calendar today = Calendar.getInstance();

        // Initialize DatePicker with the current date
        datePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                (view, year, month, day) ->
                {
                    String msg = "Date Sélectionnée: " + day + "/" + (month + 1) + "/" + year;
                    Toast.makeText(ActivityAdd.this, msg, Toast.LENGTH_SHORT).show();
                }
        );

        // redirections des menus
        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                // Handle logout action
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu) {
                startActivity(new Intent(this, ActivityTodo.class));
                return true;
            } else if (item.getItemId() == R.id.add) {
                // Handle add action
                return true;
            }
            return false;
        });

        // Add task button click listener
        binding.btnAddTask.setOnClickListener(v -> {
            String title = binding.inputTaskTitle.getText().toString();
            String description = binding.inputTaskDescription.getText().toString();
            // Récupérer la date sélectionnée dans le DatePicker
            int day = binding.DateSelector.getDayOfMonth();
            int month = binding.DateSelector.getMonth() + 1;  // Les mois commencent à 0 donc on ajoute +1
            int year = binding.DateSelector.getYear();
            String deadline = day + "/" + month + "/" + year;
            String status = "En cours";

            if (title.isEmpty() || description.isEmpty() || deadline.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                Task newTask = new Task(title, description, deadline, status);
                taskViewModel.addTask(newTask);
                saveTaskToSharedPreferences(newTask);

                Toast.makeText(this, "Tâche ajoutée", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ActivityAdd.this, ActivityTodo.class);
                startActivity(intent);
                finish();


//                // Redirection vers ActivityTodo après ajout
//                Intent intent = new Intent(ActivityAdd.this, ActivityTodo.class);
//                intent.putExtra("task_added", true);
//                intent.putExtra("title", title);
//                intent.putExtra("deadline", deadline);
//                intent.putExtra("description", description);
//                startActivity(intent);
//                finish();  // Ferme l'activité actuelle pour éviter de revenir en arrière
            }
        });
    }

    private void saveTaskToSharedPreferences(Task task) {

        SharedPreferences prefs = getSharedPreferences("TaskPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // Récupérer les tâches existantes
        String existingTasks = prefs.getString("tasks", "");
        String newTaskString = task.getTitre() + ";" + task.getDescription() + ";" + task.getDeadline() + ";" + task.getStatut();

        // Ajouter la nouvelle tâche
        String updatedTasks = existingTasks.isEmpty() ? newTaskString : existingTasks + "|" + newTaskString;
        editor.putString("tasks", updatedTasks);
        editor.apply();
    }
}
