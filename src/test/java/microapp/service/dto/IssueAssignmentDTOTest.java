package microapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import microapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssueAssignmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueAssignmentDTO.class);
        IssueAssignmentDTO issueAssignmentDTO1 = new IssueAssignmentDTO();
        issueAssignmentDTO1.setId(1L);
        IssueAssignmentDTO issueAssignmentDTO2 = new IssueAssignmentDTO();
        assertThat(issueAssignmentDTO1).isNotEqualTo(issueAssignmentDTO2);
        issueAssignmentDTO2.setId(issueAssignmentDTO1.getId());
        assertThat(issueAssignmentDTO1).isEqualTo(issueAssignmentDTO2);
        issueAssignmentDTO2.setId(2L);
        assertThat(issueAssignmentDTO1).isNotEqualTo(issueAssignmentDTO2);
        issueAssignmentDTO1.setId(null);
        assertThat(issueAssignmentDTO1).isNotEqualTo(issueAssignmentDTO2);
    }
}
