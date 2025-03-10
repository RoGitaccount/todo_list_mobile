package com.example.todolist.View;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.R;
import com.example.todolist.databinding.ActivityRegisterBinding;

public class ActivityRegister extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // bouton de retour
        binding.returnToMainactivity.setOnClickListener(v->
        {
            // Redirect to ActivityRegister
            Intent intent = new Intent(ActivityRegister.this, MainActivity.class);
            startActivity(intent);
        });

    }
}