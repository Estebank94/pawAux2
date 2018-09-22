CREATE TABLE IF NOT EXISTS doctor (
    firstName varchar(50),
    lastName varchar(50),
    sex integer,
    phoneNumber varchar(9),
    address varchar(100),
    licence integer UNIQUE,
    avatar varchar(100) UNIQUE,
    id SERIAL PRIMARY KEY
    workingHours varchar(100),
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
    id SERIAL PRIMARY KEY,
    FOREIGN KEY (doctorID) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS information (
    doctorId integer,
    certificate varchar(100),
    languages varchar(20),
    education varchar(10),
    id SERIAL PRIMARY KEY
    FOREIGN KEY (doctorID) REFERENCES doctor(id)
);

/*

CREATE TABLE IF NOT EXISTS workingHour(
    doctorId integer,
    starttime varchar(20),
    finishtime varchar(20),
    dayweek varchar(20),
    id SERIAL PRIMARY KEY
    FOREIGN KEY (doctorId) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS apploiment(
    doctorId integer,
    appoi
);

*/