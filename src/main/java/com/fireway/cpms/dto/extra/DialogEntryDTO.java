package com.fireway.cpms.dto.extra;

import com.fireway.cpms.dto.UserDTO;
import com.fireway.cpms.model.Message;
import com.fireway.cpms.util.DataUtils;
import org.hibernate.Hibernate;

public class DialogEntryDTO {
    private int id;
    private String content;
    private String creationDate;
    private String timestamp;
    private boolean unread;
    private UserDTO target;
    private UserDTO author;

    public DialogEntryDTO() {

    }

    public DialogEntryDTO(Message message) {
        if (message != null) {
            this.id = message.getId();
            this.content = message.getContent();
            this.creationDate = DataUtils.formatDate(message.getCreationDate());
            this.timestamp = DataUtils.formatTimestamp(message.getCreationDate());
            this.unread = message.isUnread();
            if (Hibernate.isInitialized(message.getAuthor()) && message.getAuthor() != null) {
                this.author = new UserDTO(message.getAuthor());
            }
            if (Hibernate.isInitialized(message.getTarget()) && message.getTarget() != null) {
                this.target = new UserDTO(message.getTarget());
            }
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UserDTO getTarget() {
        return target;
    }

    public void setTarget(UserDTO target) {
        this.target = target;
    }

    public UserDTO getAuthor() {
        return author;
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }
}
