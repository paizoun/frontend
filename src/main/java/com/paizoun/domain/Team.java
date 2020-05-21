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
 * A Team.
 */
@Table("team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String name;

    private String description;

    private ByteBuffer shell;

    @Column("shell_content_type")
    private String shellContentType;

    private Instant createTeamDate;

    private String userLogin;

    private String lastUserLogin;

    private Integer quantity;

    private Integer score;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Team name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Team description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ByteBuffer getShell() {
        return shell;
    }

    public Team shell(ByteBuffer shell) {
        this.shell = shell;
        return this;
    }

    public void setShell(ByteBuffer shell) {
        this.shell = shell;
    }

    public String getShellContentType() {
        return shellContentType;
    }

    public Team shellContentType(String shellContentType) {
        this.shellContentType = shellContentType;
        return this;
    }

    public void setShellContentType(String shellContentType) {
        this.shellContentType = shellContentType;
    }

    public Instant getCreateTeamDate() {
        return createTeamDate;
    }

    public Team createTeamDate(Instant createTeamDate) {
        this.createTeamDate = createTeamDate;
        return this;
    }

    public void setCreateTeamDate(Instant createTeamDate) {
        this.createTeamDate = createTeamDate;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public Team userLogin(String userLogin) {
        this.userLogin = userLogin;
        return this;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getLastUserLogin() {
        return lastUserLogin;
    }

    public Team lastUserLogin(String lastUserLogin) {
        this.lastUserLogin = lastUserLogin;
        return this;
    }

    public void setLastUserLogin(String lastUserLogin) {
        this.lastUserLogin = lastUserLogin;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Team quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getScore() {
        return score;
    }

    public Team score(Integer score) {
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
        if (!(o instanceof Team)) {
            return false;
        }
        return id != null && id.equals(((Team) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", shell='" + getShell() + "'" +
            ", shellContentType='" + getShellContentType() + "'" +
            ", createTeamDate='" + getCreateTeamDate() + "'" +
            ", userLogin='" + getUserLogin() + "'" +
            ", lastUserLogin='" + getLastUserLogin() + "'" +
            ", quantity=" + getQuantity() +
            ", score=" + getScore() +
            "}";
    }
}
