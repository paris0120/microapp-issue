package microapp.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A IssueAssignment.
 */
@Table("issue_assignment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueAssignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("issue_id")
    private Long issueId;

    @NotNull(message = "must not be null")
    @Column("issue_uuid")
    private UUID issueUuid;

    @NotNull(message = "must not be null")
    @Column("username")
    private String username;

    @NotNull(message = "must not be null")
    @Column("issue_assignment_displayed_username")
    private String issueAssignmentDisplayedUsername;

    @NotNull(message = "must not be null")
    @Column("issue_role")
    private String issueRole;

    @NotNull(message = "must not be null")
    @Column("displayed_issue_role")
    private String displayedIssueRole;

    @NotNull(message = "must not be null")
    @Column("created")
    private Instant created;

    @NotNull(message = "must not be null")
    @Column("modified")
    private Instant modified;

    @Column("accepted")
    private Instant accepted;

    @Column("deleted")
    private Instant deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssueAssignment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return this.issueId;
    }

    public IssueAssignment issueId(Long issueId) {
        this.setIssueId(issueId);
        return this;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public UUID getIssueUuid() {
        return this.issueUuid;
    }

    public IssueAssignment issueUuid(UUID issueUuid) {
        this.setIssueUuid(issueUuid);
        return this;
    }

    public void setIssueUuid(UUID issueUuid) {
        this.issueUuid = issueUuid;
    }

    public String getUsername() {
        return this.username;
    }

    public IssueAssignment username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIssueAssignmentDisplayedUsername() {
        return this.issueAssignmentDisplayedUsername;
    }

    public IssueAssignment issueAssignmentDisplayedUsername(String issueAssignmentDisplayedUsername) {
        this.setIssueAssignmentDisplayedUsername(issueAssignmentDisplayedUsername);
        return this;
    }

    public void setIssueAssignmentDisplayedUsername(String issueAssignmentDisplayedUsername) {
        this.issueAssignmentDisplayedUsername = issueAssignmentDisplayedUsername;
    }

    public String getIssueRole() {
        return this.issueRole;
    }

    public IssueAssignment issueRole(String issueRole) {
        this.setIssueRole(issueRole);
        return this;
    }

    public void setIssueRole(String issueRole) {
        this.issueRole = issueRole;
    }

    public String getDisplayedIssueRole() {
        return this.displayedIssueRole;
    }

    public IssueAssignment displayedIssueRole(String displayedIssueRole) {
        this.setDisplayedIssueRole(displayedIssueRole);
        return this;
    }

    public void setDisplayedIssueRole(String displayedIssueRole) {
        this.displayedIssueRole = displayedIssueRole;
    }

    public Instant getCreated() {
        return this.created;
    }

    public IssueAssignment created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return this.modified;
    }

    public IssueAssignment modified(Instant modified) {
        this.setModified(modified);
        return this;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public Instant getAccepted() {
        return this.accepted;
    }

    public IssueAssignment accepted(Instant accepted) {
        this.setAccepted(accepted);
        return this;
    }

    public void setAccepted(Instant accepted) {
        this.accepted = accepted;
    }

    public Instant getDeleted() {
        return this.deleted;
    }

    public IssueAssignment deleted(Instant deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Instant deleted) {
        this.deleted = deleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueAssignment)) {
            return false;
        }
        return id != null && id.equals(((IssueAssignment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueAssignment{" +
            "id=" + getId() +
            ", issueId=" + getIssueId() +
            ", issueUuid='" + getIssueUuid() + "'" +
            ", username='" + getUsername() + "'" +
            ", issueAssignmentDisplayedUsername='" + getIssueAssignmentDisplayedUsername() + "'" +
            ", issueRole='" + getIssueRole() + "'" +
            ", displayedIssueRole='" + getDisplayedIssueRole() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", accepted='" + getAccepted() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
