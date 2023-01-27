package microapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import microapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssueRoleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueRole.class);
        IssueRole issueRole1 = new IssueRole();
        issueRole1.setId(1L);
        IssueRole issueRole2 = new IssueRole();
        issueRole2.setId(issueRole1.getId());
        assertThat(issueRole1).isEqualTo(issueRole2);
        issueRole2.setId(2L);
        assertThat(issueRole1).isNotEqualTo(issueRole2);
        issueRole1.setId(null);
        assertThat(issueRole1).isNotEqualTo(issueRole2);
    }
}
