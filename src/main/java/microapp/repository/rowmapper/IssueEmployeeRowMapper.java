package microapp.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import microapp.domain.IssueEmployee;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link IssueEmployee}, with proper type conversions.
 */
@Service
public class IssueEmployeeRowMapper implements BiFunction<Row, String, IssueEmployee> {

    private final ColumnConverter converter;

    public IssueEmployeeRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link IssueEmployee} stored in the database.
     */
    @Override
    public IssueEmployee apply(Row row, String prefix) {
        IssueEmployee entity = new IssueEmployee();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setUsername(converter.fromRow(row, prefix + "_username", String.class));
        entity.setFirstName(converter.fromRow(row, prefix + "_first_name", String.class));
        entity.setLastName(converter.fromRow(row, prefix + "_last_name", String.class));
        entity.setDisplayName(converter.fromRow(row, prefix + "_display_name", String.class));
        entity.setIssueDepartment(converter.fromRow(row, prefix + "_issue_department", String.class));
        entity.setDefaultIssueRoleKey(converter.fromRow(row, prefix + "_default_issue_role_key", String.class));
        entity.setDefaultDisplayedIssueRole(converter.fromRow(row, prefix + "_default_displayed_issue_role", String.class));
        entity.setIsAvailable(converter.fromRow(row, prefix + "_is_available", Boolean.class));
        entity.setInOfficeFrom(converter.fromRow(row, prefix + "_in_office_from", Instant.class));
        entity.setOfficeHourFrom(converter.fromRow(row, prefix + "_office_hour_from", Instant.class));
        entity.setOfficeHourTo(converter.fromRow(row, prefix + "_office_hour_to", Instant.class));
        entity.setTimezone(converter.fromRow(row, prefix + "_timezone", String.class));
        entity.setCreated(converter.fromRow(row, prefix + "_created", Instant.class));
        entity.setModified(converter.fromRow(row, prefix + "_modified", Instant.class));
        entity.setDeleted(converter.fromRow(row, prefix + "_deleted", Instant.class));
        return entity;
    }
}
