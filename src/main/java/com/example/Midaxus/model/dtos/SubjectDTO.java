package com.example.Midaxus.model.dtos;

public class SubjectDTO {

    private String idSubject;
    private String subjectName;
    private int sessionPerWeek;
    private int durationMinutes;


    public SubjectDTO(String idSubject, String subjectName, int sessionPerWeek, int durationMinutes) {
        this.idSubject = idSubject;
        this.subjectName = subjectName;
        this.sessionPerWeek = sessionPerWeek;
        this.durationMinutes = durationMinutes;
    }

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getSessionPerWeek() {
        return sessionPerWeek;
    }

    public void setSessionPerWeek(int sessionPerWeek) {
        this.sessionPerWeek = sessionPerWeek;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}
