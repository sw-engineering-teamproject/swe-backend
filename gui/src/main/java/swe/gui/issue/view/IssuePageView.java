package swe.gui.issue.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import swe.gui.SessionStorage;
import swe.gui.issue.CreateIssuePage;
import swe.gui.issue.IssueDetail;
import swe.gui.statistics.StatisticsPage;
import swe.issue.application.IssueService;
import swe.issue.domain.Issue;
import swe.issue.domain.IssueStatus;
import swe.user.application.UserService;
import swe.user.domain.User;

public class IssuePageView {
    private JPanel resultsPanel;
    private JComboBox<String> searchCriteriaComboBox; // 검색 기준 선택용
    private JComboBox<String> searchField;
    private IssueService issueService;
    private UserService userService;
    public void settingView(JPanel panel, JFrame frame) {
        this.issueService = SessionStorage.issueService;
        this.userService = SessionStorage.userService;
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // 검색 기준 선택
        String[] searchCriteria = { "Home", "assignee", "reporter", "issue status" };
        searchCriteriaComboBox = new JComboBox<>(searchCriteria);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(5, 5, 5, 5);
        panel.add(searchCriteriaComboBox, c);

        // 검색 레이블 및 텍스트 필드
        JLabel searchLabel = new JLabel("Search");
        c.gridx = 1;
        c.gridy = 0;
        panel.add(searchLabel, c);

        searchField = new JComboBox<>();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(5, 0, 5, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(searchField, c);
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        // 검색 버튼
        JButton searchButton = new JButton("Search");
        c.gridx = 4;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 10);
        panel.add(searchButton, c);

        JButton statistics = new JButton("통계");
        c.gridx = 5;
        c.gridy = 0;
        panel.add(statistics, c);

        // 결과 표시 영역 (JPanel)
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(0, 5, 0, 5);
        c.weightx = 1.0;
        c.weighty = 1.0;
        panel.add(scrollPane, c);

        // 검색 버튼 이벤트 핸들러
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateResultsPanel(); // 검색 결과를 업데이트
            }
        });

        // 초기 결과 표시
        updateResultsPanel();

        JButton post = new JButton("등록");
        panel.add(post);
        post.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new CreateIssuePage();
            }
        });
        statistics.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new StatisticsPage();
            }
        });
        searchCriteriaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.removeAllItems();
                if(Objects.equals(searchCriteriaComboBox.getSelectedItem().toString(), "assignee") || Objects.equals(searchCriteriaComboBox.getSelectedItem().toString(), "reporter")){
                    List<User> userList = userService.findAllUsers();
                    for(User user : userList){
                        searchField.addItem(user.getNickname());
                    }
                }
                else if(Objects.equals(searchCriteriaComboBox.getSelectedItem().toString(), "issue status")){
                    List<IssueStatus> issueStatusList = issueService.getIssueStatuses();
                    for(IssueStatus issueStatus : issueStatusList){
                        searchField.addItem(issueStatus.getName());
                    }
                }
//                else {
//                    searchField.removeAllItems();
//                }

            }
        });
    }

    private void updateResultsPanel() {
        resultsPanel.removeAll(); // 기존 결과를 지움
        String selectedCriterion = (String) searchCriteriaComboBox.getSelectedItem();
        List<Issue> results = new ArrayList<>();
        if(Objects.equals(selectedCriterion, "Home")) {
            results = issueService.findIssues(SessionStorage.currentProject.id());

        }
        else if(Objects.equals(selectedCriterion, "reporter") || Objects.equals(selectedCriterion, "assignee")){
            String searchText = searchField.getSelectedItem().toString();
            Long userId = userService.findUser(searchText).getId();
            results = issueService.filterIssues(SessionStorage.currentProject.id(), selectedCriterion, userId.toString());
        }
        else{
            String searchText = searchField.getSelectedItem().toString();
            results = issueService.filterIssues(SessionStorage.currentProject.id(), "issueStatus", searchText);
        }
        if (results.isEmpty()) {
            JLabel noResultsLabel = new JLabel("No results found.");
            resultsPanel.add(noResultsLabel);

        } else {
            for (Issue issue : results) {
                String buttonText = String.format("%-11s  ||  %s     [%s]", issue.getReporter().getNickname(), issue.getTitle(), issue.getStatus());
                JButton issueButton = new JButton(buttonText);
                issueButton.setHorizontalAlignment(SwingConstants.LEFT);
                //issueButton.setSize(1000, 100);
                issueButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SessionStorage.currentIssue = issue;
                        new IssueDetail();
                    }
                });
                issueButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, issueButton.getMinimumSize().height));
                resultsPanel.add(issueButton);
            }
        }
        resultsPanel.add(Box.createVerticalGlue());
        resultsPanel.revalidate(); // UI를 업데이트합니다.
        resultsPanel.repaint();   // UI를 다시 그립니다.
    }


}
