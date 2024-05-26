package swe.gui.issue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import swe.gui.issue.view.IssueDetailView;

public class IssueDetail {

    private final IssueDetailView issueDetailView = new IssueDetailView();
    public IssueDetail(){
        JFrame frame = new JFrame("Issue");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 500);

        JPanel panel = new JPanel();
        frame.add(panel);
        issueDetailView.settingView(panel, frame);
        frame.setVisible(true);
    }



}
