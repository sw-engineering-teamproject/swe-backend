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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import org.springframework.context.ApplicationContext;
import swe.gui.SessionStorage;
import swe.gui.project.ProjectPage;
import swe.user.application.UserService;
import swe.user.domain.User;
import swe.user.domain.UserRole;
import swe.user.dto.UserRegisterRequest;

public class AuthPage {

    private final JFrame authFrame;
    private final UserService userService;
    private final ApplicationContext applicationContext;
    public static User currentUser;
    private Boolean isDuplicate = Boolean.TRUE;

    public AuthPage(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
        this.userService = applicationContext.getBean(UserService.class);
        authFrame = new JFrame("login / sign up");
        authFrame.setSize(400, 300);
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
                SessionStorage.loginUser = userService.login(inputUserId, inputPassword);
                authFrame.dispose();
                new ProjectPage(applicationContext);
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
        signupPanel.add(new JLabel("닉네임"), gbc);

        gbc.gridx = 1;
        JTextField nickname = new JTextField(15);
        signupPanel.add(nickname, gbc);

        JButton isDuplicateNickname = new JButton("중복확인");
        gbc.gridx = 2;
        gbc.insets = new Insets(0, 5, 0, 0);
        signupPanel.add(isDuplicateNickname, gbc);
        gbc.insets = new Insets(0, 0, 0, 0);

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

        isDuplicateNickname.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputNickname = nickname.getText();
                if (userService.checkDuplicateNickname(inputNickname)) {
                    JOptionPane.showMessageDialog(
                        authFrame,
                        "이미 있는 사용자 입니다.",
                        "False",
                        JOptionPane.ERROR_MESSAGE
                    );
                } else{
                    JOptionPane.showMessageDialog(
                        authFrame,
                        "사용 가능한 nickname 입니다.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    isDuplicate = Boolean.FALSE;
                }
            }
        });
        signupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUserId = userId.getText();
                String inputPassword = new String(password.getPassword());
                String inputNickname = nickname.getText();
                String inputRole = role.getSelectedItem().toString();
                UserRole userRole = switch (inputRole) {
                    case "admin" -> UserRole.ADMIN;
                    case "PL" -> UserRole.PL;
                    case "tester" -> UserRole.TESTER;
                    default -> UserRole.DEV;
                };
                UserRegisterRequest userRegisterRequest = new UserRegisterRequest(inputUserId, userRole, inputNickname, inputPassword);
                if(!isDuplicate) {
                    userService.register(userRegisterRequest);
                    JOptionPane.showMessageDialog(
                        authFrame,
                        "회원 가입 성공",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    authFrame.dispose();
                    new AuthPage(applicationContext);
                }
                else {
                    JOptionPane.showMessageDialog(
                        authFrame,
                        "중복 확인 요청",
                        "False",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        return signupPanel;
    }
}
