package com.paizoun.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Objects;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.UUID;

/**
 * A TeamDetailByUser.
 */
@Table("teamDetailByUser")
public class TeamDetailByUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String userLogin;

    private UUID teamId;

    private String teamName;

    private ByteBuffer teamShell;

    @Column("team_shell_content_type")
    private String teamShellContentType;

    private Integer teamScore;

    private Instant userJoinDate;

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

    public TeamDetailByUser userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public TeamDetailByUser teamId(UUID teamId) {
        this.teamId = teamId;
        return this;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public TeamDetailByUser teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ByteBuffer getTeamShell() {
        return teamShell;
    }

    public TeamDetailByUser teamShell(ByteBuffer teamShell) {
        this.teamShell = teamShell;
        return this;
    }

    public void setTeamShell(ByteBuffer teamShell) {
        this.teamShell = teamShell;
    }

    public String getTeamShellContentType() {
        return teamShellContentType;
    }

    public TeamDetailByUser teamShellContentType(String teamShellContentType) {
        this.teamShellContentType = teamShellContentType;
        return this;
    }

    public void setTeamShellContentType(String teamShellContentType) {
        this.teamShellContentType = teamShellContentType;
    }

    public Integer getTeamScore() {
        return teamScore;
    }

    public TeamDetailByUser teamScore(Integer teamScore) {
        this.teamScore = teamScore;
        return this;
    }

    public void setTeamScore(Integer teamScore) {
        this.teamScore = teamScore;
    }

    public Instant getUserJoinDate() {
        return userJoinDate;
    }

    public TeamDetailByUser userJoinDate(Instant userJoinDate) {
        this.userJoinDate = userJoinDate;
        return this;
    }

    public void setUserJoinDate(Instant userJoinDate) {
        this.userJoinDate = userJoinDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamDetailByUser)) {
            return false;
        }
        return id != null && id.equals(((TeamDetailByUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TeamDetailByUser{" +
            "id=" + getId() +
            ", userLogin='" + getUserLogin() + "'" +
            ", teamId='" + getTeamId() + "'" +
            ", teamName='" + getTeamName() + "'" +
            ", teamShell='" + getTeamShell() + "'" +
            ", teamShellContentType='" + getTeamShellContentType() + "'" +
            ", teamScore=" + getTeamScore() +
            ", userJoinDate='" + getUserJoinDate() + "'" +
            "}";
    }
}
