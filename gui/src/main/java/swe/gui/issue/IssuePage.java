package swe.gui.issue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.springframework.context.ApplicationContext;
import swe.gui.issue.view.IssuePageView;

public class IssuePage {
    IssuePageView issuePageView = new IssuePageView();

    public IssuePage(ApplicationContext applicationContext) {

        JFrame frame = new JFrame("Issue Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JPanel panel = new JPanel();
        frame.add(panel);

        issuePageView.settingView(panel, frame, applicationContext);

        frame.setVisible(true);
    }


}
