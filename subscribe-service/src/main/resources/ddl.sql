drop table if exists payments;
create table if not exists payments(
     payment_id     int         primary key auto_increment,
     transaction_id varchar(64) not null unique,
     order_id       int         not null,
     agency        varchar(64) not null,
     success            boolean     default true,
     created_at       timestamp   default current_timestamp,
     updated_at       timestamp   default current_timestamp
);