package microapp.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import microapp.domain.IssueType;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link IssueType}, with proper type conversions.
 */
@Service
public class IssueTypeRowMapper implements BiFunction<Row, String, IssueType> {

    private final ColumnConverter converter;

    public IssueTypeRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link IssueType} stored in the database.
     */
    @Override
    public IssueType apply(Row row, String prefix) {
        IssueType entity = new IssueType();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setIssueTypeKey(converter.fromRow(row, prefix + "_issue_type_key", String.class));
        entity.setIssueTypeWeight(converter.fromRow(row, prefix + "_issue_type_weight", Integer.class));
        entity.setIssueType(converter.fromRow(row, prefix + "_issue_type", String.class));
        entity.setIsActive(converter.fromRow(row, prefix + "_is_active", Boolean.class));
        return entity;
    }
}
