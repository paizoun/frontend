package com.paizoun.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A ScoreTeamByUser.
 */
@Table("scoreTeamByUser")
public class ScoreTeamByUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String userLogin;

    private UUID teamId;

    private Integer score;

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

    public ScoreTeamByUser userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public ScoreTeamByUser teamId(UUID teamId) {
        this.teamId = teamId;
        return this;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public Integer getScore() {
        return score;
    }

    public ScoreTeamByUser score(Integer score) {
        this.score = score;
        return this;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScoreTeamByUser)) {
            return false;
        }
        return id != null && id.equals(((ScoreTeamByUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ScoreTeamByUser{" +
            "id=" + getId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", teamId='" + getTeamId() + "'" +
            ", score=" + getScore() +
            "}";
    }
}
