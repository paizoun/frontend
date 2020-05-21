
CREATE TABLE IF NOT EXISTS scoreTeamByUser (
    id uuid,
    userLogin text,
    teamId uuid,
    score int,
    PRIMARY KEY(id)
);
