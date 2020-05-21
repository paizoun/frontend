package com.paizoun.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.web.rest.TestUtil;
import java.util.UUID;

public class TeamUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamUser.class);
        TeamUser teamUser1 = new TeamUser();
        teamUser1.setId(UUID.randomUUID());
        TeamUser teamUser2 = new TeamUser();
        teamUser2.setId(teamUser1.getId());
        assertThat(teamUser1).isEqualTo(teamUser2);
        teamUser2.setId(UUID.randomUUID());
        assertThat(teamUser1).isNotEqualTo(teamUser2);
        teamUser1.setId(null);
        assertThat(teamUser1).isNotEqualTo(teamUser2);
    }
}
