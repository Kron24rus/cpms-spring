package com.fireway.cpms.dto;


import com.fireway.cpms.model.EmployeePosition;

public class EmployeePositionDTO {
    private int id;
    private String name;
    private String description;

    public EmployeePositionDTO() {

    }

    public EmployeePositionDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public EmployeePositionDTO(EmployeePosition entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.description = entity.getDescription();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}