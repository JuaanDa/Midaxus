package com.example.Midaxus.services;

import java.util.List;

public interface ITeacher<J, K> {

    J createTeacher(J teacher);
    void deleteTeacher(K k);
    J getTeacher(K k);
    List<J> getTeachers();

}
