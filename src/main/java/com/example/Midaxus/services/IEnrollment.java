package com.example.Midaxus.services;

import java.util.List;

public interface IEnrollment<T, ID> {

    T createEnrollment(T t);
    T  getEnrollment(ID id);
    List<T> getAll ();
    void deleteEnrollment(ID id);
}
