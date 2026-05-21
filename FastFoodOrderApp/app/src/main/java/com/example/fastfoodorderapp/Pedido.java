package com.example.fastfoodorderapp;

public class Pedido {

    private int id;
    private String fecha;

    private int hamburguesa;
    private int papas;
    private int refresco;
    private int helado;

    private double total;

    public Pedido() {}

    public Pedido(
            String fecha,
            int hamburguesa,
            int papas,
            int refresco,
            int helado,
            double total
    ) {

        this.fecha = fecha;
        this.hamburguesa = hamburguesa;
        this.papas = papas;
        this.refresco = refresco;
        this.helado = helado;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public int getHamburguesa() {
        return hamburguesa;
    }

    public int getPapas() {
        return papas;
    }

    public int getRefresco() {
        return refresco;
    }

    public int getHelado() {
        return helado;
    }

    public double getTotal() {
        return total;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHamburguesa(int hamburguesa) {
        this.hamburguesa = hamburguesa;
    }

    public void setPapas(int papas) {
        this.papas = papas;
    }

    public void setRefresco(int refresco) {
        this.refresco = refresco;
    }

    public void setHelado(int helado) {
        this.helado = helado;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}