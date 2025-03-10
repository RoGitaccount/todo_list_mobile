package com.example.todolist.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.Model.Task;
import com.example.todolist.Repository.TaskRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends ViewModel {

    private TaskRepository taskRepository;
    private MutableLiveData<List<Task>> tasksLiveData;

    public TaskViewModel() {
        taskRepository = new TaskRepository();
        tasksLiveData = new MutableLiveData<>();
        loadTasks();
    }

    public LiveData<List<Task>> getTasks() {
        return tasksLiveData;
    }

    private void loadTasks() {
        taskRepository.fetchTasks(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Task> tasks = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Task task = snapshot.getValue(Task.class);
                    if (task != null) {
                        task.setId(snapshot.getKey());
                        tasks.add(task);
                    }
                }
                tasksLiveData.setValue(tasks);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("Firebase", "Erreur lecture tâches", error.toException());
            }
        });
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
        loadTasks();
    }

    public void updateTask(String taskId, Task task) {
        taskRepository.updateTask(taskId, task);
        loadTasks();
    }

    public void deleteTask(String taskId) {
        taskRepository.deleteTask(taskId);
        loadTasks();
    }

    public void fetchTasks(ValueEventListener listener) {
        taskRepository.fetchTasks(listener);
    }
}