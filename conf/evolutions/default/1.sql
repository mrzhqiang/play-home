# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accounts (
  id                            bigint auto_increment not null,
  username                      varchar(16) not null,
  password                      varchar(16) not null,
  level                         varchar(6),
  last_time                     datetime(6),
  last_device                   varchar(255),
  user_id                       bigint,
  version                       bigint not null,
  created                       datetime(6) not null,
  modified                      datetime(6) not null,
  constraint ck_accounts_level check ( level in ('GUEST','ADMIN','AUTHOR','USER')),
  constraint uq_accounts_username unique (username),
  constraint uq_accounts_user_id unique (user_id),
  constraint pk_accounts primary key (id)
);

create table clients (
  id                            bigint auto_increment not null,
  name                          varchar(24) not null,
  apikey                        varchar(40) not null,
  version                       bigint not null,
  created                       datetime(6) not null,
  modified                      datetime(6) not null,
  constraint uq_clients_name unique (name),
  constraint pk_clients primary key (id)
);

create table tokens (
  id                            bigint auto_increment not null,
  access_token                  varchar(255) not null,
  refresh_token                 varchar(255) not null,
  expires_in                    bigint not null,
  account_id                    bigint not null,
  version                       bigint not null,
  created                       datetime(6) not null,
  modified                      datetime(6) not null,
  constraint uq_tokens_access_token unique (access_token),
  constraint pk_tokens primary key (id)
);

create table treasures (
  id                            bigint auto_increment not null,
  account_id                    bigint not null,
  name                          varchar(12) not null,
  link                          varchar(255),
  version                       bigint not null,
  created                       datetime(6) not null,
  modified                      datetime(6) not null,
  constraint uq_treasures_name unique (name),
  constraint pk_treasures primary key (id)
);

create table users (
  id                            bigint auto_increment not null,
  nickname                      varchar(24) not null,
  avatar                        varchar(255),
  first_name                    varchar(255),
  last_name                     varchar(255),
  birthday                      date,
  version                       bigint not null,
  created                       datetime(6) not null,
  modified                      datetime(6) not null,
  constraint pk_users primary key (id)
);

create index index_account_username on accounts (username);
create index index_client_name on clients (name);
create index index_token_access_token on tokens (access_token);
create index index_treasure_name on treasures (name);
create index index_user_nickname on users (nickname);
alter table accounts add constraint fk_accounts_user_id foreign key (user_id) references users (id) on delete restrict on update restrict;

create index ix_tokens_account_id on tokens (account_id);
alter table tokens add constraint fk_tokens_account_id foreign key (account_id) references accounts (id) on delete restrict on update restrict;

create index ix_treasures_account_id on treasures (account_id);
alter table treasures add constraint fk_treasures_account_id foreign key (account_id) references accounts (id) on delete restrict on update restrict;


# --- !Downs

alter table accounts drop foreign key fk_accounts_user_id;

alter table tokens drop foreign key fk_tokens_account_id;
drop index ix_tokens_account_id on tokens;

alter table treasures drop foreign key fk_treasures_account_id;
drop index ix_treasures_account_id on treasures;

drop table if exists accounts;

drop table if exists clients;

drop table if exists tokens;

drop table if exists treasures;

drop table if exists users;

drop index index_account_username on accounts;
drop index index_client_name on clients;
drop index index_token_access_token on tokens;
drop index index_treasure_name on treasures;
drop index index_user_nickname on users;
