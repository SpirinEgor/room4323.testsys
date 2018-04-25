CREATE TABLE Problems (
    id serial primary key,
    name text not null
);

CREATE TABLE Tags (
    id serial primary key,
    tag text not null
);

CREATE TABLE problems_tags (
    pr_id integer references Problems(id),
    tag_id integer references Tags(id)
);

CREATE TABLE Contests (
    id serial primary key,
    name text not null,
    start_at timestamptz
);

CREATE TABLE Users (
    id serial primary key,
    username varchar(20) not null,
    fullname text not null
);

CREATE TYPE contest_role AS ENUM ('participant', 'jury');

CREATE TABLE users_contests (
    contest_id integer references Contests(id),
    user_id integer references Users(id),
    role contest_role default 'participant'
);

CREATE TABLE problems_contests (
    pr_id integer references Problems(id),
    user_id integer references Users(id)
);

CREATE TYPE submit_status AS ENUM('pending', 'running', 'ready');

CREATE TABLE Submits (
    id serial primary key,
    pr_id integer references Problems(id),
    user_id integer references Users(id),
    status submit_status default 'pending',
    time timestamptz default now(),
    verdict json
);