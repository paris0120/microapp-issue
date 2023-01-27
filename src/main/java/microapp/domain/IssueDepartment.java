package microapp.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A IssueDepartment.
 */
@Table("issue_department")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IssueDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Column("issue_department_key")
    private String issueDepartmentKey;

    @NotNull(message = "must not be null")
    @Column("issue_department")
    private String issueDepartment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IssueDepartment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIssueDepartmentKey() {
        return this.issueDepartmentKey;
    }

    public IssueDepartment issueDepartmentKey(String issueDepartmentKey) {
        this.setIssueDepartmentKey(issueDepartmentKey);
        return this;
    }

    public void setIssueDepartmentKey(String issueDepartmentKey) {
        this.issueDepartmentKey = issueDepartmentKey;
    }

    public String getIssueDepartment() {
        return this.issueDepartment;
    }

    public IssueDepartment issueDepartment(String issueDepartment) {
        this.setIssueDepartment(issueDepartment);
        return this;
    }

    public void setIssueDepartment(String issueDepartment) {
        this.issueDepartment = issueDepartment;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IssueDepartment)) {
            return false;
        }
        return id != null && id.equals(((IssueDepartment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IssueDepartment{" +
            "id=" + getId() +
            ", issueDepartmentKey='" + getIssueDepartmentKey() + "'" +
            ", issueDepartment='" + getIssueDepartment() + "'" +
            "}";
    }
}
