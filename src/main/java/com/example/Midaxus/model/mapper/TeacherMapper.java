package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.TeacherDTO;
import com.example.Midaxus.model.entities.Teacher;

import java.util.Collections;
import java.util.List;

public class TeacherMapper {

    public static TeacherDTO  toDTO(Teacher teacher){
        if (teacher == null) return null;

        return new TeacherDTO(teacher.getId(),
                              teacher.getTeacherId(),
                              teacher.getUserName(),
                              teacher.getFirstName(),
                              teacher.getLastName(),
                              teacher.getEmail());


    }
    public static Teacher toEntity(TeacherDTO dto){
        if (dto == null) return null;

        Teacher teacher = new Teacher();
        teacher.setTeacherId(dto.getTeacherId());
        teacher.setUserName(dto.getUserName());
        teacher.setFirstName(dto.getFirstName());
        teacher.setLastName(dto.getLastName());
        teacher.setEmail(dto.getEmail());

        return teacher;

    }

    public static List<TeacherDTO> toDTOlist(List<Teacher>teachers){
        if (teachers == null) return Collections.emptyList();

        return teachers.stream().map(TeacherMapper::toDTO).toList();


    }
}
