package com.example.proyectopoliza.view;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectopoliza.R;
import com.example.proyectopoliza.data.DatabaseHelper;
import com.example.proyectopoliza.model.Poliza;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ReporteActivity extends AppCompatActivity {

    private TextView textViewTotalUsuarios, textViewTotalPolizas, textViewTotalIngresos;
    private ListView listViewPolizasReporte;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        textViewTotalUsuarios = findViewById(R.id.textViewTotalUsuarios);
        textViewTotalPolizas = findViewById(R.id.textViewTotalPolizas);
        textViewTotalIngresos = findViewById(R.id.textViewTotalIngresos);
        listViewPolizasReporte = findViewById(R.id.listViewPolizasReporte);

        dbHelper = new DatabaseHelper(this);

        int totalUsuarios = dbHelper.getTotalUsuarios();
        int totalPolizas = dbHelper.getTotalPolizas();
        double totalIngresos = dbHelper.getTotalIngresosPolizas();

        textViewTotalUsuarios.setText("Total Usuarios: " + totalUsuarios);
        textViewTotalPolizas.setText("Total Pólizas: " + totalPolizas);
        textViewTotalIngresos.setText("Ingresos Totales: $" + String.format(Locale.getDefault(), "%.2f", totalIngresos));

        List<Poliza> polizas = obtenerTodasLasPolizasParaReporte();
        if (polizas != null && !polizas.isEmpty()) {
            List<String> polizaStrings = new ArrayList<>();
            for (Poliza poliza : polizas) {
                String polizaInfo = "Usuario ID: " + poliza.getUsuarioId() +
                        ", Plan: " + poliza.getPlanSeleccionado() +
                        ", Extras: " + getExtrasAsString(poliza) + // Nueva función para formatear extras
                        ", Costo: $" + poliza.getCostoPoliza();
                polizaStrings.add(polizaInfo);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, polizaStrings);
            listViewPolizasReporte.setAdapter(adapter);
        } else {
            textViewTotalPolizas.setText("Total Pólizas: 0");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[]{"No hay pólizas registradas."});
            listViewPolizasReporte.setAdapter(adapter);
        }
    }

    private String getExtrasAsString(Poliza poliza) {
        StringBuilder extras = new StringBuilder();
        if (poliza.isSeguroIncendio()) extras.append("Incendio, ");
        if (poliza.isSeguroRobo()) extras.append("Robo, ");
        if (poliza.isSeguroTerremoto()) extras.append("Terremoto, ");
        if (poliza.isSeguroInundacion()) extras.append("Inundación, ");

        if (extras.length() > 0) {
            extras.delete(extras.length() - 2, extras.length()); // Eliminar la última coma y espacio
        } else {
            extras.append("Ninguno");
        }
        return extras.toString();
    }


    private List<Poliza> obtenerTodasLasPolizasParaReporte() {
        return dbHelper.obtenerTodasLasPolizas(); // Usar el nuevo método de DatabaseHelper
    }
}