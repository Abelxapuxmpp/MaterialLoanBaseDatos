package com.example.abel.materialloanbasedatos;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity  implements View.OnClickListener {

    Button Operaciones, Consultas;
    private int cont=0,i=4;
    private ImageButton logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Operaciones = (Button) findViewById(R.id.btnver);
        Consultas = (Button) findViewById(R.id.bntcons);

        Operaciones.setOnClickListener(this);
        Consultas.setOnClickListener(this);
        //enlazamos con el xml
        ImageButton logo= (ImageButton) findViewById(R.id.imageView);
        logo.setOnClickListener(new View.OnClickListener(){
    //hacemos que escuche el click y ejecute el metodo update
    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        update_cont();
        }

        });

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.btnver:
        Intent intento = new Intent(MainActivity.this, operations.class);
        startActivity(intento);
        break;

        case  R.id.bntcons:
        Intent intento2 = new Intent(MainActivity.this, listaregistros.class);
        startActivity(intento2);
        break;
        }
        }


private void update_cont() {
        cont++;

        if(cont>i){
        reiniciar();
        Intent i = new Intent(MainActivity.this, acercade.class);
        startActivity(i);


        }


        }
private void reiniciar(){
        this.cont=0;

        }
}
