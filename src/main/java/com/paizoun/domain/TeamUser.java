package com.paizoun.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;
import java.time.Instant;
import java.util.UUID;

/**
 * A TeamUser.
 */
@Table("teamUser")
public class TeamUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String userLogin;

    private UUID teamId;

    private Instant joinDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public TeamUser userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public TeamUser teamId(UUID teamId) {
        this.teamId = teamId;
        return this;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public Instant getJoinDate() {
        return joinDate;
    }

    public TeamUser joinDate(Instant joinDate) {
        this.joinDate = joinDate;
        return this;
    }

    public void setJoinDate(Instant joinDate) {
        this.joinDate = joinDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamUser)) {
            return false;
        }
        return id != null && id.equals(((TeamUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TeamUser{" +
            "id=" + getId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", teamId='" + getTeamId() + "'" +
            ", joinDate='" + getJoinDate() + "'" +
            "}";
    }
}
