package com.example.cerverodesing.cervero_admin.cervero_models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket implements Serializable {
    public int Codigo;
    public String Fechar;
    public String Serial;
    public Double Valor;
    public int Cuenta_codigo;
    public int User_codigo;
    public int Estado;
    public Double Regalia;
    public Double Total;
    public int Tipo;
    public int Equipo_codigo;
    public String Fecha_ganado;
    public String Tag;
    public int Codigo_grupo;
    public String Fecha_pagado;

    public String getColor(){
        switch (Estado)
        {
            case 0: //NoProcesado:
                return("#607D8B");
            case 1: //GanadoEnEspera:
                return("#8BC34A");
            case 2: //Perdido:
                return("#F44336");
            case 3: //GanadoYPagado:
                return("#2196F3");
            case 4: //Anulado:
                return("#3E2723");
            case 5: //Caducado:
                return("#9E9E9E");
            case 6: //Checking:
                return("#37474F");
            default:
                return("#263238");
        }
    }

    public String getEstado(){
        switch (Estado)
        {
            case 0: //NoProcesado:
                return("Pendiente");
            case 1: //GanadoEnEspera:
                return("Ganado");
            case 2: //Perdido:
                return("Perdido");
            case 3: //GanadoYPagado:
                return("Pagado");
            case 4: //Anulado:
                return("Anulado");
            case 5: //Caducado:
                return("Caducado");
            case 6: //Checking:
                return("Checking");
            default:
                return("Error");
        }
    }
}
