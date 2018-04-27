CREATE TABLE Problems (
    id integer primary key autoincrement,
    name text not null
);

CREATE TABLE Tags (
    id integer primary key autoincrement,
    tag text not null
);

CREATE TABLE problems_tags (
    pr_id integer references Problems(id),
    tag_id integer references Tags(id)
);

CREATE TABLE Contests (
    id integer primary key autoincrement,
    name text not null,
    start_at timestamptz
);

CREATE TABLE Users (
    id integer primary key autoincrement,
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
    id integer primary key autoincrement,
    pr_id integer references Problems(id),
    user_id integer references Users(id),
    status varchar(20) default 'pending',
    submtime timestamp default CURRENT_TIMESTAMP,
    docker_return text,
    verdict varchar(2),
    testid integer default -1,
    comment text
);

CREATE VIEW ExtSubmits AS
    SELECT submits.id as submid, problems.id as prid, problems.name as problem_name, users.id as uid, users.username as uname,
     users.fullname as fullname, submits.status, submits.submtime, submits.docker_return, submits.verdict, submits.testid, submits.comment
    FROM Submits
    JOIN Users on Submits.user_id = Users.id
    JOIN Problems on Submits.pr_id = Problems.id;