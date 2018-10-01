CREATE TABLE IF NOT EXISTS doctor (
    firstName varchar(50),
    lastName varchar(50),
    sex integer,
    phoneNumber varchar(20),
    address varchar(100),
    licence integer UNIQUE,
    avatar varchar(100),
    id SERIAL PRIMARY KEY,
    district varchar (50)
);

CREATE TABLE IF NOT EXISTS insurance (
    insuranceName varchar(30),
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS insurancePlan (
    insurancePlanName varchar(50),
    id SERIAL PRIMARY KEY,
    insuranceID integer,
    FOREIGN KEY (insuranceID) REFERENCES insurance(id)
);


CREATE TABLE IF NOT EXISTS medicalCare(
    doctorID integer,
    insurancePlanID integer,
    FOREIGN KEY (insurancePlanID) REFERENCES insurancePlan(id),
    FOREIGN KEY (doctorID) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS specialty(
    specialtyName varchar(50),
    id SERIAL PRIMARY KEY
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
    userID integer,
    daytime varchar (20),
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (doctorID) REFERENCES doctor(id),
    FOREIGN KEY (userID) REFERENCES patient(id)
);

CREATE TABLE IF NOT EXISTS information (
    doctorId integer,
    certificate varchar(250),
    languages varchar(100),
    education varchar(250),
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (doctorID) REFERENCES doctor(id)
);


CREATE TABLE IF NOT EXISTS workingHour(
    doctorId integer,
    starttime varchar(10),
    finishtime varchar(10),
    dayweek integer,
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (doctorId) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS appointment(
    doctorId integer,
    clientId integer,
    appointmentDay varchar(10),
    appointmentTime VARCHAR (10),
    identifier VARCHAR (30),
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (doctorId) REFERENCES doctor(id),
    FOREIGN KEY (clientId) REFERENCES patient(id)
);

CREATE TABLE IF NOT EXISTS patient(
    id SERIAL PRIMARY KEY,
    doctorId INTEGER ,
    firstname VARCHAR (50),
    lastname VARCHAR (50),
    phonenumber VARCHAR (20),
    email VARCHAR (90),
    password VARCHAR (72)
);
