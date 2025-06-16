package com.example.diplom.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.diplom.MainActivity;
import com.example.diplom.data.UserRepository;
import com.example.diplom.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private ActivityRegisterBinding binding;
    private FirebaseAuth mAuth;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        userRepository = new UserRepository();

        binding.registerButton.setOnClickListener(v -> {
            String name = binding.nameEditText.getText().toString().trim();
            String email = binding.emailEditText.getText().toString().trim();
            String password = binding.passwordEditText.getText().toString().trim();
            String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                binding.nameEditText.setError("Введите имя");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                binding.emailEditText.setError("Введите email");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                binding.passwordEditText.setError("Введите пароль");
                return;
            }

            if (TextUtils.isEmpty(confirmPassword)) {
                binding.confirmPasswordEditText.setError("Подтвердите пароль");
                return;
            }

            if (!password.equals(confirmPassword)) {
                binding.confirmPasswordEditText.setError("Пароли не совпадают");
                return;
            }

            registerUser(name, email, password);
        });

        binding.loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void registerUser(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            // Здесь можно добавить сохранение имени пользователя в базу данных
                            Toast.makeText(RegisterActivity.this, "Регистрация успешна", 
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Ошибка регистрации: " + 
                                task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
} 