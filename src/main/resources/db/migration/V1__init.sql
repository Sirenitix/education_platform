create table users
(
    id                BIGSERIAL    NOT NULL,
    username          VARCHAR(250) NOT NULL,
    password          VARCHAR(250) NOT NULL,
    role              VARCHAR(250) NOT NULL,
    enabled           BOOLEAN,
    first_name        VARCHAR(255) NOT NULL,
    last_name         VARCHAR(255) NOT NULL,
    teacher_group     BIGINT(255),
    science_post_id   BIGINT,
    reflexion_post_id BIGINT,
    PRIMARY KEY (id)
);

create table science_post
(
    id              BIGSERIAL    not null primary key,
    content         VARCHAR(255) not null,
    user_creator_id BIGINT       not null,
    likes           BIGINT,
    foreign key (user_creator_id) references users (id)
);

create table reflexion_post
(
    id              BIGSERIAL    not null primary key,
    content         VARCHAR(255) not null,
    user_creator_id BIGINT       not null,
    likes           BIGINT,
    foreign key (user_creator_id) references users (id)
);

create table science_post_comment
(
    id              BIGSERIAL    not null primary key,
    user_creator_id BIGINT       not null,
    content         VARCHAR(255) not null,
    parent_id       BIGINT       not null,
    foreign key (parent_id) references science_post (id)

);

create table reflexion_post_comment
(
    id              BIGSERIAL    not null primary key,
    user_creator_id BIGINT       not null,
    content         VARCHAR(255) not null,
    parent_id       BIGINT       not null,
    foreign key (parent_id) references reflexion_post (id)

);