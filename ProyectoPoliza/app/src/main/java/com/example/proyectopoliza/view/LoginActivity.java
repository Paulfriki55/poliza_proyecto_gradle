package com.example.proyectopoliza.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectopoliza.R;
import com.example.proyectopoliza.controller.UserManager;
import com.example.proyectopoliza.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmailLogin, editTextPasswordLogin;
    private Button buttonLogin, buttonRegister;
    private TextView textViewReporte;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmailLogin = findViewById(R.id.editTextEmailLogin);
        editTextPasswordLogin = findViewById(R.id.editTextPasswordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewReporte = findViewById(R.id.textViewReporte);

        userManager = new UserManager(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmailLogin.getText().toString();
                String password = editTextPasswordLogin.getText().toString();

                Usuario usuario = userManager.autenticarUsuario(email, password);
                if (usuario != null) {
                    Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("USER_ID", usuario.getId()); // Pasar ID del usuario
                    intent.putExtra("USER_NAME", usuario.getNombre()); // Pasar nombre del usuario
                    startActivity(intent);
                    finish(); // Cerrar LoginActivity para que no se pueda volver atrás sin cerrar sesión.
                } else {
                    Toast.makeText(LoginActivity.this, "Credenciales inválidas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(intent);
            }
        });

        textViewReporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ReporteActivity.class);
                startActivity(intent);
            }
        });
    }
}