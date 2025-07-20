
create sequence if not exists appointment_seq start with 1 increment by 1;

create type appointment_status as enum ('SCHEDULED', 'COMPLETED', 'CANCELED');
create cast (varchar AS appointment_status) with inout as IMPLICIT;

create table if not exists appointment
(
    id         bigint primary key default nextval('appointment_seq'),
    start_time timestamp          not null,
    end_time   timestamp          not null,
    notes      text               not null,
    status     appointment_status not null,
    doctor_id  bigint,
    patient_id bigint,
    constraint fk_appointment_doctor foreign key (doctor_id) references users (id) on delete cascade,
    constraint fk_appointment_patient foreign key (patient_id) references users (id) on delete cascade,
    creation_date     timestamp    not null,
    modification_date timestamp
);

-- drop table appointment;
-- drop type appointment_status;
-- drop sequence appointment_seq;