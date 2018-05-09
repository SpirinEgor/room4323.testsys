INSERT INTO Problems (name) VALUES ('A + B');
INSERT INTO Problems (name) VALUES ('K-pop');
INSERT INTO Problems (name) VALUES ('Задача с русским названием');
INSERT INTO Problems (name) VALUES ('Задача A');
INSERT INTO Problems (name) VALUES ('Задача B');

INSERT INTO Users (username, fullname, pass) VALUES ('admarkov', 'Alexander Markov', 'hash');
INSERT INTO Users (username, fullname, pass) VALUES ('nadmarkov', 'Александр Марков', 'anotherhash');

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