package microapp.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

public class IssueEmployeeSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("username", table, columnPrefix + "_username"));
        columns.add(Column.aliased("first_name", table, columnPrefix + "_first_name"));
        columns.add(Column.aliased("last_name", table, columnPrefix + "_last_name"));
        columns.add(Column.aliased("display_name", table, columnPrefix + "_display_name"));
        columns.add(Column.aliased("issue_department", table, columnPrefix + "_issue_department"));
        columns.add(Column.aliased("default_issue_role_key", table, columnPrefix + "_default_issue_role_key"));
        columns.add(Column.aliased("default_displayed_issue_role", table, columnPrefix + "_default_displayed_issue_role"));
        columns.add(Column.aliased("is_available", table, columnPrefix + "_is_available"));
        columns.add(Column.aliased("in_office_from", table, columnPrefix + "_in_office_from"));
        columns.add(Column.aliased("office_hour_from", table, columnPrefix + "_office_hour_from"));
        columns.add(Column.aliased("office_hour_to", table, columnPrefix + "_office_hour_to"));
        columns.add(Column.aliased("timezone", table, columnPrefix + "_timezone"));
        columns.add(Column.aliased("created", table, columnPrefix + "_created"));
        columns.add(Column.aliased("modified", table, columnPrefix + "_modified"));
        columns.add(Column.aliased("deleted", table, columnPrefix + "_deleted"));

        return columns;
    }
}
