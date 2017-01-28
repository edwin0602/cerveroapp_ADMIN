package com.example.cerverodesing.cervero_admin.cervero_models;


import java.util.Date;

public class Usuario {
    public int codigo;
    public String user;
    public String pass;
    public int estado;
    public String Role_codigo;
    public String Persona_documento;
    public String tag;
    public int grupo_codigo;
    public String SecurePass;
    public String Last_conexion;

    public String Nombre() {
        return tag.split(";")[0];
    }

    public Boolean CheckEstado() {
        return (estado == 0) ? true : false;
    }

    public String Estado() {
        switch (estado) {
            case 0:
                return "Activo";
            case 1:
                return "Inactivo";
            case 2:
                return "Bloqueado";
            case 3:
                return "NoAsignado";
            case 4:
                return "Cerrado";
            default:
                return "Error";
        }
    }

    public String getColor() {
        switch (estado) {
            case 0:
                return ("#03A9F4");
            case 1:
                return ("#9E9E9E");
            case 2:
                return ("#D50000");
            case 3:
                return ("#D50000");
            case 4:
                return ("#9E9E9E");
            default:
                return ("#9E9E9E");
        }
    }
}
