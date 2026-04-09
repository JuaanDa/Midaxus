package com.example.Midaxus.services;

import java.util.List;

public interface ITeacher<J,K> {

    void deleteTeacher(K k);
    J getTeacher(K k);
    List<J>getTeachers();


}
