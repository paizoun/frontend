
CREATE TABLE IF NOT EXISTS team (
    id uuid,
    name text,
    description text,
    shell blob,
    shell_content_type text,
    createTeamDate timestamp,
    userLogin text,
    lastUserLogin text,
    quantity int,
    score int,
    PRIMARY KEY(id)
);
