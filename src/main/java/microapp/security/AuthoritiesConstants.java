package microapp.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ISSUE_ISSUE_MANAGER = "ROLE_ISSUE_ISSUE_MANAGER"; //change status

    public static final String ISSUE_MANAGER = "ROLE_ISSUE_MANAGER"; //assignment, close issue

    public static final String ISSUE_ENGINEER = "ROLE_ISSUE_ENGINEER"; //comment

    public static final String ISSUE_ADMIN = "ROLE_ISSUE_ADMIN"; //create tag, etc

    public static final String ISSUE_CUSTOMER_SERVICE = "ROLE_ISSUE_CUSTOMER_SERVICE"; //comment, see all open issues, take issues

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
