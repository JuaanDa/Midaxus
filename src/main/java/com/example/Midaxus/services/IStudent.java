package com.example.Midaxus.services;

import java.util.List;

public interface IStudent <K, J> {


    J getStudent(K k);
    void deleteStudent(J j);
    List<J>getStudents();

}
