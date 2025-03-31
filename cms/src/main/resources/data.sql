
INSERT INTO captains (userID, firstName, lastName, email, password, role)
VALUES (1, 'John', 'Doe', 'john.doe@example.com', 'password123', 'captain'),
       (2, 'Jane', 'Smith', 'jane.smith@example.com', 'securepass', 'captain'),
       (3, 'Alice', 'Johnson', 'alice.johnson@example.com', 'referee456', 'captain'),
       (4, 'Bob', 'Williams', 'bob.williams@example.com', 'adminpass', 'captain'),
       (5, 'Charlie', 'Brown', 'charlie.brown@example.com', 'playerpass', 'captain');

INSERT INTO admins (userID, firstName, lastName, email, password, role)
VALUES (6, 'Admin', 'User', 'admin@example.com', 'adminpassword', 'admin'),
       (7, 'Super', 'Admin', 'superadmin@example.com', 'superadminpass', 'admin');

INSERT INTO players (userID, firstName, lastName, email, password, role)
VALUES (8, 'Player', 'One', 'player1@example.com', 'player1pass', 'player'),
       (9, 'Player', 'Two', 'player2@example.com', 'player2pass', 'player'),
       (10, 'Player', 'Three', 'player3@example.com', 'player3pass', 'player');

INSERT INTO referees (userID, firstName, lastName, email, password, role)
VALUES (11, 'Referee', 'One', 'ref1@example.com', 'ref1pass', 'referee'),
       (12, 'Referee', 'Two', 'ref2@example.com', 'ref2pass', 'referee'),
       (13, 'Referee', 'Three', 'ref3@example.com', 'ref3pass', 'referee');

-- Insert into Leagues
INSERT INTO leagues (leagueID, leagueName, leagueGender, divisionNum, leagueSport) VALUES
('L001', 'WDiv1Soccer', 'Womens', 'Division1', 'Soccer'),
('L002', 'WDiv2Soccer', 'Womens', 'Division2', 'Soccer'),
('L003', 'MDiv1Soccer', 'Mens', 'Division1', 'Soccer'),
('L004', 'WDiv1Volleyball', 'Womens', 'Division1', 'Volleyball');

-- Insert into Teams
--INSERT INTO teams (teamID, teamName, ranking) VALUES
INSERT INTO teams (teamID, teamName, captainID, leagueID) VALUES
('T001', 'Team A', 1, 'L003'),
('T002', 'Team B', 2, 'L004'),
('T003', 'Team C', 3, 'L004'),
('T004', 'Team D', 4, 'L004');
-- testing creating team without captain
INSERT INTO teams (teamID, teamName, leagueID) VALUES
('T005', 'Team Without Captain', 'L001');

-- Insert into games
INSERT INTO games (gameID, datetime, location, team1ID, team2ID, teamScore1, teamScore2, gameStatus, refereeID, leagueID) VALUES
(1, '2024-11-10 10:00:00', 'City Stadium', 'T001', 'T002', 2, 1, 'completed', 11, 'L001'),
(2, '2024-11-10 14:00:00', 'Valley Field', 'T003', 'T004', 3, 0, 'completed', 11, 'L002'),
(3, '2024-11-11 18:00:00', 'Peak Arena', 'T005', 'T001', 1, 1, 'completed', 11, 'L003'),
(4, '2024-11-11 12:00:00', 'Spike Court', 'T002', 'T003', 3, 2, 'completed', 12, 'L004'),
(5, '2024-11-17 10:00:00', 'City Stadium', 'T002', 'T001', 0, 0, 'upcoming', 12, 'L001'),
(6, '2024-11-17 14:00:00', 'Valley Field', 'T004', 'T003', 0, 0, 'upcoming', 12, 'L002'),
(7, '2024-11-18 18:00:00', 'Peak Arena', 'T001', 'T005', 0, 0, 'upcoming', 13, 'L003'),
(8, '2024-11-18 12:00:00', 'Spike Court', 'T003', 'T004', 0, 0, 'upcoming', 13, 'L004');










---- Insert Captains (with teamID)
--INSERT INTO captains (userID, firstName, lastName, email, password, role, teamID)
--VALUES (2001, 'John', 'Doe', 'john.doe@example.com', 'password123', 'captain', 'T001'), -- Assign to T001
--       (2002, 'Jane', 'Smith', 'jane.smith@example.com', 'securepass', 'captain', 'T002'), -- Assign to T002
--       (2003, 'Alice', 'Johnson', 'alice.johnson@example.com', 'referee456', 'captain', 'T003'), -- Assign to T003
--       (2004, 'Bob', 'Williams', 'bob.williams@example.com', 'adminpass', 'captain', 'T004'), -- Assign to T004
--       (2005, 'Charlie', 'Brown', 'charlie.brown@example.com', 'playerpass', 'captain', 'T005'); -- Assign to T005
--
---- Insert Admins (no teamID)
--INSERT INTO admins (userID, firstName, lastName, email, password, role)
--VALUES (4006, 'Admin', 'User', 'admin@example.com', 'adminpassword', 'admin'),
--       (4007, 'Super', 'Admin', 'superadmin@example.com', 'superadminpass', 'admin');
--
---- Insert Players (with teamID)
--INSERT INTO players (userID, firstName, lastName, email, password, role, teamID)
--VALUES (1001, 'Player', 'One', 'player1@example.com', 'player1pass', 'player', 'T001'), -- Assign to T001
--       (1002, 'Player', 'Two', 'player2@example.com', 'player2pass', 'player', 'T002'), -- Assign to T002
--       (1003, 'Player', 'Three', 'player3@example.com', 'player3pass', 'player', 'T003'), -- Assign to T003
--       (1004, 'Player', 'Four', 'player4@example.com', 'player4pass', 'player', 'T004'),
--       (1005, 'Player', 'Five', 'player5@example.com', 'player5pass', 'player', 'T005'),
--       (1006, 'Player', 'Six', 'player6@example.com', 'player6pass', 'player', 'T006'),
--       (1007, 'Player', 'Seven', 'player7@example.com', 'player7pass', 'player', 'T007'),
--       (1008, 'Player', 'Eight', 'player8@example.com', 'player8pass', 'player', 'T008');