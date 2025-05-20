drop table if exists admin cascade;
drop table if exists gate cascade;
drop table if exists flight cascade;

create table admin(
    id serial primary key,
    oauth2_id integer not null
);

create table gate(
    id serial primary key,
    number text not null unique
);

create table flight(
    id serial primary key,
    number text not null unique ,
    destination text not null,
    departure_time time not null,
    gate_number text references gate(number)
);
