package com.example.todolist.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {
    private String id;
    private String titre;
    private String description;
    private String statut;
    private String deadline;

    // Default constructor required for calls to DataSnapshot.getValue(Task.class)
    public Task() {
    }

    public Task(String titre, String description, String statut, String deadline) {
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.deadline = deadline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatut() {
        return statut;
    }

    private boolean isDeadlinePassed() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date deadlineDate = sdf.parse(deadline);
            Date currentDate = new Date();
            return currentDate.after(deadlineDate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}