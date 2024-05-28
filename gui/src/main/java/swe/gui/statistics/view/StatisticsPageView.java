package swe.gui.statistics.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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

public class StatisticsPageView {
    private IssueService issueService;

    public void settingView(JPanel staticPanel, JFrame frame){
        this.issueService = SessionStorage.issueService;
        staticPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        frame.add(staticPanel);

        JButton back = new JButton("뒤로가기");
        gbc.gridx = 0;
        gbc.gridy = 0;
        staticPanel.add(back, gbc);

        JLabel title = new JLabel("통계");
        gbc.gridx = 1;
        staticPanel.add(title, gbc);

        JButton day = new JButton("일별");
        gbc.gridy = 1;
        staticPanel.add(day, gbc);

        JButton month = new JButton("월별");
        gbc.gridx = 2;
        staticPanel.add(month, gbc);

        JPanel result = new JPanel();
        gbc.gridy = 2;
        gbc.gridx = 1;
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
                for (Map.Entry<LocalDate, Long> entry : monthStatic.entrySet()) {
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
    }

}
