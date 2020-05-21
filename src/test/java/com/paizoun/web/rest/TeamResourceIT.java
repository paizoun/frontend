package com.paizoun.web.rest;

import com.paizoun.AbstractCassandraTest;
import com.paizoun.PaizounApp;
import com.paizoun.config.SecurityBeanOverrideConfiguration;
import com.paizoun.domain.Team;
import com.paizoun.repository.TeamRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.nio.ByteBuffer;
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
 * Integration tests for the {@link TeamResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, PaizounApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class TeamResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ByteBuffer DEFAULT_SHELL = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_SHELL = ByteBuffer.wrap(TestUtil.createByteArray(1, "1"));
    private static final String DEFAULT_SHELL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SHELL_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_CREATE_TEAM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TEAM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LAST_USER_LOGIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_SCORE = 1;
    private static final Integer UPDATED_SCORE = 2;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MockMvc restTeamMockMvc;

    private Team team;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createEntity() {
        Team team = new Team()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .shell(DEFAULT_SHELL)
            .shellContentType(DEFAULT_SHELL_CONTENT_TYPE)
            .createTeamDate(DEFAULT_CREATE_TEAM_DATE)
            .userLogin(DEFAULT_USER_LOGIN)
            .lastUserLogin(DEFAULT_LAST_USER_LOGIN)
            .quantity(DEFAULT_QUANTITY)
            .score(DEFAULT_SCORE);
        return team;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Team createUpdatedEntity() {
        Team team = new Team()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .shell(UPDATED_SHELL)
            .shellContentType(UPDATED_SHELL_CONTENT_TYPE)
            .createTeamDate(UPDATED_CREATE_TEAM_DATE)
            .userLogin(UPDATED_USER_LOGIN)
            .lastUserLogin(UPDATED_LAST_USER_LOGIN)
            .quantity(UPDATED_QUANTITY)
            .score(UPDATED_SCORE);
        return team;
    }

    @BeforeEach
    public void initTest() {
        teamRepository.deleteAll();
        team = createEntity();
    }

    @Test
    public void createTeam() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team
        restTeamMockMvc.perform(post("/api/teams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isCreated());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate + 1);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTeam.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTeam.getShell()).isEqualTo(DEFAULT_SHELL);
        assertThat(testTeam.getShellContentType()).isEqualTo(DEFAULT_SHELL_CONTENT_TYPE);
        assertThat(testTeam.getCreateTeamDate()).isEqualTo(DEFAULT_CREATE_TEAM_DATE);
        assertThat(testTeam.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
        assertThat(testTeam.getLastUserLogin()).isEqualTo(DEFAULT_LAST_USER_LOGIN);
        assertThat(testTeam.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTeam.getScore()).isEqualTo(DEFAULT_SCORE);
    }

    @Test
    public void createTeamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamRepository.findAll().size();

        // Create the Team with an existing ID
        team.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamMockMvc.perform(post("/api/teams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllTeams() throws Exception {
        // Initialize the database
        team.setId(UUID.randomUUID());
        teamRepository.save(team);

        // Get all the teamList
        restTeamMockMvc.perform(get("/api/teams"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(team.getId().toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].shellContentType").value(hasItem(DEFAULT_SHELL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].shell").value(hasItem(Base64Utils.encodeToString(DEFAULT_SHELL.array()))))
            .andExpect(jsonPath("$.[*].createTeamDate").value(hasItem(DEFAULT_CREATE_TEAM_DATE.toString())))
            .andExpect(jsonPath("$.[*].userLogin").value(hasItem(DEFAULT_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].lastUserLogin").value(hasItem(DEFAULT_LAST_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)));
    }
    
    @Test
    public void getTeam() throws Exception {
        // Initialize the database
        team.setId(UUID.randomUUID());
        teamRepository.save(team);

        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", team.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(team.getId().toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.shellContentType").value(DEFAULT_SHELL_CONTENT_TYPE))
            .andExpect(jsonPath("$.shell").value(Base64Utils.encodeToString(DEFAULT_SHELL.array())))
            .andExpect(jsonPath("$.createTeamDate").value(DEFAULT_CREATE_TEAM_DATE.toString()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN))
            .andExpect(jsonPath("$.lastUserLogin").value(DEFAULT_LAST_USER_LOGIN))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE));
    }

    @Test
    public void getNonExistingTeam() throws Exception {
        // Get the team
        restTeamMockMvc.perform(get("/api/teams/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTeam() throws Exception {
        // Initialize the database
        team.setId(UUID.randomUUID());
        teamRepository.save(team);

        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Update the team
        Team updatedTeam = teamRepository.findById(team.getId()).get();
        updatedTeam
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .shell(UPDATED_SHELL)
            .shellContentType(UPDATED_SHELL_CONTENT_TYPE)
            .createTeamDate(UPDATED_CREATE_TEAM_DATE)
            .userLogin(UPDATED_USER_LOGIN)
            .lastUserLogin(UPDATED_LAST_USER_LOGIN)
            .quantity(UPDATED_QUANTITY)
            .score(UPDATED_SCORE);

        restTeamMockMvc.perform(put("/api/teams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeam)))
            .andExpect(status().isOk());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
        Team testTeam = teamList.get(teamList.size() - 1);
        assertThat(testTeam.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTeam.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTeam.getShell()).isEqualTo(UPDATED_SHELL);
        assertThat(testTeam.getShellContentType()).isEqualTo(UPDATED_SHELL_CONTENT_TYPE);
        assertThat(testTeam.getCreateTeamDate()).isEqualTo(UPDATED_CREATE_TEAM_DATE);
        assertThat(testTeam.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
        assertThat(testTeam.getLastUserLogin()).isEqualTo(UPDATED_LAST_USER_LOGIN);
        assertThat(testTeam.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTeam.getScore()).isEqualTo(UPDATED_SCORE);
    }

    @Test
    public void updateNonExistingTeam() throws Exception {
        int databaseSizeBeforeUpdate = teamRepository.findAll().size();

        // Create the Team

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamMockMvc.perform(put("/api/teams").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(team)))
            .andExpect(status().isBadRequest());

        // Validate the Team in the database
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTeam() throws Exception {
        // Initialize the database
        team.setId(UUID.randomUUID());
        teamRepository.save(team);

        int databaseSizeBeforeDelete = teamRepository.findAll().size();

        // Delete the team
        restTeamMockMvc.perform(delete("/api/teams/{id}", team.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Team> teamList = teamRepository.findAll();
        assertThat(teamList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
