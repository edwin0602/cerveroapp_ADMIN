package com.example.cerverodesing.cervero_admin.cervero_models;

/**
 * Created by Cervero Desing on 20/01/2017.
 */

public class Equipo {
    public Equipo(int codigo, String nombre){
        this.Codigo = codigo;
        this.Nombre = nombre;
    }

    public final int Codigo;
    public final String Nombre;
    public String Usuario_user;
    public int Estado;
    private int Codigo_grupo;
    public String Posicion;
    private String Ciudad;
    private String Ultima_conexion;
    public String Macs;
    private String Tecnico;
    private String Permisos;
    private String Direccion;
    private int Tipo;
    private String Informe;
    private String Tag;
    private String TagResource;
    private String Error;
    private String Programas;
    public String Codigo_instalacion;

    public String getColor(){
        switch (this.Estado){
            case 50://Asignable
                return("#03A9F4");
            case 51://Activa
                return("#4CAF50");
            case 52://Reasignable
                return("#03A9F4");
            case 53://Bloqueado
                return("#D50000");
            case 54://Online
                return("#8BC34A");
            case 55://Offline
                return("#9E9E9E");
            case 56://Mantenimiento
                return("#9E9E9E");
            case 57://MalFuncionamiento
                return("#9E9E9E");
            case 58://SinConfirmacionDeUsuario
                return("#9E9E9E");
            case 59://Cerrado
                return("#795548");
            default:
                return("#795548");

        }
    }

    public String getEstado(){
        switch (this.Estado){
            case 50://Asignable
                return("Asignable");
            case 51://Activa
                return("Activo");
            case 52://Reasignable
                return("Reasignable");
            case 53://Bloqueado
                return("Bloqueado");
            case 54://Online
                return("Online");
            case 55://Offline
                return("Offline");
            case 56://Mantenimiento
                return("Mantenimiento");
            case 57://MalFuncionamiento
                return("MalFuncionamiento");
            case 58://SinConfirmacionDeUsuario
                return("SinConfirmacionDeUsuario");
            case 59://Cerrado
                return("Cerrado");
            default:
                return("Sin indexar");

        }
    }
}
