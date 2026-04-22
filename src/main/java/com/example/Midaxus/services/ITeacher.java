package com.example.Midaxus.services;

import java.util.List;

public interface ITeacher<T, ID> {

    T createTeacher(T dto);
    T updateTeacher(ID id, T dto);
    void deleteTeacher(ID id);
    T getTeacher(ID id);
    List<T> getTeachers();

}
