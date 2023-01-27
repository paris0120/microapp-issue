package microapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import microapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssueAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueAssignment.class);
        IssueAssignment issueAssignment1 = new IssueAssignment();
        issueAssignment1.setId(1L);
        IssueAssignment issueAssignment2 = new IssueAssignment();
        issueAssignment2.setId(issueAssignment1.getId());
        assertThat(issueAssignment1).isEqualTo(issueAssignment2);
        issueAssignment2.setId(2L);
        assertThat(issueAssignment1).isNotEqualTo(issueAssignment2);
        issueAssignment1.setId(null);
        assertThat(issueAssignment1).isNotEqualTo(issueAssignment2);
    }
}
