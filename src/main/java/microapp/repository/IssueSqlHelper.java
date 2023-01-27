package microapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class IssueSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("username", table, columnPrefix + "_username"));
        columns.add(Column.aliased("first_name", table, columnPrefix + "_first_name"));
        columns.add(Column.aliased("last_name", table, columnPrefix + "_last_name"));
        columns.add(Column.aliased("displayed_username", table, columnPrefix + "_displayed_username"));
        columns.add(Column.aliased("issue_title", table, columnPrefix + "_issue_title"));
        columns.add(Column.aliased("issue_content", table, columnPrefix + "_issue_content"));
        columns.add(Column.aliased("issue_type", table, columnPrefix + "_issue_type"));
        columns.add(Column.aliased("issue_workflow_status", table, columnPrefix + "_issue_workflow_status"));
        columns.add(Column.aliased("issue_workflow_status_key", table, columnPrefix + "_issue_workflow_status_key"));
        columns.add(Column.aliased("issue_priority_level", table, columnPrefix + "_issue_priority_level"));
        columns.add(Column.aliased("issue_uuid", table, columnPrefix + "_issue_uuid"));
        columns.add(Column.aliased("created", table, columnPrefix + "_created"));
        columns.add(Column.aliased("modified", table, columnPrefix + "_modified"));
        columns.add(Column.aliased("deleted", table, columnPrefix + "_deleted"));
        columns.add(Column.aliased("closed", table, columnPrefix + "_closed"));

        return columns;
    }
}
