package com.fireway.cpms.dto.extra;

import com.fireway.cpms.model.Message;
import com.fireway.cpms.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class DialogListDTO {
    private HashMap<String, DialogDTO> dialogs;

    public DialogListDTO() {

    }

    public DialogListDTO(List<Message> messages) {
        dialogs = new HashMap<>();
        for (Message message : messages) {
            User author = message.getAuthor();
            if (author != null) {
                String id = Integer.toString(author.getId());
                if (!dialogs.containsKey(id)) {
                    dialogs.put(id, new DialogDTO(author));
                }
                dialogs.get(id).checkEntry(message);
            }
        }
    }

    public HashMap<String, DialogDTO> getDialogs() {
        return dialogs;
    }

    public void setDialogs(HashMap<String, DialogDTO> dialogs) {
        this.dialogs = dialogs;
    }
}
