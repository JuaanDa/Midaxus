package com.example.Midaxus.services;

public interface StudentService<T> {

    /**
     * Verifica las credenciales de un usuario.
     *
     * @param username nombre de usuario
     * @param password contraseña del usuario
     * @return true si las credenciales son correctas, false en caso contrario
     */
    boolean login(T username, T password);

}
