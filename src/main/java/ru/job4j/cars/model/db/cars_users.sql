create table item(
    id serial primary key,
    mark varchar not null,
    model varchar not null,
    body_type varchar not null,
    description varchar not null,
    created date not null,
    photo varchar,
    is_active boolean,
    author varchar not null  references users(name)
);

create table users(
    id serial primary key,
    name varchar not null,
    email varchar unique not null,
    password varchar not null
);

create table users_item(
    user_id int,
    items_id int
);

