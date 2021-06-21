INSERT INTO player (id, name) VALUES
(1, 'Alex'),
(2, 'Dan'),
(3, 'Nick');

INSERT INTO team (id, name) VALUES
(1, 'Easy Company'),
(2, 'UnEasy Company');

INSERT INTO roster (id, player_id, team_id, current_roster) VALUES
(1, 1, 1, TRUE),
(2, 2, 1, TRUE),
(3, 3, 2, TRUE);

INSERT INTO game (id, home_team_id, away_team_id, home_score, away_score, status, start_timestamp) VALUES
(1, 1, 2, 2, 1, 'COMPLETED', '2020-01-01 00:00:00'),
(2, 2, 1, 0, 0, 'IN_PROGRESS', '2020-01-01 01:00:00'),
(3, 1, 2, 0, 0, 'SCHEDULED', '2020-01-01 01:00:00');