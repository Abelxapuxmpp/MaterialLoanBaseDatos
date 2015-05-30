package com.example.abel.materialloanbasedatos;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class operations extends ActionBarActivity {

    //Tipos de variables utilizadas en la actividad
    EditText edt_clave,edtfecha, edt_nombre_sol, edt_area_sol, edtdescripcion, recibido, entregado;
    CheckBox ck_recibido, ck_entregado;
    Button seleccionar, guardar, buscar, editar, eliminar, limpiar;
    //Variabes para calendario
    int a,m,d;
    //Variables para respuestas de los chekbox
    String rec, ent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

        //Enlazar variables con los elementos graficos correspondientes
        edt_clave = (EditText) findViewById(R.id.edtclave);
        edtfecha = (EditText) findViewById(R.id.edtfecha);
        edt_nombre_sol = (EditText) findViewById(R.id.edtnombre);
        edt_area_sol = (EditText) findViewById(R.id.edtcarrera);
        edtdescripcion = (EditText) findViewById(R.id.edtdescripcion);
        ck_recibido = (CheckBox) findViewById(R.id.rdrecibido);
        recibido = (EditText) findViewById(R.id.ck1);
        ck_entregado = (CheckBox) findViewById(R.id.rdrentregado);
        entregado = (EditText) findViewById(R.id.ck2);

        seleccionar = (Button) findViewById(R.id.btnseleccionar);
        guardar = (Button) findViewById(R.id.btnregistrar);
        buscar = (Button) findViewById(R.id.btnbuscar);
        editar = (Button) findViewById(R.id.btnactualizar);
        eliminar = (Button) findViewById(R.id.btneliminar);
        limpiar = (Button) findViewById(R.id.btnlimpiar);
    }

    //Metodo para mostrar calendario al seleccionar boton de calendario el el entorno grafico
    public void calendario (View v) {
        //Variable del calendario
        Calendar calendario = Calendar.getInstance();
        //Valor año en variable a
        a = calendario.get(Calendar.YEAR);
        //Valor mes en variable m
        m = calendario.get(Calendar.MONTH);
        //Valor dia en variable d
        d = calendario.get(Calendar.DAY_OF_MONTH);

        //Evento clic al seleccionar fecha
        DatePickerDialog.OnDateSetListener mbpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                a = year;
                m = monthOfYear;
                d = dayOfMonth;
                int uno = 1;
                //Mostrar fecha seleccionada en un editText
                edtfecha.setText(""+(m+uno)+"/"+d+"/"+a);
            }
        };
        DatePickerDialog dpd = new DatePickerDialog(this, mbpd, a, m, d);
        dpd.show();
    }

    //Evento para registrar los datos en tabla de base de datos de la aplicacion
    public void registrar (View v) {
        //Conexion a la base de datos
        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        //Variables enlazadas a elementos graficos
        String claveprestamo = edt_clave.getText().toString();
        String fecha = edtfecha.getText().toString();
        String nombre = edt_nombre_sol.getText().toString();
        String area = edt_area_sol.getText().toString();
        String descripcion = edtdescripcion.getText().toString();

        //Conjunto de if´s para identificar que chekbox estan seleccionadas y que valor asignar a las variables rec y ent
        if (ck_recibido.isChecked() == true && ck_entregado.isChecked() == true) {
            rec = "SI";
            ent = "SI";
        }
        else if (ck_recibido.isChecked() == false && ck_entregado.isChecked() == false) {
            rec = "NO";
            ent = "NO";
        }
        else if (ck_recibido.isChecked() == true && ck_entregado.isChecked() == false) {
            rec = "SI";
            ent = "NO";
        }
        else if (ck_recibido.isChecked() == false && ck_entregado.isChecked() == true) {
            rec = "NO";
            ent = "SI";
        }

        //Comando para verificr si ya esta registrado el registro
        Cursor fila = BD.rawQuery("select fecha, nombre_sol, area_sol, descripcion, recibido, entregado from prestamos where clave_prestamo=" + claveprestamo, null);
        if (fila.getCount() >= 1) {
            Toast.makeText(this, "La clave de prestamos ya se encuentra registrado", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues registro = new ContentValues();

            //Agregar datos a la base de datos
            registro.put("clave_prestamo", claveprestamo);
            registro.put("fecha", fecha);
            registro.put("nombre_sol", nombre);
            registro.put("area_sol", area);
            registro.put("descripcion", descripcion);
            registro.put("recibido", rec);
            registro.put("entregado", ent);

            BD.insert("prestamos", null, registro);
            BD.close();

            //Limpiar los editText y mostrarlos en blanco
            edt_clave.setText("");
            edtfecha.setText("");
            edt_nombre_sol.setText("");
            edt_area_sol.setText("");
            edtdescripcion.setText("");
            ck_recibido.setChecked(false);
            ck_entregado.setChecked(false);

            //Mensaje de registro exitoso
            Toast.makeText(this,"Se agrego el registro del prestamo",Toast.LENGTH_SHORT).show();
        }
        edt_clave.setFocusable(true);
    }

    //Metodo para realizar consultas
    public void buscar (View v) {
        //Conexion a la base de datos
        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        //Varibla y su elemento grafico
        String claveprestamo = edt_clave.getText().toString();

        //Comando de consulta de registro a la base de datos
        Cursor fila = BD.rawQuery("select fecha, nombre_sol, area_sol, descripcion, recibido, entregado from prestamos where clave_prestamo=" + claveprestamo, null);
        if (fila.moveToFirst()) {
            //Mostrar resultado de la busqued en elementos graficos
            edtfecha.setText(fila.getString(0));
            edt_nombre_sol.setText(fila.getString(1));
            edt_area_sol.setText(fila.getString(2));
            edtdescripcion.setText(fila.getString(3));
            recibido.setText(fila.getString(4));
            entregado.setText(fila.getString(5));
            //Identificar el resultado de los campos de los chekbox
            if (recibido.getText().toString().equals("SI") && entregado.getText().toString().equals("SI") ){
                ck_recibido.setChecked(true);
                ck_entregado.setChecked(true);
            }
            else if (recibido.getText().toString().equals("NO") && entregado.getText().toString().equals("NO")){
                ck_recibido.setChecked(false);
                ck_entregado.setChecked(false);
            }
            else if (recibido.getText().toString().equals("SI") && entregado.getText().toString().equals("NO")){
                ck_recibido.setChecked(true);
                ck_entregado.setChecked(false);
            }
            else if (recibido.getText().toString().equals("NO") && entregado.getText().toString().equals("SI")){
                ck_recibido.setChecked(false);
                ck_entregado.setChecked(true);
            }
            else {
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
            }
        } else {
            //Mensaje de eeror en la consulta
            Toast.makeText(this,"No existe el registro del prestamo",Toast.LENGTH_SHORT).show();
        }
        BD.close();
    }

    //Metodo para actualizar datos
    public void actualizar (View v) {
        //Conexion a la base de datos
        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        //Variables con elementos graficos
        String claveprestamo = edt_clave.getText().toString();
        String fecha = edtfecha.getText().toString();
        String nombre = edt_nombre_sol.getText().toString();
        String area = edt_area_sol.getText().toString();
        String descripcion = edtdescripcion.getText().toString();

        //Conjunto de if´s ´para validar que chekbox estan seleccionados
        if (ck_recibido.isChecked() == true && ck_entregado.isChecked() == true) {
            rec = "SI";
            ent = "SI";
        }
        else if (ck_recibido.isChecked() == false && ck_entregado.isChecked() == false) {
            rec = "NO";
            ent = "NO";
        }
        else if (ck_recibido.isChecked() == true && ck_entregado.isChecked() == false) {
            rec = "SI";
            ent = "NO";
        }
        else if (ck_recibido.isChecked() == false && ck_entregado.isChecked() == true) {
            rec = "NO";
            ent = "SI";
        }

        ContentValues registro = new ContentValues();

        //Insertar los datos
        registro.put("clave_prestamo", claveprestamo);
        registro.put("fecha", fecha);
        registro.put("nombre_sol", nombre);
        registro.put("area_sol", area);
        registro.put("descripcion", descripcion);
        registro.put("recibido", rec);
        registro.put("entregado", ent);

        int contador = BD.update("prestamos", registro, "clave_prestamo="+claveprestamo, null);
        BD.close();

        if (contador == 1) {
            Toast.makeText(this, "Se modificado el registro del prestamo",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No existe el registro del prestamo",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para eliminar registro
    public void eliminar (View v) {
        //Conexion a la base de datos
        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        //Variable enlazad al elemento grafico correspondiente
        String claveprestamo = edt_clave.getText().toString();

        //Comando borrar
        int contador = BD.delete("prestamos", "clave_prestamo="+ claveprestamo, null);
        BD.close();

        //Limpiar los editText
        edt_clave.setText("");
        edtfecha.setText("");
        edt_nombre_sol.setText("");
        edt_area_sol.setText("");
        edtdescripcion.setText("");
        ck_recibido.setChecked(false);
        ck_entregado.setChecked(false);

        if (contador == 1) {
            Toast.makeText(this, "El registro del prestamos ha sido borrado",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "No existe el registro del prestamo",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para limpiar los TextView
    public void limpiar (View v) {
        edt_clave.setText("");
        edtfecha.setText("");
        edt_nombre_sol.setText("");
        edt_area_sol.setText("");
        edtdescripcion.setText("");
        ck_recibido.setChecked(false);
        ck_entregado.setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_operations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
