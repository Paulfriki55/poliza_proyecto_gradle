package com.example.proyectopoliza.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.proyectopoliza.model.Poliza;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PolizasDB";
    private static final int DATABASE_VERSION = 2; // Incrementa la versión de la base de datos

    // Nombre de las tablas
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_POLIZAS = "polizas";

    // Columnas de la tabla usuarios
    public static final String COLUMN_USUARIO_ID = "id_usuario";
    public static final String COLUMN_USUARIO_NOMBRE = "nombre_usuario";
    public static final String COLUMN_USUARIO_EMAIL = "email_usuario";
    public static final String COLUMN_USUARIO_PASSWORD = "password_usuario";

    // Columnas de la tabla polizas
    public static final String COLUMN_POLIZA_ID = "id_poliza";
    public static final String COLUMN_POLIZA_USUARIO_ID_FK = "id_usuario_fk";
    public static final String COLUMN_POLIZA_PLAN = "plan_seleccionado";
    public static final String COLUMN_POLIZA_COSTO = "costo_poliza";
    public static final String COLUMN_POLIZA_SEGURO_INCENDIO = "seguro_incendio";
    public static final String COLUMN_POLIZA_SEGURO_ROBO = "seguro_robo";
    public static final String COLUMN_POLIZA_SEGURO_TERREMOTO = "seguro_terremoto";
    public static final String COLUMN_POLIZA_SEGURO_INUNDACION = "seguro_inundacion";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla usuarios (sin cambios)
        String CREATE_USUARIOS_TABLE = "CREATE TABLE " + TABLE_USUARIOS + "("
                + COLUMN_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USUARIO_NOMBRE + " TEXT NOT NULL,"
                + COLUMN_USUARIO_EMAIL + " TEXT UNIQUE NOT NULL,"
                + COLUMN_USUARIO_PASSWORD + " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_USUARIOS_TABLE);

        // Crear tabla polizas (con nuevas columnas para seguros extras)
        String CREATE_POLIZAS_TABLE = "CREATE TABLE " + TABLE_POLIZAS + "("
                + COLUMN_POLIZA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_POLIZA_USUARIO_ID_FK + " INTEGER NOT NULL,"
                + COLUMN_POLIZA_PLAN + " TEXT NOT NULL,"
                + COLUMN_POLIZA_COSTO + " REAL NOT NULL,"
                + COLUMN_POLIZA_SEGURO_INCENDIO + " INTEGER NOT NULL DEFAULT 0," // 0 para false, 1 para true
                + COLUMN_POLIZA_SEGURO_ROBO + " INTEGER NOT NULL DEFAULT 0,"
                + COLUMN_POLIZA_SEGURO_TERREMOTO + " INTEGER NOT NULL DEFAULT 0,"
                + COLUMN_POLIZA_SEGURO_INUNDACION + " INTEGER NOT NULL DEFAULT 0,"
                + "FOREIGN KEY(" + COLUMN_POLIZA_USUARIO_ID_FK + ") REFERENCES " + TABLE_USUARIOS + "(" + COLUMN_USUARIO_ID + ")" + ")";
        db.execSQL(CREATE_POLIZAS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar tablas antiguas si existen
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLIZAS);
        // Crear tablas nuevamente
        onCreate(db);
    }

    // --- Métodos CRUD para Usuarios --- (Sin cambios)
    public long crearUsuario(String nombre, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_NOMBRE, nombre);
        values.put(COLUMN_USUARIO_EMAIL, email);
        values.put(COLUMN_USUARIO_PASSWORD, password);
        long id = db.insert(TABLE_USUARIOS, null, values);
        db.close();
        return id;
    }

    public Cursor obtenerUsuario(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USUARIOS,
                new String[]{COLUMN_USUARIO_ID, COLUMN_USUARIO_NOMBRE, COLUMN_USUARIO_EMAIL, COLUMN_USUARIO_PASSWORD},
                COLUMN_USUARIO_EMAIL + "=?",
                new String[]{email}, null, null, null, null);
    }

    public int actualizarUsuario(int id, String nombre, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USUARIO_NOMBRE, nombre);
        values.put(COLUMN_USUARIO_PASSWORD, password);
        return db.update(TABLE_USUARIOS, values, COLUMN_USUARIO_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public void eliminarUsuario(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USUARIOS, COLUMN_USUARIO_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // --- Métodos CRUD para Pólizas --- (Actualizados para incluir seguros extras)
    public long crearPoliza(int usuarioId, String plan, double costo, boolean incendio, boolean robo, boolean terremoto, boolean inundacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POLIZA_USUARIO_ID_FK, usuarioId);
        values.put(COLUMN_POLIZA_PLAN, plan);
        values.put(COLUMN_POLIZA_COSTO, costo);
        values.put(COLUMN_POLIZA_SEGURO_INCENDIO, incendio ? 1 : 0);
        values.put(COLUMN_POLIZA_SEGURO_ROBO, robo ? 1 : 0);
        values.put(COLUMN_POLIZA_SEGURO_TERREMOTO, terremoto ? 1 : 0);
        values.put(COLUMN_POLIZA_SEGURO_INUNDACION, inundacion ? 1 : 0);
        long id = db.insert(TABLE_POLIZAS, null, values);
        db.close();
        return id;
    }

    public Cursor obtenerPoliza(int polizaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_POLIZAS,
                new String[]{COLUMN_POLIZA_ID, COLUMN_POLIZA_USUARIO_ID_FK, COLUMN_POLIZA_PLAN, COLUMN_POLIZA_COSTO,
                        COLUMN_POLIZA_SEGURO_INCENDIO, COLUMN_POLIZA_SEGURO_ROBO, COLUMN_POLIZA_SEGURO_TERREMOTO, COLUMN_POLIZA_SEGURO_INUNDACION},
                COLUMN_POLIZA_ID + "=?",
                new String[]{String.valueOf(polizaId)}, null, null, null, null);
    }

    public List<Poliza> obtenerPolizasPorUsuario(int usuarioId) {
        List<Poliza> polizas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POLIZAS,
                new String[]{COLUMN_POLIZA_ID, COLUMN_POLIZA_PLAN, COLUMN_POLIZA_COSTO,
                        COLUMN_POLIZA_SEGURO_INCENDIO, COLUMN_POLIZA_SEGURO_ROBO, COLUMN_POLIZA_SEGURO_TERREMOTO, COLUMN_POLIZA_SEGURO_INUNDACION},
                COLUMN_POLIZA_USUARIO_ID_FK + "=?",
                new String[]{String.valueOf(usuarioId)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Poliza poliza = new Poliza();
                poliza.setId(Integer.parseInt(cursor.getString(0)));
                poliza.setPlanSeleccionado(cursor.getString(1));
                poliza.setCostoPoliza(Double.parseDouble(cursor.getString(2)));
                poliza.setSeguroIncendio(cursor.getInt(3) == 1);
                poliza.setSeguroRobo(cursor.getInt(4) == 1);
                poliza.setSeguroTerremoto(cursor.getInt(5) == 1);
                poliza.setSeguroInundacion(cursor.getInt(6) == 1);
                polizas.add(poliza);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return polizas;
    }


    public int actualizarPoliza(int id, String plan, double costo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_POLIZA_PLAN, plan);
        values.put(COLUMN_POLIZA_COSTO, costo);
        return db.update(TABLE_POLIZAS, values, COLUMN_POLIZA_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public int eliminarPoliza(int id) { // Modificado para retornar int (sin cambios)
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = db.delete(TABLE_POLIZAS, COLUMN_POLIZA_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return affectedRows; // Retorna el número de filas eliminadas
    }

    // --- Métodos para Reporte --- (Sin cambios)
    public int getTotalUsuarios() {
        String countQuery = "SELECT  * FROM " + TABLE_USUARIOS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public int getTotalPolizas() {
        String countQuery = "SELECT  * FROM " + TABLE_POLIZAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public double getTotalIngresosPolizas() {
        String sumQuery = "SELECT SUM(" + COLUMN_POLIZA_COSTO + ") FROM " + TABLE_POLIZAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sumQuery, null);
        double totalIngresos = 0;
        if (cursor.moveToFirst()) {
            totalIngresos = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return totalIngresos;
    }

    public List<Poliza> obtenerTodasLasPolizas() {
        List<Poliza> polizas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POLIZAS,
                new String[]{COLUMN_POLIZA_ID, COLUMN_POLIZA_USUARIO_ID_FK, COLUMN_POLIZA_PLAN, COLUMN_POLIZA_COSTO,
                        COLUMN_POLIZA_SEGURO_INCENDIO, COLUMN_POLIZA_SEGURO_ROBO, COLUMN_POLIZA_SEGURO_TERREMOTO, COLUMN_POLIZA_SEGURO_INUNDACION},
                null, null, null, null, null); // Null for selection and selectionArgs to get all rows

        if (cursor.moveToFirst()) {
            do {
                Poliza poliza = new Poliza();
                poliza.setId(Integer.parseInt(cursor.getString(0)));
                poliza.setUsuarioId(Integer.parseInt(cursor.getString(1)));
                poliza.setPlanSeleccionado(cursor.getString(2));
                poliza.setCostoPoliza(Double.parseDouble(cursor.getString(3)));
                poliza.setSeguroIncendio(cursor.getInt(4) == 1);
                poliza.setSeguroRobo(cursor.getInt(5) == 1);
                poliza.setSeguroTerremoto(cursor.getInt(6) == 1);
                poliza.setSeguroInundacion(cursor.getInt(7) == 1);
                polizas.add(poliza);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return polizas;
    }
}