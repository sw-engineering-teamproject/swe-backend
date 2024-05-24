package swe.gui.issue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.springframework.context.ApplicationContext;
import swe.gui.issue.view.CreateIssuePageView;

public class CreateIssuePage {
    private final CreateIssuePageView createIssuePageView = new CreateIssuePageView();

    public CreateIssuePage(ApplicationContext applicationContext) {
        JFrame frame = new JFrame("New Issue");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        createIssuePageView.settingView(panel, frame, applicationContext);
        frame.setVisible(true);
    }


}
