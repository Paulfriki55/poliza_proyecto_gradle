package com.example.proyectopoliza.controller;

import java.util.List;

public class PolicyCalculator {

    public static double calcularCostoPoliza(String plan, boolean incendio, boolean robo, boolean terremoto, boolean inundacion) {
        double costoBase = 0.0;
        switch (plan) {
            case "Básico":
                costoBase = 25.0;
                break;
            case "Dental":
                costoBase = 35.0; // Básico + Dental
                break;
            case "Óptimo":
                costoBase = 40.0;
                break;
            case "Premium":
                costoBase = 50.0;
                break;
            default:
                return 0.0; // Plan no reconocido
        }

        double costoExtras = 0.0;
        if (incendio) costoExtras += 15.0;
        if (robo) costoExtras += 20.0;
        if (terremoto) costoExtras += 25.0;
        if (inundacion) costoExtras += 18.0;

        return costoBase + costoExtras;
    }
}