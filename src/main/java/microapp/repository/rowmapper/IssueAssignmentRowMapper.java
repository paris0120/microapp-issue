package microapp.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;
import microapp.domain.IssueAssignment;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link IssueAssignment}, with proper type conversions.
 */
@Service
public class IssueAssignmentRowMapper implements BiFunction<Row, String, IssueAssignment> {

    private final ColumnConverter converter;

    public IssueAssignmentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link IssueAssignment} stored in the database.
     */
    @Override
    public IssueAssignment apply(Row row, String prefix) {
        IssueAssignment entity = new IssueAssignment();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setIssueId(converter.fromRow(row, prefix + "_issue_id", Long.class));
        entity.setIssueUuid(converter.fromRow(row, prefix + "_issue_uuid", UUID.class));
        entity.setUsername(converter.fromRow(row, prefix + "_username", String.class));
        entity.setIssueAssignmentDisplayedUsername(converter.fromRow(row, prefix + "_issue_assignment_displayed_username", String.class));
        entity.setIssueRole(converter.fromRow(row, prefix + "_issue_role", String.class));
        entity.setDisplayedIssueRole(converter.fromRow(row, prefix + "_displayed_issue_role", String.class));
        entity.setCreated(converter.fromRow(row, prefix + "_created", Instant.class));
        entity.setModified(converter.fromRow(row, prefix + "_modified", Instant.class));
        entity.setAccepted(converter.fromRow(row, prefix + "_accepted", Instant.class));
        entity.setDeleted(converter.fromRow(row, prefix + "_deleted", Instant.class));
        return entity;
    }
}
