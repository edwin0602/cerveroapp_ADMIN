package com.example.cerverodesing.cervero_admin.cervero_models;


import java.util.Date;

public class Usuario {
    public int codigo;
    public String user;
    public String pass;
    public String estado;
    public String Role_codigo;
    public String Persona_documento;
    public String tag;
    public int grupo_codigo;
    public String SecurePass;
    public String Last_conexion;

    public String Nombre(){
        return tag.split(";")[0];
    }
}
