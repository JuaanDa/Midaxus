package com.example.Midaxus.services;

import java.util.List;

public interface ISubject<T,K> {

    T create(T t);
    void delete(K id);
    List<T> findAll();
    T findById(K id);

}
