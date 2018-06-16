package com.fireway.cpms.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = false)
    private int id;
    @Basic
    @Column(name = "Login", nullable = false, unique = true, length = 255)
    private String login;

    @Basic
    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Basic
    @Column(name = "FirstName", nullable = false, length = 32)
    private String firstName;

    @Basic
    @Column(name = "LastName", nullable = false, length = 32)
    private String lastName;

    @Basic
    @Column(name = "MiddleName", length = 32)
    private String middleName;

    @Basic
    @Column(name = "IsAdmin", nullable = false, columnDefinition = "int")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isAdmin;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "EmployeeInfoId")
    private EmployeeInfo info;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target", cascade = CascadeType.ALL)
    private Set<Message> receivedMessages = new HashSet<>(0);

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ProjectStage.class, cascade = CascadeType.ALL)
    @JoinTable(name = "UserToProjectStage", joinColumns = {
            @JoinColumn(name = "UserId", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "ProjectStageId", nullable = false, updatable = false)
    })
    private Set<ProjectStage> projectStagesAssigned = new HashSet<>(0);

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserToProject> userToProjects = new HashSet<>(0);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public EmployeeInfo getInfo() {
        return info;
    }

    public void setInfo(EmployeeInfo info) {
        this.info = info;
    }
    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }
    public Set<ProjectStage> getProjectStagesAssigned() {
        return projectStagesAssigned;
    }

    public void setProjectStagesAssigned(Set<ProjectStage> projectStagesAssigned) {
        this.projectStagesAssigned = projectStagesAssigned;
    }

    public Set<UserToProject> getUserToProjects() {
        return userToProjects;
    }

    public void setUserToProjects(Set<UserToProject> userToProjects) {
        this.userToProjects = userToProjects;
    }
}
