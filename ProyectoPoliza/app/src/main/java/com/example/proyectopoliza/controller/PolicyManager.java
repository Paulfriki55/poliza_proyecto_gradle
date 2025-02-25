package com.example.proyectopoliza.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.proyectopoliza.data.DatabaseHelper;
import com.example.proyectopoliza.model.Poliza;

import java.util.List;

public class PolicyManager {

    private DatabaseHelper dbHelper;

    public PolicyManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long crearPoliza(int usuarioId, String plan, boolean incendio, boolean robo, boolean terremoto, boolean inundacion) {
        double costo = PolicyCalculator.calcularCostoPoliza(plan, incendio, robo, terremoto, inundacion);
        return dbHelper.crearPoliza(usuarioId, plan, costo, incendio, robo, terremoto, inundacion);
    }

    public Poliza obtenerPoliza(int polizaId) {
        Cursor cursor = dbHelper.obtenerPoliza(polizaId);
        Poliza poliza = null;
        if (cursor != null && cursor.moveToFirst()) {
            poliza = new Poliza();
            poliza.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_ID)));
            poliza.setUsuarioId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_USUARIO_ID_FK)));
            poliza.setPlanSeleccionado(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_PLAN)));
            poliza.setCostoPoliza(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_COSTO)));
            poliza.setSeguroIncendio(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_SEGURO_INCENDIO)) == 1);
            poliza.setSeguroRobo(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_SEGURO_ROBO)) == 1);
            poliza.setSeguroTerremoto(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_SEGURO_TERREMOTO)) == 1);
            poliza.setSeguroInundacion(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_POLIZA_SEGURO_INUNDACION)) == 1);
            cursor.close();
        }
        return poliza;
    }

    public List<Poliza> obtenerPolizasPorUsuario(int usuarioId) {
        return dbHelper.obtenerPolizasPorUsuario(usuarioId);
    }

    public boolean actualizarPoliza(int polizaId, String plan, boolean incendio, boolean robo, boolean terremoto, boolean inundacion) {
        double costo = PolicyCalculator.calcularCostoPoliza(plan, incendio, robo, terremoto, inundacion);
        // DatabaseHelper.actualizarPoliza no está modificado para extras en este ejemplo, si necesitas editar extras, debes actualizarlo.
        // Por ahora, solo actualizamos el plan y el costo, asumiendo que los extras no se editan directamente.
        int affectedRows = dbHelper.actualizarPoliza(polizaId, plan, costo);
        return affectedRows > 0;
    }

    public boolean eliminarPoliza(int polizaId) {
        int affectedRows = dbHelper.eliminarPoliza(polizaId); // Get the number of affected rows
        return affectedRows > 0; // Return true if at least one row was deleted
    }
}