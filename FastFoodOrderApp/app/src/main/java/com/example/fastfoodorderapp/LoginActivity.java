package com.example.fastfoodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class LoginActivity extends AppCompatActivity {

    EditText etCorreo, etPassword;
    Button btnLogin;
    TextView tvCrearCuenta;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("sesion", MODE_PRIVATE);
        boolean logueado = prefs.getBoolean("logueado", false);

        if (logueado) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
            return;
        }

        boolean dark = prefs.getBoolean("dark_mode", false);

        if (dark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_login);

        etCorreo = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);

        btnLogin = findViewById(R.id.btnLogin);
        tvCrearCuenta = findViewById(R.id.tvCrearCuenta);

        databaseHelper = new DatabaseHelper(this);

        // LOGIN
        btnLogin.setOnClickListener(v -> {

            String correo = etCorreo.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (correo.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                        this,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            boolean loginCorrecto =
                    databaseHelper.loginUsuario(correo, password);

            if (loginCorrecto) {

                getSharedPreferences("sesion", MODE_PRIVATE)
                        .edit()
                        .putBoolean("logueado", true)
                        .apply();

                Toast.makeText(
                        this,
                        "Inicio de sesión exitoso ✅",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(LoginActivity.this, MainActivity.class);

                startActivity(intent);
                finish();

            } else {

                Toast.makeText(
                        this,
                        "Correo o contraseña incorrectos ❌",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // IR A REGISTER
        tvCrearCuenta.setOnClickListener(v -> {

            Intent intent =
                    new Intent(LoginActivity.this, RegisterActivity.class);

            startActivity(intent);
        });
    }
}