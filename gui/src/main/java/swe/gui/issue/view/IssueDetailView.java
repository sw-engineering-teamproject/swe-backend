package swe.gui.issue.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import swe.gui.SessionStorage;
import swe.issue.application.IssueService;
import swe.issue.domain.Comment;
import swe.issue.domain.Issue;
import swe.issue.domain.IssueStatus;
import swe.user.application.UserService;
import swe.user.domain.User;

public class IssueDetailView {
    private JScrollPane scrollPane;
    private IssueService issueService;
    private UserService userService;

    public void settingView(JPanel issuePanel, JFrame frame) {
        this.userService = SessionStorage.userService;
        this.issueService = SessionStorage.issueService;
        issuePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Issue issue = issueService.findIssueDetail(SessionStorage.currentIssue.getId());
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        JLabel titleLabel = new JLabel("title: " + issue.getTitle());
        issuePanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        JLabel reporterLabel = new JLabel("reporter: " + issue.getReporter().getNickname());
        issuePanel.add(reporterLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 5;
        gbc.gridheight = 7;
//        JLabel contentLabel = new JLabel(issue.getDescription());
//        contentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
//        gbc.fill = GridBagConstraints.BOTH;
//        issuePanel.add(contentLabel, gbc);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
        JTextArea contentLabel = new JTextArea(issue.getDescription());
        contentLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        contentLabel.setLineWrap(true);
        contentLabel.setWrapStyleWord(true);
        contentLabel.setEditable(false);
        gbc.fill = GridBagConstraints.BOTH;
        issuePanel.add(contentLabel, gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridheight = 1;
        gbc.gridy = 0;
        gbc.gridx = 7;
        gbc.gridwidth = 1;
        JButton fixedBtn = new JButton("수정");
        issuePanel.add(fixedBtn, gbc);


        gbc.gridy = 1;
        gbc.gridx = 6;
        gbc.gridwidth=2;
        JLabel asigneeLabel = new JLabel("assignee");
        issuePanel.add(asigneeLabel, gbc);

        gbc.gridy = 2;
        List<User> userList = issueService.recommendIssue(SessionStorage.currentIssue.getId());
        JComboBox<String> assignee = new JComboBox<>();
        assignee.addItem("NULL");
        for(User user : userList){
            assignee.addItem(user.getNickname());
        }
        if(issue.getAssignee().isPresent()){
            assignee.setSelectedItem(issue.getAssignee().get().getNickname());
        }
        else {
            assignee.setSelectedItem("NULL");
        }
        assignee.setPreferredSize(new Dimension(150, 25));
        issuePanel.add(assignee, gbc);

        gbc.gridy = 3;
        JLabel statusLabel = new JLabel("status");
        issuePanel.add(statusLabel, gbc);

        gbc.gridy = 4;
        List<IssueStatus> issueStatus = issueService.getIssueStatuses();
        List<String> issueStatusStr = new ArrayList<>();
        for(IssueStatus issueStat : issueStatus){
            issueStatusStr.add(issueStat.getName());
        }
        int issueStatusSize = issueStatusStr.size();
        String statusList[] = issueStatusStr.toArray(new String[issueStatusSize]);
        JComboBox<String> status = new JComboBox<>(statusList);
        status.setSelectedItem(issue.getStatus().getName());
        if(status.getSelectedItem() == "fixed"){
            status.setEnabled(false);
        }
        status.setPreferredSize(new Dimension(150, 25));
        issuePanel.add(status, gbc);


        JLabel rankLabel = new JLabel("Priority");
        gbc.gridy = 5;
        issuePanel.add(rankLabel, gbc);

        gbc.gridy = 6;
        String rankList[] = {"blocker", "critical", "major", "minor", "trivial"};
        JComboBox<String> rank = new JComboBox<>(rankList);
        rank.setPreferredSize(new Dimension(150, 25));
        rank.setSelectedItem(issue.getPriority().getName());
        issuePanel.add(rank, gbc);

        gbc.gridy = 7;
        JLabel fixerLabel = new JLabel("Fixer: ");
        issuePanel.add(fixerLabel, gbc);

        gbc.gridy = 8;
        Optional<User> fixerUser = issue.getFixer();
        JLabel fixer;
        if(fixerUser.isPresent()){
            fixer = new JLabel(fixerUser.get().getNickname());
        }
        else {
            fixer = new JLabel("NULL");
        }
        fixer.setPreferredSize(new Dimension(150, 25));
        issuePanel.add(fixer, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.gridheight = 7;
        JTextArea contentFix = new JTextArea(contentLabel.getText(), 5, 15);
        contentFix.setLineWrap(true);

        // 댓글 입력 필드 및 버튼
        JTextField commentField = new JTextField(10);
        JButton commentButton = new JButton("등록");
        gbc.gridy = 9;
        gbc.gridheight = 1;
        gbc.gridwidth = 4;
        issuePanel.add(commentField, gbc);

        gbc.gridx = 4;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        issuePanel.add(commentButton, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);


        scrollPane = new JScrollPane(issuePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel comments = new JPanel();

        // 댓글 모음
        comments.setLayout(new GridLayout(0, 3));
        gbc.gridx = 0;
        gbc.gridy = 10;
        issuePanel.add(comments, gbc);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(Comment comment : issue.getComments()){
            comments.add(new JLabel(comment.getContent()));
            comments.add(new JLabel(comment.getCommenter().getNickname()));
            comments.add(new JLabel(comment.getCreatedAt().format(formatter)));
        }
        // 댓글 버튼 이벤트 핸들러
        commentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String comment = commentField.getText();
                if (!comment.isEmpty()) {
                    JLabel commentLabel = new JLabel(comment);
                    issueService.commentContent(SessionStorage.loginUser.getId(), SessionStorage.currentIssue.getId(), comment);
                    comments.add(commentLabel);
                    comments.add(new JLabel(SessionStorage.loginUser.getNickname()));
                    comments.add(new JLabel(LocalDateTime.now().format(formatter)));
                    comments.revalidate();
                    comments.repaint();
                    commentField.setText("");
                } else {
                    JOptionPane.showMessageDialog(
                        frame,
                        "Comment cannot be empty",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        fixedBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(fixedBtn.getText(), "수정")) {
                    issuePanel.remove(contentLabel);
                    fixedBtn.setText("수정 완료");
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 5;
                    gbc.gridheight = 7;
                    gbc.fill = GridBagConstraints.BOTH;
                    issuePanel.add(contentFix, gbc);
                    issuePanel.revalidate();
                    issuePanel.repaint();
                }
                else {
                    issuePanel.remove(contentFix);
                    fixedBtn.setText("수정");
                    gbc.gridx = 0;
                    gbc.gridy = 2;
                    gbc.gridwidth = 5;
                    gbc.gridheight = 7;
                    gbc.fill = GridBagConstraints.BOTH;
                    contentLabel.setText(contentFix.getText());
                    issueService.updateDescription(SessionStorage.currentIssue.getId(), contentFix.getText());
                    issuePanel.add(contentLabel, gbc);
                    issuePanel.revalidate();
                    issuePanel.repaint();
                }
            }
        });
        assignee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User user = userService.findUser(assignee.getSelectedItem().toString());
                issueService.assignUser(SessionStorage.loginUser.getId(), SessionStorage.currentIssue.getId(), user.getId());
                status.setSelectedItem("assigned");
                issuePanel.revalidate();
                issuePanel.repaint();
            }
        });

        status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(status.getSelectedItem().toString(), "fixed")){
                    issueService.updateStatus(SessionStorage.loginUser.getId(), SessionStorage.currentIssue.getId(), "fixed");
                    //만약 권한 없는 사람이 fixed를 누르면 변환 안되게
                    status.setEnabled(false);
                    fixer.setText(SessionStorage.loginUser.getNickname());
                }
            }
        });
        frame.add(scrollPane);
        rank.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueService.updatePriority(SessionStorage.currentIssue.getId(), (String) rank.getSelectedItem());
            }
        });
        frame.add(scrollPane);
    }
}
