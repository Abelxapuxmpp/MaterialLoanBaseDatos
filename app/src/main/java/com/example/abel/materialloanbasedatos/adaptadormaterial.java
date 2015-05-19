package com.example.abel.materialloanbasedatos;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abel on 18/05/2015.
 */
public class adaptadormaterial extends RecyclerView.Adapter<adaptadormaterial.materialViewHolder> {

    private List<modelomaterial> items;

    public static class materialViewHolder extends RecyclerView.ViewHolder {

        public TextView clave_prestamo;
        public TextView fecha;
        public TextView nombre_sol;
        public TextView area_sol;
        public TextView descripcion;
        public TextView recibido;
        public TextView entregado;

        public materialViewHolder(View v) {
            super(v);
            clave_prestamo = (TextView) v.findViewById(R.id.clave_prestamo);
            fecha = (TextView) v.findViewById(R.id.fecha);
            nombre_sol = (TextView) v.findViewById(R.id.nombre_sol);
            area_sol = (TextView) v.findViewById(R.id.area_sol);
            descripcion = (TextView) v.findViewById(R.id.descripcion);
            recibido = (TextView) v.findViewById(R.id.recibido);
            entregado = (TextView) v.findViewById(R.id.entregado);
        }
    }

    public adaptadormaterial(List<modelomaterial> items) {
        this.items = items;
    }

    @Override
    public materialViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.registros_card, viewGroup, false);
        return new materialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(materialViewHolder viewHolder, int i) {
        //el String.valuOf
        viewHolder.clave_prestamo.setText(items.get(i).getClave_prestamo());
        viewHolder.fecha.setText(items.get(i).getFecha());
        viewHolder.nombre_sol.setText(items.get(i).getNombre_sol());
        viewHolder.area_sol.setText(items.get(i).getArea_sol());
        viewHolder.descripcion.setText(items.get(i).getDescripcion());
        viewHolder.recibido.setText(items.get(i).getRecibido());
        viewHolder.entregado.setText(items.get(i).getEntregado());
    }

    @Override
    public int getItemCount() {
        return  items.size();
    }
}