
CREATE TABLE IF NOT EXISTS teamUser (
    id uuid,
    userLogin text,
    teamId uuid,
    joinDate timestamp,
    PRIMARY KEY(id)
);
