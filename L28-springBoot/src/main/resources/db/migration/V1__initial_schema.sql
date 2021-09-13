-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
-- create sequence if not exists hibernate_sequence start with 1 increment by 1;

create table if not exists client
(
    id bigserial not null primary key,
    name varchar(50)
);

create table if not exists address
(
    id bigserial not null primary key,
    street varchar(250),
    client_id bigint,
    CONSTRAINT fk_client
        FOREIGN KEY(client_id)
            REFERENCES client(id)
);

create table if not exists phone
(
    id bigserial not null primary key,
    phone_number varchar(250),
    client_id bigint,
    CONSTRAINT fk_client_2
        FOREIGN KEY(client_id)
            REFERENCES client(id)
);
