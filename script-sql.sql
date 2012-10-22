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

SELECT * FROM posts where post_user_id = 4;

--por dia no periodo ??w
SELECT post_user_id, post_content, post_data, post_id, SUM(post_sigma_comment*5 + post_sigma_like) as total FROM (
SELECT post_id, post_data, post_user_id, post_content, post_sigma_comment, post_sigma_like FROM posts where date_trunc('day', post_data) = '2012-10-12' AND post_user_id IN (
SELECT fuser_id FROM friends WHERE ffriend_id = 1)
) j
GROUP BY post_data, post_user_id, post_id, post_content
ORDER BY total DESC
LIMIT 3;


SELECT post_user_id, post_content, post_data, post_id, SUM(post_sigma_comment*5 + post_sigma_like) as total 
FROM posts 
WHERE post_user_id = 1
GROUP BY post_data, post_user_id, post_id, post_content
ORDER BY total DESC

SELECT AVG(post_sigma_comment*5 + post_sigma_like) as total 
FROM posts 
WHERE post_user_id = 1

SELECT AVG(post_sigma_comment*5 + post_sigma_like) as total 
FROM posts 
WHERE post_user_id = 1 AND post_data > '2012-01-01' AND post_data < '2012-12-12'

SELECT A

SELECT date '2012-12-23' + integer '1' as nova_data
SELECT date '2012-12-23' as nova_data

SELECT * from posts

SELECT * FROM posts WHERE date_trunc('day', post_data) 

SELECT COUNT (*) as total_posts FROM posts WHERE post_user_id = 2 AND date_trunc('day', post_data) = '2012-10-12'

SELECT COUNT (*) as total_posts FROM posts WHERE post_user_id = 2 AND date_trunc('day', post_data) = '2012-10-12'

SELECT COUNT (*) as total FROM likes WHERE like_user_id = ? AND date_trunc('day', like_data) = ? AND like_post_id IN (
SELECT post_id FROM posts WHERE post_user_id = ?)


	SELECT DISTINCT date_trunc('day', post_data) AS datas 
	FROM posts WHERE post_data > '2012-01-01' AND post_data < '2012-12-12' AND post_user_id IN(
		SELECT fuser_id FROM friends WHERE ffriend_id = 1)

SELECT DISTINCT date_trunc('day', post_data) as datas FROM posts WHERE post_user_id = ?  AND post_data > ? AND post_data < ?
UNION
SELECT DISTINCT date_trunc('day', like_data) as datas FROM likes WHERE like_user_id = ? AND like_data > ? AND like_data < ?
UNION 
SELECT DISTINCT date_trunc('day', comment_data) as datas FROM posts_comments WHERE comment_user_id = ? AND comment_data > ? AND comment_data < ?

DROP TABLE git_posts

CREATE TABLE posts
(
	post_id SERIAL PRIMARY KEY,
	post_user_id INTEGER REFERENCES users(uid),
	post_content TEXT NOT NULL,
	post_sigma_like INTEGER DEFAULT 0,
	post_sigma_comment INTEGER DEFAULT 0,
	post_data TIMESTAMP DEFAULT now()
);

SELECT * from posts 
SELECT * from git_posts 

UPDATE posts SET post_content = 'roberto carlos' WHERE post_id = 7

DELETE FROM postg WHERE post_id = 21 



CREATE TRIGGER historico_posts AFTER DELETE OR UPDATE
ON posts FOR EACH ROW
EXECUTE PROCEDURE make_historico();

CREATE OR REPLACE FUNCTION make_historico()
RETURNS trigger LANGUAGE plpgsql
AS $$
begin
 INSERT INTO git_posts (git_post_id, git_user_id, git_content) VALUES (old.post_id, old.post_user_id, old.post_content);
 return old;
 END; $$;


DROP TRIGGER historico_posts ON posts

insert into foo (name, age) VALUES ('huashu', 23) RETURNING age;


 DROP TABLE log

 CREATE TABLE log
 (
	log_id SERIAL PRIMARY KEY,
	log_user_id INTEGER REFERENCES users(uid),
	log_user_fname VARCHAR(60),
	log_user_lname VARCHAR(60),
	log_post_id INTEGER,
	log_post_content TEXT,
	log_post_data TIMESTAMP,
	log_denovo boolean default false
 );

CREATE TABLE git_posts
(
	git_id SERIAL PRIMARY KEY,
	git_post_id INTEGER,
	git_user_id INTEGER REFERENCES users(uid),
	git_content TEXT NOT NULL,
	git_data TIMESTAMP DEFAULT now()
);

CREATE TABLE posts_comments
(
	comment_id SERIAL PRIMARY KEY,
	comment_user_id INTEGER REFERENCES users(uid),
	comment_post_id INTEGER REFERENCES posts(post_id),
	comment_content TEXT NOT NULL,
	comment_data TIMESTAMP DEFAULT now()
);

CREATE TABLE likes
(
	like_id SERIAL PRIMARY KEY,
	like_user_id INTEGER REFERENCES users(uid),
	like_post_id INTEGER REFERENCES posts(post_id) ON DELETE CASCADE,
	like_data TIMESTAMP DEFAULT now()
);

ALTER TABLE image_post ADD COLUMN size INTEGER;

SELECT * FROM image_post

CREATE TABLE image_post
(
	image_id SERIAL PRIMARY KEY,
	image_user_id INTEGER REFERENCES users(uid),
	image_post_id INTEGER REFERENCES posts(post_id) ON DELETE CASCADE,
	image_image bytea
);

SELECT * FROM posts;

SELECT * FROM posts_comments ORDER BY comment_data DESC;

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


SELECT j.uid, j.ufname, j.ulname, j.gid, j.gname, Count(*) AS total 
 FROM   posts p 
        JOIN (SELECT * 
              FROM   users u 
                     JOIN (SELECT * 
                           FROM   friends f 
                                  JOIN (SELECT * 
                                        FROM   friend_group f 
                                               JOIN groups g 
                                                 ON f.group_id = gid 
                                                    AND gid = 2) j 
                                    ON f.fid = j.friendship_id) j 
                       ON uid = ffriend_id) j 
          ON p.post_user_id = j.uid 
             AND post_data > '2012-01-01' 
             AND post_data < '2012-11-11' 
 GROUP  BY j.uid, j.ufname, j.gid, j.gname, j.ulname 
 ORDER  BY total DESC 
 LIMIT  3 ;


SELECT post_user_id, SUM(post_sigma_comment*5 + post_sigma_like) AS total 
FROM posts 
WHERE post_data > '2011-01-01' AND post_data < '2013-01-01' AND post_user_id  IN (
	SELECT uid FROM users 
	EXCEPT
	SELECT ffriend_id FROM friends WHERE fuser_id = 1)
group by post_user_id
ORDER BY total DESC
LIMIT 10;

SELECT post_user_id, SUM(post_sigma_comment*5 + post_sigma_like) AS total 
FROM posts 
WHERE post_data > '2011-01-01' AND post_data < '2013-01-01' AND post_user_id  IN (
	SELECT ffriend_id FROM friends WHERE fuser_id = 1)
group by post_user_id
ORDER BY total DESC
LIMIT 10;

SELECT * FROM log

log_user_id INTEGER REFERENCES users(uid),
	log_user_fname VARCHAR(60),
	log_user_lname VARCHAR(60),
	log_post_data TIMESTAMP,
	log_denovo boolean default false
	
INSERT INTO log (log_user_id, log_user_fname, log_user_lname, log_post_data) (
SELECT u.uid, u.ufname, u.ulname, p.post_data
	FROM posts p JOIN users u 
	ON p.post_user_id = u.uid 
	AND p.post_content LIKE '%prof%' 
	AND p.post_data > '2012-01-01' 
	AND p.post_data < '2012-12-12')

UPDATE log SET log_denovo = true WHERE post
	 (
SELECT DISTINCT case WHEN post_user_id=1 THEN 'me' ELSE 'other' END from posts where post_user_id = 1)  

SELECT u.uid, u.ufname, u.ulname, p.post_data, (SELECT DISTINCT CASE WHEN log_user_id = u.uid THEN 'true' ELSE 'false' END from log LIMIT 1)
	FROM posts p JOIN users u 
	ON p.post_user_id = u.uid 
	AND p.post_content LIKE '%bijuzinho%' 
	AND p.post_data > '2012-01-01' 
	AND p.post_data < '2012-12-12'

SELECT uid, (SELECT CASE WHEN (SELECT COUNT(*) FROM log WHERE log_user_id = uid) > 0 THEN 'true' ELSE 'false' END) from users;

SELECT * from posts

SELECT * FROM users u JOIN (SELECT p.post_content, p.post_user_id, CASE WHEN post_user_id = 1 THEN 'true' ELSE 'false' END from posts p) j ON u.uid = j.post_user_id;	

SELECT * FROM posts p join users u ON p.post_user_id = u.uid

SELECT * from posts where post_content like '%prof%'

UPDATE users SET ufname='Luiz Fernando' WHERE uid = 4;

select procurar('%prof%');

DROP FUNCTION procurar(TEXT)

CREATE OR REPLACE FUNCTION procurar(word TEXT)
RETURNS void AS $$
SELECT * FROM posts WHERE post_content LIKE $1;
$$ LANGUAGE SQL;

CREATE OR REPLACE FUNCTION offensive(word TEXT, inicio TIMESTAMP, fim TIMESTAMP)
RETURNS void AS $$
INSERT INTO log (log_user_id, log_user_fname, log_user_lname, log_post_id, log_post_content, log_post_data, log_denovo) (
SELECT u.uid, u.ufname, u.ulname, p.post_id, p.post_content, p.post_data, (SELECT CASE WHEN (SELECT COUNT(*) FROM log WHERE log_user_id = uid) > 0 THEN 'true'::boolean ELSE 'false'::boolean END)
	FROM posts p JOIN users u 
	ON p.post_user_id = u.uid 
	AND p.post_content LIKE '%' || $1 || '%'
	AND p.post_data > $2 
	AND p.post_data < $3
);
$$ LANGUAGE SQL;


SELECT offensive('prof', '2012-01-01', '2012-12-12')

DROP FUNCTION offensive(TEXT,TIMESTAMP,TIMESTAMP)
CREATE TABLE log
 (
	log_id SERIAL PRIMARY KEY,
	log_user_id INTEGER REFERENCES users(uid),
	log_user_fname VARCHAR(60),
	log_user_lname VARCHAR(60),
	log_post_id INTEGER,
	log_post_content TEXT,
	log_post_data TIMESTAMP,
	log_denovo boolean default false NOT NULL
 );
DROP table log