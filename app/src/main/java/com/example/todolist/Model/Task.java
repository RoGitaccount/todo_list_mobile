package com.example.todolist.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private String titre;
    private String description;
    private String deadline;
    private String statut;

    public Task(String titre, String description, String statut, String deadline) {
        this.titre = titre;
        this.description = description;
        this.statut = statut;
        this.deadline = deadline;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }



    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getStatut() {
        // Vérifier si la date est dépassée
        if (isDeadlinePassed()) {
            return "Date d'échéance dépassée";
        }
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
}


