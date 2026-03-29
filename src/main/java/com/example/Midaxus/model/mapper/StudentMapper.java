package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.StudentDTO;
import com.example.Midaxus.model.entities.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class StudentMapper {


    public static StudentDTO toDTO(Student student){

        if (student == null) return null;
        return new StudentDTO(student.getId(),
                              student.getStudentId(),
                              student.getUserName(),
                              student.getEmail());

    }

    public static List<StudentDTO> toDTOlist(List<Student>students){
        if (students == null) return Collections.emptyList();

        return students.stream().map(StudentMapper::toDTO).toList();


    }
}
