package microapp.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import microapp.domain.IssueRole;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link IssueRole}, with proper type conversions.
 */
@Service
public class IssueRoleRowMapper implements BiFunction<Row, String, IssueRole> {

    private final ColumnConverter converter;

    public IssueRoleRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link IssueRole} stored in the database.
     */
    @Override
    public IssueRole apply(Row row, String prefix) {
        IssueRole entity = new IssueRole();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setIssueRoleKey(converter.fromRow(row, prefix + "_issue_role_key", String.class));
        entity.setIssueRole(converter.fromRow(row, prefix + "_issue_role", String.class));
        return entity;
    }
}
