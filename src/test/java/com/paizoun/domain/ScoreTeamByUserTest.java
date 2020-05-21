package com.paizoun.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.paizoun.web.rest.TestUtil;
import java.util.UUID;

public class ScoreTeamByUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScoreTeamByUser.class);
        ScoreTeamByUser scoreTeamByUser1 = new ScoreTeamByUser();
        scoreTeamByUser1.setId(UUID.randomUUID());
        ScoreTeamByUser scoreTeamByUser2 = new ScoreTeamByUser();
        scoreTeamByUser2.setId(scoreTeamByUser1.getId());
        assertThat(scoreTeamByUser1).isEqualTo(scoreTeamByUser2);
        scoreTeamByUser2.setId(UUID.randomUUID());
        assertThat(scoreTeamByUser1).isNotEqualTo(scoreTeamByUser2);
        scoreTeamByUser1.setId(null);
        assertThat(scoreTeamByUser1).isNotEqualTo(scoreTeamByUser2);
    }
}
