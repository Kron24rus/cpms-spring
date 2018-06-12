package com.fireway.cpms.dto;


import com.fireway.cpms.model.EmployeeInfo;
import com.fireway.cpms.model.User;
import org.hibernate.Hibernate;

import java.util.List;

public class UserDTO {
    private int id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private boolean isAdmin;

    private EmployeeInfoDTO info;
    private List<MessageDTO> receivedMessages;

    private boolean ownUser;

    public UserDTO() {

    }

    public UserDTO(int id, String login, String firstName, String lastName, String middleName, boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.isAdmin = isAdmin;
    }

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.login = user.getLogin();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.middleName = user.getMiddleName();
            this.isAdmin = user.isAdmin();
            if (Hibernate.isInitialized(user.getInfo())) {
                setInfo(user.getInfo());
            }
        }
    }

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

    public EmployeeInfoDTO getInfo() {
        return info;
    }

    public void setInfo(EmployeeInfoDTO info) {
        this.info = info;
    }

    public void setInfo(EmployeeInfo info) {
        if (info != null) {
            this.info = new EmployeeInfoDTO(info);
        } else {
            this.info = null;
        }
    }

    public List<MessageDTO> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<MessageDTO> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public boolean isOwnUser() {
        return ownUser;
    }

    public void setOwnUser(boolean ownUser) {
        this.ownUser = ownUser;
    }
}
