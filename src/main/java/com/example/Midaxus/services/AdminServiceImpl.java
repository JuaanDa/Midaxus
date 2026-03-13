package com.example.Midaxus.services;

public class AdminServiceImpl implements AdminService<String> {
    @Override
    public boolean login(String username, String password) {
       if (username.equals("Admin") && password.equals("decano2026")){
           return true;
       }else {
           return false;
       }
    }
}
