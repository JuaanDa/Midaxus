package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.EnrollmentDTO;
import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.Enrollment;
import com.example.Midaxus.model.entities.Student;
import com.example.Midaxus.model.enums.EnrollmentStatus;
import com.example.Midaxus.model.mapper.EnrollmentMapper;
import com.example.Midaxus.repositories.CourseGroupRepository;
import com.example.Midaxus.repositories.EnrollmentRepository;
import com.example.Midaxus.model.entities.InstitutionPolicy;
import com.example.Midaxus.repositories.InstitutionPolicyRepository;
import com.example.Midaxus.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService implements IEnrollment<EnrollmentDTO, String> {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseGroupRepository courseGroupRepository;

    @Autowired
    private InstitutionPolicyRepository policyRepository;

    @Override
    public EnrollmentDTO createEnrollment(EnrollmentDTO dto) {

        if (dto == null) throw new RuntimeException("Datos inválidos");

        // buscar entidades por ID
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student no encontrado"));

        CourseGroup courseGroup = courseGroupRepository.findById(dto.getCourseGroupId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        //  evitar duplicado
        if (enrollmentRepository.existsByStudentAndCourseGroup(student, courseGroup)) {
            throw new RuntimeException("Ya estás inscrito en este curso");
        }

        InstitutionPolicy policy = policyRepository.findById(1L).orElseGet(() -> {
            InstitutionPolicy p = new InstitutionPolicy();
            p.setStandardCapacity(40);
            p.setCapacityTolerancePercent(10);
            return p;
        });

        //  capacidad (HU-14: control de aforo por grupo con un estándar y tolerancia)
        int current = enrollmentRepository.getAllByCourseGroup(courseGroup).size();
        int baseCapacity = courseGroup.getCapacity() > 0 ? courseGroup.getCapacity() : policy.getStandardCapacity();
        int maxAllowed = baseCapacity + (baseCapacity * policy.getCapacityTolerancePercent() / 100);

        if (current >= maxAllowed) {
            throw new RuntimeException("Curso lleno (aforo máximo alcanzado)");
        }

        //  crear enrollment
        Enrollment enrollment = EnrollmentMapper.toEntity(dto);

        enrollment.setStudent(student);
        enrollment.setCourseGroup(courseGroup);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);

        Enrollment saved = enrollmentRepository.save(enrollment);

        return EnrollmentMapper.toDTO(saved);
    }

    @Override
    public EnrollmentDTO getEnrollment(String id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No encontrado"));

        return EnrollmentMapper.toDTO(e);
    }

    @Override
    public List<EnrollmentDTO> getAll() {
        return EnrollmentMapper.toDTOList(
                enrollmentRepository.findAllByStatus(EnrollmentStatus.ENROLLED)
        );
    }

    @Override
    public void deleteEnrollment(String id) {

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment no encontrado"));

        if (enrollment.getStatus() == EnrollmentStatus.DROPPED) {
            throw new RuntimeException("El estudiante ya se retiró de este curso");
        }

        enrollment.setStatus(EnrollmentStatus.DROPPED);

        enrollmentRepository.save(enrollment);
    }
}