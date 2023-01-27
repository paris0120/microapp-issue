package microapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class IssueAssignmentSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("issue_id", table, columnPrefix + "_issue_id"));
        columns.add(Column.aliased("issue_uuid", table, columnPrefix + "_issue_uuid"));
        columns.add(Column.aliased("username", table, columnPrefix + "_username"));
        columns.add(Column.aliased("issue_assignment_displayed_username", table, columnPrefix + "_issue_assignment_displayed_username"));
        columns.add(Column.aliased("issue_role", table, columnPrefix + "_issue_role"));
        columns.add(Column.aliased("displayed_issue_role", table, columnPrefix + "_displayed_issue_role"));
        columns.add(Column.aliased("created", table, columnPrefix + "_created"));
        columns.add(Column.aliased("modified", table, columnPrefix + "_modified"));
        columns.add(Column.aliased("accepted", table, columnPrefix + "_accepted"));
        columns.add(Column.aliased("deleted", table, columnPrefix + "_deleted"));

        return columns;
    }
}
