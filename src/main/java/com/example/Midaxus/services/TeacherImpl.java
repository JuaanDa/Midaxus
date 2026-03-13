package com.example.Midaxus.services;

public interface TeacherImpl<T> {

    /**
     * Verifica las credenciales de un usuario.
     *
     * @param username credencial del profesor
     * @param password contraseña del profesor
     * @return true si las credenciales son correctas, false en caso contrario
     */

    boolean login(T username, T password);


}
