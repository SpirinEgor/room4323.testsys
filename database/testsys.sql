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

CREATE TABLE users_contests (
    contest_id integer references Contests(id),
    user_id integer references Users(id),
    role varchar(20) default 'participant'
);

CREATE TABLE problems_contests (
    pr_id integer references Problems(id),
    user_id integer references Users(id)
);

CREATE TABLE Submits (
    id serial primary key,
    pr_id integer references Problems(id),
    user_id integer references Users(id),
    status varchar(20) default 'pending',
    time timestamp default CURRENT_TIMESTAMP,
    verdict text
);
