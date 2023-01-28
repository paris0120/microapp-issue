package microapp.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import microapp.domain.IssueType;
import microapp.domain.MenuItem;
import microapp.service.IssueTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueMenuService {

    private List<MenuItem> issueMenu;

    @Autowired
    private IssueTypeService issueTypeService;

    public List<MenuItem> getIssueMenu() {
        if (issueMenu == null) refreshIssueMenu();
        return issueMenu;
    }

    public void setIssueMenu(List<MenuItem> issueMenu) {
        this.issueMenu = issueMenu;
    }

    public void refreshIssueMenu() {
        issueMenu = new ArrayList<>();
        MenuItem item = new MenuItem();
        item.label = "MY Issues";
        issueMenu.add(item);
        item = new MenuItem();
        item.label = "New Issue";
        issueMenu.add(item);
        for (IssueType type : issueTypeService.issueTypes) {
            MenuItem typeItem = new MenuItem();
            typeItem.label = type.getIssueType();
            typeItem.url = type.getIssueTypeKey();
        }
    }
}
