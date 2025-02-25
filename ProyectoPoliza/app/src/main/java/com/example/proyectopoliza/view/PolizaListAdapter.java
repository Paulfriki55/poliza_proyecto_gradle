package com.example.proyectopoliza.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopoliza.R;
import com.example.proyectopoliza.controller.PolicyManager;
import com.example.proyectopoliza.model.Poliza;

import java.util.List;

public class PolizaListAdapter extends ArrayAdapter<Poliza> {

    private Context context;
    private List<Poliza> polizas;
    private PolicyManager policyManager;

    public PolizaListAdapter(Context context, List<Poliza> polizas) {
        super(context, 0, polizas);
        this.context = context;
        this.polizas = polizas;
        this.policyManager = new PolicyManager(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_poliza, parent, false);
        }

        Poliza polizaActual = getItem(position);

        TextView planTextView = listItemView.findViewById(R.id.textViewPlanPoliza);
        TextView costoTextView = listItemView.findViewById(R.id.textViewCostoPolizaItem);
        Button buttonEditarPoliza = listItemView.findViewById(R.id.buttonEditarPoliza);
        Button buttonEliminarPoliza = listItemView.findViewById(R.id.buttonEliminarPoliza);

        planTextView.setText("Plan: " + polizaActual.getPlanSeleccionado());
        costoTextView.setText("Costo: $" + String.format("%.2f", polizaActual.getCostoPoliza()));

        buttonEditarPoliza.setOnClickListener(v -> {
            // TODO: Implementar lógica para editar la póliza
            Toast.makeText(context, "Editar póliza ID: " + polizaActual.getId(), Toast.LENGTH_SHORT).show();
            // Puedes abrir un diálogo o una nueva actividad para editar los detalles de la póliza.
        });

        buttonEliminarPoliza.setOnClickListener(v -> {
            boolean deleted = policyManager.eliminarPoliza(polizaActual.getId());
            if (deleted) {
                polizas.remove(position); // Eliminar de la lista local
                notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                Toast.makeText(context, "Póliza eliminada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Error al eliminar la póliza", Toast.LENGTH_SHORT).show();
            }
        });

        return listItemView;
    }
}