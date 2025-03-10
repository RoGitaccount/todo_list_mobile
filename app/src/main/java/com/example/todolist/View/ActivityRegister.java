package com.example.todolist.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.todolist.R;
import com.example.todolist.databinding.ActivityRegisterBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class ActivityRegister extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // bouton de retour
        binding.returnToMainactivity.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityRegister.this, MainActivity.class);
            startActivity(intent);
        });

        // Gestion des clics sur les icônes
        binding.iconEmail.setOnClickListener(v -> startAuthWithProvider(AuthUI.IdpConfig.EmailBuilder.class));
        binding.iconPhone.setOnClickListener(v -> startAuthWithProvider(AuthUI.IdpConfig.PhoneBuilder.class));
        binding.iconGoogle.setOnClickListener(v -> startAuthWithProvider(AuthUI.IdpConfig.GoogleBuilder.class));

        // Inscription classique
        binding.btnSubmitLogin.setOnClickListener(v -> registerWithEmailPassword());
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    startActivity(new Intent(this, ActivityTodo.class));
                    finish();
                } else {
                    IdpResponse response = IdpResponse.fromResultIntent(result.getData());
                    if (response == null) {
                        Toast.makeText(this, "Inscription annulée", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

    private void startAuthWithProvider(Class<?> providerClass) {
        AuthUI.IdpConfig provider = null;

        if (providerClass.equals(AuthUI.IdpConfig.EmailBuilder.class)) {
            provider = new AuthUI.IdpConfig.EmailBuilder().build();
        } else if (providerClass.equals(AuthUI.IdpConfig.PhoneBuilder.class)) {
            provider = new AuthUI.IdpConfig.PhoneBuilder().build();
        } else if (providerClass.equals(AuthUI.IdpConfig.GoogleBuilder.class)) {
            provider = new AuthUI.IdpConfig.GoogleBuilder().build();
        }

        if (provider != null) {
            Intent intent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(Arrays.asList(provider))
                    .setTheme(R.style.Theme_TodoList)
                    .setIsSmartLockEnabled(false)
                    .build();
            signInLauncher.launch(intent);
        }
    }

    private void registerWithEmailPassword() {
        String email = binding.inputEmail.getText().toString().trim();
        String password = binding.inputPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, ActivityTodo.class));
                        finish();
                    } else {
                        Toast.makeText(this, "Erreur : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
