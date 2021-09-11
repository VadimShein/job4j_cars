create table item(
    id serial primary key,
    description varchar,
    mark varchar not null,
    body_type varchar not null,
    photo bytea,
    is_active boolean,
    user_id int references PUser(id)
);

create table PUser(
    id serial primary key,
    name varchar not null
)