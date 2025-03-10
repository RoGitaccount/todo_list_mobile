package com.example.todolist.Model;

public class Users {

    // Dans le cas où on n'utilise pas de base de donnée on doit définir les utilisateurs pour la connexion et pour accede à ActivityTodo
    // pour l'inscription les données ne seront pas enregistrer mais si on crée un utilisateurs on accede à ActivityTodo

    private String email;

    private String mot_de_passe;

    public Users(String email, String mot_de_passe) {
        this.email = email;
        this.mot_de_passe = mot_de_passe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }
}
