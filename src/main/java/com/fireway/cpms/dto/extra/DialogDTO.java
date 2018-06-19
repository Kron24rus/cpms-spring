package com.fireway.cpms.dto.extra;

import com.fireway.cpms.dto.UserDTO;
import com.fireway.cpms.exception.BadRequestException;
import com.fireway.cpms.model.Message;
import com.fireway.cpms.model.User;
import com.fireway.cpms.util.DataUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DialogDTO {
    private UserDTO user;
    private List<DialogEntryDTO> entries;
    private String timestamp;

    public DialogDTO() {
    }

    public DialogDTO(User user) {
        if (user != null) {
            this.user = new UserDTO(user);
        }
    }

    private Date getDate() {
        if (this.timestamp != null) {
            try {
                return DataUtils.parseDate(this.timestamp);
            } catch (BadRequestException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void addEntry(Message message) {
        if (entries == null) {
            entries = new ArrayList<>();
        }

        if (message != null) {
            DialogEntryDTO entry = new DialogEntryDTO(message);
            Date date = getDate();
            if (date == null || date.before(message.getCreationDate())) {
                this.timestamp = entry.getCreationDate();
            }
            entries.add(new DialogEntryDTO(message));
        }
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<DialogEntryDTO> getEntries() {
        return entries;
    }

    public void setEntries(List<DialogEntryDTO> entries) {
        this.entries = entries;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
