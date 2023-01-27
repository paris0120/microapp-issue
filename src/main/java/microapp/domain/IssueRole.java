package microapp.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A IssueRole.
 */
@Table("issue_role")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("issue_role_key")
    private String issueRoleKey;

    @NotNull(message = "must not be null")
    @Column("issue_role")
    private String issueRole;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssueRole id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueRoleKey() {
        return this.issueRoleKey;
    }

    public IssueRole issueRoleKey(String issueRoleKey) {
        this.setIssueRoleKey(issueRoleKey);
        return this;
    }

    public void setIssueRoleKey(String issueRoleKey) {
        this.issueRoleKey = issueRoleKey;
    }

    public String getIssueRole() {
        return this.issueRole;
    }

    public IssueRole issueRole(String issueRole) {
        this.setIssueRole(issueRole);
        return this;
    }

    public void setIssueRole(String issueRole) {
        this.issueRole = issueRole;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueRole)) {
            return false;
        }
        return id != null && id.equals(((IssueRole) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueRole{" +
            "id=" + getId() +
            ", issueRoleKey='" + getIssueRoleKey() + "'" +
            ", issueRole='" + getIssueRole() + "'" +
            "}";
    }
}
