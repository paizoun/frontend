package com.paizoun.web.rest;

import com.paizoun.AbstractCassandraTest;
import com.paizoun.PaizounApp;
import com.paizoun.config.SecurityBeanOverrideConfiguration;
import com.paizoun.domain.TeamDetailByUser;
import com.paizoun.repository.TeamDetailByUserRepository;

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
 * Integration tests for the {@link TeamDetailByUserResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, PaizounApp.class })

@AutoConfigureMockMvc
@WithMockUser
public class TeamDetailByUserResourceIT extends AbstractCassandraTest {

    private static final String DEFAULT_USER_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_USER_LOGIN = "BBBBBBBBBB";

    private static final UUID DEFAULT_TEAM_ID = UUID.randomUUID();
    private static final UUID UPDATED_TEAM_ID = UUID.randomUUID();

    private static final String DEFAULT_TEAM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TEAM_NAME = "BBBBBBBBBB";

    private static final ByteBuffer DEFAULT_TEAM_SHELL = ByteBuffer.wrap(TestUtil.createByteArray(1, "0"));
    private static final ByteBuffer UPDATED_TEAM_SHELL = ByteBuffer.wrap(TestUtil.createByteArray(1, "1"));
    private static final String DEFAULT_TEAM_SHELL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TEAM_SHELL_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_TEAM_SCORE = 1;
    private static final Integer UPDATED_TEAM_SCORE = 2;

    private static final Instant DEFAULT_USER_JOIN_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_USER_JOIN_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private TeamDetailByUserRepository teamDetailByUserRepository;

    @Autowired
    private MockMvc restTeamDetailByUserMockMvc;

    private TeamDetailByUser teamDetailByUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamDetailByUser createEntity() {
        TeamDetailByUser teamDetailByUser = new TeamDetailByUser()
            .userLogin(DEFAULT_USER_LOGIN)
            .teamId(DEFAULT_TEAM_ID)
            .teamName(DEFAULT_TEAM_NAME)
            .teamShell(DEFAULT_TEAM_SHELL)
            .teamShellContentType(DEFAULT_TEAM_SHELL_CONTENT_TYPE)
            .teamScore(DEFAULT_TEAM_SCORE)
            .userJoinDate(DEFAULT_USER_JOIN_DATE);
        return teamDetailByUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TeamDetailByUser createUpdatedEntity() {
        TeamDetailByUser teamDetailByUser = new TeamDetailByUser()
            .userLogin(UPDATED_USER_LOGIN)
            .teamId(UPDATED_TEAM_ID)
            .teamName(UPDATED_TEAM_NAME)
            .teamShell(UPDATED_TEAM_SHELL)
            .teamShellContentType(UPDATED_TEAM_SHELL_CONTENT_TYPE)
            .teamScore(UPDATED_TEAM_SCORE)
            .userJoinDate(UPDATED_USER_JOIN_DATE);
        return teamDetailByUser;
    }

    @BeforeEach
    public void initTest() {
        teamDetailByUserRepository.deleteAll();
        teamDetailByUser = createEntity();
    }

    @Test
    public void createTeamDetailByUser() throws Exception {
        int databaseSizeBeforeCreate = teamDetailByUserRepository.findAll().size();

        // Create the TeamDetailByUser
        restTeamDetailByUserMockMvc.perform(post("/api/team-detail-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamDetailByUser)))
            .andExpect(status().isCreated());

        // Validate the TeamDetailByUser in the database
        List<TeamDetailByUser> teamDetailByUserList = teamDetailByUserRepository.findAll();
        assertThat(teamDetailByUserList).hasSize(databaseSizeBeforeCreate + 1);
        TeamDetailByUser testTeamDetailByUser = teamDetailByUserList.get(teamDetailByUserList.size() - 1);
        assertThat(testTeamDetailByUser.getUserLogin()).isEqualTo(DEFAULT_USER_LOGIN);
        assertThat(testTeamDetailByUser.getTeamId()).isEqualTo(DEFAULT_TEAM_ID);
        assertThat(testTeamDetailByUser.getTeamName()).isEqualTo(DEFAULT_TEAM_NAME);
        assertThat(testTeamDetailByUser.getTeamShell()).isEqualTo(DEFAULT_TEAM_SHELL);
        assertThat(testTeamDetailByUser.getTeamShellContentType()).isEqualTo(DEFAULT_TEAM_SHELL_CONTENT_TYPE);
        assertThat(testTeamDetailByUser.getTeamScore()).isEqualTo(DEFAULT_TEAM_SCORE);
        assertThat(testTeamDetailByUser.getUserJoinDate()).isEqualTo(DEFAULT_USER_JOIN_DATE);
    }

    @Test
    public void createTeamDetailByUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = teamDetailByUserRepository.findAll().size();

        // Create the TeamDetailByUser with an existing ID
        teamDetailByUser.setId(UUID.randomUUID());

        // An entity with an existing ID cannot be created, so this API call must fail
        restTeamDetailByUserMockMvc.perform(post("/api/team-detail-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamDetailByUser)))
            .andExpect(status().isBadRequest());

        // Validate the TeamDetailByUser in the database
        List<TeamDetailByUser> teamDetailByUserList = teamDetailByUserRepository.findAll();
        assertThat(teamDetailByUserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllTeamDetailByUsers() throws Exception {
        // Initialize the database
        teamDetailByUser.setId(UUID.randomUUID());
        teamDetailByUserRepository.save(teamDetailByUser);

        // Get all the teamDetailByUserList
        restTeamDetailByUserMockMvc.perform(get("/api/team-detail-by-users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(teamDetailByUser.getId().toString())))
            .andExpect(jsonPath("$.[*].userLogin").value(hasItem(DEFAULT_USER_LOGIN)))
            .andExpect(jsonPath("$.[*].teamId").value(hasItem(DEFAULT_TEAM_ID.toString())))
            .andExpect(jsonPath("$.[*].teamName").value(hasItem(DEFAULT_TEAM_NAME)))
            .andExpect(jsonPath("$.[*].teamShellContentType").value(hasItem(DEFAULT_TEAM_SHELL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].teamShell").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEAM_SHELL.array()))))
            .andExpect(jsonPath("$.[*].teamScore").value(hasItem(DEFAULT_TEAM_SCORE)))
            .andExpect(jsonPath("$.[*].userJoinDate").value(hasItem(DEFAULT_USER_JOIN_DATE.toString())));
    }
    
    @Test
    public void getTeamDetailByUser() throws Exception {
        // Initialize the database
        teamDetailByUser.setId(UUID.randomUUID());
        teamDetailByUserRepository.save(teamDetailByUser);

        // Get the teamDetailByUser
        restTeamDetailByUserMockMvc.perform(get("/api/team-detail-by-users/{id}", teamDetailByUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(teamDetailByUser.getId().toString()))
            .andExpect(jsonPath("$.userLogin").value(DEFAULT_USER_LOGIN))
            .andExpect(jsonPath("$.teamId").value(DEFAULT_TEAM_ID.toString()))
            .andExpect(jsonPath("$.teamName").value(DEFAULT_TEAM_NAME))
            .andExpect(jsonPath("$.teamShellContentType").value(DEFAULT_TEAM_SHELL_CONTENT_TYPE))
            .andExpect(jsonPath("$.teamShell").value(Base64Utils.encodeToString(DEFAULT_TEAM_SHELL.array())))
            .andExpect(jsonPath("$.teamScore").value(DEFAULT_TEAM_SCORE))
            .andExpect(jsonPath("$.userJoinDate").value(DEFAULT_USER_JOIN_DATE.toString()));
    }

    @Test
    public void getNonExistingTeamDetailByUser() throws Exception {
        // Get the teamDetailByUser
        restTeamDetailByUserMockMvc.perform(get("/api/team-detail-by-users/{id}", UUID.randomUUID().toString()))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateTeamDetailByUser() throws Exception {
        // Initialize the database
        teamDetailByUser.setId(UUID.randomUUID());
        teamDetailByUserRepository.save(teamDetailByUser);

        int databaseSizeBeforeUpdate = teamDetailByUserRepository.findAll().size();

        // Update the teamDetailByUser
        TeamDetailByUser updatedTeamDetailByUser = teamDetailByUserRepository.findById(teamDetailByUser.getId()).get();
        updatedTeamDetailByUser
            .userLogin(UPDATED_USER_LOGIN)
            .teamId(UPDATED_TEAM_ID)
            .teamName(UPDATED_TEAM_NAME)
            .teamShell(UPDATED_TEAM_SHELL)
            .teamShellContentType(UPDATED_TEAM_SHELL_CONTENT_TYPE)
            .teamScore(UPDATED_TEAM_SCORE)
            .userJoinDate(UPDATED_USER_JOIN_DATE);

        restTeamDetailByUserMockMvc.perform(put("/api/team-detail-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTeamDetailByUser)))
            .andExpect(status().isOk());

        // Validate the TeamDetailByUser in the database
        List<TeamDetailByUser> teamDetailByUserList = teamDetailByUserRepository.findAll();
        assertThat(teamDetailByUserList).hasSize(databaseSizeBeforeUpdate);
        TeamDetailByUser testTeamDetailByUser = teamDetailByUserList.get(teamDetailByUserList.size() - 1);
        assertThat(testTeamDetailByUser.getUserLogin()).isEqualTo(UPDATED_USER_LOGIN);
        assertThat(testTeamDetailByUser.getTeamId()).isEqualTo(UPDATED_TEAM_ID);
        assertThat(testTeamDetailByUser.getTeamName()).isEqualTo(UPDATED_TEAM_NAME);
        assertThat(testTeamDetailByUser.getTeamShell()).isEqualTo(UPDATED_TEAM_SHELL);
        assertThat(testTeamDetailByUser.getTeamShellContentType()).isEqualTo(UPDATED_TEAM_SHELL_CONTENT_TYPE);
        assertThat(testTeamDetailByUser.getTeamScore()).isEqualTo(UPDATED_TEAM_SCORE);
        assertThat(testTeamDetailByUser.getUserJoinDate()).isEqualTo(UPDATED_USER_JOIN_DATE);
    }

    @Test
    public void updateNonExistingTeamDetailByUser() throws Exception {
        int databaseSizeBeforeUpdate = teamDetailByUserRepository.findAll().size();

        // Create the TeamDetailByUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTeamDetailByUserMockMvc.perform(put("/api/team-detail-by-users").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(teamDetailByUser)))
            .andExpect(status().isBadRequest());

        // Validate the TeamDetailByUser in the database
        List<TeamDetailByUser> teamDetailByUserList = teamDetailByUserRepository.findAll();
        assertThat(teamDetailByUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteTeamDetailByUser() throws Exception {
        // Initialize the database
        teamDetailByUser.setId(UUID.randomUUID());
        teamDetailByUserRepository.save(teamDetailByUser);

        int databaseSizeBeforeDelete = teamDetailByUserRepository.findAll().size();

        // Delete the teamDetailByUser
        restTeamDetailByUserMockMvc.perform(delete("/api/team-detail-by-users/{id}", teamDetailByUser.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TeamDetailByUser> teamDetailByUserList = teamDetailByUserRepository.findAll();
        assertThat(teamDetailByUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
