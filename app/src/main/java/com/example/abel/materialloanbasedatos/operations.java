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

    EditText edt_clave,edtfecha, edt_nombre_sol, edt_area_sol, edtdescripcion, recibido, entregado;
    CheckBox ck_recibido, ck_entregado;
    Button seleccionar, guardar, buscar, editar, eliminar, limpiar;
    int a,m,d;
    String rec, ent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operations);

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


    public void calendario (View v) {
        Calendar calendario = Calendar.getInstance();
        a = calendario.get(Calendar.YEAR);
        m = calendario.get(Calendar.MONTH);
        d = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener mbpd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                a = year;
                m = monthOfYear;
                d = dayOfMonth;
                int uno = 1;
                edtfecha.setText(""+(m+uno)+"/"+d+"/"+a);
            }
        };
        DatePickerDialog dpd = new DatePickerDialog(this, mbpd, a, m, d);
        dpd.show();
    }

    public void registrar (View v) {
        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        String claveprestamo = edt_clave.getText().toString();
        String fecha = edtfecha.getText().toString();
        String nombre = edt_nombre_sol.getText().toString();
        String area = edt_area_sol.getText().toString();
        String descripcion = edtdescripcion.getText().toString();

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

        Cursor fila = BD.rawQuery("select fecha, nombre_sol, area_sol, descripcion, recibido, entregado from prestamos where clave_prestamo=" + claveprestamo, null);
        if (fila.getCount() >= 1) {
            Toast.makeText(this, "La clave de prestamos ya se encuentra registrado", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues registro = new ContentValues();

            registro.put("clave_prestamo", claveprestamo);
            registro.put("fecha", fecha);
            registro.put("nombre_sol", nombre);
            registro.put("area_sol", area);
            registro.put("descripcion", descripcion);
            registro.put("recibido", rec);
            registro.put("entregado", ent);

            BD.insert("prestamos", null, registro);
            BD.close();

            edt_clave.setText("");
            edtfecha.setText("");
            edt_nombre_sol.setText("");
            edt_area_sol.setText("");
            edtdescripcion.setText("");
            ck_recibido.setChecked(false);
            ck_entregado.setChecked(false);

            Toast.makeText(this,"Se agrego el registro del prestamo",Toast.LENGTH_SHORT).show();
        }
        edt_clave.setFocusable(true);
    }

    public void buscar (View v) {

        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        String claveprestamo = edt_clave.getText().toString();

        Cursor fila = BD.rawQuery("select fecha, nombre_sol, area_sol, descripcion, recibido, entregado from prestamos where clave_prestamo=" + claveprestamo, null);
        if (fila.moveToFirst()) {
            edtfecha.setText(fila.getString(0));
            edt_nombre_sol.setText(fila.getString(1));
            edt_area_sol.setText(fila.getString(2));
            edtdescripcion.setText(fila.getString(3));
            recibido.setText(fila.getString(4));
            entregado.setText(fila.getString(5));
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
            Toast.makeText(this,"No existe el registro del prestamo",Toast.LENGTH_SHORT).show();
        }
        BD.close();
    }

    public void actualizar (View v) {
        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        String claveprestamo = edt_clave.getText().toString();
        String fecha = edtfecha.getText().toString();
        String nombre = edt_nombre_sol.getText().toString();
        String area = edt_area_sol.getText().toString();
        String descripcion = edtdescripcion.getText().toString();

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

    public void eliminar (View v) {
        ConexionSQL funcion = new ConexionSQL(this, "prestamos", null, 1);
        SQLiteDatabase BD = funcion.getWritableDatabase();

        String claveprestamo = edt_clave.getText().toString();

        int contador = BD.delete("prestamos", "clave_prestamo="+ claveprestamo, null);
        BD.close();

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
