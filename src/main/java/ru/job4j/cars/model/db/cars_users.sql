create table item(
    id serial primary key,
    description varchar,
    mark varchar not null,
    body_type varchar not null,
    photo bytea,
    user_id int references user(id)
);

create table user(
    id serial primary key,
    name varchar not null,
    is_author boolean
)