package com.example.todolist.Repository;

import com.example.todolist.Model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {

    private List<Task> tasks;

    public TaskRepository()
    {
        tasks = new ArrayList<>(); // on crée une liste vide de tâche
        tasks.add(new Task("envoyer des dossier","cette tâche est importante","Encours","12/03/2025"));
        tasks.add(new Task("faire le ménage","pour acceuillir des invités","Faite","12/04/2025"));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addNewTask(Task tache){
        tasks.add(tache);
    }

    public void updateTask(int index,Task tache)
    {
        tasks.set(index,tache);
    }

    public void deleteTask(int index)
    {
        tasks.remove(index);
    }

}
