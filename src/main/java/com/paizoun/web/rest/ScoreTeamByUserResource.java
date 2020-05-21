package com.paizoun.web.rest;

import com.paizoun.domain.ScoreTeamByUser;
import com.paizoun.repository.ScoreTeamByUserRepository;
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
 * REST controller for managing {@link com.paizoun.domain.ScoreTeamByUser}.
 */
@RestController
@RequestMapping("/api")
public class ScoreTeamByUserResource {

    private final Logger log = LoggerFactory.getLogger(ScoreTeamByUserResource.class);

    private static final String ENTITY_NAME = "scoreTeamByUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScoreTeamByUserRepository scoreTeamByUserRepository;

    public ScoreTeamByUserResource(ScoreTeamByUserRepository scoreTeamByUserRepository) {
        this.scoreTeamByUserRepository = scoreTeamByUserRepository;
    }

    /**
     * {@code POST  /score-team-by-users} : Create a new scoreTeamByUser.
     *
     * @param scoreTeamByUser the scoreTeamByUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scoreTeamByUser, or with status {@code 400 (Bad Request)} if the scoreTeamByUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/score-team-by-users")
    public ResponseEntity<ScoreTeamByUser> createScoreTeamByUser(@RequestBody ScoreTeamByUser scoreTeamByUser) throws URISyntaxException {
        log.debug("REST request to save ScoreTeamByUser : {}", scoreTeamByUser);
        if (scoreTeamByUser.getId() != null) {
            throw new BadRequestAlertException("A new scoreTeamByUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        scoreTeamByUser.setId(UUID.randomUUID());
        ScoreTeamByUser result = scoreTeamByUserRepository.save(scoreTeamByUser);
        return ResponseEntity.created(new URI("/api/score-team-by-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /score-team-by-users} : Updates an existing scoreTeamByUser.
     *
     * @param scoreTeamByUser the scoreTeamByUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scoreTeamByUser,
     * or with status {@code 400 (Bad Request)} if the scoreTeamByUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scoreTeamByUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/score-team-by-users")
    public ResponseEntity<ScoreTeamByUser> updateScoreTeamByUser(@RequestBody ScoreTeamByUser scoreTeamByUser) throws URISyntaxException {
        log.debug("REST request to update ScoreTeamByUser : {}", scoreTeamByUser);
        if (scoreTeamByUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScoreTeamByUser result = scoreTeamByUserRepository.save(scoreTeamByUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scoreTeamByUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /score-team-by-users} : get all the scoreTeamByUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scoreTeamByUsers in body.
     */
    @GetMapping("/score-team-by-users")
    public List<ScoreTeamByUser> getAllScoreTeamByUsers() {
        log.debug("REST request to get all ScoreTeamByUsers");
        return scoreTeamByUserRepository.findAll();
    }

    /**
     * {@code GET  /score-team-by-users/:id} : get the "id" scoreTeamByUser.
     *
     * @param id the id of the scoreTeamByUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scoreTeamByUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/score-team-by-users/{id}")
    public ResponseEntity<ScoreTeamByUser> getScoreTeamByUser(@PathVariable UUID id) {
        log.debug("REST request to get ScoreTeamByUser : {}", id);
        Optional<ScoreTeamByUser> scoreTeamByUser = scoreTeamByUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(scoreTeamByUser);
    }

    /**
     * {@code DELETE  /score-team-by-users/:id} : delete the "id" scoreTeamByUser.
     *
     * @param id the id of the scoreTeamByUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/score-team-by-users/{id}")
    public ResponseEntity<Void> deleteScoreTeamByUser(@PathVariable UUID id) {
        log.debug("REST request to delete ScoreTeamByUser : {}", id);
        scoreTeamByUserRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
