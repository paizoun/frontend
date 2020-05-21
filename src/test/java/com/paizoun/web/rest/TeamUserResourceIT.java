package com.paizoun.web.rest;

import com.paizoun.AbstractCassandraTest;
import com.paizoun.PaizounApp;
import com.paizoun.config.SecurityBeanOverrideConfiguration;
import com.paizoun.domain.TeamUser;
import com.paizoun.repository.TeamUserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TeamUserResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, PaizounApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class TeamUserResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    private static final UUID DEFAULT_TEAM_ID = UUID.randomUUID();
    private static final UUID UPDATED_TEAM_ID = UUID.randomUUID();

    private static final Instant DEFAULT_JOIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TeamUserRepository teamUserRepository;

    @Autowired
    private MockMvc restTeamUserMockMvc;

    private TeamUser teamUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamUser createEntity() {
        TeamUser teamUser = new TeamUser()
            .userLogin(DEFAULT_USER_LOGIN)
            .teamId(DEFAULT_TEAM_ID)
            .joinDate(DEFAULT_JOIN_DATE);
        return teamUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamUser createUpdatedEntity() {
        TeamUser teamUser = new TeamUser()
            .userLogin(UPDATED_USER_LOGIN)
            .teamId(UPDATED_TEAM_ID)
            .joinDate(UPDATED_JOIN_DATE);
        return teamUser;
    }

    @BeforeEach
    public void initTest() {
        teamUserRepository.deleteAll();
        teamUser = createEntity();
    }

    @Test
    public void createTeamUser() throws Exception {
        int databaseSizeBeforeCreate = teamUserRepository.findAll().size();

        // Create the TeamUser
        restTeamUserMockMvc.perform(post("/api/team-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamUser)))
            .andExpect(status().isCreated());

        // Validate the TeamUser in the database
        List<TeamUser> teamUserList = teamUserRepository.findAll();
        assertThat(teamUserList).hasSize(databaseSizeBeforeCreate + 1);
        TeamUser testTeamUser = teamUserList.get(teamUserList.size() - 1);
        assertThat(testTeamUser.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
        assertThat(testTeamUser.getTeamId()).isEqualTo(DEFAULT_TEAM_ID);
        assertThat(testTeamUser.getJoinDate()).isEqualTo(DEFAULT_JOIN_DATE);
    }

    @Test
    public void createTeamUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamUserRepository.findAll().size();

        // Create the TeamUser with an existing ID
        teamUser.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamUserMockMvc.perform(post("/api/team-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamUser)))
            .andExpect(status().isBadRequest());

        // Validate the TeamUser in the database
        List<TeamUser> teamUserList = teamUserRepository.findAll();
        assertThat(teamUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllTeamUsers() throws Exception {
        // Initialize the database
        teamUser.setId(UUID.randomUUID());
        teamUserRepository.save(teamUser);

        // Get all the teamUserList
        restTeamUserMockMvc.perform(get("/api/team-users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamUser.getId().toString())))
            .andExpect(jsonPath("$.[*].userLogin").value(hasItem(DEFAULT_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].teamId").value(hasItem(DEFAULT_TEAM_ID.toString())))
            .andExpect(jsonPath("$.[*].joinDate").value(hasItem(DEFAULT_JOIN_DATE.toString())));
    }
    
    @Test
    public void getTeamUser() throws Exception {
        // Initialize the database
        teamUser.setId(UUID.randomUUID());
        teamUserRepository.save(teamUser);

        // Get the teamUser
        restTeamUserMockMvc.perform(get("/api/team-users/{id}", teamUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamUser.getId().toString()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN))
            .andExpect(jsonPath("$.teamId").value(DEFAULT_TEAM_ID.toString()))
            .andExpect(jsonPath("$.joinDate").value(DEFAULT_JOIN_DATE.toString()));
    }

    @Test
    public void getNonExistingTeamUser() throws Exception {
        // Get the teamUser
        restTeamUserMockMvc.perform(get("/api/team-users/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTeamUser() throws Exception {
        // Initialize the database
        teamUser.setId(UUID.randomUUID());
        teamUserRepository.save(teamUser);

        int databaseSizeBeforeUpdate = teamUserRepository.findAll().size();

        // Update the teamUser
        TeamUser updatedTeamUser = teamUserRepository.findById(teamUser.getId()).get();
        updatedTeamUser
            .userLogin(UPDATED_USER_LOGIN)
            .teamId(UPDATED_TEAM_ID)
            .joinDate(UPDATED_JOIN_DATE);

        restTeamUserMockMvc.perform(put("/api/team-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamUser)))
            .andExpect(status().isOk());

        // Validate the TeamUser in the database
        List<TeamUser> teamUserList = teamUserRepository.findAll();
        assertThat(teamUserList).hasSize(databaseSizeBeforeUpdate);
        TeamUser testTeamUser = teamUserList.get(teamUserList.size() - 1);
        assertThat(testTeamUser.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
        assertThat(testTeamUser.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
        assertThat(testTeamUser.getJoinDate()).isEqualTo(UPDATED_JOIN_DATE);
    }

    @Test
    public void updateNonExistingTeamUser() throws Exception {
        int databaseSizeBeforeUpdate = teamUserRepository.findAll().size();

        // Create the TeamUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamUserMockMvc.perform(put("/api/team-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamUser)))
            .andExpect(status().isBadRequest());

        // Validate the TeamUser in the database
        List<TeamUser> teamUserList = teamUserRepository.findAll();
        assertThat(teamUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTeamUser() throws Exception {
        // Initialize the database
        teamUser.setId(UUID.randomUUID());
        teamUserRepository.save(teamUser);

        int databaseSizeBeforeDelete = teamUserRepository.findAll().size();

        // Delete the teamUser
        restTeamUserMockMvc.perform(delete("/api/team-users/{id}", teamUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamUser> teamUserList = teamUserRepository.findAll();
        assertThat(teamUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
