package com.example.Midaxus.model.entities;

import java.util.Date;

/**
 * Clase abstracta que representa un usuario dentro del sistema Midaxus.
 *
 * Esta clase contiene los atributos y comportamientos comunes para todos
 * los tipos de usuarios del sistema, como estudiantes, profesores y administradores.
 *
 * Las clases que hereden de esta clase deberán implementar los métodos
 * abstractos relacionados con el registro y autenticación del usuario.
 *
 * Clases que heredan de User:
 * - Student
 * - Teacher
 * - Admin
 */
public abstract class User {

    /** Nombre de usuario utilizado para autenticación */
    private String userName;

    /** Nombre del usuario */
    private String firstName;

    /** Apellido del usuario */
    private String lastName;

    /** Identificación única del usuario */
    private String id;

    /** Correo electrónico del usuario */
    private String email;

    /** Contraseña del usuario */
    private String password;

    /** Fecha en la que el usuario se registró en el sistema */
    private Date singInDate;


    /**
     * Constructor básico para crear un usuario con información mínima.
     *
     * @param userName nombre de usuario
     * @param lastName apellido del usuario
     */
    public User(String userName, String lastName){
        this.userName = userName;
        this.userName = userName;
    }


    /**
     * Constructor completo para inicializar todos los atributos del usuario.
     *
     * @param userName nombre de usuario
     * @param firstName nombre del usuario
     * @param lastName apellido del usuario
     * @param id identificador único
     * @param email correo electrónico
     * @param password contraseña
     * @param singInDate fecha de registro en el sistema
     */
    public User(String userName, String firstName, String lastName, String id, String email, String password, Date singInDate) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.password = password;
        this.singInDate = singInDate;
    }


    /**
     * Obtiene el nombre de usuario.
     *
     * @return userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param userName nombre de usuario
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param firstName nombre del usuario
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param lastName apellido del usuario
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param id identificador del usuario
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la contraseña del usuario.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     *
     * @param password contraseña
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene la fecha de registro del usuario.
     *
     * @return singInDate
     */
    public Date getSingInDate() {
        return singInDate;
    }

    /**
     * Establece la fecha de registro del usuario.
     *
     * @param singInDate fecha de registro
     */
    public void setSingInDate(Date singInDate) {
        this.singInDate = singInDate;
    }


    /**
     * Método abstracto para registrar un usuario en el sistema.
     *
     * Debe ser implementado por las clases hijas.
     *
     * @return true si el registro fue exitoso
     */
    public abstract Boolean SignIn();


    /**
     * Método abstracto para autenticar un usuario en el sistema.
     *
     * Debe ser implementado por las clases hijas.
     *
     * @return true si las credenciales son válidas
     */
    public abstract Boolean login();

}