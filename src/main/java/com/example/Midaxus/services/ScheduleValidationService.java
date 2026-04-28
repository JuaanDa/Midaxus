package com.example.Midaxus.services;

import com.example.Midaxus.model.entities.ScheduleSession;
import com.example.Midaxus.model.entities.CourseGroup;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public class ScheduleValidationService implements IScheduleValidation {

    @Override
    public List<String> validateNoConsecutiveSessions(List<ScheduleSession> sessions) {

        List<String> warnings = new ArrayList<>();

        Map<CourseGroup, List<ScheduleSession>> sessionsByCourse =
                sessions.stream()
                        .collect(Collectors.groupingBy(ScheduleSession::getCourseGroup));

        for (Map.Entry<CourseGroup, List<ScheduleSession>> entry : sessionsByCourse.entrySet()) {

            CourseGroup courseGroup = entry.getKey();
            List<ScheduleSession> courseSessions = entry.getValue();

            List<DayOfWeek> days = courseSessions.stream()
                    .map(s -> s.getVinculationSlot().getDay())
                    .sorted(Comparator.comparingInt(DayOfWeek::getValue))
                    .toList();

            for (int i = 0; i < days.size() - 1; i++) {

                DayOfWeek current = days.get(i);
                DayOfWeek next = days.get(i + 1);

                if (next.getValue() - current.getValue() == 1) {

                    warnings.add(
                            "Curso " + courseGroup +
                                    " tiene sesiones consecutivas: " +
                                    current + " y " + next
                    );
                }
            }
        }

        return warnings;
    }
}