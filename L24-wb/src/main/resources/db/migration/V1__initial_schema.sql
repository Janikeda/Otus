-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
-- create sequence if not exists hibernate_sequence start with 1 increment by 1;

create table if not exists address
(
    address_id bigserial not null primary key,
    street varchar(250)
);

create table if not exists client
(
    client_id bigserial not null primary key,
    name varchar(50),
    address_id bigint,
    CONSTRAINT fk_client
        FOREIGN KEY(address_id)
            REFERENCES address(address_id)
);

create table if not exists phone
(
    phone_id bigserial not null primary key,
    phone_number varchar(250),
    client_id bigint,
    CONSTRAINT fk_client_2
        FOREIGN KEY(client_id)
            REFERENCES client(client_id)
);
