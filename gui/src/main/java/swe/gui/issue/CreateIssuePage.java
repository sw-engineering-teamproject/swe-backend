package swe.gui.issue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import swe.gui.issue.view.CreateIssuePageView;

public class CreateIssuePage {
    private final CreateIssuePageView createIssuePageView = new CreateIssuePageView();

    public CreateIssuePage() {
        JFrame frame = new JFrame("New Issue");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 400);

        frame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        frame.add(panel);
        createIssuePageView.settingView(panel, frame);
        frame.setVisible(true);
    }


}
