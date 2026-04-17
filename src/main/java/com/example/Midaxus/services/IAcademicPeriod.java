package com.example.Midaxus.services;

import java.util.List;

public interface IAcademicPeriod<T, K> {


    T create(T dto);


    T getById(K id);


    List<T> getAll();


    void delete(K id);
}