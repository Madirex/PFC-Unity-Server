-- DROP DATABASE IF EXISTS gameserver; /* ONLY ON RESET PURPOSES (need restart and comment this line also)*/
/* Usuario y Login */
CREATE TABLE login
(
    id      varchar(255) not null,
    instant datetime(6),
    token   varchar(512),
    id_user varchar(255) not null,
    primary key (id)
) engine=InnoDB;

CREATE TABLE user_roles
(
    user_id varchar(255) not null,
    roles   varchar(255)
) engine=InnoDB;

CREATE TABLE users
(
    id       varchar(255) not null,
    email    varchar(255),
    username varchar(255),
    password varchar(255),
    primary key (id)
) engine=InnoDB;

/* Score */
CREATE TABLE score
(
    id     varchar(255) not null,
    user   varchar(255) not null,
    level  int,
    amount int,
    date   datetime(6),
    primary key (id)
) engine=InnoDB;

/* Tienda e Items */
CREATE TABLE item
(
    id          varchar(255) not null,
    user        varchar(255),
    shop      varchar(255),
    name        varchar(255),
    price       int,
    item_type    varchar(255),
    amount_power double,
    primary key (id)
) engine=InnoDB;

CREATE TABLE shop
(
    id       varchar(255) not null,
    shop_name varchar(255),
    primary key (id)
) engine=InnoDB;

/* Crear el usuario admin */
INSERT INTO users
VALUES ('261eafa9-4611-44e9-9cfe-6ede4b41324a',
        'contact@madirex.com',
        'Admin',
        '$2a$12$tkmFeFcSZ4CLCgjbNhgrO.1D3izDlrNjidrkOZZlOvPlJm2D/oBYq');

/* FOREIGN KEYS */
ALTER TABLE users
    ADD CONSTRAINT UK_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);
ALTER TABLE users
    ADD CONSTRAINT UK_r43af9ap4edm43mmtq01oddj6 UNIQUE (username);
ALTER TABLE user_roles
    ADD CONSTRAINT FKhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES users (id);
ALTER TABLE login
    ADD CONSTRAINT FKddrmlhg56oaq3coq9xohjulr4 FOREIGN KEY (id_user) REFERENCES users (id);
ALTER TABLE shop
    ADD CONSTRAINT UK_h4rqgjwnqidx6mvj4i22dxwxe UNIQUE (shop_name);
ALTER TABLE item
    ADD CONSTRAINT FK5k0oem3ypbsnpc0kt1htx37w4 FOREIGN KEY (shop) REFERENCES shop (id);
ALTER TABLE item
    ADD CONSTRAINT FKmlrfgone1e43v2q6qrtjvb9jh FOREIGN KEY (user) REFERENCES users (id);
ALTER TABLE score
    ADD CONSTRAINT FKmldusone9n43v3q6qrtjvb9jh FOREIGN KEY (user) REFERENCES users (id);

/* INSERTAR */
INSERT INTO user_roles (user_id, roles)
VALUES ('261eafa9-4611-44e9-9cfe-6ede4b41324a', 'ADMIN');
INSERT INTO user_roles (user_id, roles)
VALUES ('261eafa9-4611-44e9-9cfe-6ede4b41324a', 'PLAYER');

/* Crear scores de prueba */
INSERT INTO score (id, user, level, amount, date)
VALUES ('82b5398e-c1cf-4282-a253-8d8c74938805', /*id*/
        '261eafa9-4611-44e9-9cfe-6ede4b41324a', /*user*/
        1, /*level*/
        500, /*amount*/
        '2022-02-01 09:14:00.04'); /*date*/

INSERT INTO score (id, user, level, amount, date)
VALUES ('ab5ecc7a-da86-483e-8c31-7f04834489cb', /*id*/
        '261eafa9-4611-44e9-9cfe-6ede4b41324a', /*user*/
        1, /*level*/
        1300, /*amount*/
        '2022-03-13 11:58:00.07');

INSERT INTO score (id, user, level, amount, date)
VALUES ('538d36f1-e89e-40d3-a5e8-f0fc3e288592', /*id*/
        '261eafa9-4611-44e9-9cfe-6ede4b41324a', /*user*/
        1, /*level*/
        200, /*amount*/
        '2022-04-05 07:32:00.07');

INSERT INTO score (id, user, level, amount, date)
VALUES ('eecd076e-c463-4eec-a798-1cfc07a1ffb8', /*id*/
        '261eafa9-4611-44e9-9cfe-6ede4b41324a', /*user*/
        2, /*level*/
        1000, /*amount*/
        '2022-02-13 09:57:00.07');
/*date*/

/* Crear tiendas de prueba */
INSERT INTO shop (id, shop_name)
VALUES ('a8b8f8e7-f8e7-4f7f-a8e7-f8e7f8e7f8e7', /*id*/
        'Tienda 1'); /*shop_name*/
INSERT INTO shop (id, shop_name)
VALUES ('c2e04e7c-97a3-428f-aaeb-86c14b3d94c8', /*id*/
        'Tienda 2');
/*shop_name*/

/* Crear ítems de prueba */
INSERT INTO item (id, user, shop, name, price, item_type, amount_power)
VALUES ('d6eef0f1-8ce4-4d7d-8c4d-250917def843', /*id*/
        '261eafa9-4611-44e9-9cfe-6ede4b41324a', /*user*/
        'a8b8f8e7-f8e7-4f7f-a8e7-f8e7f8e7f8e7', /*shop*/
        'Pistola', /*name*/
        100, /*price*/
        'WEAPON', /*item_type*/
        10.0); /*amount_power*/

INSERT INTO item (id, user, shop, name, price, item_type, amount_power)
VALUES ('e9cb4fa0-0b77-4665-957b-d52d33123fda', /*id*/
        '261eafa9-4611-44e9-9cfe-6ede4b41324a', /*user*/
        'a8b8f8e7-f8e7-4f7f-a8e7-f8e7f8e7f8e7', /*shop*/
        'Armadura cabeza', /*name*/
        200, /*price*/
        'ARMOR', /*item_type*/
        5.5); /*amount_power*/