create sequence if not exists users_seq start with 1 increment by 1;

create table if not exists users
(
    id                bigint primary key default nextval('users_seq'),
    username          varchar(255) not null,
    email             varchar(255) not null,
    first_name        varchar(255) not null,
    last_name         varchar(255) not null,
    phone_number      varchar(255) not null,
    creation_date     timestamp    not null,
    modification_date timestamp
);

-- drop table if exists users;