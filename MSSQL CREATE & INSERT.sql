
USE spotitube;
DROP TABLE token;
DROP TABLE account;
DROP TABLE playlist;
DROP TABLE track;
DROP TABLE tracksinplaylist;

CREATE TABLE account(username varchar(255), password varchar(255), PRIMARY KEY(username));
CREATE TABLE token(token varchar(255), username varchar(255), FOREIGN KEY (username) REFERENCES account(username));
CREATE TABLE playlist(id int, name varchar(255), owner bit);
CREATE TABLE track(id int, title varchar(255),performer varchar(255),duration int, album varchar(255), playcount int, publicationDate DATE, description varchar(255), offlineAvailable BIT);
CREATE TABLE tracksinplaylist(playlist_id int, track_id int, offlineAvailable bit);
	
--accounts
INSERT INTO account VALUES ('test', 'cc03e747a6afbbcbf8be7668acfebee5');
INSERT INTO account VALUES ('test2', 'cc03e747a6afbbcbf8be7668acfebee5');
--MD5 PASSWORD = test123

--tracks
insert into track VALUES (1, 'Song for someone', 'The Frames', 350, 'The cost', 0, GETDATE(), 'undefined', 0),
						 (2, 'The cost', 'The Frames', 423, 'undefined', 37, '2005-01-10', 'Title song from the Album The Cost', 1),
						 (3, 'Ocean and a rock', 'Lisa Hannigan', 337, 'Sea sew', 0, GETDATE(), 'undefined', 0),
						 (4, 'So Long, Marianne', 'Leonard Cohen', 546, 'Songs of Leonard Cohen', 0, GETDATE(), 'undefined', 0),
						 (5, 'One', 'Metallica', 423, 'undefined', 37, '2001-11-1', 'undefined', 0),
						 (6, 'Best of you', 'Foo Fighters', 356, 'Greatest Hits', 199, GETDATE(), 'Best of you sung by Dave Grohl', 1),
						 (7, 'Make a move', 'Icon For Hire', 231, 'Scripted', 21, GETDATE(), 'undefined', 1),
						 (8, 'Congratulations', 'Post Malone', 265, 'undefined', 510, GETDATE(), 'Posty popular song', 1),
						 (9, 'Killshot', 'Eminem', 210, 'undefined', 666, GETDATE(), 'Diss to MGK', 1),
						 (10, 'Rapdevil', 'Machine Gun Kelly', 190, 'undefined', 666, GETDATE(), 'Diss to Eminem', 1),
						 (11, 'No Role Modelz', 'J.Cole', 195, '2014 Forest Hills Drive', 245, GETDATE(), '', 1);

--playlist
insert into playlist VALUES (1, 'Heavy Metal', 1),
							(2, 'Pop', 0),
							(3, 'Progressive Rock', 0),
							(4, 'Rock', 1),
							(5, 'Hip hop', 1); 

--tracksinplay
insert into tracksinplaylist VALUES (1, 5, 0),
									(4, 6, 1),
									(3, 7, 1),
									(5, 8, 1),
									(5, 9, 1),
									(5, 10, 1),
									(5, 11, 1);

insert into token VALUES('1234-1234-1234', 'test');

