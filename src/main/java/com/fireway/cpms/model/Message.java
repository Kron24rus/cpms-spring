package com.fireway.cpms.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "Message", schema = "", catalog = "dream_team_cpms")
public class Message implements Serializable {
    private int id;
    private String content;
    private Timestamp creationDate;
    private boolean unread;
    private User author;
    private User target;

    private int authorForeignKey;
    private int targetForeignKey;

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
    @Column(name = "Content", nullable = false)
    @Type(type = "text")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "CreationDate")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "Unread", nullable = false, columnDefinition = "int")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AuthorId")
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TargetUserId")
    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    @Column(name = "AuthorId", updatable = false, insertable = false)
    public int getAuthorForeignKey() {
        return authorForeignKey;
    }

    public void setAuthorForeignKey(int authorForeignKey) {
        this.authorForeignKey = authorForeignKey;
    }

    @Column(name = "TargetUserId", updatable = false, insertable = false)
    public int getTargetForeignKey() {
        return targetForeignKey;
    }

    public void setTargetForeignKey(int targetForeignKey) {
        this.targetForeignKey = targetForeignKey;
    }
}
