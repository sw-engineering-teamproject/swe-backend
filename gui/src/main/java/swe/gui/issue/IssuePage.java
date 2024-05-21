package swe.gui.issue;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.springframework.context.ApplicationContext;
import swe.issue.application.IssueService;
import swe.issue.domain.Issue;

public class IssuePage {
    private JPanel resultsPanel;
    private JComboBox<String> searchCriteriaComboBox; // 검색 기준 선택용
    private JTextField searchField; // 검색 텍스트 필드
    private final ApplicationContext applicationContext;
    private final IssueService issueService;

    public IssuePage(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.issueService = applicationContext.getBean(IssueService.class);
        JFrame frame = new JFrame("Issue Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JPanel panel = new JPanel();
        frame.add(panel);

        placeComponents(panel, frame);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel, JFrame frame) {
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

        searchField = new JTextField(30);
        c.gridx = 2;
        c.gridy = 0;
        c.insets = new Insets(5, 0, 5, 0);
        panel.add(searchField, c);

        // 검색 버튼
        JButton searchButton = new JButton("Search");
        c.gridx = 3;
        c.gridy = 0;
        panel.add(searchButton, c);

        // 결과 표시 영역 (JPanel)
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(resultsPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.BOTH;
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
                new CreateIssuePage(applicationContext);
            }
        });
    }

    private void updateResultsPanel() {
        resultsPanel.removeAll(); // 기존 결과를 지웁니다.

        String searchText = searchField.getText();
        String selectedCriterion = (String) searchCriteriaComboBox.getSelectedItem();

        if(Objects.equals(selectedCriterion, "Home")) {
            List<Issue> results = issueService.findIssues(1L);
            if (results.isEmpty()) {
                JLabel noResultsLabel = new JLabel("No results found.");
                resultsPanel.add(noResultsLabel);

            } else {
                for (Issue issue : results) {
                    JButton issueButton = new JButton(issue.getTitle());
                    issueButton.setSize(1000, 100);
                    issueButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            showIssueDetails(issue);
                        }
                    });
                    resultsPanel.add(issueButton);
                }
            }
        }
//        List<Issue> results;
//
//        results = issueService.getIssueBySearch(selectedCriterion, searchText);
//
//        if (results.isEmpty()) {
//            JLabel noResultsLabel = new JLabel("No results found.");
//            resultsPanel.add(noResultsLabel);
//        } else {
//            for (Issue issue : results) {
//                JButton issueButton = new JButton(issue.getTitle());
//                issueButton.setSize(1000, 100);
//                issueButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        showIssueDetails(issue);
//                    }
//                });
//                resultsPanel.add(issueButton);
//            }
//        }

        resultsPanel.revalidate(); // UI를 업데이트합니다.
        resultsPanel.repaint();   // UI를 다시 그립니다.
    }

    private void showIssueDetails(Issue issue) {
        new IssueForm(applicationContext);
    }
}
