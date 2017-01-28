package com.example.cerverodesing.cervero_admin.cervero_models;

public class Detalle_Ticket {
    public int codigo;
    public int logros_codigo;
    public int visible;
    public String valor;
    public int estado;
    public int Match_codigo;
    public int Player_codigo;
    public String condicion;
    public String descripcion;
    public String tag;
    public String nombre;
    public int tipo_logro;
    public String grupo;

    public String Estado(){
        switch (estado)
        {
            case 1:
                return "Activo";
            case 2:
                return "Inactivo";
            case 3:
                return "Ganado";
            case 4:
                return "Perdido";
            case 5:
                return "Anulado";
            case 6:
                return "Checking";
            default:
                return "Ninguno";
        }
    }
}
