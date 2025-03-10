package com.example.todolist.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todolist.R;
import com.example.todolist.View.ActivityLogin;
import com.example.todolist.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // bouton pour se connecter
        binding.btnLogin.setOnClickListener(v->
        {
            // Redirect to ActivityLogin
            Intent intent = new Intent(MainActivity.this, ActivityLogin.class);
            startActivity(intent);
        });

        // bouton pour s'inscrire
        binding.btnRegister.setOnClickListener(v->
        {
            // Redirect to ActivityRegister
            Intent intent = new Intent(MainActivity.this, ActivityRegister.class);
            startActivity(intent);
        });
    }
}