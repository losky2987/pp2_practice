drop table if exists admin cascade;
drop table if exists gate cascade;
drop table if exists flight cascade;

create table admin(
    user_id text primary key
);

create table gate(
    number text primary key,
    flights text references flight(number)
);

create table flight(
    number text primary key,
    destination text not null,
    departure_time time not null
);
