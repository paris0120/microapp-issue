package microapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import microapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssueTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueType.class);
        IssueType issueType1 = new IssueType();
        issueType1.setId(1L);
        IssueType issueType2 = new IssueType();
        issueType2.setId(issueType1.getId());
        assertThat(issueType1).isEqualTo(issueType2);
        issueType2.setId(2L);
        assertThat(issueType1).isNotEqualTo(issueType2);
        issueType1.setId(null);
        assertThat(issueType1).isNotEqualTo(issueType2);
    }
}
