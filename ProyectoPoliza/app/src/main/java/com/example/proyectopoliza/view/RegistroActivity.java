package com.example.proyectopoliza.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectopoliza.R;
import com.example.proyectopoliza.controller.UserManager;

public class RegistroActivity extends AppCompatActivity {

    private EditText editTextNombreRegistro, editTextEmailRegistro, editTextPasswordRegistro;
    private Button buttonRegistrar;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        editTextNombreRegistro = findViewById(R.id.editTextNombreRegistro);
        editTextEmailRegistro = findViewById(R.id.editTextEmailRegistro);
        editTextPasswordRegistro = findViewById(R.id.editTextPasswordRegistro);
        buttonRegistrar = findViewById(R.id.buttonRegistrar);

        userManager = new UserManager(this);

        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editTextNombreRegistro.getText().toString();
                String email = editTextEmailRegistro.getText().toString();
                String password = editTextPasswordRegistro.getText().toString();

                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                long userId = userManager.registrarUsuario(nombre, email, password);
                if (userId != -1) {
                    Toast.makeText(RegistroActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish(); // Volver a LoginActivity después del registro exitoso
                } else {
                    Toast.makeText(RegistroActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}