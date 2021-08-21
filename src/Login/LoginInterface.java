package Login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import dao.*;
import Main.MainFrame;
import Main.Global;

public class LoginInterface extends JFrame{
	private static final long serialVersionUID = 1L;
	private LoginPanel loginPanel = null;// 登录面板
	private JLabel jLabel = null;// “用户名”标签
	private JTextField userField = null;// “用户名”文本框
	private JLabel jLabel1 = null;// “密码”标签
	private JPasswordField passwordField = null;// “密码”文本框
	private JButton loginButton = null;// “登录”按钮
	private JButton exitButton = null;// “退出”按钮
	private static String userStr;// “用户名”文本框中的内容
	private MainFrame mainFrame;// 主窗体

	public LoginInterface() {// 登录窗体的构造方法
		try { 
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LoginPanel getLoginPanel() {
		if (loginPanel == null) {// 登录面板中没有组件时
			jLabel1 = new JLabel();// “密码”标签
			jLabel1.setBounds(new Rectangle(86, 71, 55, 18));
			jLabel1.setText("密　码：");
			jLabel = new JLabel();
			jLabel.setText("用户名：");
			jLabel.setBounds(new Rectangle(85, 41, 56, 18));
			loginPanel = new LoginPanel();
			loginPanel.setLayout(null);
			loginPanel.setBackground(new Color(0xD8DDC7));
			loginPanel.add(jLabel, null);
			loginPanel.add(getUserField(), null);
			loginPanel.add(jLabel1, null);
			loginPanel.add(getPasswordField(), null);
			loginPanel.add(getLoginButton(), null);
			loginPanel.add(getExitButton(), null);
		}
		return loginPanel;// 返回登录面板
	}

	private JTextField getUserField() {// 初始化“用户名”文本框
		if (userField == null) {
			userField = new JTextField();
			userField.setBounds(new Rectangle(142, 39, 127, 22));
		}
		return userField;
	}

	private JPasswordField getPasswordField() {// 初始化“密码”文本框
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.setBounds(new Rectangle(143, 69, 125, 22));
			passwordField.addKeyListener(new KeyAdapter() {// 为“密码”文本框添加键盘时间的监听
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == '\n')
						loginButton.doClick();
				}
			});
		}
		return passwordField;
	}

	private JButton getLoginButton() {// 初始化“登录”按钮
		if (loginButton == null) {
			loginButton = new JButton();
			loginButton.setBounds(new Rectangle(109, 114, 48, 20));
			loginButton.setIcon(new ImageIcon(getClass().getResource("/res/loginbutton.jpg")));
			loginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						userStr = userField.getText();
						String passStr = new String(passwordField.getPassword());
						//userStr="a";passStr="a";
						if (!Dao.checkLogin(userStr, passStr)) {// 验证用户名、密码失败
							JOptionPane.showMessageDialog(LoginInterface.this, "用户名与密码无法登录", "登录失败",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					mainFrame = new MainFrame();
					mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					mainFrame.setVisible(true);
					String str=null;
					if(Global.userID.equals("sale"))str="销售员： ";
					else if(Global.userID.equals("stock"))str="仓库管理员： ";
					else if(Global.userID.equals("buy"))str="采购员： ";
					MainFrame.getCzyStateLabel().setText(str+userStr);
					setVisible(false);
				}
			});
		}
		return loginButton;
	}

	private JButton getExitButton() {// “退出”按钮
		if (exitButton == null) {
			exitButton = new JButton();
			exitButton.setBounds(new Rectangle(181, 114, 48, 20));
			exitButton.setIcon(new ImageIcon(getClass().getResource("/res/exitbutton.jpg")));
			exitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);// 退出
				}
			});
		}
		return exitButton;
	}

	private void initialize() {// 初始化登录窗体
		Dimension size = getToolkit().getScreenSize();
		setLocation((size.width - 296) / 2, (size.height - 188) / 2);
		setSize(296, 188);
		this.setTitle("系统登录");
		setContentPane(getLoginPanel());
	}

	public String getUserStr() {
		
		return userStr;
	}
}
