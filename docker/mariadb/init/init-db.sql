create table login
(
    id      varchar(255) not null,
    instant datetime(6),
    token   varchar(512),
    id_user varchar(255) not null,
    primary key (id)
) engine=InnoDB;

create table user_roles
(
    user_id varchar(255) not null,
    roles   varchar(255)
) engine=InnoDB;

create table users
(
    id           varchar(255) not null,
    email        varchar(255),
    username     varchar(255),
    password     varchar(255),
    primary key (id)
) engine=InnoDB;

insert into users
values ('261eafa9-4611-44e9-9cfe-6ede4b41324a',
        'contact@madirex.com',
        'Admin',
        '$2a$12$tkmFeFcSZ4CLCgjbNhgrO.1D3izDlrNjidrkOZZlOvPlJm2D/oBYq');

insert into user_roles (user_id, roles)
values ('261eafa9-4611-44e9-9cfe-6ede4b41324a', 'ADMIN');
insert into user_roles (user_id, roles)
values ('261eafa9-4611-44e9-9cfe-6ede4b41324a', 'PLAYER');
