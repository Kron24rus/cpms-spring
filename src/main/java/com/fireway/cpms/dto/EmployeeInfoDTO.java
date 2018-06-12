package com.fireway.cpms.dto;


import com.fireway.cpms.model.EmployeeInfo;
import com.fireway.cpms.model.EmployeePosition;
import org.hibernate.Hibernate;

public class EmployeeInfoDTO {
    private int id;
    private String description;
    private EmployeePositionDTO position;

    public EmployeeInfoDTO() {

    }

    public EmployeeInfoDTO(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public EmployeeInfoDTO(EmployeeInfo entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.description = entity.getDescription();
            if (Hibernate.isInitialized(entity.getPosition())) {
                setPosition(entity.getPosition());
            }
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EmployeePositionDTO getPosition() {
        return position;
    }

    public void setPosition(EmployeePositionDTO position) {
        this.position = position;
    }

    public void setPosition(EmployeePosition position) {
        if (position != null) {
            this.position = new EmployeePositionDTO(position);
        } else {
            this.position = null;
        }
    }
}
