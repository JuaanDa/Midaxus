package com.example.Midaxus.services;

import com.example.Midaxus.model.dtos.ScheduleSessionDTO;
import com.example.Midaxus.model.entities.CourseGroup;
import com.example.Midaxus.model.entities.ScheduleSession;
import com.example.Midaxus.model.entities.VinculationSlot;
import com.example.Midaxus.repositories.CourseGroupRepository;
import com.example.Midaxus.repositories.ScheduleSessionRepository;
import com.example.Midaxus.repositories.VinculationSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ScheduleSessionService implements IScheduleSessionService {

    @Autowired
    private ScheduleSessionRepository scheduleSessionRepository;

    @Autowired
    private CourseGroupRepository courseGroupRepository;

    @Autowired
    private VinculationSlotRepository vinculationSlotRepository;

    @Override
    public ScheduleSession saveSession(ScheduleSessionDTO dto) {
        ScheduleSession session = new ScheduleSession();
        session.setScheduleSessionId(UUID.randomUUID().toString());

        if (dto.getCourseGroupId() != null) {
            courseGroupRepository.findById(dto.getCourseGroupId()).ifPresentOrElse(
                session::setCourseGroup,
                () -> {
                    CourseGroup cg = new CourseGroup();
                    cg.setCourseGroupId(dto.getCourseGroupId());
                    cg.setCode(dto.getCourseCode() != null ? dto.getCourseCode() : dto.getCourseGroupId());
                    cg = courseGroupRepository.save(cg);
                    session.setCourseGroup(cg);
                }
            );
        } else if (dto.getCourseCode() != null) {
            CourseGroup cg = new CourseGroup();
            cg.setCourseGroupId(UUID.randomUUID().toString());
            cg.setCode(dto.getCourseCode());
            cg = courseGroupRepository.save(cg);
            session.setCourseGroup(cg);
        }

        if (dto.getVinculationSlotId() != null) {
            vinculationSlotRepository.findById(dto.getVinculationSlotId()).ifPresent(session::setVinculationSlot);
        } else if (dto.getDay() != null) {
            // Find or create VinculationSlot
            VinculationSlot vs = new VinculationSlot();
            vs.setVinculationSlotId(UUID.randomUUID().toString());
            try {
                vs.setDay(java.time.DayOfWeek.valueOf(dto.getDay().toUpperCase()));
            } catch(Exception e) {
                vs.setDay(java.time.DayOfWeek.MONDAY); // fallback
            }
            vs = vinculationSlotRepository.save(vs);
            session.setVinculationSlot(vs);
        }
        
        // Simulating the room and schedule for now since we just created empty entities
        // In a real scenario we'd do the same as courseGroup
        
        return scheduleSessionRepository.save(session);
    }

    @Override
    public List<ScheduleSession> getSessionsByCourse(String courseGroupId) {
        // Here we simulate getting all sessions for the course group
        // Real implementation would use repository custom query
        return scheduleSessionRepository.findAll().stream()
                .filter(s -> s.getCourseGroup() != null && s.getCourseGroup().getCourseGroupId().equals(courseGroupId))
                .toList();
    }
}
