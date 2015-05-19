package com.example.abel.materialloanbasedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by abel on 18/05/2015.
 */
public class ConexionSQL extends SQLiteOpenHelper {
    public ConexionSQL(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BD) {
        BD.execSQL("create table prestamos (clave_prestamo integer primary key unique, fecha text, nombre_sol text, area_sol text, descripcion text, recibido text, entregado text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase BD, int oldVersion, int newVersion) {
        BD.execSQL("drop table if exists prestamos");
        BD.execSQL("create table prestamos (clave_prestamo integer primary key unique, fecha text, nombre_sol text, area_sol text, descripcion text, recibido text, entregado text)");
    }
}
