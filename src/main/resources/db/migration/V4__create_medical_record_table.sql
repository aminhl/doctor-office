create sequence if not exists medical_record_seq start with 1 increment by 1;

create table if not exists medical_record
(
    id           bigint primary key default nextval('medical_record_seq'),
    patient_id   bigint       not null,
    doctor_id    bigint       not null,
    date         date         not null,
    diagnosis    varchar(255) not null,
    treatment    varchar(255) not null,
    prescription varchar(255) not null,
    notes        text,
    constraint fk_medical_record_patient foreign key (patient_id) references users (id) on delete cascade,
    constraint fk_medical_record_doctor foreign key (doctor_id) references users (id) on delete cascade
);

-- drop sequence medical_record_seq;
-- drop table medical_record;