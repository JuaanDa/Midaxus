package com.example.Midaxus.services;

public interface AdminService<T> {

    /**
     * Verifica las credenciales de un usuario.
     *
     * @param username credencial del administrador(decano)
     * @param password contraseña del administrador
     * @return true si las credenciales son correctas, false en caso contrario
     */

    boolean login(T username, T password);

}
