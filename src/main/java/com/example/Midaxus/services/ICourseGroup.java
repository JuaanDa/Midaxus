package com.example.Midaxus.services;

import java.util.List;

public interface ICourseGroup<T, K> {


    T create(T dto);

    T update(K id, T dto);

    T getById(K id);


    List<T> getAll();


    void delete(K id);

    // Buscar por teacher
    List<T> getByTeacher(K teacherId);

    // Buscar por subject
    List<T> getBySubject(K subjectId);

    List<T> getCoursesByTeacher(K teacherId);
    List<T> getCoursesByStudent(K subjectId);
}