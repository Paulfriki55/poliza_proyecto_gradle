package com.example.proyectopoliza.controller;

import android.content.Context;
import android.database.Cursor;

import com.example.proyectopoliza.data.DatabaseHelper;
import com.example.proyectopoliza.model.Usuario;

public class UserManager {

    private DatabaseHelper dbHelper;

    public UserManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long registrarUsuario(String nombre, String email, String password) {
        return dbHelper.crearUsuario(nombre, email, password);
    }

    public Usuario autenticarUsuario(String email, String password) {
        Cursor cursor = dbHelper.obtenerUsuario(email);
        Usuario usuario = null;
        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_PASSWORD));
            if (password.equals(storedPassword)) { // ¡No seguro para producción! Usar hashing.
                usuario = new Usuario();
                usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_ID)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_NOMBRE)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_EMAIL)));
                usuario.setPassword(storedPassword);
            }
            cursor.close();
        }
        return usuario;
    }

    public Usuario obtenerUsuarioPorEmail(String email) {
        Cursor cursor = dbHelper.obtenerUsuario(email);
        Usuario usuario = null;
        if (cursor != null && cursor.moveToFirst()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_ID)));
            usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_NOMBRE)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_EMAIL)));
            usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USUARIO_PASSWORD)));
            cursor.close();
        }
        return usuario;
    }

    public boolean actualizarUsuario(int id, String nombre, String password) {
        int affectedRows = dbHelper.actualizarUsuario(id, nombre, password);
        return affectedRows > 0;
    }

    public boolean eliminarUsuario(int id) {
        dbHelper.eliminarUsuario(id);
        return true; // Suponiendo que la eliminación siempre tiene éxito.
    }
}