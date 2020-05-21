
CREATE TABLE IF NOT EXISTS teamDetailByUser (
    id uuid,
    userLogin text,
    teamId uuid,
    teamName text,
    teamShell blob,
    team_shell_content_type text,
    teamScore int,
    userJoinDate timestamp,
    PRIMARY KEY(id)
);
