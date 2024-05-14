package swe.gui.user;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import swe.user.User;
import swe.user.application.UserService;

public class AuthPage {

    private JFrame authFrame;

    private UserService userService;

    public AuthPage(){
        authFrame = new JFrame("login / sign up");
        authFrame.setSize(400, 200);
        authFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        authFrame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel loginPanel = createLoginPanel();
        tabbedPane.add("login", loginPanel);

        JPanel signupPanel = createSignupPanel();
        tabbedPane.add("sign up", signupPanel);

        authFrame.add(tabbedPane);
        authFrame.setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("아이디"), gbc);

        gbc.gridx = 1;
        JTextField userId = new JTextField(15);
        loginPanel.add(userId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(new JLabel("비밀번호"), gbc);

        gbc.gridx = 1;
        JPasswordField password = new JPasswordField(15);
        loginPanel.add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton loginBtn = new JButton("로그인");
        loginPanel.add(loginBtn, gbc);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUserId = userId.getText();
                String inputPassword = new String(password.getPassword());
                //userService.login(inputUserId, inputPassword);
            }
        });
        return loginPanel;
    }

    private JPanel createSignupPanel(){
        JPanel signupPanel = new JPanel();
        signupPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        signupPanel.add(new JLabel("닉네입"), gbc);

        gbc.gridx = 1;
        JTextField nickname = new JTextField(15);
        signupPanel.add(nickname, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        signupPanel.add(new JLabel("아이디"), gbc);

        gbc.gridx = 1;
        JTextField userId = new JTextField(15);
        signupPanel.add(userId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        signupPanel.add(new JLabel("비밀번호"), gbc);

        gbc.gridx = 1;
        JPasswordField password = new JPasswordField(15);
        signupPanel.add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;

        signupPanel.add(new JLabel("authority"), gbc);

        gbc.gridx = 1;
        String[] authority = { "admin", "PL", "dev", "tester" };
        JComboBox<String> role = new JComboBox<>(authority);
        role.setPreferredSize(new Dimension(150, 25));
        gbc.insets = new Insets(5, 0, 5, 0);
        signupPanel.add(role, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton signupBtn = new JButton("회원가입");
        signupPanel.add(signupBtn, gbc);

        signupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUserId = userId.getText();
                String inputPassword = new String(password.getPassword());
                String inputNickname = nickname.getText();
                String inputRole = role.getSelectedItem().toString();
//                User user = new User(inputUserId, inputPassword, inputNickname, inputRole);
                //userService.enroll(user);
            }
        });
        return signupPanel;
    }
}
