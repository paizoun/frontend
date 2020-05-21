package com.paizoun.web.rest;

import com.paizoun.domain.TeamDetailByUser;
import com.paizoun.repository.TeamDetailByUserRepository;
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
 * REST controller for managing {@link com.paizoun.domain.TeamDetailByUser}.
 */
@RestController
@RequestMapping("/api")
public class TeamDetailByUserResource {

    private final Logger log = LoggerFactory.getLogger(TeamDetailByUserResource.class);

    private static final String ENTITY_NAME = "teamDetailByUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TeamDetailByUserRepository teamDetailByUserRepository;

    public TeamDetailByUserResource(TeamDetailByUserRepository teamDetailByUserRepository) {
        this.teamDetailByUserRepository = teamDetailByUserRepository;
    }

    /**
     * {@code POST  /team-detail-by-users} : Create a new teamDetailByUser.
     *
     * @param teamDetailByUser the teamDetailByUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new teamDetailByUser, or with status {@code 400 (Bad Request)} if the teamDetailByUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/team-detail-by-users")
    public ResponseEntity<TeamDetailByUser> createTeamDetailByUser(@RequestBody TeamDetailByUser teamDetailByUser) throws URISyntaxException {
        log.debug("REST request to save TeamDetailByUser : {}", teamDetailByUser);
        if (teamDetailByUser.getId() != null) {
            throw new BadRequestAlertException("A new teamDetailByUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        teamDetailByUser.setId(UUID.randomUUID());
        TeamDetailByUser result = teamDetailByUserRepository.save(teamDetailByUser);
        return ResponseEntity.created(new URI("/api/team-detail-by-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /team-detail-by-users} : Updates an existing teamDetailByUser.
     *
     * @param teamDetailByUser the teamDetailByUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated teamDetailByUser,
     * or with status {@code 400 (Bad Request)} if the teamDetailByUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the teamDetailByUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/team-detail-by-users")
    public ResponseEntity<TeamDetailByUser> updateTeamDetailByUser(@RequestBody TeamDetailByUser teamDetailByUser) throws URISyntaxException {
        log.debug("REST request to update TeamDetailByUser : {}", teamDetailByUser);
        if (teamDetailByUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeamDetailByUser result = teamDetailByUserRepository.save(teamDetailByUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, teamDetailByUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /team-detail-by-users} : get all the teamDetailByUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of teamDetailByUsers in body.
     */
    @GetMapping("/team-detail-by-users")
    public List<TeamDetailByUser> getAllTeamDetailByUsers() {
        log.debug("REST request to get all TeamDetailByUsers");
        return teamDetailByUserRepository.findAll();
    }

    /**
     * {@code GET  /team-detail-by-users/:id} : get the "id" teamDetailByUser.
     *
     * @param id the id of the teamDetailByUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the teamDetailByUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/team-detail-by-users/{id}")
    public ResponseEntity<TeamDetailByUser> getTeamDetailByUser(@PathVariable UUID id) {
        log.debug("REST request to get TeamDetailByUser : {}", id);
        Optional<TeamDetailByUser> teamDetailByUser = teamDetailByUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(teamDetailByUser);
    }

    /**
     * {@code DELETE  /team-detail-by-users/:id} : delete the "id" teamDetailByUser.
     *
     * @param id the id of the teamDetailByUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/team-detail-by-users/{id}")
    public ResponseEntity<Void> deleteTeamDetailByUser(@PathVariable UUID id) {
        log.debug("REST request to delete TeamDetailByUser : {}", id);
        teamDetailByUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
