package microapp.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A IssuePriority.
 */
@Table("issue_priority")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssuePriority implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("issue_priority")
    private String issuePriority;

    @NotNull(message = "must not be null")
    @Column("issue_priority_level")
    private Integer issuePriorityLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssuePriority id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssuePriority() {
        return this.issuePriority;
    }

    public IssuePriority issuePriority(String issuePriority) {
        this.setIssuePriority(issuePriority);
        return this;
    }

    public void setIssuePriority(String issuePriority) {
        this.issuePriority = issuePriority;
    }

    public Integer getIssuePriorityLevel() {
        return this.issuePriorityLevel;
    }

    public IssuePriority issuePriorityLevel(Integer issuePriorityLevel) {
        this.setIssuePriorityLevel(issuePriorityLevel);
        return this;
    }

    public void setIssuePriorityLevel(Integer issuePriorityLevel) {
        this.issuePriorityLevel = issuePriorityLevel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssuePriority)) {
            return false;
        }
        return id != null && id.equals(((IssuePriority) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssuePriority{" +
            "id=" + getId() +
            ", issuePriority='" + getIssuePriority() + "'" +
            ", issuePriorityLevel=" + getIssuePriorityLevel() +
            "}";
    }
}
