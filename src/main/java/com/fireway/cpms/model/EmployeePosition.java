package com.fireway.cpms.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

@Entity
@Table(name = "EmployeePosition", schema = "", catalog = "dream_team_cpms")
public class EmployeePosition implements Serializable {
    public static Comparator<EmployeePosition> positionComparator = new Comparator<EmployeePosition>() {
        @Override
        public int compare(EmployeePosition o1, EmployeePosition o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
    private int id;
    private String name;
    private String description;

    private Set<EmployeeInfo> infos;

    public EmployeePosition() {
    }

    public EmployeePosition(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = false, unique = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Description")
    @Type(type = "text")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "position", cascade = CascadeType.ALL)
    public Set<EmployeeInfo> getInfos() {
        return infos;
    }

    public void setInfos(Set<EmployeeInfo> infos) {
        this.infos = infos;
    }
}
