package microapp.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.UUID;
import java.util.function.BiFunction;
import microapp.domain.Issue;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Issue}, with proper type conversions.
 */
@Service
public class IssueRowMapper implements BiFunction<Row, String, Issue> {

    private final ColumnConverter converter;

    public IssueRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Issue} stored in the database.
     */
    @Override
    public Issue apply(Row row, String prefix) {
        Issue entity = new Issue();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setUsername(converter.fromRow(row, prefix + "_username", String.class));
        entity.setFirstName(converter.fromRow(row, prefix + "_first_name", String.class));
        entity.setLastName(converter.fromRow(row, prefix + "_last_name", String.class));
        entity.setDisplayedUsername(converter.fromRow(row, prefix + "_displayed_username", String.class));
        entity.setIssueTitle(converter.fromRow(row, prefix + "_issue_title", String.class));
        entity.setIssueContent(converter.fromRow(row, prefix + "_issue_content", String.class));
        entity.setIssueType(converter.fromRow(row, prefix + "_issue_type", String.class));
        entity.setIssueWorkflowStatus(converter.fromRow(row, prefix + "_issue_workflow_status", String.class));
        entity.setIssueWorkflowStatusKey(converter.fromRow(row, prefix + "_issue_workflow_status_key", String.class));
        entity.setIssuePriorityLevel(converter.fromRow(row, prefix + "_issue_priority_level", Integer.class));
        entity.setIssueUuid(converter.fromRow(row, prefix + "_issue_uuid", UUID.class));
        entity.setCreated(converter.fromRow(row, prefix + "_created", Instant.class));
        entity.setModified(converter.fromRow(row, prefix + "_modified", Instant.class));
        entity.setDeleted(converter.fromRow(row, prefix + "_deleted", Instant.class));
        entity.setClosed(converter.fromRow(row, prefix + "_closed", Instant.class));
        return entity;
    }
}
