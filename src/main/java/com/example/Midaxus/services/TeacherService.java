package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.TeacherDTO;
import com.example.Midaxus.model.entities.Teacher;
import com.example.Midaxus.model.mapper.TeacherMapper;
import com.example.Midaxus.repositories.TeacherRepository;
import com.example.Midaxus.model.dtos.TeacherAvailabilityDTO;
import com.example.Midaxus.model.entities.Subject;
import com.example.Midaxus.model.entities.TeacherAvailability;
import com.example.Midaxus.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements ITeacher<TeacherDTO, String> {

    @Autowired
    private TeacherRepository teacherRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher teacher = TeacherMapper.toEntity(teacherDTO);
        if (teacher.getPassword() != null) {
            teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        }
        
        // Asignar materias (competencias)
        if (teacherDTO.getSubjectsIds() != null) {
            List<Subject> subjects = subjectRepository.findAllById(teacherDTO.getSubjectsIds());
            teacher.setHabilitatedSubjects(subjects);
        }
        
        // Asignar disponibilidades
        if (teacherDTO.getAvailabilities() != null) {
            List<TeacherAvailability> availabilities = teacherDTO.getAvailabilities().stream().map(dto -> {
                TeacherAvailability availability = new TeacherAvailability();
                availability.setDayOfWeek(dto.getDayOfWeek());
                availability.setStartTime(dto.getStartTime());
                availability.setEndTime(dto.getEndTime());
                availability.setTeacher(teacher);
                return availability;
            }).toList();
            teacher.setAvailabilities(availabilities);
        }
        
        Teacher saved = teacherRepository.save(teacher);
        return TeacherMapper.toDTO(saved);
    }

    @Override
    public TeacherDTO updateTeacher(String teacherId, TeacherDTO teacherDTO) {
        Teacher teacher = teacherRepository.findByTeacherCode(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher no encontrado"));
                
        // Actualizar materias (competencias)
        if (teacherDTO.getSubjectsIds() != null) {
            List<Subject> subjects = subjectRepository.findAllById(teacherDTO.getSubjectsIds());
            teacher.setHabilitatedSubjects(subjects);
        }
        
        // Actualizar disponibilidades
        if (teacherDTO.getAvailabilities() != null) {
            if (teacher.getAvailabilities() != null) {
                teacher.getAvailabilities().clear();
            }
            List<TeacherAvailability> availabilities = teacherDTO.getAvailabilities().stream().map(dto -> {
                TeacherAvailability availability = new TeacherAvailability();
                availability.setDayOfWeek(dto.getDayOfWeek());
                availability.setStartTime(dto.getStartTime());
                availability.setEndTime(dto.getEndTime());
                availability.setTeacher(teacher);
                return availability;
            }).toList();
            if (teacher.getAvailabilities() != null) {
                teacher.getAvailabilities().addAll(availabilities);
            } else {
                teacher.setAvailabilities(availabilities);
            }
        }
        
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