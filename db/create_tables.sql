/* 
 * Tables for user access 
 */

create table DiammUser
(
   id           int(10) unsigned auto_increment,
   username     varchar(20) not null,
   displayName  varchar(50) not null,
   passwd       varchar(80) not null,
   email        varchar(80) not null,
   affiliation  varchar(200),
   constraint pk_diammUser primary key(id),
   constraint uk_username   unique(username),
   constraint uk_email      unique(email)
) engine=InnoDB;


create table UserRole
(
   id       int(10) unsigned auto_increment,
   username varchar(20) not null,
   rolename varchar(20) not null,
   constraint pk_userRole primary key(id),
   constraint uk_userRole unique(username, rolename),
   constraint fk_username foreign key(username) references DiammUser(username)
) engine=InnoDB;



/* 
 * Tables for User Collections
 */

create table Collection
(
   id          int(10) unsigned auto_increment,
   title       varchar(30) not null,
   description varchar(100),
   userId      int(10) unsigned not null,
   constraint pk_collection primary key(id),
   constraint uk_titleUser  unique(title, userId),
   constraint fk_userId     foreign key(userId) references DiammUser(id)
) engine=InnoDB;


create table CollectionSource
(
   id           int(10) unsigned auto_increment,
   collectionId int(10) unsigned not null,
   sourceId     int(10) unsigned not null,
   constraint pk_collectionSource primary key(id),
   constraint uk_collectionSource unique(collectionId, sourceId),
   constraint fk_csCollection     foreign key(collectionId) references Collection(id),
   constraint fk_csSource         foreign key(sourceId)     references Source(sourceKey)
) engine=InnoDB;


create table CollectionItem
(
   id           int(10) unsigned auto_increment,
   collectionId int(10) unsigned not null,
   itemId       int(10) unsigned not null,
   constraint pk_collectionItem primary key(id),
   constraint uk_collectionItem unique(collectionId, itemId),
   constraint fk_ciCollection   foreign key(collectionId) references Collection(id),
   constraint fk_ciItem         foreign key(itemId)       references Item(itemKey)
) engine=InnoDB;


create table CollectionImage
(
   id           int(10) unsigned auto_increment,
   collectionId int(10) unsigned not null,
   imageId      int(10) unsigned not null,
   constraint pk_collectionImage primary key(id),
   constraint uk_collectionImage unique(collectionId, imageId),
   constraint fk_cmCollection    foreign key(collectionId) references Collection(id),
   constraint fk_cmImage         foreign key(imageId)      references Image(imageKey)
) engine=InnoDB;



/*
 * Tables for User Notes
 */

create table NoteVisibility
(
   id          int(10) unsigned not null auto_increment,
   code        char(2) not null,
   description varchar(10) not null,
   constraint pk_note_visibility primary key(id)
) engine=InnoDB;

insert into NoteVisibility (id, code, description)
values (1, 'PV', 'private'),
       (2, 'PB', 'public');


alter table NoteType
add code char(3) not null after noteTypeKey;

alter table NoteType
change type description varchar(100) not null;

delete from NoteType
where noteTypekey = 3;

update NoteType
set code = 'COM',
description = 'comment'
where noteTypeKey = 1;

update NoteType
set code = 'TRA',
description = 'text transcription'
where noteTypeKey = 2;


alter table Note
add visibilityId int(10) unsigned not null;

alter table Note
add dateModified timestamp default current_timestamp on update current_timestamp;

alter table Note
add constraint fk_note_visibilty foreign key(visibilityId) references NoteVisibility(id);

alter table Note
add constraint fk_note_user foreign key(userKey) references DiammUser(id);




