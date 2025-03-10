package com.example.todolist.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.Model.Task;
import com.example.todolist.Repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends ViewModel {

    private final TaskRepository repository;
    private final MutableLiveData<List<Task>> tasks;

    public TaskViewModel()
    {
        repository = new TaskRepository();
        tasks = new MutableLiveData<>(repository.getTasks());
    }

    public LiveData<List<Task>> getTasks()
    {
        return tasks;
    }

    public void addTask(Task tache)
    {
        repository.addNewTask(tache);
        tasks.setValue(repository.getTasks());
    }

    public void updateTask(int index,Task tache)
    {
        repository.updateTask(index,tache);
        tasks.setValue(repository.getTasks());
    }
    public void deleteTask(int index)
    {
        repository.deleteTask(index);
        tasks.setValue(repository.getTasks());
    }
}
