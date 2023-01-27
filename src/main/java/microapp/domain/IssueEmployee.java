package microapp.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A IssueEmployee.
 */
@Table("issue_employee")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueEmployee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("username")
    private String username;

    @NotNull(message = "must not be null")
    @Column("first_name")
    private String firstName;

    @NotNull(message = "must not be null")
    @Column("last_name")
    private String lastName;

    @NotNull(message = "must not be null")
    @Column("display_name")
    private String displayName;

    @NotNull(message = "must not be null")
    @Column("issue_department")
    private String issueDepartment;

    @NotNull(message = "must not be null")
    @Column("default_issue_role_key")
    private String defaultIssueRoleKey;

    @NotNull(message = "must not be null")
    @Column("default_displayed_issue_role")
    private String defaultDisplayedIssueRole;

    @NotNull(message = "must not be null")
    @Column("is_available")
    private Boolean isAvailable;

    @NotNull(message = "must not be null")
    @Column("in_office_from")
    private Instant inOfficeFrom;

    @NotNull(message = "must not be null")
    @Column("office_hour_from")
    private Instant officeHourFrom;

    @NotNull(message = "must not be null")
    @Column("office_hour_to")
    private Instant officeHourTo;

    @NotNull(message = "must not be null")
    @Column("timezone")
    private String timezone;

    @NotNull(message = "must not be null")
    @Column("created")
    private Instant created;

    @NotNull(message = "must not be null")
    @Column("modified")
    private Instant modified;

    @Column("deleted")
    private Instant deleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssueEmployee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public IssueEmployee username(String username) {
        this.setUsername(username);
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public IssueEmployee firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public IssueEmployee lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public IssueEmployee displayName(String displayName) {
        this.setDisplayName(displayName);
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getIssueDepartment() {
        return this.issueDepartment;
    }

    public IssueEmployee issueDepartment(String issueDepartment) {
        this.setIssueDepartment(issueDepartment);
        return this;
    }

    public void setIssueDepartment(String issueDepartment) {
        this.issueDepartment = issueDepartment;
    }

    public String getDefaultIssueRoleKey() {
        return this.defaultIssueRoleKey;
    }

    public IssueEmployee defaultIssueRoleKey(String defaultIssueRoleKey) {
        this.setDefaultIssueRoleKey(defaultIssueRoleKey);
        return this;
    }

    public void setDefaultIssueRoleKey(String defaultIssueRoleKey) {
        this.defaultIssueRoleKey = defaultIssueRoleKey;
    }

    public String getDefaultDisplayedIssueRole() {
        return this.defaultDisplayedIssueRole;
    }

    public IssueEmployee defaultDisplayedIssueRole(String defaultDisplayedIssueRole) {
        this.setDefaultDisplayedIssueRole(defaultDisplayedIssueRole);
        return this;
    }

    public void setDefaultDisplayedIssueRole(String defaultDisplayedIssueRole) {
        this.defaultDisplayedIssueRole = defaultDisplayedIssueRole;
    }

    public Boolean getIsAvailable() {
        return this.isAvailable;
    }

    public IssueEmployee isAvailable(Boolean isAvailable) {
        this.setIsAvailable(isAvailable);
        return this;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Instant getInOfficeFrom() {
        return this.inOfficeFrom;
    }

    public IssueEmployee inOfficeFrom(Instant inOfficeFrom) {
        this.setInOfficeFrom(inOfficeFrom);
        return this;
    }

    public void setInOfficeFrom(Instant inOfficeFrom) {
        this.inOfficeFrom = inOfficeFrom;
    }

    public Instant getOfficeHourFrom() {
        return this.officeHourFrom;
    }

    public IssueEmployee officeHourFrom(Instant officeHourFrom) {
        this.setOfficeHourFrom(officeHourFrom);
        return this;
    }

    public void setOfficeHourFrom(Instant officeHourFrom) {
        this.officeHourFrom = officeHourFrom;
    }

    public Instant getOfficeHourTo() {
        return this.officeHourTo;
    }

    public IssueEmployee officeHourTo(Instant officeHourTo) {
        this.setOfficeHourTo(officeHourTo);
        return this;
    }

    public void setOfficeHourTo(Instant officeHourTo) {
        this.officeHourTo = officeHourTo;
    }

    public String getTimezone() {
        return this.timezone;
    }

    public IssueEmployee timezone(String timezone) {
        this.setTimezone(timezone);
        return this;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Instant getCreated() {
        return this.created;
    }

    public IssueEmployee created(Instant created) {
        this.setCreated(created);
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return this.modified;
    }

    public IssueEmployee modified(Instant modified) {
        this.setModified(modified);
        return this;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }

    public Instant getDeleted() {
        return this.deleted;
    }

    public IssueEmployee deleted(Instant deleted) {
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
        if (!(o instanceof IssueEmployee)) {
            return false;
        }
        return id != null && id.equals(((IssueEmployee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueEmployee{" +
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
