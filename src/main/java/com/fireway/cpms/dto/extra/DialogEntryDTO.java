package com.fireway.cpms.dto.extra;

import com.fireway.cpms.model.Message;
import com.fireway.cpms.util.DataUtils;

public class DialogEntryDTO {
    private int id;
    private String content;
    private String creationDate;
    private boolean unread;

    public DialogEntryDTO() {

    }

    public DialogEntryDTO(Message message) {
        if (message != null) {
            this.id = message.getId();
            this.content = message.getContent();
            this.creationDate = DataUtils.formatDate(message.getCreationDate());
            this.unread = message.isUnread();
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
}
