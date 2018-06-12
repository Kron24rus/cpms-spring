package com.fireway.cpms.model;

import javax.persistence.*;

@Entity
@Table(name = "LogType", schema = "", catalog = "dream_team_cpms")
public class LogType {
    public static final String PROJECT_CREATED = "newproj";
    public static final String PROJECT_NAME_UPDATED = "updprojname";
    public static final String PROJECT_DESCR_UPDATED = "updprojdesc";
    public static final String PROJECT_ACTIVE_UPDATED = "updprojactive";
    public static final String PROJECT_PRIORITY_UPDATED = "updprojpriority";

    public static final String MEMBER_ASSIGNED = "usrprojassign";
    public static final String MEMBER_ROLE_UPDATED = "usrprojroleupd";

    public static final String PROJECT_STAGE_CREATED = "newstage";

    public static final String DEPLOYMENT_CREATED = "newdepl";
    public static final String DEPLOYMENT_NAME_UPDATED = "upddeplname";
    public static final String DEPLOYMENT_URL_UPDATED = "upddeplurl";
    public static final String DEPLOYMENT_DESCR_UPDATED = "upddepldesc";
    public static final String DEPLOYMENT_VERSION_UPDATED = "upddeplversion";
    public static final String DEPLOYMENT_REMOVED_UPDATED = "upddeplremoved";
    public static final String DEPLOYMENT_DELETED = "deldepl";

    private int id;
    private String slug;
    private String name;

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
    @Column(name = "Slug", nullable = false, length = 64, unique = true)
    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Basic
    @Column(name = "Name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
