drop table if exists users CASCADE;
drop table if exists restaurant CASCADE;
drop table if exists notice CASCADE;
drop table if exists icon CASCADE;
drop table if exists discussion CASCADE;
drop table if exists comment CASCADE;
drop table if exists suggestion CASCADE;
drop table if exists role CASCADE;
drop table if exists user_role CASCADE;


create table users
(
    id            bigint generated by default as identity,
    created_date  timestamp    not null,
    deleted_date  timestamp    not null,
    user_email    varchar(255) not null,
    user_name     varchar(255) not null,
    user_nickname varchar(255) not null,
    user_id       varchar(255) not null,
    user_password varchar(255) not null,
    enabled       BOOLEAN      not null,
    primary key (id)
);

create table restaurant
(
    id       bigint generated by default as identity,
    category varchar(255) null,
    content  text,
    icon     varchar(255) null,
    name     varchar(255) null,
    primary key (id)
);

create table notice
(
    id           bigint generated by default as identity,
    closed       BOOLEAN null,
    closed_date  varchar(255) null,
    content      text,
    created_date varchar(255) not null,
    image        varchar(255) null,
    title        varchar(20)  not null,
    primary key (id)
);
create table icon
(
    id   bigint generated by default as identity,
    icon varchar(255) null,
    link varchar(255) not null,
    name varchar(255) null,
    primary key (id)
);

create table discussion
(
    id           bigint generated by default as identity,
    closed       BOOLEAN null,
    closed_date  varchar(255) null,
    content      text,
    created_date varchar(255) not null,
    title        varchar(20)  not null,
    user_id      bigint null,
    primary key (id),
    foreign key (user_id) references users (id)
);

create table comment
(
    id            bigint generated by default as identity,
    comment       text,
    created_date  varchar(255) not null,
    modified_date varchar(255) null,
    discussion_id bigint null,
    user_id       bigint null,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (discussion_id) references discussion (id)
);

create table suggestion
(
    id            bigint generated by default as identity,
    closed        BOOLEAN null,
    closed_date   varchar(255) null,
    content       text,
    created_date  varchar(255) not null,
    title         varchar(20)  not null,
    restaurant_id bigint null,
    user_id       bigint null,
    primary key (id),
    foreign key (restaurant_id) references restaurant (id),
    foreign key (user_id) references users (id)
);

create table role
(
    id   bigint generated by default as identity,
    role varchar(255) null,
    primary key (id)
);

create table user_role
(
    user_role_id bigint generated by default as identity,
    created      timestamp null,
    role_id      bigint null,
    user_id      bigint null,
    primary key (user_role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references role (id)
);