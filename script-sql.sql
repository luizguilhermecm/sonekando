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

select * from users
select * from friends

INSERT INTO friends (fuser_id, ffriend_id) VALUES (1,2), (2,3);
INSERT INTO friends (fuser_id, ffriend_id, faccept) VALUES (1,3,'true');

SELECT uid FROM users WHERE uemail='root';

SELECT (ufname || ' ' || ulname) AS fullname FROM users WHERE uid=1;

UPDATE friends SET faccept = 'false' WHERE faccept = 'true' AND fid = 1 ;

