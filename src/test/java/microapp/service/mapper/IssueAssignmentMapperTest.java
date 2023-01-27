package microapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IssueAssignmentMapperTest {

    private IssueAssignmentMapper issueAssignmentMapper;

    @BeforeEach
    public void setUp() {
        issueAssignmentMapper = new IssueAssignmentMapperImpl();
    }
}
