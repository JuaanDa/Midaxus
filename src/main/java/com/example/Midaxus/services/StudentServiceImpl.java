package com.example.Midaxus.services;

public class StudentServiceImpl implements StudentService<String> {
    @Override
    public boolean login(String username, String password) {

        if (username.equals("decano") && password.equals("Unbosqu3")){
            return true;
        }else
    return false;
    }
}
