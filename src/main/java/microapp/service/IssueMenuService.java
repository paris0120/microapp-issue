package microapp.service;

import java.util.ArrayList;
import java.util.Collections;
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
        final MenuItem newitem = new MenuItem();
        newitem.label = "New Issue";
        newitem.items = new ArrayList<>();
        issueMenu.add(item);
        issueTypeService
            .getIssueTypes()
            .collectList()
            .subscribe(types -> {
                for (IssueType type : types) {
                    MenuItem typeItem = new MenuItem();
                    typeItem.label = type.getIssueType();
                    typeItem.url = type.getIssueTypeKey();
                    newitem.items.add(typeItem);
                }
            });
    }
}
