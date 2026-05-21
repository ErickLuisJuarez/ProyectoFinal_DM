package com.example.fastfoodorderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etNombreRegistro,etCorreoRegistro, etPasswordRegistro;
    Button btnRegistrar;
    TextView tvLogin;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombreRegistro =
                findViewById(R.id.etNombreRegistro);

        etCorreoRegistro =
                findViewById(R.id.etCorreoRegistro);

        etPasswordRegistro =
                findViewById(R.id.etPasswordRegistro);

        btnRegistrar =
                findViewById(R.id.btnRegistrar);

        tvLogin =
                findViewById(R.id.tvLogin);

        databaseHelper = new DatabaseHelper(this);

        // REGISTRAR
        btnRegistrar.setOnClickListener(v -> {

            String nombre =
                    etNombreRegistro.getText().toString().trim();

            String correo =
                    etCorreoRegistro.getText().toString().trim();

            String password =
                    etPasswordRegistro.getText().toString().trim();

            if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {

                Toast.makeText(
                        this,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            boolean registrado =
                    databaseHelper.registrarUsuario(
                            nombre,
                            correo,
                            password
                    );

            if (registrado) {

                Toast.makeText(
                        this,
                        "Cuenta creada correctamente ✅",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent =
                        new Intent(
                                RegisterActivity.this,
                                LoginActivity.class
                        );

                startActivity(intent);

                finish();

            } else {

                Toast.makeText(
                        this,
                        "Error al registrar usuario ❌",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // VOLVER AL LOGIN
        tvLogin.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    );

            startActivity(intent);

            finish();
        });
    }
}