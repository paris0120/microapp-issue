package microapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link microapp.domain.Issue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueDTO implements Serializable {

    private Long id;

    private String username;

    private String firstName;

    private String lastName;

    @NotNull(message = "must not be null")
    private String displayedUsername;

    @NotNull(message = "must not be null")
    @Size(min = 2)
    private String issueTitle;

    @Lob
    private String issueContent;

    @NotNull(message = "must not be null")
    private String issueTypeKey;

    @NotNull(message = "must not be null")
    private String issueWorkflowStatus;

    @NotNull(message = "must not be null")
    private String issueWorkflowStatusKey;

    @NotNull(message = "must not be null")
    private Integer issuePriorityLevel;

    private UUID issueUuid;

    @NotNull(message = "must not be null")
    private Instant created;

    @NotNull(message = "must not be null")
    private Instant modified;

    private Instant deleted;

    private Instant closed;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayedUsername() {
        return displayedUsername;
    }

    public void setDisplayedUsername(String displayedUsername) {
        this.displayedUsername = displayedUsername;
    }

    public String getIssueTitle() {
        return issueTitle;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueContent() {
        return issueContent;
    }

    public void setIssueContent(String issueContent) {
        this.issueContent = issueContent;
    }

    public String getIssueTypeKey() {
        return issueTypeKey;
    }

    public void setIssueTypeKey(String issueTypeKey) {
        this.issueTypeKey = issueTypeKey;
    }

    public String getIssueWorkflowStatus() {
        return issueWorkflowStatus;
    }

    public void setIssueWorkflowStatus(String issueWorkflowStatus) {
        this.issueWorkflowStatus = issueWorkflowStatus;
    }

    public String getIssueWorkflowStatusKey() {
        return issueWorkflowStatusKey;
    }

    public void setIssueWorkflowStatusKey(String issueWorkflowStatusKey) {
        this.issueWorkflowStatusKey = issueWorkflowStatusKey;
    }

    public Integer getIssuePriorityLevel() {
        return issuePriorityLevel;
    }

    public void setIssuePriorityLevel(Integer issuePriorityLevel) {
        this.issuePriorityLevel = issuePriorityLevel;
    }

    public UUID getIssueUuid() {
        return issueUuid;
    }

    public void setIssueUuid(UUID issueUuid) {
        this.issueUuid = issueUuid;
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

    public Instant getDeleted() {
        return deleted;
    }

    public void setDeleted(Instant deleted) {
        this.deleted = deleted;
    }

    public Instant getClosed() {
        return closed;
    }

    public void setClosed(Instant closed) {
        this.closed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueDTO)) {
            return false;
        }

        IssueDTO issueDTO = (IssueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, issueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", displayedUsername='" + getDisplayedUsername() + "'" +
            ", issueTitle='" + getIssueTitle() + "'" +
            ", issueContent='" + getIssueContent() + "'" +
            ", issueTypeKey='" + getIssueTypeKey() + "'" +
            ", issueWorkflowStatus='" + getIssueWorkflowStatus() + "'" +
            ", issueWorkflowStatusKey='" + getIssueWorkflowStatusKey() + "'" +
            ", issuePriorityLevel=" + getIssuePriorityLevel() +
            ", issueUuid='" + getIssueUuid() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", deleted='" + getDeleted() + "'" +
            ", closed='" + getClosed() + "'" +
            "}";
    }
}
