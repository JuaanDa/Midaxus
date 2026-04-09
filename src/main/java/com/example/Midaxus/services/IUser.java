package com.example.Midaxus.services;

public interface IUser<T,ID> {
    T createUser(T t);
    void deleteUser(ID id);
    T getUser(ID id);

}
