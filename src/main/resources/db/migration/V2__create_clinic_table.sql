CREATE SEQUENCE if not exists clinic_seq START WITH 1 INCREMENT BY 1;

create table if not exists clinic
(
    id                bigint primary key default nextval('clinic_seq'),
    name              varchar(255) not null,
    address           varchar(255) not null,
    city              varchar(255) not null,
    phone_number      varchar(255),
    creation_date     timestamp    not null,
    modification_date timestamp
);

alter table users add column clinic_id bigint;

alter table users add constraint fk_users_clinic foreign key (clinic_id) references clinic(id);

-- drop table if exists clinic;
-- drop sequence if exists clinic_seq
-- alter table users drop constraint fk_users_clinic;
-- alter table users drop column clinic_id;
