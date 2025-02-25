package com.example.proyectopoliza.view;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectopoliza.R;
import com.example.proyectopoliza.controller.PolicyManager;
import com.example.proyectopoliza.model.Poliza;

import java.util.List;

public class ListaPolizasActivity extends AppCompatActivity {

    private ListView listViewPolizasUsuario;
    private PolicyManager policyManager;
    private int usuarioId;
    private PolizaListAdapter polizaListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_polizas);

        listViewPolizasUsuario = findViewById(R.id.listViewPolizasUsuario);
        policyManager = new PolicyManager(this);
        usuarioId = getIntent().getIntExtra("USER_ID", -1);

        if (usuarioId == -1) {
            Toast.makeText(this, "Error: Usuario no identificado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarPolizasUsuario();
    }

    private void cargarPolizasUsuario() {
        List<Poliza> polizas = policyManager.obtenerPolizasPorUsuario(usuarioId);
        if (polizas != null && !polizas.isEmpty()) {
            polizaListAdapter = new PolizaListAdapter(this, polizas);
            listViewPolizasUsuario.setAdapter(polizaListAdapter);
        } else {
            Toast.makeText(this, "No tienes pólizas registradas.", Toast.LENGTH_SHORT).show();
        }
    }
}