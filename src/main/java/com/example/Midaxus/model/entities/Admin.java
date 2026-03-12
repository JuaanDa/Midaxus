package com.example.Midaxus.model.entities;

import java.util.Date;

/**
 * Clase que representa a un administrador dentro del sistema Midaxus.
 *
 * Admin hereda de la clase abstracta {@link User}, por lo que posee
 * todos los atributos y comportamientos comunes de un usuario del sistema,
 * como nombre de usuario, correo, contraseña y fecha de registro.
 *
 * Esta clase añade atributos específicos relacionados con los administradores.
 */
public class Admin extends User {

    /** Identificador único del administrador */
    private String adminId;


    /**
     * Constructor simplificado utilizado para pruebas de autenticación.
     *
     * Permite crear un objeto Admin únicamente con las credenciales
     * necesarias para probar el proceso de login sin necesidad de
     * inicializar todos los atributos del administrador.
     *
     * @param userName nombre de usuario
     * @param password contraseña del usuario
     */
    public Admin(String userName, String password) {
        super(userName, password);
    }


    /**
     * Constructor completo para inicializar todos los atributos del administrador.
     *
     * Primero se inicializan los atributos heredados de la clase {@link User}
     * mediante el uso de {@code super()}, y posteriormente se inicializan
     * los atributos propios de Admin.
     *
     * @param userName nombre de usuario
     * @param firstName nombre del administrador
     * @param lastName apellido del administrador
     * @param id identificador del usuario
     * @param email correo electrónico
     * @param password contraseña
     * @param singInDate fecha de registro en el sistema
     * @param adminId identificador del administrador
     */
    public Admin(String userName, String firstName, String lastName, String id, String email, String password, Date singInDate, String adminId) {
        super(userName, firstName, lastName, id, email, password, singInDate);
        this.adminId = adminId;
    }


    /**
     * Obtiene el identificador del administrador.
     *
     * @return adminId
     */
    public String getAdminId() {
        return adminId;
    }


    /**
     * Establece el identificador del administrador.
     *
     * @param adminId identificador del administrador
     */
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }


    /**
     * Representación en texto del objeto Admin.
     *
     * @return cadena con la información del administrador
     */
    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                '}';
    }


    /**
     * Método que representa el proceso de registro de un administrador
     * dentro del sistema.
     *
     * @return true si el registro fue exitoso
     */
    @Override
    public Boolean SignIn() {
        return null;
    }


    /**
     * Método que representa el proceso de autenticación del administrador.
     *
     * Aquí debería implementarse la lógica que valide las credenciales
     * del administrador dentro del sistema.
     *
     * @return true si las credenciales son válidas
     */
    @Override
    public Boolean login() {
        return null;
    }
}