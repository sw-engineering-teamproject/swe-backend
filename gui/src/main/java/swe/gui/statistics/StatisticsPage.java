package swe.gui.statistics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import swe.gui.statistics.view.StatisticsPageView;

public class StatisticsPage {
    private final StatisticsPageView statisticsPageView = new StatisticsPageView();

    public StatisticsPage() {
        JFrame frame = new JFrame("프로젝트 통계");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);

        JPanel panel = new JPanel();
        frame.add(panel);

        statisticsPageView.settingView(panel, frame);

        frame.setVisible(true);
    }


}
