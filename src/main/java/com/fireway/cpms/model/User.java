package com.fireway.cpms.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User", schema = "", catalog = "dream_team_cpms")
public class User implements Serializable {
    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private boolean isAdmin;

    private EmployeeInfo info;
    private Set<Message> receivedMessages = new HashSet<>(0);
    private Set<ProjectStage> projectStagesAssigned = new HashSet<>(0);
    private Set<UserToProject> userToProjects = new HashSet<>(0);

    @Id
    @GeneratedValue
    @Column(name = "Id", nullable = false, insertable = true, updatable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Login", nullable = false, unique = true, length = 255)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "Password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "FirstName", nullable = false, length = 32)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LastName", nullable = false, length = 32)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "MiddleName", length = 32)
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Basic
    @Column(name = "IsAdmin", nullable = false, columnDefinition = "int")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "EmployeeInfoId")
    public EmployeeInfo getInfo() {
        return info;
    }

    public void setInfo(EmployeeInfo info) {
        this.info = info;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target", cascade = CascadeType.ALL)
    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = ProjectStage.class, cascade = CascadeType.ALL)
    @JoinTable(name = "UserToProjectStage", joinColumns = {
            @JoinColumn(name = "UserId", nullable = false, updatable = false)
    }, inverseJoinColumns = {
            @JoinColumn(name = "ProjectStageId", nullable = false, updatable = false)
    })
    public Set<ProjectStage> getProjectStagesAssigned() {
        return projectStagesAssigned;
    }

    public void setProjectStagesAssigned(Set<ProjectStage> projectStagesAssigned) {
        this.projectStagesAssigned = projectStagesAssigned;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    public Set<UserToProject> getUserToProjects() {
        return userToProjects;
    }

    public void setUserToProjects(Set<UserToProject> userToProjects) {
        this.userToProjects = userToProjects;
    }
}
