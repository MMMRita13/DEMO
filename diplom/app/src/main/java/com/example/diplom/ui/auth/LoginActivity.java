package com.example.diplom.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diplom.MainActivity;
import com.example.diplom.data.UserRepository;
import com.example.diplom.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        userRepository = new UserRepository();

        binding.loginButton.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                binding.emailEditText.setError("Введите email");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                binding.passwordEditText.setError("Введите пароль");
                return;
            }

            loginUser(email, password);
        });

        binding.registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        
                        // Проверяем роль пользователя
                        userRepository.checkUserRole(isAdmin -> {
                            Log.d(TAG, "User is admin: " + isAdmin);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("isAdmin", isAdmin);
                            startActivity(intent);
                            finish();
                        });
                    } else {
                        Toast.makeText(LoginActivity.this, "Ошибка: Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
} 