package com.example.abel.materialloanbasedatos;

/**
 * Created by abel on 18/05/2015.
 */
public class modelomaterial {

    private String clave_prestamo;
    private String fecha;
    private String nombre_sol;
    private String area_sol;
    private String descripcion;
    private String recibido;
    private String entregado;

    public modelomaterial(String area_sol, String clave_prestamo, String descripcion, String entregado, String fecha, String nombre_sol, String recibido) {
        this.area_sol = area_sol;
        this.clave_prestamo = clave_prestamo;
        this.descripcion = descripcion;
        this.entregado = entregado;
        this.fecha = fecha;
        this.nombre_sol = nombre_sol;
        this.recibido = recibido;
    }

    public String getArea_sol() {
        return area_sol;
    }

    public String getClave_prestamo() {
        return clave_prestamo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEntregado() {
        return entregado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre_sol() {
        return nombre_sol;
    }

    public String getRecibido() {
        return recibido;
    }
}

