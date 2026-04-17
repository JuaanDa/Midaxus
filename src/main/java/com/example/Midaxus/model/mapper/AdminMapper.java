package com.example.Midaxus.model.mapper;

import com.example.Midaxus.model.dtos.AdminDTO;
import com.example.Midaxus.model.dtos.StudentDTO;
import com.example.Midaxus.model.entities.Admin;
import com.example.Midaxus.model.entities.Student;

import java.util.Collections;
import java.util.List;

public class AdminMapper {


    public static AdminDTO toDTO(Admin admin){
        if (admin == null) return null;

        return new AdminDTO(admin.getAdminId(),
                            admin.getFirstName(),
                            admin.getLastName(),
                            admin.getUserName(),
                            admin.getEmail());
    }


    public static List<AdminDTO> toDTOlist(List<Admin>admins){

        if (admins == null) return Collections.emptyList();

        return admins.stream().map(AdminMapper::toDTO).toList();
    }


}
