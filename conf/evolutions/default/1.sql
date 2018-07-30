# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table treasures (
  id                            bigint auto_increment not null,
  name                          varchar(12) not null,
  link                          varchar(255) not null,
  description                   varchar(255),
  version                       bigint not null,
  created                       datetime(6) not null,
  modified                      datetime(6) not null,
  constraint uq_treasures_name unique (name),
  constraint uq_treasures_link unique (link),
  constraint pk_treasures primary key (id)
);


# --- !Downs

drop table if exists treasures;

