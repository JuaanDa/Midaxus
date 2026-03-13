package com.example.Midaxus.services;

public class TeacherServiceImpl implements TeacherImpl <String> {
    @Override
    public boolean login(String username, String password) {
        if(username.equals("christian") && password.equals("BD2"))
        return true;
        else{
            return false;
        }
    }
}
