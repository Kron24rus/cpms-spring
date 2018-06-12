package com.fireway.cpms.dto;


import com.fireway.cpms.model.Message;
import com.fireway.cpms.model.User;
import com.fireway.cpms.util.DataUtils;
import org.hibernate.Hibernate;

public class MessageDTO {
    private int id;
    private String content;
    private String creationDate;
    private boolean unread;

    private UserDTO author;
    private UserDTO target;

    public MessageDTO() {

    }

    public MessageDTO(int id, String content, String creationDate, boolean unread) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.unread = unread;
    }

    public MessageDTO(Message entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.creationDate = DataUtils.formatDate(entity.getCreationDate());
        this.unread = entity.isUnread();
        if (Hibernate.isInitialized(entity.getAuthor()) && entity.getAuthor() != null) {
            this.author = new UserDTO(entity.getAuthor());
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }

    public void setAuthor(User author) {
        if (author != null) {
            this.author = new UserDTO(author);
        } else {
            this.author = null;
        }
    }

    public UserDTO getTarget() {
        return target;
    }

    public void setTarget(UserDTO target) {
        this.target = target;
    }

    public void setTarget(User target) {
        if (target != null) {
            this.target = new UserDTO(target);
        } else {
            this.target = null;
        }
    }
}
