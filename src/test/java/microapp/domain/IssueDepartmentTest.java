package microapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import microapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IssueDepartmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IssueDepartment.class);
        IssueDepartment issueDepartment1 = new IssueDepartment();
        issueDepartment1.setId(1L);
        IssueDepartment issueDepartment2 = new IssueDepartment();
        issueDepartment2.setId(issueDepartment1.getId());
        assertThat(issueDepartment1).isEqualTo(issueDepartment2);
        issueDepartment2.setId(2L);
        assertThat(issueDepartment1).isNotEqualTo(issueDepartment2);
        issueDepartment1.setId(null);
        assertThat(issueDepartment1).isNotEqualTo(issueDepartment2);
    }
}
