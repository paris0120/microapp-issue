package microapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class IssueTypeSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("issue_type_key", table, columnPrefix + "_issue_type_key"));
        columns.add(Column.aliased("issue_type_weight", table, columnPrefix + "_issue_type_weight"));
        columns.add(Column.aliased("issue_type", table, columnPrefix + "_issue_type"));
        columns.add(Column.aliased("is_active", table, columnPrefix + "_is_active"));
        return columns;
    }
}
