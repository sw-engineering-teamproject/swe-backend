package swe.gui.statistics.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import swe.gui.SessionStorage;
import swe.gui.issue.IssuePage;
import swe.issue.application.IssueService;
import swe.issue.domain.IssuePriority;
import swe.issue.domain.IssueStatus;
import swe.user.domain.User;

public class StatisticsPageView {
    private IssueService issueService;

    public void settingView(JPanel staticPanel, JFrame frame){
        this.issueService = SessionStorage.issueService;
        staticPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        frame.add(staticPanel);

        JButton back = new JButton("뒤로가기");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        staticPanel.add(back, gbc);

        JLabel title = new JLabel("통계");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.NONE;
        staticPanel.add(title, gbc);

        JButton day = new JButton("일별");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        staticPanel.add(day, gbc);

        JButton month = new JButton("월별");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        staticPanel.add(month, gbc);

        JButton assignee = new JButton("assignee");
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        staticPanel.add(assignee, gbc);

        JButton reporter = new JButton("reporter");
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        staticPanel.add(reporter, gbc);

        JButton status = new JButton("status");
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        staticPanel.add(status, gbc);

        JButton priority = new JButton("priority");
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        staticPanel.add(priority, gbc);

        JPanel result = new JPanel();
        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 6;
        staticPanel.add(result, gbc);


        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new IssuePage();
            }
        });

        day.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.removeAll();
                Map<LocalDate, Long> dayStatic = issueService.getIssueCreateCountByDay(SessionStorage.currentProject.id());
                String header[] = {"일", "이슈 수"};

                String[][] contents = new String[dayStatic.size()][2];

                // Map 데이터를 contents 배열에 넣기
                int index = 0;
                for (Map.Entry<LocalDate, Long> entry : dayStatic.entrySet()) {
                    contents[index][0] = entry.getKey().toString();
                    contents[index][1] = entry.getValue().toString();
                    index++;
                }
                JTable dayTable = new JTable(contents, header);

                JScrollPane scrollPane = new JScrollPane(dayTable);
                result.add(scrollPane);
                result.revalidate();
                result.repaint();
            }
        });

        month.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.removeAll();
                Map<LocalDate, Long> monthStatic = issueService.getIssueCreateCountByMonth(SessionStorage.currentProject.id());
                String header[] = {"월", "이슈 수"};

                String[][] contents = new String[monthStatic.size()][2];

                // Map 데이터를 contents 배열에 넣기
                int index = 0;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                for (Map.Entry<LocalDate, Long> entry : monthStatic.entrySet()) {
                    contents[index][0] = entry.getKey().format(formatter);
                    contents[index][1] = entry.getValue().toString();
                    index++;
                }
                JTable monthTable = new JTable(contents, header);

                JScrollPane scrollPane = new JScrollPane(monthTable);
                result.add(scrollPane);
                result.revalidate();
                result.repaint();
            }
        });

        status.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.removeAll();
                Map<IssueStatus, Long> statusStatic = issueService.getStatusCount(SessionStorage.currentProject.id());
                String header[] = {"status", "이슈 수"};

                String[][] contents = new String[statusStatic.size()][2];

                // Map 데이터를 contents 배열에 넣기
                int index = 0;
                for (Map.Entry<IssueStatus, Long> entry : statusStatic.entrySet()) {
                    contents[index][0] = entry.getKey().toString();
                    contents[index][1] = entry.getValue().toString();
                    index++;
                }
                JTable statusTable = new JTable(contents, header);

                JScrollPane scrollPane = new JScrollPane(statusTable);
                result.add(scrollPane);
                result.revalidate();
                result.repaint();
            }
        });

        priority.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.removeAll();
                Map<IssuePriority, Long> priorityStatic = issueService.getPriorityCount(SessionStorage.currentProject.id());
                String header[] = {"priority", "이슈 수"};

                String[][] contents = new String[priorityStatic.size()][2];

                // Map 데이터를 contents 배열에 넣기
                int index = 0;
                for (Map.Entry<IssuePriority, Long> entry : priorityStatic.entrySet()) {
                    contents[index][0] = entry.getKey().toString();
                    contents[index][1] = entry.getValue().toString();
                    index++;
                }
                JTable priorityTable = new JTable(contents, header);

                JScrollPane scrollPane = new JScrollPane(priorityTable);
                result.add(scrollPane);
                result.revalidate();
                result.repaint();
            }
        });

        assignee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.removeAll();
                Map<User, Long> assigneeStatic = issueService.getAssigneeCount(SessionStorage.currentProject.id());
                String header[] = {"assignee", "이슈 수"};

                String[][] contents = new String[assigneeStatic.size()][2];

                // Map 데이터를 contents 배열에 넣기
                int index = 0;
                for (Map.Entry<User, Long> entry : assigneeStatic.entrySet()) {
                    contents[index][0] = entry.getKey().getNickname();
                    contents[index][1] = entry.getValue().toString();
                    index++;
                }
                JTable assigneeTable = new JTable(contents, header);

                JScrollPane scrollPane = new JScrollPane(assigneeTable);
                result.add(scrollPane);
                result.revalidate();
                result.repaint();
            }
        });

        reporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.removeAll();
                Map<User, Long> reporterStatic = issueService.getAssigneeCount(SessionStorage.currentProject.id());
                String header[] = {"reporter", "이슈 수"};

                String[][] contents = new String[reporterStatic.size()][2];

                // Map 데이터를 contents 배열에 넣기
                int index = 0;
                for (Map.Entry<User, Long> entry : reporterStatic.entrySet()) {
                    contents[index][0] = entry.getKey().getNickname();
                    contents[index][1] = entry.getValue().toString();
                    index++;
                }
                JTable reporterTable = new JTable(contents, header);

                JScrollPane scrollPane = new JScrollPane(reporterTable);
                result.add(scrollPane);
                result.revalidate();
                result.repaint();
            }
        });
    }

}
