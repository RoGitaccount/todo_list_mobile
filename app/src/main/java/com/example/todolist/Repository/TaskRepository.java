package com.example.todolist.Repository;

import com.example.todolist.Model.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskRepository {
    private final DatabaseReference dbRef;
    private final FirebaseAuth auth;
    private String uid;

    public TaskRepository() {
        auth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase
                .getInstance("https://mobile-todolist-81116-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("users");
        uid = auth.getCurrentUser().getUid();
    }

    public void fetchTasks(ValueEventListener listener) {
        dbRef.child(uid).child("tasks").addValueEventListener(listener);
    }

    public void addTask(Task task) {
        if (isDeadlinePassed(task.getDeadline(), task.getStatut())) {
            task.setStatut("Date d'échéance dépassée");
        }
        String key = dbRef.child(uid).child("tasks").push().getKey();
        if (key != null) {
            dbRef.child(uid).child("tasks").child(key).setValue(task);
        }
    }

    public void updateTask(String taskId, Task task) {
        if (isDeadlinePassed(task.getDeadline(), task.getStatut())) {
            task.setStatut("Date d'échéance dépassée");
        }
        dbRef.child(uid).child("tasks").child(taskId).setValue(task);
    }

    public void deleteTask(String taskId) {
        dbRef.child(uid).child("tasks").child(taskId).removeValue();
    }

    private boolean isDeadlinePassed(String deadline, String statut) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date taskDate = sdf.parse(deadline);
            Date currentDate = new Date();
            if (taskDate != null && taskDate.before(currentDate) && "En cours".equalsIgnoreCase(statut)) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}