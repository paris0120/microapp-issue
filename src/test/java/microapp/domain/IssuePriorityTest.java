package microapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import microapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssuePriorityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssuePriority.class);
        IssuePriority issuePriority1 = new IssuePriority();
        issuePriority1.setId(1L);
        IssuePriority issuePriority2 = new IssuePriority();
        issuePriority2.setId(issuePriority1.getId());
        assertThat(issuePriority1).isEqualTo(issuePriority2);
        issuePriority2.setId(2L);
        assertThat(issuePriority1).isNotEqualTo(issuePriority2);
        issuePriority1.setId(null);
        assertThat(issuePriority1).isNotEqualTo(issuePriority2);
    }
}
