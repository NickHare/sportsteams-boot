CREATE TABLE player (
    id BIGINT AUTO_INCREMENT NOT NULL,
    external_id VARCHAR(128),
    name VARCHAR(128) NOT NULL,
    created_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_player PRIMARY KEY (id),
    CONSTRAINT uq_player_name UNIQUE (name)
);

CREATE TABLE team (
    id BIGINT AUTO_INCREMENT NOT NULL,
    external_id VARCHAR(128),
    name VARCHAR(128) NOT NULL,
    created_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_team PRIMARY KEY (id),
    CONSTRAINT uq_team_name UNIQUE (name)
);

CREATE TABLE roster (
    id BIGINT AUTO_INCREMENT NOT NULL,
    external_id VARCHAR(128),
    player_id BIGINT NOT NULL,
    team_id BIGINT NOT NULL,
    created_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_roster PRIMARY KEY (id),
    CONSTRAINT fk_roster_player FOREIGN KEY (player_id) REFERENCES player (id),
    CONSTRAINT fk_roster_team FOREIGN KEY (team_id) REFERENCES team (id)
);

CREATE TABLE game (
    id BIGINT AUTO_INCREMENT NOT NULL,
    external_id VARCHAR(128),
    home_team_id BIGINT NOT NULL,
    away_team_id BIGINT NOT NULL,
    home_score INT UNSIGNED NOT NULL,
    away_score INT UNSIGNED NOT NULL,
    status varchar(32) NOT NULL,
    start_timestamp DATETIME NOT NULL,
    created_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_game PRIMARY KEY (id),
    CONSTRAINT fk_game_home_team FOREIGN KEY (home_team_id) REFERENCES team (id),
    CONSTRAINT fk_game_away_team FOREIGN KEY (away_team_id) REFERENCES team (id)
);