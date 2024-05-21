package swe.gui.issue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import org.springframework.context.ApplicationContext;
import swe.issue.application.IssueService;

public class IssueForm {

    private final ApplicationContext applicationContext;
    private final IssueService issueService;
    public IssueForm(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.issueService = applicationContext.getBean(IssueService.class);
        JFrame frame = new JFrame("Issue");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 500);

        JPanel panel = new JPanel();
        frame.add(panel);
        setupForm(panel, frame);
        frame.setVisible(true);
    }


    private void setupForm(JPanel panel, JFrame frame){
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel titleLabel = new JLabel("Title");
        c.gridx = 0;
        c.gridy = 0;
        panel.add(titleLabel, c);

        JLabel descriptionLabel = new JLabel("Description");
        c.gridx = 0;
        c.gridy = 1;
        panel.add(descriptionLabel, c);

        JLabel commentLabel = new JLabel("Comment");
        c.gridx = 0;
        c.gridy = 2;
        panel.add(commentLabel, c);

        JTextArea commentArea = new JTextArea(4, 15);
        c.gridy = 3;
        panel.add(commentArea, c);
        JButton commentBtn = new JButton("등록");
        c.gridx = 1;
        panel.add(commentArea, c);

        commentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = commentArea.getText();
                if(comment.isEmpty()){
                    JOptionPane.showMessageDialog(
                        frame, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    System.out.println("곧 제작");
                }
            }
        });
    }

}
