package com.paizoun.web.rest;

import com.paizoun.AbstractCassandraTest;
import com.paizoun.PaizounApp;
import com.paizoun.config.SecurityBeanOverrideConfiguration;
import com.paizoun.domain.ScoreTeamByUser;
import com.paizoun.repository.ScoreTeamByUserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ScoreTeamByUserResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, PaizounApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class ScoreTeamByUserResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    private static final UUID DEFAULT_TEAM_ID = UUID.randomUUID();
    private static final UUID UPDATED_TEAM_ID = UUID.randomUUID();

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    @Autowired
    private ScoreTeamByUserRepository scoreTeamByUserRepository;

    @Autowired
    private MockMvc restScoreTeamByUserMockMvc;

    private ScoreTeamByUser scoreTeamByUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScoreTeamByUser createEntity() {
        ScoreTeamByUser scoreTeamByUser = new ScoreTeamByUser()
            .userLogin(DEFAULT_USER_LOGIN)
            .teamId(DEFAULT_TEAM_ID)
            .score(DEFAULT_SCORE);
        return scoreTeamByUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScoreTeamByUser createUpdatedEntity() {
        ScoreTeamByUser scoreTeamByUser = new ScoreTeamByUser()
            .userLogin(UPDATED_USER_LOGIN)
            .teamId(UPDATED_TEAM_ID)
            .score(UPDATED_SCORE);
        return scoreTeamByUser;
    }

    @BeforeEach
    public void initTest() {
        scoreTeamByUserRepository.deleteAll();
        scoreTeamByUser = createEntity();
    }

    @Test
    public void createScoreTeamByUser() throws Exception {
        int databaseSizeBeforeCreate = scoreTeamByUserRepository.findAll().size();

        // Create the ScoreTeamByUser
        restScoreTeamByUserMockMvc.perform(post("/api/score-team-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreTeamByUser)))
            .andExpect(status().isCreated());

        // Validate the ScoreTeamByUser in the database
        List<ScoreTeamByUser> scoreTeamByUserList = scoreTeamByUserRepository.findAll();
        assertThat(scoreTeamByUserList).hasSize(databaseSizeBeforeCreate + 1);
        ScoreTeamByUser testScoreTeamByUser = scoreTeamByUserList.get(scoreTeamByUserList.size() - 1);
        assertThat(testScoreTeamByUser.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
        assertThat(testScoreTeamByUser.getTeamId()).isEqualTo(DEFAULT_TEAM_ID);
        assertThat(testScoreTeamByUser.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    public void createScoreTeamByUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scoreTeamByUserRepository.findAll().size();

        // Create the ScoreTeamByUser with an existing ID
        scoreTeamByUser.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restScoreTeamByUserMockMvc.perform(post("/api/score-team-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreTeamByUser)))
            .andExpect(status().isBadRequest());

        // Validate the ScoreTeamByUser in the database
        List<ScoreTeamByUser> scoreTeamByUserList = scoreTeamByUserRepository.findAll();
        assertThat(scoreTeamByUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllScoreTeamByUsers() throws Exception {
        // Initialize the database
        scoreTeamByUser.setId(UUID.randomUUID());
        scoreTeamByUserRepository.save(scoreTeamByUser);

        // Get all the scoreTeamByUserList
        restScoreTeamByUserMockMvc.perform(get("/api/score-team-by-users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scoreTeamByUser.getId().toString())))
            .andExpect(jsonPath("$.[*].userLogin").value(hasItem(DEFAULT_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].teamId").value(hasItem(DEFAULT_TEAM_ID.toString())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }
    
    @Test
    public void getScoreTeamByUser() throws Exception {
        // Initialize the database
        scoreTeamByUser.setId(UUID.randomUUID());
        scoreTeamByUserRepository.save(scoreTeamByUser);

        // Get the scoreTeamByUser
        restScoreTeamByUserMockMvc.perform(get("/api/score-team-by-users/{id}", scoreTeamByUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(scoreTeamByUser.getId().toString()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN))
            .andExpect(jsonPath("$.teamId").value(DEFAULT_TEAM_ID.toString()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    public void getNonExistingScoreTeamByUser() throws Exception {
        // Get the scoreTeamByUser
        restScoreTeamByUserMockMvc.perform(get("/api/score-team-by-users/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateScoreTeamByUser() throws Exception {
        // Initialize the database
        scoreTeamByUser.setId(UUID.randomUUID());
        scoreTeamByUserRepository.save(scoreTeamByUser);

        int databaseSizeBeforeUpdate = scoreTeamByUserRepository.findAll().size();

        // Update the scoreTeamByUser
        ScoreTeamByUser updatedScoreTeamByUser = scoreTeamByUserRepository.findById(scoreTeamByUser.getId()).get();
        updatedScoreTeamByUser
            .userLogin(UPDATED_USER_LOGIN)
            .teamId(UPDATED_TEAM_ID)
            .score(UPDATED_SCORE);

        restScoreTeamByUserMockMvc.perform(put("/api/score-team-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedScoreTeamByUser)))
            .andExpect(status().isOk());

        // Validate the ScoreTeamByUser in the database
        List<ScoreTeamByUser> scoreTeamByUserList = scoreTeamByUserRepository.findAll();
        assertThat(scoreTeamByUserList).hasSize(databaseSizeBeforeUpdate);
        ScoreTeamByUser testScoreTeamByUser = scoreTeamByUserList.get(scoreTeamByUserList.size() - 1);
        assertThat(testScoreTeamByUser.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
        assertThat(testScoreTeamByUser.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
        assertThat(testScoreTeamByUser.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    public void updateNonExistingScoreTeamByUser() throws Exception {
        int databaseSizeBeforeUpdate = scoreTeamByUserRepository.findAll().size();

        // Create the ScoreTeamByUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScoreTeamByUserMockMvc.perform(put("/api/score-team-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(scoreTeamByUser)))
            .andExpect(status().isBadRequest());

        // Validate the ScoreTeamByUser in the database
        List<ScoreTeamByUser> scoreTeamByUserList = scoreTeamByUserRepository.findAll();
        assertThat(scoreTeamByUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteScoreTeamByUser() throws Exception {
        // Initialize the database
        scoreTeamByUser.setId(UUID.randomUUID());
        scoreTeamByUserRepository.save(scoreTeamByUser);

        int databaseSizeBeforeDelete = scoreTeamByUserRepository.findAll().size();

        // Delete the scoreTeamByUser
        restScoreTeamByUserMockMvc.perform(delete("/api/score-team-by-users/{id}", scoreTeamByUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScoreTeamByUser> scoreTeamByUserList = scoreTeamByUserRepository.findAll();
        assertThat(scoreTeamByUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
