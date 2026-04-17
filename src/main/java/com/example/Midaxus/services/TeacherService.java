package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.TeacherDTO;
import com.example.Midaxus.model.entities.Teacher;
import com.example.Midaxus.model.mapper.TeacherMapper;
import com.example.Midaxus.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements ITeacher<TeacherDTO, String> {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = TeacherMapper.toEntity(teacherDTO);
        Teacher saved = teacherRepository.save(teacher);
        return TeacherMapper.toDTO(saved);
    }

    @Override
    public void deleteTeacher(String teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public TeacherDTO getTeacher(String teacherId) {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher no encontrado"));
        return TeacherMapper.toDTO(teacher);
    }

    @Override
    public List<TeacherDTO> getTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(TeacherMapper::toDTO)
                .toList();
    }
}