package com.paizoun.web.rest;

import com.paizoun.domain.TeamUser;
import com.paizoun.repository.TeamUserRepository;
import com.paizoun.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for managing {@link com.paizoun.domain.TeamUser}.
 */
@RestController
@RequestMapping("/api")
public class TeamUserResource {

    private final Logger log = LoggerFactory.getLogger(TeamUserResource.class);

    private static final String ENTITY_NAME = "teamUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamUserRepository teamUserRepository;

    public TeamUserResource(TeamUserRepository teamUserRepository) {
        this.teamUserRepository = teamUserRepository;
    }

    /**
     * {@code POST  /team-users} : Create a new teamUser.
     *
     * @param teamUser the teamUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamUser, or with status {@code 400 (Bad Request)} if the teamUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-users")
    public ResponseEntity<TeamUser> createTeamUser(@RequestBody TeamUser teamUser) throws URISyntaxException {
        log.debug("REST request to save TeamUser : {}", teamUser);
        if (teamUser.getId() != null) {
            throw new BadRequestAlertException("A new teamUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        teamUser.setId(UUID.randomUUID());
        TeamUser result = teamUserRepository.save(teamUser);
        return ResponseEntity.created(new URI("/api/team-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-users} : Updates an existing teamUser.
     *
     * @param teamUser the teamUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamUser,
     * or with status {@code 400 (Bad Request)} if the teamUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-users")
    public ResponseEntity<TeamUser> updateTeamUser(@RequestBody TeamUser teamUser) throws URISyntaxException {
        log.debug("REST request to update TeamUser : {}", teamUser);
        if (teamUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeamUser result = teamUserRepository.save(teamUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /team-users} : get all the teamUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamUsers in body.
     */
    @GetMapping("/team-users")
    public List<TeamUser> getAllTeamUsers() {
        log.debug("REST request to get all TeamUsers");
        return teamUserRepository.findAll();
    }

    /**
     * {@code GET  /team-users/:id} : get the "id" teamUser.
     *
     * @param id the id of the teamUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-users/{id}")
    public ResponseEntity<TeamUser> getTeamUser(@PathVariable UUID id) {
        log.debug("REST request to get TeamUser : {}", id);
        Optional<TeamUser> teamUser = teamUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(teamUser);
    }

    /**
     * {@code DELETE  /team-users/:id} : delete the "id" teamUser.
     *
     * @param id the id of the teamUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-users/{id}")
    public ResponseEntity<Void> deleteTeamUser(@PathVariable UUID id) {
        log.debug("REST request to delete TeamUser : {}", id);
        teamUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
