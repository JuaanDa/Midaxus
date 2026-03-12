package com.example.Midaxus.model.entities;

import javax.security.auth.Subject;
import java.util.Date;
import java.util.Set;

/**
 * Clase que representa a un profesor dentro del sistema Midaxus.
 *
 * Teacher hereda de la clase abstracta {@link User}, por lo que adquiere
 * todos los atributos y comportamientos comunes de un usuario del sistema.
 *
 * Además, agrega atributos específicos para los profesores como:
 * - Identificador del profesor
 * - Materias que imparte
 * - Fecha de inicio en la institución
 */
public class Teacher extends User {

    /** Identificador único del profesor */
    private String teacherId;

    /** Conjunto de materias que el profesor imparte */
    private Set<Subject> subjects;

    /** Fecha en la que el profesor comenzó a trabajar */
    private Date startDate;


    /**
     * Constructor simplificado utilizado para pruebas de autenticación.
     *
     * Permite crear un objeto Teacher únicamente con las credenciales
     * necesarias para realizar pruebas de login sin necesidad de
     * inicializar todos los atributos del profesor.
     *
     * @param userName nombre de usuario
     * @param password contraseña del usuario
     */
    public Teacher(String userName, String password) {
        super(userName, password);
    }


    /**
     * Constructor completo para inicializar todos los atributos de un profesor.
     *
     * Este constructor inicializa primero los atributos heredados de la clase
     * User mediante el uso de {@code super()}, y posteriormente inicializa
     * los atributos propios de Teacher.
     *
     * @param userName nombre de usuario
     * @param firstName nombre del profesor
     * @param lastName apellido del profesor
     * @param id identificador del usuario
     * @param email correo electrónico
     * @param password contraseña
     * @param singInDate fecha de registro en el sistema
     * @param teacherId identificador del profesor
     * @param subjects materias que el profesor imparte
     * @param startDate fecha de inicio como profesor
     */
    public Teacher(String userName, String firstName, String lastName, String id, String email, String password,
                   Date singInDate, String teacherId, Set<Subject> subjects, Date startDate) {
        super(userName, firstName, lastName, id, email, password, singInDate);
        this.teacherId = teacherId;
        this.subjects = subjects;
        this.startDate = startDate;
    }


    /**
     * Obtiene el identificador del profesor.
     *
     * @return teacherId
     */
    public String getTeacherId() {
        return teacherId;
    }

    /**
     * Establece el identificador del profesor.
     *
     * @param teacherId identificador del profesor
     */
    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }


    /**
     * Obtiene las materias que imparte el profesor.
     *
     * @return conjunto de materias
     */
    public Set<Subject> getSubjects() {
        return subjects;
    }

    /**
     * Establece las materias que imparte el profesor.
     *
     * @param subjects conjunto de materias
     */
    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }


    /**
     * Obtiene la fecha de inicio del profesor.
     *
     * @return startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Establece la fecha de inicio del profesor.
     *
     * @param startDate fecha de inicio
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }


    /**
     * Método que representa el registro de un profesor en el sistema.
     *
     * Este método debe implementar la lógica necesaria para registrar
     * un profesor dentro del sistema Midaxus.
     *
     * @return true si el registro fue exitoso
     */
    @Override
    public Boolean SignIn() {
        return null;
    }


    /**
     * Método que representa el proceso de autenticación de un profesor.
     *
     * Aquí debería implementarse la lógica que valida las credenciales
     * del usuario contra el sistema o base de datos.
     *
     * @return true si el login es válido
     */
    @Override
    public Boolean login() {
        return null;
    }
}