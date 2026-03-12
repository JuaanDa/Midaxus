package com.example.Midaxus.model.entities;
import java.util.Date;

//Clase abstracta de la cual sus atributos y metodos sera heredados a Estudiantes, Profesores, alumnos

public abstract class User {
    String userName;
    String id;
    String email;
    String password;
    Date singInDate;


    public Boolean SignIn(int id, String userName, String password, String fullName){
        return true;
    }

    public Boolean login(String userName, String password){
        return true;
    }
}
