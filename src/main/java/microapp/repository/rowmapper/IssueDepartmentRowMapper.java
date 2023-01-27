package microapp.repository.rowmapper;

import io.r2dbc.spi.Row;
import java.util.function.BiFunction;
import microapp.domain.IssueDepartment;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link IssueDepartment}, with proper type conversions.
 */
@Service
public class IssueDepartmentRowMapper implements BiFunction<Row, String, IssueDepartment> {

    private final ColumnConverter converter;

    public IssueDepartmentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link IssueDepartment} stored in the database.
     */
    @Override
    public IssueDepartment apply(Row row, String prefix) {
        IssueDepartment entity = new IssueDepartment();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setIssueDepartmentKey(converter.fromRow(row, prefix + "_issue_department_key", String.class));
        entity.setIssueDepartment(converter.fromRow(row, prefix + "_issue_department", String.class));
        return entity;
    }
}
