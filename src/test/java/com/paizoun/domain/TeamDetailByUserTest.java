package com.paizoun.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.web.rest.TestUtil;
import java.util.UUID;

public class TeamDetailByUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TeamDetailByUser.class);
        TeamDetailByUser teamDetailByUser1 = new TeamDetailByUser();
        teamDetailByUser1.setId(UUID.randomUUID());
        TeamDetailByUser teamDetailByUser2 = new TeamDetailByUser();
        teamDetailByUser2.setId(teamDetailByUser1.getId());
        assertThat(teamDetailByUser1).isEqualTo(teamDetailByUser2);
        teamDetailByUser2.setId(UUID.randomUUID());
        assertThat(teamDetailByUser1).isNotEqualTo(teamDetailByUser2);
        teamDetailByUser1.setId(null);
        assertThat(teamDetailByUser1).isNotEqualTo(teamDetailByUser2);
    }
}
