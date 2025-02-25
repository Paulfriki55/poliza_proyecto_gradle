package com.example.proyectopoliza.model;

public class Poliza {
    private int id;
    private int usuarioId;
    private String planSeleccionado;
    private double costoPoliza;
    private boolean seguroIncendio;
    private boolean seguroRobo;
    private boolean seguroTerremoto;
    private boolean seguroInundacion;

    public Poliza() {
    }

    public Poliza(int id, int usuarioId, String planSeleccionado, double costoPoliza, boolean seguroIncendio, boolean seguroRobo, boolean seguroTerremoto, boolean seguroInundacion) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.planSeleccionado = planSeleccionado;
        this.costoPoliza = costoPoliza;
        this.seguroIncendio = seguroIncendio;
        this.seguroRobo = seguroRobo;
        this.seguroTerremoto = seguroTerremoto;
        this.seguroInundacion = seguroInundacion;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getPlanSeleccionado() {
        return planSeleccionado;
    }

    public void setPlanSeleccionado(String planSeleccionado) {
        this.planSeleccionado = planSeleccionado;
    }

    public double getCostoPoliza() {
        return costoPoliza;
    }

    public void setCostoPoliza(double costoPoliza) {
        this.costoPoliza = costoPoliza;
    }

    public boolean isSeguroIncendio() {
        return seguroIncendio;
    }

    public void setSeguroIncendio(boolean seguroIncendio) {
        this.seguroIncendio = seguroIncendio;
    }

    public boolean isSeguroRobo() {
        return seguroRobo;
    }

    public void setSeguroRobo(boolean seguroRobo) {
        this.seguroRobo = seguroRobo;
    }

    public boolean isSeguroTerremoto() {
        return seguroTerremoto;
    }

    public void setSeguroTerremoto(boolean seguroTerremoto) {
        this.seguroTerremoto = seguroTerremoto;
    }

    public boolean isSeguroInundacion() {
        return seguroInundacion;
    }

    public void setSeguroInundacion(boolean seguroInundacion) {
        this.seguroInundacion = seguroInundacion;
    }
}