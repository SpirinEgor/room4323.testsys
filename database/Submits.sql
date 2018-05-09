--Угловые скобочки - заглушки, которые нужно заполнить данными

--Добавление сабмита в очередь
INSERT INTO Submits (pr_id, user_id) VALUES(<id задачи>, <id пользователя>);
--Докер берет сабмит из очереди, ставит статус running
UPDATE Submits SET status = 'running' where id = <id сабмита>;
--Докер дотестировал, задача получила OK
UPDATE Submits SET status = 'finished', docker_return = <возврат докера>, verdict = 'OK' where id = <id сабмита>;
--Докер дотестировал, задача не получила ОК
UPDATE Submits SET status = 'finished', docker_return = <возврат докера>, verdict = <'OK'|'WA'|'TL'|...>, testid = <номер теста, на котором программа упала>, comment=<комментарий чекера> where id = <id сабмита>;
--Получить все сабмиты пользователя
SELECT * from ExtSubmits where uid = <id пользователя>
--Получить все сабмиты пользователя по задаче
SELECT * from Submits where user_id = <id пользователя> and pr_id = <id задачи>
