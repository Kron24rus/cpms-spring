# MYSQL

DROP DATABASE IF EXISTS dream_team_cpms;
DROP USER IF EXISTS 'cpms_admin'@'localhost';

CREATE DATABASE dream_team_cpms 
    DEFAULT CHARACTER SET utf8
    DEFAULT COLLATE utf8_general_ci;

USE dream_team_cpms;

SET NAMES 'utf8';

CREATE USER 'cpms_admin'@'localhost'
    IDENTIFIED BY 'Dq81la+23Ez_0w';

GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, INDEX, ALTER
    ON dream_team_cpms.*
    TO 'cpms_admin'@'localhost';

# TABLES

CREATE TABLE EmployeePosition
(
    Id              INT NOT NULL AUTO_INCREMENT,
    Name            VARCHAR(100) NOT NULL UNIQUE,
    Description     TEXT,
    
    PRIMARY KEY (Id)
);

CREATE TABLE EmployeeInfo
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Description         TEXT,
    EmployeePositionId  INT,

    PRIMARY KEY (Id),
    FOREIGN KEY (EmployeePositionId) REFERENCES EmployeePosition (Id)
);

CREATE TABLE User
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Login               VARCHAR(255) NOT NULL UNIQUE,
    Password            VARCHAR(255) NOT NULL,
    FirstName           VARCHAR(32) NOT NULL,
    LastName            VARCHAR(32) NOT NULL,
    MiddleName          VARCHAR(32),
    IsAdmin             BOOLEAN NOT NULL DEFAULT FALSE,
    EmployeeInfoId      INT,

    PRIMARY KEY (Id),
    FOREIGN KEY (EmployeeInfoId) REFERENCES EmployeeInfo (Id)
);

CREATE TABLE Message
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Content             TEXT NOT NULL,
    CreationDate        DATETIME DEFAULT current_timestamp,
    Unread              BOOLEAN NOT NULL DEFAULT FALSE,
    AuthorId            INT NOT NULL,
    TargetUserId        INT NOT NULL,

    PRIMARY KEY (Id),
    FOREIGN KEY (AuthorId) REFERENCES User (Id),
    FOREIGN KEY (TargetUserId) REFERENCES User (Id)
);

CREATE TABLE ProjectType
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Slug                VARCHAR(64) NOT NULL UNIQUE,
    Name                VARCHAR(255) NOT NULL UNIQUE,
    Description         TEXT,

    PRIMARY KEY (Id)
);

CREATE TABLE ProjectStageTemplate
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Name                VARCHAR(255) NOT NULL UNIQUE,
    Description         TEXT,
    ProjectTypeId       INT NOT NULL,

    PRIMARY KEY (Id),
    FOREIGN KEY (ProjectTypeId) REFERENCES ProjectType (Id)
);

CREATE TABLE Project
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Name                VARCHAR(255) NOT NULL UNIQUE,
    Description         TEXT,
    Active              BOOLEAN NOT NULL DEFAULT FALSE,
    Priority            INT,
    ProjectTypeId       INT NOT NULL,

    PRIMARY KEY (Id),
    FOREIGN KEY (ProjectTypeId) REFERENCES ProjectType (Id)
);

CREATE TABLE ProjectStage
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Name                VARCHAR(255) NOT NULL,
    Ordr                INT NOT NULL,
    StartDate           DATETIME DEFAULT current_timestamp,
    EndDate             DATETIME,
    ProjectId           INT NOT NULL,
    TemplateId          INT NOT NULL,

    PRIMARY KEY (Id),
    FOREIGN KEY (ProjectId) REFERENCES Project (Id),
    FOREIGN KEY (TemplateId) REFERENCES ProjectStageTemplate (Id)
);

CREATE TABLE Deployment
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Name                VARCHAR(255) NOT NULL,
    Version             VARCHAR(64),
    URL                 VARCHAR(255) NOT NULL,
    Description         TEXT,
    Removed             BOOLEAN NOT NULL DEFAULT FALSE,
    ProjectStageId      INT NOT NULL,

    PRIMARY KEY (Id),
    FOREIGN KEY (ProjectStageId) REFERENCES ProjectStage (Id)
);

CREATE TABLE UserToProjectStage
(
    ProjectStageId      INT NOT NULL,
    UserId              INT NOT NULL,

    PRIMARY KEY (ProjectStageId, UserId),
    FOREIGN KEY (ProjectStageId) REFERENCES ProjectStage (Id),
    FOREIGN KEY (UserId) REFERENCES User (Id)
);

CREATE TABLE ProjectRole
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Slug                VARCHAR(64) NOT NULL UNIQUE,
    Name                VARCHAR(255) NOT NULL UNIQUE,

    PRIMARY KEY (Id)
);

CREATE TABLE UserToProject
(
    UserId              INT NOT NULL,
    ProjectId           INT NOT NULL,
    RoleId              INT NOT NULL,

    PRIMARY KEY (UserId, ProjectId),
    FOREIGN KEY (UserId) REFERENCES User (Id),
    FOREIGN KEY (ProjectId) REFERENCES Project (Id),
    FOREIGN KEY (RoleId) REFERENCES ProjectRole (Id)
);

CREATE TABLE LogType
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Slug                VARCHAR(64) NOT NULL UNIQUE,
    Name                VARCHAR(255) NOT NULL UNIQUE,

    PRIMARY KEY (Id)
);

CREATE TABLE ProjectLog
(
    Id                  INT NOT NULL AUTO_INCREMENT,
    Date                DATETIME NOT NULL DEFAULT current_timestamp,
    OldValue            TEXT,
    NewValue            TEXT,
    LogTypeId           INT NOT NULL,
    AuthorUserId        INT NOT NULL,
    TargetUserId        INT,
    ProjectId           INT NOT NULL,
    ProjectStageId      INT,
    DeploymentId        INT,

    PRIMARY KEY (Id),
    FOREIGN KEY (LogTypeId) REFERENCES LogType(Id),
    FOREIGN KEY (AuthorUserId) REFERENCES User(Id),
    FOREIGN KEY (TargetUserId) REFERENCES User(Id),
    FOREIGN KEY (ProjectId) REFERENCES Project(Id),
    FOREIGN KEY (ProjectStageId) REFERENCES ProjectStage(Id),
    FOREIGN KEY (DeploymentId) REFERENCES Deployment(Id)
);

SET @dreamteam = '947:5b424061353236346632:67115f74e0c30d154a5cc9625d628679e8a6835a95e2701305a787c36f59e5b2b0a52c1c0d19e5a49ea5e7c33b816c8ec208d9a30fc9d33b7b602b24e8db53bc';

INSERT INTO EmployeePosition (Name)
    VALUES ('Junior Software Developer'),
        ('Software Developer'),
        ('Senior Software Developer'),
        ('System administrator');

SET @SysAdminPos = LAST_INSERT_ID();

INSERT INTO EmployeeInfo (Description, EmployeePositionId)
    VALUES ('CPMS Admin', @SysAdminPos);

SET @SysAdminInfo = LAST_INSERT_ID();

INSERT INTO User (Login, Password, FirstName, LastName, IsAdmin, EmployeeInfoId)
    VALUES ('admin', @dreamteam, "Ivan", "Ivanov", TRUE, @SysAdminInfo);

SET @SysAdmin = LAST_INSERT_ID();

INSERT INTO EmployeeInfo (Description)
    VALUES ('Test User');

SET @TestUserInfo = LAST_INSERT_ID();

INSERT INTO User (Login, Password, FirstName, LastName, IsAdmin, EmployeeInfoId)
    VALUES ('test', @dreamteam, "Petr", "Petrov", TRUE, @TestUserInfo);

SET @TestUser = LAST_INSERT_ID();

INSERT INTO ProjectType (Slug, Name) 
    VALUES ('waterfall', 'Waterfall');

SET @WaterfallProjectType = LAST_INSERT_ID();

INSERT INTO ProjectType (Slug, Name) 
    VALUES ('scrum', 'Scrum');

SET @ScrumProjectType = LAST_INSERT_ID();

INSERT INTO ProjectStageTemplate (Name, ProjectTypeId)
    VALUES
        ('Planning', @WaterfallProjectType),
        ('Architecture design', @WaterfallProjectType),
        ('Development', @WaterfallProjectType),
        ('Testing', @WaterfallProjectType),
        ('Sprint', @ScrumProjectType);

INSERT INTO ProjectRole (Slug, Name)
    VALUES
        ('customer', 'Customer'),
        ('manager', 'Project Manager'),
        ('bedev', 'Back-end developer'),
        ('fedev', 'Front-end developer'),
        ('fsdev', 'Full-stack developer'),
        ('mdev', 'Mobile developer'),
        ('test', 'Tester');

INSERT INTO ProjectRole (Slug, Name)
    VALUES
      ('dev', 'Developer');

SET @DeveloperRole = LAST_INSERT_ID();

INSERT INTO LogType (Slug, Name)
    VALUES 
        ('newproj', 'Project created'),
        ('updprojname', 'Project name updated'),
        ('updprojdesc', 'Project description updated'),
        ('updprojactive', 'Project active state updated'),
        ('updprojpriority', 'Project priority updated'),
        ('newstage', 'Project stage created'),
        ('updstageorder', 'Project stage order updated'),
        ('updstagestart', 'Project stage start date updated'),
        ('updstageend', 'Project stage end date updated'),
        ('newdepl', 'Deployment created'),
        ('upddeplname', 'Deployment name updated'),
        ('upddeplurl', 'Deployment url updated'),
        ('upddepldesc', 'Deployment description updated'),
        ('upddeplversion', 'Deployment version updated'),
        ('upddeplremoved', 'Deployment removed state updated'),
        ('deldepl', 'Deployment removed'),
        ('usrprojassign', 'User assigned to the project'),
        ('usrprojunassign', 'User unassigned from the project'),
        ('usrprojroleupd', 'Updated user\'s role in the project');

INSERT INTO Message(Content, AuthorId, TargetUserId)
    VALUES
      ('Message from admin', @SysAdmin, @TestUser),
      ('Message to admin', @TestUser, @SysAdmin);

INSERT INTO Project(Name, Description, Active, Priority, ProjectTypeId)
    VALUES ('CPMS', 'Corporate Project Management System', TRUE, 1, @WaterfallProjectType);
SET @CPMSProject = LAST_INSERT_ID();

INSERT INTO UserToProject(UserId, ProjectId, RoleId)
    VALUES (@TestUser, @CPMSProject, @DeveloperRole);