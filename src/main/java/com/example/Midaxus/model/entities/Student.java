package com.example.Midaxus.model.entities;

import java.util.Date;
import java.util.Set;

/**
 * Clase que representa a un estudiante dentro del sistema Midaxus.
 *
 * Student hereda de la clase abstracta {@link User}, por lo que adquiere
 * los atributos y comportamientos comunes de cualquier usuario del sistema
 * como nombre, correo, contraseña y fecha de registro.
 *
 * Esta clase agrega información específica de los estudiantes,
 * como su identificador académico.
 */
public class Student extends User {

    /** Identificador único del estudiante */
    private String studentId;

    // private Set<Enrollment> enrollment;


    /**
     * Constructor simplificado utilizado para pruebas de autenticación.
     *
     * Permite crear un objeto Student únicamente con las credenciales
     * necesarias para probar el proceso de login sin necesidad de
     * inicializar todos los atributos del estudiante.
     *
     * @param userName nombre de usuario
     * @param password contraseña del usuario
     */
    public Student(String userName, String password) {
        super(userName, password);
    }


    /**
     * Constructor completo para inicializar todos los atributos del estudiante.
     *
     * Primero se inicializan los atributos heredados de la clase {@link User}
     * mediante el uso de {@code super()}, y posteriormente se inicializan
     * los atributos propios de Student.
     *
     * @param userName nombre de usuario
     * @param firstName nombre del estudiante
     * @param lastName apellido del estudiante
     * @param id identificador del usuario
     * @param email correo electrónico
     * @param password contraseña del usuario
     * @param singInDate fecha de registro en el sistema
     * @param studentId identificador del estudiante
     */
    public Student(String userName, String firstName, String lastName, String id, String email, String password, Date singInDate, String studentId) {
        super(userName, firstName, lastName, id, email, password, singInDate);
        this.studentId = studentId;
    }


    /**
     * Obtiene el identificador del estudiante.
     *
     * @return studentId
     */
    public String getStudentId() {
        return studentId;
    }


    /**
     * Establece el identificador del estudiante.
     *
     * @param studentId identificador del estudiante
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }


    /**
     * Método que representa el proceso de registro de un estudiante en el sistema.
     *
     * Este método deberá implementar la lógica necesaria para registrar
     * un estudiante dentro del sistema Midaxus.
     *
     * @return true si el registro fue exitoso
     */
    @Override
    public Boolean SignIn() {
        return null;
    }


    /**
     * Método que representa el proceso de autenticación del estudiante.
     *
     * Aquí debería implementarse la lógica para validar las credenciales
     * del estudiante dentro del sistema.
     *
     * @return true si las credenciales son válidas
     */
    @Override
    public Boolean login() {
        return null;
    }
}