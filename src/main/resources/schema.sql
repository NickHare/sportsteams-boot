CREATE TABLE player (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(128) NOT NULL,
    created_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_player PRIMARY KEY (id),
    CONSTRAINT uq_player_name UNIQUE (name)
);

CREATE TABLE team (
    id BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(128) NOT NULL,
    created_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_team PRIMARY KEY (id),
    CONSTRAINT uq_team_name UNIQUE (name)
);

CREATE TABLE roster (
    id BIGINT AUTO_INCREMENT NOT NULL,
    player BIGINT NOT NULL,
    team BIGINT NOT NULL,
    created_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_timestamp DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT pk_roster PRIMARY KEY (id),
    CONSTRAINT fk_roster_player FOREIGN KEY (player) REFERENCES player (id),
    CONSTRAINT fk_roster_team FOREIGN KEY (team) REFERENCES team (id)
);