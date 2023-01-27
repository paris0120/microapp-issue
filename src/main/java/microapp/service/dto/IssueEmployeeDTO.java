package microapp.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link microapp.domain.IssueEmployee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueEmployeeDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    private String username;

    @NotNull(message = "must not be null")
    private String firstName;

    @NotNull(message = "must not be null")
    private String lastName;

    @NotNull(message = "must not be null")
    private String displayName;

    @NotNull(message = "must not be null")
    private String issueDepartment;

    @NotNull(message = "must not be null")
    private String defaultIssueRoleKey;

    @NotNull(message = "must not be null")
    private String defaultDisplayedIssueRole;

    @NotNull(message = "must not be null")
    private Boolean isAvailable;

    @NotNull(message = "must not be null")
    private Instant inOfficeFrom;

    @NotNull(message = "must not be null")
    private Instant officeHourFrom;

    @NotNull(message = "must not be null")
    private Instant officeHourTo;

    @NotNull(message = "must not be null")
    private String timezone;

    @NotNull(message = "must not be null")
    private Instant created;

    @NotNull(message = "must not be null")
    private Instant modified;

    private Instant deleted;

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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIssueDepartment() {
        return issueDepartment;
    }

    public void setIssueDepartment(String issueDepartment) {
        this.issueDepartment = issueDepartment;
    }

    public String getDefaultIssueRoleKey() {
        return defaultIssueRoleKey;
    }

    public void setDefaultIssueRoleKey(String defaultIssueRoleKey) {
        this.defaultIssueRoleKey = defaultIssueRoleKey;
    }

    public String getDefaultDisplayedIssueRole() {
        return defaultDisplayedIssueRole;
    }

    public void setDefaultDisplayedIssueRole(String defaultDisplayedIssueRole) {
        this.defaultDisplayedIssueRole = defaultDisplayedIssueRole;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Instant getInOfficeFrom() {
        return inOfficeFrom;
    }

    public void setInOfficeFrom(Instant inOfficeFrom) {
        this.inOfficeFrom = inOfficeFrom;
    }

    public Instant getOfficeHourFrom() {
        return officeHourFrom;
    }

    public void setOfficeHourFrom(Instant officeHourFrom) {
        this.officeHourFrom = officeHourFrom;
    }

    public Instant getOfficeHourTo() {
        return officeHourTo;
    }

    public void setOfficeHourTo(Instant officeHourTo) {
        this.officeHourTo = officeHourTo;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueEmployeeDTO)) {
            return false;
        }

        IssueEmployeeDTO issueEmployeeDTO = (IssueEmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, issueEmployeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueEmployeeDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", displayName='" + getDisplayName() + "'" +
            ", issueDepartment='" + getIssueDepartment() + "'" +
            ", defaultIssueRoleKey='" + getDefaultIssueRoleKey() + "'" +
            ", defaultDisplayedIssueRole='" + getDefaultDisplayedIssueRole() + "'" +
            ", isAvailable='" + getIsAvailable() + "'" +
            ", inOfficeFrom='" + getInOfficeFrom() + "'" +
            ", officeHourFrom='" + getOfficeHourFrom() + "'" +
            ", officeHourTo='" + getOfficeHourTo() + "'" +
            ", timezone='" + getTimezone() + "'" +
            ", created='" + getCreated() + "'" +
            ", modified='" + getModified() + "'" +
            ", deleted='" + getDeleted() + "'" +
            "}";
    }
}
