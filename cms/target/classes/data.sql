INSERT INTO captains (userID, firstName, lastName, email, password, role)
VALUES (1, 'John', 'Doe', 'john.doe@gmail.com', 'password123', 'captain'),
       (2, 'Jane', 'Smith', 'jane.smith@hotmail.com', 'securepass', 'captain'),
       (3, 'Alice', 'Johnson', 'alice.johnson@gmail.com', 'referee456', 'captain'),
       (4, 'Bob', 'Williams', 'bob.williams@yahoo.com', 'adminpass', 'captain'),
       (5, 'Charlie', 'Brown', 'charlie.brown@hotmail.com', 'playerpass', 'captain'),
       (6, 'Emma', 'Johnson', 'emma.johnson@gmail.com', 'pass1234', 'captain'),
       (7, 'Liam', 'Smith', 'liam.smith@yahoo.com', 'secure789', 'captain'),
       (8, 'Olivia', 'Davis', 'olivia.davis@outlook.com', 'football321', 'captain'),
       (9, 'Anika', 'Mule', 'anika.mule@hotmail.com', 'goalkeeper99', 'captain'),
       (10, 'Ava', 'Wilson', 'ava.wilson@gmail.com', 'teamleader22', 'captain'),
       (11, 'Ethan', 'Garcia', 'ethan.garcia@yahoo.com', 'winning456', 'captain'),
       (12, 'Sophia', 'Anderson', 'sophia.anderson@outlook.com', 'captainXyz', 'captain'),
       (13, 'Mason', 'Taylor', 'mason.taylor@hotmail.com', 'sports999', 'captain'),
       (14, 'Isabella', 'Hernandez', 'isabella.hernandez@gmail.com', 'playmaker777', 'captain'),
       (15, 'James', 'Lopez', 'james.lopez@yahoo.com', 'soccerboss55', 'captain');


INSERT INTO admins (userID, firstName, lastName, email, password, role)
VALUES (16, 'Emma', 'Brown', 'emma.brown@gmail.com', 'admin1234', 'admin'),
       (17, 'Liam', 'Clark', 'liam.clark@yahoo.com', 'secureAdmin789', 'admin'),
       (18, 'Olivia', 'Evans', 'olivia.evans@outlook.com', 'manage321', 'admin'),
       (19, 'Noah', 'White', 'noah.white@hotmail.com', 'system99!', 'admin'),
       (20, 'Ava', 'Hall', 'ava.hall@gmail.com', 'adminControl22', 'admin'),
       (21, 'Ethan', 'Young', 'ethan.young@yahoo.com', 'superadmin456', 'admin'),
       (22, 'Sophia', 'King', 'sophia.king@outlook.com', 'adminMasterXyz', 'admin'),
       (23, 'Mason', 'Wright', 'mason.wright@hotmail.com', 'rootAccess999', 'admin'),
       (24, 'Isabella', 'Scott', 'isabella.scott@gmail.com', 'adminPower777', 'admin'),
       (25, 'James', 'Green', 'james.green@yahoo.com', 'adminBoss55', 'admin');


INSERT INTO players (userID, firstName, lastName, email, password, role)
VALUES (26, 'Mia', 'Roberts', 'miaroberts@example.com', 'goalKick26!', 'player'),
       (27, 'Lucas', 'Mitchell', 'lucasmitchell@example.com', 'fastDribble27$', 'player'),
       (28, 'Amelia', 'Carter', 'ameliacarter@example.com', 'headerShot28#', 'player'),
       (29, 'Benjamin', 'Adams', 'benjaminadams@example.com', 'defenseWall29%', 'player'),
       (30, 'Harper', 'Parker', 'harperparker@example.com', 'powerPass30&', 'player'),
       (31, 'Elijah', 'Bennett', 'elijahbennett@example.com', 'speedRun31*', 'player'),
       (32, 'Evelyn', 'Phillips', 'evelynphillips@example.com', 'assistKing32!', 'player'),
       (33, 'Henry', 'Collins', 'henrycollins@example.com', 'quickFeet33$', 'player'),
       (34, 'Scarlett', 'Ward', 'scarlettward@example.com', 'sharpShooter34#', 'player'),
       (35, 'Daniel', 'Turner', 'danielturner@example.com', 'agileMove35%', 'player'),
       (36, 'Lily', 'Morris', 'lilymorris@example.com', 'midfieldMaestro36&', 'player'),
       (37, 'Jack', 'Murphy', 'jackmurphy@example.com', 'penaltyPro37*', 'player'),
       (38, 'Grace', 'Rivera', 'gracerivera@example.com', 'tackleMaster38!', 'player'),
       (39, 'Oliver', 'Reed', 'oliverreed@example.com', 'cornerKick39$', 'player'),
       (40, 'Avery', 'Cook', 'averycook@example.com', 'freeKick40#', 'player'),
       (41, 'Sebastian', 'Morgan', 'sebastianmorgan@example.com', 'crossField41%', 'player'),
       (42, 'Chloe', 'Bailey', 'chloebailey@example.com', 'ballControl42&', 'player'),
       (43, 'Matthew', 'Cooper', 'matthewcooper@example.com', 'topScorer43*', 'player'),
       (44, 'Zoey', 'Bell', 'zoeybell@example.com', 'hatTrick44!', 'player'),
       (45, 'David', 'Gomez', 'davidgomez@example.com', 'safeHands45$', 'player'),
       (46, 'Natalie', 'Ward', 'natalieward@example.com', 'defender46#', 'player'),
       (47, 'Leo', 'Foster', 'leofoster@example.com', 'gameChanger47%', 'player'),
       (48, 'Hannah', 'Gonzalez', 'hannahgonzalez@example.com', 'soccerIQ48&', 'player'),
       (49, 'Wyatt', 'Long', 'wyattlong@example.com', 'longShot49*', 'player'),
       (50, 'Addison', 'Ross', 'addisonross@example.com', 'strongLeg50!', 'player'),
       (51, 'Samuel', 'Diaz', 'samueldiaz@example.com', 'playmaker51$', 'player'),
       (52, 'Aubrey', 'Hughes', 'aubreyhughes@example.com', 'quickPass52#', 'player'),
       (53, 'Carter', 'Price', 'carterprice@example.com', 'cleverMove53%', 'player'),
       (54, 'Ellie', 'Myers', 'elliemyers@example.com', 'reflexSave54&', 'player'),
       (55, 'Gabriel', 'Barnes', 'gabrielbarnes@example.com', 'teamSpirit55*', 'player');

INSERT INTO referees (userID, firstName, lastName, email, password, role)
VALUES
    (56, 'Ethan', 'Clark', 'ethan.clark@example.com', 'whistleBlow56!', 'referee'),
    (57, 'Ava', 'Henderson', 'ava.henderson@example.com', 'fairPlay57$', 'referee'),
    (58, 'Noah', 'Ramirez', 'noah.ramirez@example.com', 'offsideCall58#', 'referee'),
    (59, 'Isabella', 'Foster', 'isabella.foster@example.com', 'yellowCard59%', 'referee'),
    (60, 'Mason', 'Reynolds', 'mason.reynolds@example.com', 'redCard60&', 'referee'),
    (61, 'Sophie', 'Nguyen', 'sophie.nguyen@example.com', 'VARcheck61*', 'referee'),
    (62, 'Alexander', 'Patel', 'alexander.patel@example.com', 'fairRef62!', 'referee'),
    (63, 'Emily', 'Scott', 'emily.scott@example.com', 'extraTime63$', 'referee'),
    (64, 'William', 'King', 'william.king@example.com', 'penaltyCall64#', 'referee'),
    (65, 'Madison', 'Barnes', 'madison.barnes@example.com', 'handBall65%', 'referee'),
    (66, 'James', 'Bennett', 'james.bennett@example.com', 'goalCheck66&', 'referee'),
    (67, 'Chloe', 'Rodriguez', 'chloe.rodriguez@example.com', 'clearFoul67*', 'referee'),
    (68, 'Logan', 'White', 'logan.white@example.com', 'gameStart68!', 'referee'),
    (69, 'Natalie', 'Hill', 'natalie.hill@example.com', 'finalWhistle69$', 'referee'),
    (70, 'Daniel', 'Perez', 'daniel.perez@example.com', 'matchControl70#', 'referee');



-- Insert into Leagues
INSERT INTO leagues (leagueID, leagueName, leagueGender, divisionNum, leagueSport) VALUES
('L001', 'WDiv1Soccer', 'Womens', 'Division1', 'Soccer'),
('L002', 'WDiv2Soccer', 'Womens', 'Division2', 'Soccer'),
('L003', 'MDiv1Soccer', 'Mens', 'Division1', 'Soccer'),
('L004', 'WDiv1Volleyball', 'Womens', 'Division1', 'Volleyball'),
('L005', 'MDiv2Soccer', 'Mens', 'Division2', 'Soccer'),
('L006', 'WDiv1Basketball', 'Womens', 'Division1', 'Basketball'),
('L007', 'MDiv1Basketball', 'Mens', 'Division1', 'Basketball'),
('L008', 'WDiv2Volleyball', 'Womens', 'Division2', 'Volleyball'),
('L009', 'MDiv1Hockey', 'Mens', 'Division1', 'Hockey');

-- Insert into Teams
--INSERT INTO teams (teamID, teamName, ranking) VALUES
INSERT INTO teams (teamID, teamName, captainID, leagueID) VALUES
('T001', 'Skule', 1, 'L003'),
('T002', 'Victoria College', 2, 'L003'),
('T003', 'Woodsworth', 3, 'L003'),
('T004', 'Kinesiology', 4, 'L001'),
('T005', 'New College', 5, 'L001'),
('T006', 'University College', 6, 'L004'),
('T007', 'UTSC', 7, 'L004'),
('T008', 'UTM', 8, 'L002'),
('T009', 'Skule', 9, 'L002'),
('T010', 'Victoria College', 10, 'L005'),
('T011', 'Woodsworth', 11, 'L005'),
('T012', 'Kinesiology', 12, 'L001'),
('T013', 'New College', 13, 'L001'),
('T014', 'University College', 14, 'L001'),
('T015', 'UTSC', 15, 'L001');

-- testing creating team without captain
INSERT INTO teams (teamID, teamName, leagueID) VALUES
('T037', 'Team Without Captain', 'L001');

-- Insert into games
INSERT INTO games (gameID, datetime, location, team1ID, team2ID, teamScore1, teamScore2, gameStatus, refereeID, leagueID) VALUES
(1, '2025-03-28 10:00:00', 'Athletic Centre', 'T001', 'T002', 2, 1, 'completed', 56, 'L003'),
(2, '2025-03-28 14:00:00', 'Goldring Centre', 'T002', 'T003', 3, 0, 'completed', 57, 'L003'),
(3, '2025-03-29 18:00:00', 'Varsity Centre & Arena', 'T004', 'T005', 1, 1, 'completed', 58, 'L001'),
(4, '2025-03-29 12:00:00', 'Back Campus Fields', 'T006', 'T007', 3, 2, 'completed', 59, 'L004'),
(5, '2025-03-30 10:00:00', 'Robert Street Field', 'T008', 'T009', 0, 0, 'completed', 57, 'L002'),
(6, '2025-04-02 10:00:00', 'Athletic Centre', 'T010', 'T011', 0, 0, 'upcoming', 60, 'L005'),
(7, '2025-04-02 14:00:00', 'Goldring Centre', 'T012', 'T013', 0, 0, 'upcoming', 61, 'L001'),
(8, '2025-04-03 18:00:00', 'Varsity Centre & Arena', 'T013', 'T014', 0, 0, 'upcoming', 62, 'L001'),
(9, '2025-04-03 12:00:00', 'Back Campus Fields', 'T014', 'T015', 0, 0, 'upcoming', 63, 'L001'),
(10, '2025-04-04 10:00:00', 'Robert Street Field', 'T004', 'T014', 0, 0, 'upcoming', 64, 'L002'),
(11, '2025-04-06 12:00:00', 'Varsity Centre & Arena', 'T005', 'T014', 0, 0, 'upcoming', 65, 'L001'),
(12, '2025-05-2 15:00:00', 'Athletic Centre', 'T005', 'T015', 0, 0, 'upcoming', 60, 'L001');