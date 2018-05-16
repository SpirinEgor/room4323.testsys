INSERT INTO Problems (name) VALUES ('A + B');
INSERT INTO Problems (name) VALUES ('K-pop');
INSERT INTO Problems (name) VALUES ('Задача с русским названием');
INSERT INTO Problems (name) VALUES ('Задача A');
INSERT INTO Problems (name) VALUES ('Задача B');

INSERT INTO Users (username, fullname, pass) VALUES ('test1', 'TEST USER #1', 'e6c3da5b206634d7f3f3586d747ffdb36b5c675757b380c6a5fe5c570c714349'); -- pass1
INSERT INTO Users (username, fullname, pass) VALUES ('test2', 'TEST USER #2', '1ba3d16e9881959f8c9a9762854f72c6e6321cdd44358a10a4e939033117eab9'); -- pass2

INSERT INTO Submits (pr_id, user_id) VALUES(1, 1);
UPDATE Submits SET status = 'finished', docker_return = 'array from docker', verdict = 'OK' where id = 1;
INSERT INTO Submits (pr_id, user_id) VALUES(1, 2);
UPDATE Submits SET status = 'finished', docker_return = 'array from docker', verdict = 'TL', testid = 12, comment="" where id = 2;
INSERT INTO Submits (pr_id, user_id) VALUES(2, 1);
UPDATE Submits SET status = 'finished', docker_return = 'array from docker', verdict = 'WA', testid = 5, comment="expected 'a', found 'b'" where id = 3;
INSERT INTO Submits (pr_id, user_id) VALUES(3, 1);
UPDATE Submits SET status = 'running' where id = 4;
INSERT INTO Submits (pr_id, user_id) VALUES(2, 2);
UPDATE Submits SET status = 'running' where id = 5;
INSERT INTO Submits (pr_id, user_id) VALUES(4, 1);
INSERT INTO Submits (pr_id, user_id) VALUES(5, 1);
