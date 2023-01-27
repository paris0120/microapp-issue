package microapp.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import microapp.domain.IssuePriority;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link IssuePriority}, with proper type conversions.
 */
@Service
public class IssuePriorityRowMapper implements BiFunction<Row, String, IssuePriority> {

    private final ColumnConverter converter;

    public IssuePriorityRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link IssuePriority} stored in the database.
     */
    @Override
    public IssuePriority apply(Row row, String prefix) {
        IssuePriority entity = new IssuePriority();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setIssuePriority(converter.fromRow(row, prefix + "_issue_priority", String.class));
        entity.setIssuePriorityLevel(converter.fromRow(row, prefix + "_issue_priority_level", Integer.class));
        return entity;
    }
}
