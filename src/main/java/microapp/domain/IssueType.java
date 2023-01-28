package microapp.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A IssueType.
 */
@Table("issue_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("issue_type_key")
    private String issueTypeKey;

    @NotNull(message = "must not be null")
    @Column("issue_type_weight")
    private Integer issueTypeWeight;

    @NotNull(message = "must not be null")
    @Column("issue_type")
    private String issueType;

    @NotNull(message = "must not be null")
    @Column("is_active")
    private Boolean isActive;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssueType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueTypeKey() {
        return this.issueTypeKey;
    }

    public IssueType issueTypeKey(String issueTypeKey) {
        this.setIssueTypeKey(issueTypeKey);
        return this;
    }

    public void setIssueTypeKey(String issueTypeKey) {
        this.issueTypeKey = issueTypeKey;
    }

    public Integer getIssueTypeWeight() {
        return this.issueTypeWeight;
    }

    public IssueType issueTypeWeight(Integer issueTypeWeight) {
        this.setIssueTypeWeight(issueTypeWeight);
        return this;
    }

    public void setIssueTypeWeight(Integer issueTypeWeight) {
        this.issueTypeWeight = issueTypeWeight;
    }

    public String getIssueType() {
        return this.issueType;
    }

    public IssueType issueType(String issueType) {
        this.setIssueType(issueType);
        return this;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public IssueType isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueType)) {
            return false;
        }
        return id != null && id.equals(((IssueType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueType{" +
            "id=" + getId() +
            ", issueTypeKey='" + getIssueTypeKey() + "'" +
            ", issueTypeWeight=" + getIssueTypeWeight() +
            ", issueType='" + getIssueType() + "'" +
            ", isActive='" + getIsActive() + "'" +
            "}";
    }
}
