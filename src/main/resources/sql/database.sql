CREATE DATABASE IF NOT EXISTS fitLife;

USE fitLife;

CREATE TABLE user (
                      username varchar(20) primary key,
                      name varchar(50),
                      email varchar(50),
                      password varchar(20)
);

INSERT INTO user (username, name, email, password) VALUES
    ('U001', 'Lahiru Chanaka', 'lahiru@gmail.com', '1234');


CREATE TABLE trainer (
    trainerId varchar(10) primary key,
    trainerName varchar(20),
    trainerAddress varchar(50),
    trainerContact varchar(20),
    trainerExperience varchar(20)
);

INSERT INTO trainer (trainerId, trainerName, trainerAddress, trainerContact, trainerExperience) VALUES
    ('T001', 'Jehan Silva', 'Main St, Galle', '0714567890', '1 years'),
    ('T002', 'Supun Keshan', '12, Gintota, Galle', '0776543210', '3 years'),
    ('T003', 'Sahani Priyadarshani', '10/1 Boossa, Galle', '0755555555', '2 years');


CREATE TABLE membership (
    membershipId varchar(10) primary key,
    membershipType varchar(20),
    description varchar(200),
    membershipFee double(10,2)
);

CREATE TABLE member (
    memberId varchar(10) primary key,
    memberName varchar(20),
    memberContact varchar(20),
    dateOfBirth date,
    gender varchar(10),
    email varchar(50),
    membershipId varchar(100),
    startDate date,
    endDate date,
    FOREIGN KEY (membershipId) REFERENCES membership(membershipId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE schedule (
    scheduleId varchar(10) primary key,
    scheduleName varchar(50),
    description varchar(100)
);

INSERT INTO schedule (scheduleId, scheduleName, description) VALUES
    ('S001', 'Cardio', 'Cardio workout session'),
    ('S002', 'Weightlifting', 'Weightlifting session'),
    ('S003', 'Abs Training', 'Abdominal workout session'),
    ('S004', 'Legs Training', 'Lower body workout session'),
    ('S005', 'Upper Body Training', 'Upper body strength training'),
    ('S006', 'Lower Body Training', 'Lower body strength training'),
    ('S007', 'Shoulders Training', 'Shoulder strength training'),
    ('S008', 'Chest Training', 'Chest strength training'),
    ('S009', 'Arms Training', 'Arm strength training'),
    ('S010', 'Full Body Training', 'Full body strength training');


CREATE TABLE payment (
    paymentId varchar(10) primary key,
    paymentMethod varchar(20),
    membershipFee double(10,2),
    date date,
    time time,
    membershipId varchar(10),
    FOREIGN KEY (membershipId) REFERENCES membership(membershipId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE purchase (
    purchaseId varchar(10) primary key,
    purchaseDate date,
    purchaseTime time,
    totalPrice double(10,2),
    memberId varchar(10),
    FOREIGN KEY (memberId) REFERENCES member(memberId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE product (
    productId varchar(10) primary key,
    productName varchar(50),
    unitPrice double(10,2),
    qtyOnHand int(10),
    addedDate date,
    addedTime time
);

INSERT INTO product (productId, productName, unitPrice, qtyOnHand, addedDate, addedTime) VALUES
    ('PRD001', 'Protein Powder', 25000.00, 20, '2024-05-01', '08:00:00'),
    ('PRD002', 'Pre-Workout Supplement', 10000.00, 15, '2024-05-02', '09:00:00'),
    ('PRD003', 'Mass Gainers', 30000.00, 15, '2024-05-03', '10:00:00'),
    ('PRD004', 'Fat Burners', 8500.00, 20, '2024-05-04', '11:00:00'),
    ('PRD005', 'Protein Bars', 1000.00, 50, '2024-05-05', '12:00:00'),
    ('PRD006', 'Lifting Straps', 4000.00, 25, '2024-05-06', '13:00:00'),
    ('PRD007', 'Vitamins', 6500.00, 20, '2024-05-07', '14:00:00'),
    ('PRD008', 'Weightlifting Gloves', 5500.00, 35, '2024-05-08', '15:00:00'),
    ('PRD009', 'Wrist Wrap', 5500.00, 25, '2024-05-09', '16:00:00'),
    ('PRD010', 'Leather Belt', 13500.00, 10, '2024-05-10', '17:00:00'),
    ('PRD011', 'Knee Sleeves', 9500.00, 15, '2024-05-11', '18:00:00'),
    ('PRD012', 'Elbow Sleeves', 8500.00, 15, '2024-05-12', '19:00:00'),
    ('PRD013', 'Shaker Bottles', 2500.00, 50, '2024-05-13', '20:00:00'),
    ('PRD014', 'Fitness Bag', 3500.00, 10, '2024-05-14', '21:00:00'),
    ('PRD015', 'Knee Wrap', 6500.00, 25, '2024-05-15', '22:00:00');


CREATE TABLE attendance (
    attendanceId varchar(10) primary key,
    memberName varchar(50),
    date date,
    time time,
    memberId varchar(10),
    FOREIGN KEY (memberId) REFERENCES member(memberId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE scheduleDetails (
    scheduleId varchar(10),
    memberId varchar(10),
    FOREIGN KEY (scheduleId) REFERENCES schedule(scheduleId) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (memberId) REFERENCES member(memberId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE trainerDetails (
    scheduleId varchar(10),
    trainerId varchar(10),
    FOREIGN KEY (scheduleId) REFERENCES schedule(scheduleId) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (trainerId) REFERENCES trainer(trainerId) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE purchaseDetails (
    purchaseId varchar(10),
    productId varchar(10),
    date date,
    time time,
    qty int(10),
    FOREIGN KEY (purchaseId) REFERENCES purchase(purchaseId) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (productId) REFERENCES product(productId) ON UPDATE CASCADE ON DELETE CASCADE
);


select * from attendance;
select * from member;
select * from membership;
select * from payment;
select * from product;
select * from purchase;
select * from purchasedetails;
select * from schedule;
select * from scheduledetails;
select * from trainer;
select * from trainerdetails;
select * from user;



SELECT memberId, COUNT(attendanceId) AS attendance_count FROM attendance GROUP BY memberId;

SELECT * FROM membership ORDER BY memberId;

DELETE FROM user WHERE userId = 'U002';

SELECT t.trainerId, t.trainerName, s.scheduleId, s.scheduleName FROM trainer t JOIN trainerdetails td ON t.trainerId = td.trainerId JOIN schedule s ON s.scheduleId = td.scheduleId;

SELECT MONTHNAME(date) AS month, SUM(membershipFee) AS total FROM payment GROUP BY MONTHNAME(date);

SELECT m.memberId, memberName, email, gender, startDate, endDate, membershipType FROM member m JOIN membership ms ON m.memberId=ms.memberId;

SELECT purchaseId, date, qtyOnHand, productName, unitPrice, qty, p.productId, unitPrice * qty as Total FROM purchaseDetails pd JOIN product p ON pd.productId=p.productId ORDER BY purchaseId;
