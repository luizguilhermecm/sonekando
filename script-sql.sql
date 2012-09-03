DROP TABLE users CASCADE;
DROP TABLE friends;

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
fgroup VARCHAR(50) DEFAULT 'Amigos',
faccept BOOLEAN DEFAULT 'false'
);

INSERT INTO users (ufname, ulname, usex, ucity, uemail, upass) VALUES 
	('Luiz Guilherme', 'Castilho Martins', 'm', 'Londrina', 'luizgui@gmail.com', 'soneka'),
	('admin', 'root', 'm', 'Londrina', 'root', 'root'),
	('Luiz Gustavo', 'Castilho Martins', 'm', 'Londrina', 'luizgustavocm@gmail.com', 'senha');

select * from friends
select * from users

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