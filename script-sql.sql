DROP TABLE users CASCADE;
DROP TABLE friends;
DROP TABLE groups;
DROP TABLE friend_group;

CREATE TABLE users 
(
uid SERIAL PRIMARY KEY,
ufname VARCHAR(60) NOT NULL,
ulname VARCHAR(60) NOT NULL,
usex VARCHAR(1) CHECK ( usex IN ('m', 'f')),
ucity VARCHAR(30) NOT NULL,
uemail VARCHAR(50) UNIQUE NOT NULL,
upass VARCHAR(20) NOT NULL
);

CREATE TABLE friends
(
fid SERIAL PRIMARY KEY,
fuser_id INTEGER REFERENCES users(uid),
ffriend_id INTEGER REFERENCES users(uid),
faccept BOOLEAN DEFAULT 'false',
UNIQUE(fuser_id, ffriend_id)
);

CREATE TABLE groups
(
	gid SERIAL PRIMARY KEY,
	guser_id INTEGER REFERENCES users(uid),
	gname VARCHAR(50) NOT NULL,
	UNIQUE(guser_id, gname)
	
);

CREATE TABLE friend_group
(
	fgid SERIAL PRIMARY KEY,
	friendship_id INTEGER REFERENCES friends(fid),
	group_id INTEGER REFERENCES groups(gid),
	UNIQUE(friendship_id, group_id)
);


CREATE TABLE profile_image
(
	piid SERIAL PRIMARY KEY,
	piuser_id INTEGER REFERENCES users(uid),
	piimage bytea
);


INSERT INTO friend_group (friendship_id, group_id) VALUES (59, 2);

INSERT INTO groups (guser_id, gname) VALUES (1, 'Amigos'), (1, 'UEL');

INSERT INTO users (ufname, ulname, usex, ucity, uemail, upass) VALUES 
	('Luiz Guilherme', 'Castilho Martins', 'm', 'Londrina', 'luizgui@gmail.com', 'soneka'),
	('admin', 'root', 'm', 'Londrina', 'root', 'root'),
	('Luiz Gustavo', 'Castilho Martins', 'm', 'Londrina', 'luizgustavocm@gmail.com', 'senha');

select * from friends
select * from groups
select * from profile_image

DELETE FROM friend_group where fgid = 3;

DELETE FROM friends WHERE fid=25;

INSERT INTO friends (fuser_id, ffriend_id, faccept) VALUES (34, 1, 'true');

UPDATE users SET uemail='nando' WHERE uid=4;

DELETE FROM users WHERE ufname='asdf';

INSERT INTO friends (fuser_id, ffriend_id) VALUES (1,2), (2,3);
INSERT INTO friends (fuser_id, ffriend_id, faccept) VALUES (1,3,'true');

INSERT INTO friends (fuser_id, ffriend_id) VALUES (1,28), (1,27), (26,23), (25,28), (36,1), (33,1), (34,1);
INSERT INTO friends (fuser_id, ffriend_id) VALUES  (6,37), (30,37);

SELECT uid FROM users WHERE uemail='root';

SELECT (ufname || ' ' || ulname) AS fullname FROM users WHERE uid=1;

UPDATE friends SET faccept = 'false' WHERE faccept = 'true' AND fid = 1 ;

UPDATE friends SET faccept = 'false' WHERE faccept = 'true'

INSERT INTO users (ufname, ulname, usex, ucity, uemail, upass) VALUES ('Luiz Carlos', 'Martins', 'm', 'Abatiá', 'canhoto', 'senha');

create user junior password 'junior';

GRANT INSERT ON users TO junior;
GRANT SELECT ON users TO junior;
GRANT SELECT ON friends TO junior;
GRANT REFERENCES ON users TO junior;
GRANT REFERENCES ON friends TO junior;

select * from pg_user;

SELECT uid, (ufname || ' ' || ulname) AS full_name FROM users WHERE LOWER(ufname) LIKE LOWER('luiz%') OR ulname LIKE 'luiz';

SELECT gid, gname FROM groups WHERE gid IN (SELECT gid FROM groups WHERE guser_id = 1
EXCEPT
(SELECT group_id FROM friend_group WHERE friendship_id = 12))

SELECT uid, ufname, ulname FROM users WHERE uid IN(
SELECT ffriend_id FROM friends WHERE fid IN (
SELECT friendship_id FROM friend_group WHERE group_id = 1));

DELETE FROM users WHERE uemail = 'dskaster'

DELETE FROM friends WHERE fuser_id = 39 OR ffriend_id = 39

SELECT * FROM groups, friend_group WHERE f.friendship_id <> 12 AND group_id = gid;

-- return all active connections 
select * from pg_stat_activity where datname='postgres';

SELECT ffriend_id, Count(*) FROM friends WHERE  fuser_id IN (
	SELECT ffriend_id FROM   friends WHERE  fuser_id = 2 ) 
	AND ffriend_id NOT IN (SELECT ffriend_id FROM   friends WHERE  fuser_id = 2) 
	AND ffriend_id <> 2 GROUP  BY ffriend_id ORDER  BY Count(*) DESC LIMIT  10;