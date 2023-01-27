package microapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link microapp.domain.IssueAssignment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueAssignmentDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private Long issueId;

    @NotNull(message = "must not be null")
    private UUID issueUuid;

    @NotNull(message = "must not be null")
    private String username;

    @NotNull(message = "must not be null")
    private String issueAssignmentDisplayedUsername;

    @NotNull(message = "must not be null")
    private String issueRole;

    @NotNull(message = "must not be null")
    private String displayedIssueRole;

    @NotNull(message = "must not be null")
    private Instant created;

    @NotNull(message = "must not be null")
    private Instant modified;

    private Instant accepted;

    private Instant deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public UUID getIssueUuid() {
        return issueUuid;
    }

    public void setIssueUuid(UUID issueUuid) {
        this.issueUuid = issueUuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIssueAssignmentDisplayedUsername() {
        return issueAssignmentDisplayedUsername;
    }

    public void setIssueAssignmentDisplayedUsername(String issueAssignmentDisplayedUsername) {
        this.issueAssignmentDisplayedUsername = issueAssignmentDisplayedUsername;
    }

    public String getIssueRole() {
        return issueRole;
    }

    public void setIssueRole(String issueRole) {
        this.issueRole = issueRole;
    }

    public String getDisplayedIssueRole() {
        return displayedIssueRole;
    }

    public void setDisplayedIssueRole(String displayedIssueRole) {
        this.displayedIssueRole = displayedIssueRole;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public Instant getAccepted() {
        return accepted;
    }

    public void setAccepted(Instant accepted) {
        this.accepted = accepted;
    }

    public Instant getDeleted() {
        return deleted;
    }

    public void setDeleted(Instant deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueAssignmentDTO)) {
            return false;
        }

        IssueAssignmentDTO issueAssignmentDTO = (IssueAssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, issueAssignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueAssignmentDTO{" +
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
