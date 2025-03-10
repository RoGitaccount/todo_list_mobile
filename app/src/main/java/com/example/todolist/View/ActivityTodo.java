package com.example.todolist.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Model.Task;
import com.example.todolist.R;
import com.example.todolist.TaskAdapter;
import com.example.todolist.View.ActivityAdd;
import com.example.todolist.View.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class ActivityTodo extends AppCompatActivity {

    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;  // Adapter persistant

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialiser l'adapter avec la liste persistante
        adapter = new TaskAdapter(taskList, position -> {
            adapter.removeItem(position);
            saveTasksToSharedPreferences();  // Sauvegarder les tâches après suppression
        });

        recyclerView.setAdapter(adapter);

        // Ajouter les tâches en dur seulement au premier lancement
        // Vérifier si c'est le premier lancement avec SharedPreferences
        SharedPreferences prefs = getSharedPreferences("TaskPrefs", MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);

        if (isFirstLaunch) {
            // Ajouter les valeurs initiales codées en dur uniquement la première fois
            taskList.add(new Task("envoyer des dossiers", "cette tâche est importante", "En cours", "17/04/2025"));
            taskList.add(new Task("faire le ménage", "pour accueillir des invités", "Faite", "12/03/2025"));
            taskList.add(new Task("faire des courses", "pour la semaine", "Date d'échéance dépassée", "9/03/2025"));

            // Sauvegarder ces tâches initiales
            saveTasksToSharedPreferences();

            // Mettre à jour le statut de premier lancement
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirstLaunch", false);
            editor.apply();
        } else {
            // Charger les tâches depuis SharedPreferences si ce n'est pas le premier lancement
            loadTasksFromSharedPreferences();
        }

//        // Ajouter les tâches en dur seulement au premier lancement
//        if (savedInstanceState == null) {  // Vérifie si l'activité est créée pour la première fois
//            taskList.add(new Task("envoyer des dossiers", "cette tâche est importante", "En cours", "17/04/2025"));
//            taskList.add(new Task("faire le ménage", "pour accueillir des invités", "Faite", "12/03/2025"));
//            taskList.add(new Task("faire des courses", "pour la semaine", "Date d'échéance dépassée", "9/03/2025"));
//        }


        adapter.notifyDataSetChanged();


//        List<Task> taskList = new ArrayList<>();
//        taskList.add(new Task("envoyer des dossier", "cette tâche est importante", "En cours", "17/04/2025"));
//        taskList.add(new Task("faire le ménage", "pour acceuillir des invités", "faite", "12/03/2025"));
//        taskList.add(new Task("rendre le projet", "car on risque une pénalité", "date d'echéance dépassé", "9/03/2025"));

//        com.example.todolist.TaskAdapter adapter = new com.example.todolist.TaskAdapter(taskList);
//        recyclerView.setAdapter(adapter);

//        List<Task> taskList = new ArrayList<>();
//        com.example.todolist.TaskAdapter adapter = new com.example.todolist.TaskAdapter(taskList);
//        recyclerView.setAdapter(adapter);



        // Vérifier si une tâche a été ajoutée
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("task_added", false)) {
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            String deadline = intent.getStringExtra("deadline");
            String status = "En cours";  // Statut par défaut

            taskList.add(new Task(title, description, status, deadline));
            adapter.notifyDataSetChanged();  // Rafraîchir la RecyclerView
            saveTasksToSharedPreferences();  // Sauvegarder les tâches après ajout
        }


        // Navigation du menu
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.logout) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu) {
                return true;
            } else if (item.getItemId() == R.id.add) {
                startActivity(new Intent(this, ActivityAdd.class));
                return true;
            }
            return false;
        });
    }

    //Fonction pour charger les tâches depuis SharedPreferences
    private void loadTasksFromSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("TaskPrefs", MODE_PRIVATE);
        String savedTasks = prefs.getString("tasks", "");

        if (!savedTasks.isEmpty()) {
            String[] tasksArray = savedTasks.split("\\|");
            for (String taskString : tasksArray) {
                String[] taskData = taskString.split(";");
                if (taskData.length == 4) {
                    String title = taskData[0];
                    String description = taskData[1];
                    String status = taskData[2];
                    String deadline = taskData[3];
                    taskList.add(new Task(title, description, status, deadline));
                }
            }
        }
    }

    // Fonction pour sauvegarder les tâches dans SharedPreferences
    private void saveTasksToSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("TaskPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        StringBuilder savedTasks = new StringBuilder();
        for (Task task : taskList) {
            savedTasks.append(task.getTitre()).append(";")
                    .append(task.getDescription()).append(";")
                    .append(task.getStatut()).append(";")
                    .append(task.getDeadline()).append("|");
        }

        // Supprimer le dernier caractère "|" s'il existe
        if (savedTasks.length() > 0) {
            savedTasks.setLength(savedTasks.length() - 1);
        }

        editor.putString("tasks", savedTasks.toString());
        editor.apply();  // Sauvegarder les changements
    }
}




