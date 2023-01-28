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
 * A Issue.
 */
@Table("issue")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Issue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("username")
    private String username;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @NotNull(message = "must not be null")
    @Column("displayed_username")
    private String displayedUsername;

    @NotNull(message = "must not be null")
    @Size(min = 2)
    @Column("issue_title")
    private String issueTitle;

    @Column("issue_content")
    private String issueContent;

    @NotNull(message = "must not be null")
    @Column("issue_type_key")
    private String issueTypeKey;

    @NotNull(message = "must not be null")
    @Column("issue_workflow_status")
    private String issueWorkflowStatus;

    @NotNull(message = "must not be null")
    @Column("issue_workflow_status_key")
    private String issueWorkflowStatusKey;

    @NotNull(message = "must not be null")
    @Column("issue_priority_level")
    private Integer issuePriorityLevel;

    @Column("issue_uuid")
    private UUID issueUuid;

    @NotNull(message = "must not be null")
    @Column("created")
    private Instant created;

    @NotNull(message = "must not be null")
    @Column("modified")
    private Instant modified;

    @Column("deleted")
    private Instant deleted;

    @Column("closed")
    private Instant closed;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Issue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public Issue username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Issue firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Issue lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayedUsername() {
        return this.displayedUsername;
    }

    public Issue displayedUsername(String displayedUsername) {
        this.setDisplayedUsername(displayedUsername);
        return this;
    }

    public void setDisplayedUsername(String displayedUsername) {
        this.displayedUsername = displayedUsername;
    }

    public String getIssueTitle() {
        return this.issueTitle;
    }

    public Issue issueTitle(String issueTitle) {
        this.setIssueTitle(issueTitle);
        return this;
    }

    public void setIssueTitle(String issueTitle) {
        this.issueTitle = issueTitle;
    }

    public String getIssueContent() {
        return this.issueContent;
    }

    public Issue issueContent(String issueContent) {
        this.setIssueContent(issueContent);
        return this;
    }

    public void setIssueContent(String issueContent) {
        this.issueContent = issueContent;
    }

    public String getIssueTypeKey() {
        return this.issueTypeKey;
    }

    public Issue issueTypeKey(String issueTypeKey) {
        this.setIssueTypeKey(issueTypeKey);
        return this;
    }

    public void setIssueTypeKey(String issueTypeKey) {
        this.issueTypeKey = issueTypeKey;
    }

    public String getIssueWorkflowStatus() {
        return this.issueWorkflowStatus;
    }

    public Issue issueWorkflowStatus(String issueWorkflowStatus) {
        this.setIssueWorkflowStatus(issueWorkflowStatus);
        return this;
    }

    public void setIssueWorkflowStatus(String issueWorkflowStatus) {
        this.issueWorkflowStatus = issueWorkflowStatus;
    }

    public String getIssueWorkflowStatusKey() {
        return this.issueWorkflowStatusKey;
    }

    public Issue issueWorkflowStatusKey(String issueWorkflowStatusKey) {
        this.setIssueWorkflowStatusKey(issueWorkflowStatusKey);
        return this;
    }

    public void setIssueWorkflowStatusKey(String issueWorkflowStatusKey) {
        this.issueWorkflowStatusKey = issueWorkflowStatusKey;
    }

    public Integer getIssuePriorityLevel() {
        return this.issuePriorityLevel;
    }

    public Issue issuePriorityLevel(Integer issuePriorityLevel) {
        this.setIssuePriorityLevel(issuePriorityLevel);
        return this;
    }

    public void setIssuePriorityLevel(Integer issuePriorityLevel) {
        this.issuePriorityLevel = issuePriorityLevel;
    }

    public UUID getIssueUuid() {
        return this.issueUuid;
    }

    public Issue issueUuid(UUID issueUuid) {
        this.setIssueUuid(issueUuid);
        return this;
    }

    public void setIssueUuid(UUID issueUuid) {
        this.issueUuid = issueUuid;
    }

    public Instant getCreated() {
        return this.created;
    }

    public Issue created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return this.modified;
    }

    public Issue modified(Instant modified) {
        this.setModified(modified);
        return this;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public Instant getDeleted() {
        return this.deleted;
    }

    public Issue deleted(Instant deleted) {
        this.setDeleted(deleted);
        return this;
    }

    public void setDeleted(Instant deleted) {
        this.deleted = deleted;
    }

    public Instant getClosed() {
        return this.closed;
    }

    public Issue closed(Instant closed) {
        this.setClosed(closed);
        return this;
    }

    public void setClosed(Instant closed) {
        this.closed = closed;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Issue)) {
            return false;
        }
        return id != null && id.equals(((Issue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Issue{" +
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
