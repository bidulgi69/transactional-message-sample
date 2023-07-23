drop table if exists orders;
create table if not exists orders(
    order_id     int         primary key auto_increment,
    paid_at      timestamp   not null,
    agency          varchar(32) not null,
    transaction_id   varchar(64) not null unique,
    created_at       timestamp   default current_timestamp,
    updated_at       timestamp   default current_timestamp
);

drop table if exists outbox;
create table outbox(
    outbox_id       int primary key auto_increment,
    transaction_id      varchar(64) not null unique,
    topic       varchar(64) not null,
    partition_key   varchar(32) not null,
    payload         text    not null,
    created_at  timestamp   default current_timestamp,
    updated_at  timestamp   default current_timestamp
);

