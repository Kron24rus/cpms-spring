package com.fireway.cpms.dto.extra;


import com.fireway.cpms.dto.ProjectDTO;

import java.util.List;

public class ProjectListDTO {
    private List<ProjectDTO> my;
    private List<ProjectDTO> all;

    public ProjectListDTO() {

    }

    public ProjectListDTO(List<ProjectDTO> my, List<ProjectDTO> all) {
        setMy(my);
        setAll(all);
    }

    public List<ProjectDTO> getMy() {
        return my;
    }

    public void setMy(List<ProjectDTO> my) {
        this.my = my;
    }

    public List<ProjectDTO> getAll() {
        return all;
    }

    public void setAll(List<ProjectDTO> all) {
        this.all = all;
    }
}
