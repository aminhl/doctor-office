create table users
(
    id                bigint       not null,
    username          varchar(255) not null,
    email             varchar(255) not null,
    first_name        varchar(255) not null,
    last_name         varchar(255) not null,
    phone_number      varchar(255) not null,
    creation_date     timestamp    not null,
    modification_date timestamp,
    primary key (id)
);

-- drop table if exists users;