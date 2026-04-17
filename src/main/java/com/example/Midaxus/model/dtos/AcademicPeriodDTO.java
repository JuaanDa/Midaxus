package com.example.Midaxus.model.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AcademicPeriodDTO {

    private String periodId;
    private String code;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime enrollmentStartDate;
    private LocalDateTime enrollmentEndDate;

    public AcademicPeriodDTO() {}

    public AcademicPeriodDTO(String periodId, String code, String description,
                             LocalDate startDate, LocalDate endDate,
                             LocalDateTime enrollmentStartDate,
                             LocalDateTime enrollmentEndDate) {
        this.periodId = periodId;
        this.code = code;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.enrollmentStartDate = enrollmentStartDate;
        this.enrollmentEndDate = enrollmentEndDate;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getEnrollmentStartDate() {
        return enrollmentStartDate;
    }

    public void setEnrollmentStartDate(LocalDateTime enrollmentStartDate) {
        this.enrollmentStartDate = enrollmentStartDate;
    }

    public LocalDateTime getEnrollmentEndDate() {
        return enrollmentEndDate;
    }

    public void setEnrollmentEndDate(LocalDateTime enrollmentEndDate) {
        this.enrollmentEndDate = enrollmentEndDate;
    }
}
