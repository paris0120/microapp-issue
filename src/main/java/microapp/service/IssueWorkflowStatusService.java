package microapp.service;

import java.util.SortedMap;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IssueWorkflowStatusService {

    SortedMap<String, String> issueWorkflowStatus;

    private final Logger log = LoggerFactory.getLogger(IssueWorkflowStatusService.class);

    private static final String ENTITY_NAME = "issueServerIssueWorkflowStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    @PostConstruct
    public void refreshIssuePriority() {
        issueWorkflowStatus = new TreeMap<>();
        issueWorkflowStatus.put("OPEN", "Open");
        issueWorkflowStatus.put("CLOSED", "Closed");
        log.info("Issue workflow status updated.");
    }

    public String getIssueWorkflowStatus(String issueWorkflowStatusKey) {
        return issueWorkflowStatus.get(issueWorkflowStatusKey);
    }
}
