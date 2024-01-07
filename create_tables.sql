CREATE TABLE IF NOT EXISTS users
(
    id        uuid default gen_random_uuid() not null
        constraint user_pk
            primary key,
    username  varchar                        not null,
    password  varchar                        not null,
    jwt_token text
);

alter table users
    owner to root;

create unique index user_id_uindex
    on users (id);

create unique index user_username_uindex
    on users (username);

CREATE TABLE IF NOT EXISTS task
(
    description     text                                not null,
    due_date        timestamp,
    check_mark      boolean   default false             not null,
    completion_date timestamp,
    creation_date   timestamp default now()             not null,
    id              uuid      default gen_random_uuid() not null
        constraint task_pk
            primary key,
    user_id         uuid                                not null
        constraint task_user_id_fk
            references users
);

alter table task
    owner to root;