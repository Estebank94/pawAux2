CREATE TABLE IF NOT EXISTS doctor (
  firstName varchar(50),
  lastName varchar(50),
  sex varchar(1),
  phoneNumber varchar(9),
  address varchar(100),
  licence integer UNIQUE,
  avatar varchar(100) UNIQUE,
  id IDENTITY PRIMARY KEY,
  workingHours varchar(100),
  district varchar (50)
);

CREATE TABLE IF NOT EXISTS insurance (
  insuranceName varchar(30),
  id IDENTITY PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS insurancePlan (
  insurancePlanName varchar(50),
  id IDENTITY PRIMARY KEY,
  insuranceid integer,
  FOREIGN KEY (insuranceid) REFERENCES insurance(id)
);

CREATE TABLE IF NOT EXISTS medicalCare(
  doctorID integer,
  insurancePlanID integer,
  FOREIGN KEY (insurancePlanID) REFERENCES insurancePlan(id),
  FOREIGN KEY (doctorID) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS specialty(
  specialtyName varchar(50),
  id IDENTITY PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS doctorSpecialty(
  specialtyID integer,
  doctorID integer,
  FOREIGN KEY (specialtyID) REFERENCES specialty(id),
  FOREIGN KEY (doctorID) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS review(
  description varchar(100),
  stars integer,
  doctorID integer,
  id IDENTITY PRIMARY KEY,
  FOREIGN KEY (doctorID) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS information (
 doctorId integer,
 certificate varchar(100),
 languages varchar(20),
 education varchar(10),
 id IDENTITY PRIMARY KEY
 );

CREATE TABLE IF NOT EXISTS workingHour(
    doctorId integer,
    starttime varchar(10),
    finishtime varchar(10),
    dayweek integer,
    id IDENTITY PRIMARY KEY,
    FOREIGN KEY (doctorId) REFERENCES doctor(id)
);

INSERT INTO doctor (firstName, lastName, sex, phoneNumber, address, licence, avatar, id, workingHours, district)
VALUES ('Roberto Nicolas Agustin', 'Rosa', 'M', '47777777', 'Arce 211', '1234', 'https://d1cesmq0xhh7we.cloudfront.net/724f4a59-0f34-4cbc-980f-766f4df17d9bcircle_medium__v1__.png', '1', 'lunes 9am', 'Palermo');

INSERT INTO doctor (firstName, lastName, sex, phoneNumber, address, licence, avatar, id, workingHours, district)
VALUES ('Ramiro', 'Roca', 'M', '47777778', 'Maure 211', '4567',
'https://dsw5h1xg5uvx.cloudfront.net/1deb1e7e-f412-4446-9e79-dabb625a883ccircle_medium.png', '2', 'lunes 9pm', 'Belgrano');

INSERT INTO doctor (firstName, lastName, sex, phoneNumber, address, licence, avatar, id, workingHours, district)
VALUES ('Rihanna', 'Remo', 'F', '47777771', 'Bulnes 211', '4321',
'https://dsw5h1xg5uvx.cloudfront.net/93af945e-50b0-45ef-a181-c1b638a0c898circle_medium.png', '3', 'martes 9am', 'Recoleta');

INSERT INTO insurance (insuranceName, id) VALUES ('Accord', 206);
INSERT INTO insurance (insuranceName, id) VALUES ('OSECAC', 210);
INSERT INTO insurance (insuranceName, id) VALUES ('OSPLAD', 216);
INSERT INTO insurance (insuranceName, id) VALUES ('OSDE', 217);
INSERT INTO insurance (insuranceName, id) VALUES ('OSPLA', 218);

INSERT INTO specialty (specialtyName, id) VALUES('NEURÓLOGO INFANTIL', 551);
INSERT INTO specialty (specialtyName, id) VALUES('NUTRICIÓN', 552);
INSERT INTO specialty (specialtyName, id) VALUES('OBSTETRICIA', 553);
INSERT INTO specialty (specialtyName, id) VALUES('OFTALMOLOGÍA', 554);
INSERT INTO specialty (specialtyName, id) VALUES('ONCOLOGÍA', 555);

INSERT INTO insurancePlan (insurancePlanName, id, insuranceid) VALUES ('Accord Salud', 124, 206);
INSERT INTO insurancePlan (insurancePlanName, id, insuranceid) VALUES ('210', 125, 210);
INSERT INTO insurancePlan (insurancePlanName, id, insuranceid) VALUES ('310', 126, 216);
INSERT INTO insurancePlan (insurancePlanName, id, insuranceid) VALUES ('410', 127, 206);

INSERT INTO medicalCare(doctorID, insurancePlanID) VALUES (1, 124);
INSERT INTO medicalCare(doctorID, insurancePlanID) VALUES (2, 125);
INSERT INTO medicalCare(doctorID, insurancePlanID) VALUES (3, 126);
INSERT INTO medicalCare(doctorID, insurancePlanID) VALUES (3, 127);

INSERT INTO information (doctorId, languages, education, certificate) VALUES (1, 'Ingles', 'UBA', 'Master');
INSERT INTO information (doctorId, languages, education, certificate) VALUES (2, 'Ingles', 'UBA', 'Master');
INSERT INTO information (doctorId, languages, education, certificate) VALUES (3, 'Ingles', 'UBA', 'Master');

INSERT INTO doctorSpecialty (specialtyID, doctorID) VALUES (552, 1);
INSERT INTO doctorSpecialty (specialtyID, doctorID) VALUES (551, 3);
INSERT INTO doctorSpecialty (specialtyID, doctorID) VALUES (555, 2);