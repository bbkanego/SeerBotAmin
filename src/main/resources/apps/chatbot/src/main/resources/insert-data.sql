SET SCHEMA PUBLIC;
--clear data before running query...
truncate schema PUBLIC and commit;
INSERT INTO ROLE VALUES(1,'2017-11-04 20:53:54.012000','2017-11-04 20:53:54.012000',1,'READ,WRITE,CREATE,DELETE','ADMIN');
INSERT INTO ROLE VALUES(2,'2017-11-04 21:16:12.057000','2017-11-04 21:16:12.057000',1,'READ,UPDATE,WRITE','USER');
INSERT INTO ROLE VALUES(3,'2017-11-04 22:07:02.628000','2017-11-06 20:51:37.227000',4,'READ,UPDATE','ASSESSOR');

INSERT INTO PARTY VALUES(15,'2017-11-09 20:21:02.327000','2017-11-09 20:21:02.327000',2,'Aditi','PERSON');
INSERT INTO PARTY VALUES(19,'2017-11-11 17:22:25.232000','2017-11-11 17:22:25.232000',1,'Aditi','PERSON');
INSERT INTO PARTY VALUES(21,'2017-11-11 20:11:11.645000','2017-11-11 20:11:11.645000',2,'Ashok','PERSON');
INSERT INTO PARTY VALUES(23,'2017-11-11 21:13:37.253000','2017-11-11 21:13:37.253000',1,'Admin','PERSON');
INSERT INTO PARTY VALUES(25,'2017-11-11 21:20:50.795000','2017-11-11 21:20:50.795000',1,'Test User','PERSON');
INSERT INTO PARTY VALUES(26,'2017-11-12 17:17:56.004000','2017-11-12 17:17:56.004000',2,'Test Assessor','PERSON');
INSERT INTO PARTY VALUES(27,'2017-11-12 17:17:56.004000','2017-11-12 17:17:56.004000',2,'ChatBot','PERSON');
INSERT INTO PARTY VALUES(75,'2017-11-13 11:35:07.133000','2017-11-13 11:35:07.133000',1,'Bhushan','PERSON');
INSERT INTO PARTY VALUES(123,'2017-12-18 20:35:06.220000','2017-12-18 20:35:06.220000',1,'NeuvoArtist','ARTIST');
INSERT INTO PARTY VALUES(124,'2017-12-18 20:35:06.272000','2017-12-18 20:35:06.272000',1,'Jam','PERSON');
INSERT INTO PARTY VALUES(125,'2017-12-18 21:54:11.744000','2017-12-18 21:54:11.744000',1,'FakeArtist','ARTIST');
INSERT INTO PARTY VALUES(126,'2017-12-18 21:54:11.765000','2017-12-18 21:54:11.765000',1,'John','PERSON');
INSERT INTO PARTY VALUES(7,'2017-11-08 21:27:31.967000','2017-11-08 21:27:31.967000',1,'Bhushan','PERSON');

INSERT INTO ARTIST VALUES('2017-12-20 00:00:00.000000','2011-12-20 00:00:00.000000',123);
INSERT INTO ARTIST VALUES('2017-12-14 00:00:00.000000','2011-12-14 00:00:00.000000',125);

INSERT INTO PERSON VALUES('1076-10-10 00:00:00.000000','Bhushan','KaneKane',7);
INSERT INTO PERSON VALUES('1076-10-10 00:00:00.000000','Aditi','KaneKane',15);
INSERT INTO PERSON VALUES('1934-10-10 00:00:00.000000','Aditi','KaneKane',19);
INSERT INTO PERSON VALUES('1076-10-10 00:00:00.000000','Ashok','KaneKane',21);
INSERT INTO PERSON VALUES('1987-10-10 00:00:00.000000','Admin','User',23);
INSERT INTO PERSON VALUES('1987-10-10 00:00:00.000000','Test','User',25);
INSERT INTO PERSON VALUES('1076-10-10 00:00:00.000000','Test','Assessor',26);
INSERT INTO PERSON VALUES('1987-10-10 00:00:00.000000','Bhushan','KaneKaneKane',75);
INSERT INTO PERSON VALUES('2017-12-20 00:00:00.000000','Jam','Man',124);
INSERT INTO PERSON VALUES('2017-12-14 00:00:00.000000','John','Fakester',126);

INSERT INTO ARTIST_MEMBERS VALUES(123,124);
INSERT INTO ARTIST_MEMBERS VALUES(125,126);

-- password 12345678
INSERT INTO ACCOUNT VALUES(22,'2017-11-11 21:13:37.241000','2017-11-11 21:13:37.241000',1,'$2a$11$w5mj67IlEHj5k615XhuuruXhARLN6ZjQPSqbJcWobpvdQXQVac2Uy','admin',23);
INSERT INTO ACCOUNT VALUES(24,'2017-11-11 21:20:50.792000','2017-11-11 21:20:50.792000',1,'$2a$11$w5mj67IlEHj5k615XhuuruXhARLN6ZjQPSqbJcWobpvdQXQVac2Uy','test_user',25);
INSERT INTO ACCOUNT VALUES(74,'2017-11-11 21:13:37.241000','2017-11-11 21:13:37.241000',1,'$2a$11$w5mj67IlEHj5k615XhuuruXhARLN6ZjQPSqbJcWobpvdQXQVac2Uy','test_assessor',26);
INSERT INTO ACCOUNT VALUES(75,'2017-11-11 21:13:37.241000','2017-11-11 21:13:37.241000',1,'$2a$11$w5mj67IlEHj5k615XhuuruXhARLN6ZjQPSqbJcWobpvdQXQVac2Uy','ChatBot',27);
--INSERT INTO ACCOUNT VALUES(24,'2017-11-11 21:20:50.792000','2017-11-11 21:20:50.792000',1,'$2a$11$vygpGuvh8Hegdm3rzMLEbOQOQoK4cx8XTpWxy5XlKbUk9cDsGOFMu','aditi_kane',25);
--INSERT INTO ACCOUNT VALUES(42,'2017-11-12 17:17:55.949000','2017-11-12 17:17:55.949000',1,'$2a$11$HijCRmB1rFoz1r3kzfUbP.kwQRJk0fdUHpr3AszzQoGSbCmsbVddi','ashok_kane',43);
--INSERT INTO ACCOUNT VALUES(74,'2017-11-13 11:35:07.130000','2017-11-13 11:35:07.130000',1,'$2a$11$BRe8DihRQ8qMHVhZGD8frOO.6lWbd1kdxPLDfzAzrX8bAUkkMVcwG','bhushan_kane2',75);

INSERT INTO EVENT VALUES(80,'2017-11-18 21:20:35.835000','2017-12-21 21:41:42.846000',2,'Nicki Minaj concert','2017-11-22 00:00:00.000000',121,'Nicki Minaj concert','2017-11-15 00:00:00.000000');
INSERT INTO EVENT VALUES(82,'2017-11-19 19:55:34.985000','2017-11-19 19:55:34.985000',1,'Desposito Concert','2000-10-10 00:00:00.000000',34,'Desposito Concert','1987-10-10 00:00:00.000000');
INSERT INTO EVENT VALUES(83,'2017-11-19 19:56:08.404000','2017-11-26 21:35:20.574000',2,'Justin Bieber Concert','2018-10-12 00:00:00.000000',198,'Justin Bieber Concert','2001-10-12 00:00:00.000000');
INSERT INTO EVENT VALUES(89,'2017-11-26 21:22:16.902000','2017-11-26 21:22:16.902000',1,'Test Event','2018-10-12 00:00:00.000000',0,'Test Event','2018-10-10 00:00:00.000000');

INSERT INTO BOOKING VALUES(84,'2017-11-20 10:50:23.861000','2017-11-24 22:10:52.470000',7,'2017-11-23 00:00:00.000000',FALSE,45.3444E0,12.3233E0,'wweexxxxxx','2017-11-17 00:00:00.000000',80,22);
INSERT INTO BOOKING VALUES(85,'2017-11-20 15:40:22.296000','2017-11-24 22:13:00.834000',3,'2013-10-10 00:00:00.000000',FALSE,453.1233E0,12.233E0,'wtestfddfff','2012-10-10 00:00:00.000000',83,22);
INSERT INTO BOOKING VALUES(88,'2017-11-26 21:22:16.894000','2017-11-26 21:35:20.573000',2,'2018-10-12 00:00:00.000000',FALSE,34.0E0,23.0E0,'testbooking','2018-10-10 00:00:00.000000',83,22);

INSERT INTO ACCOUNT_ROLE VALUES(22,1);
--INSERT INTO ACCOUNT_ROLE VALUES(24,2);
INSERT INTO ACCOUNT_ROLE VALUES(24,2);
INSERT INTO ACCOUNT_ROLE VALUES(74,3);