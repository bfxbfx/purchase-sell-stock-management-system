package Main;

import static java.awt.BorderLayout.*;
import static javax.swing.border.BevelBorder.*;
import java.awt.*;
import java.util.Date;
import javax.swing.*;

import Login.LoginInterface;

public class MainFrame extends JFrame {// 主窗体
	private static final long serialVersionUID = 1L;
	private JPanel frameContentPane = null;// 内容面板
	private MenuBar frameMenuBar = null;// 菜单栏
//	private ToolBar toolBar = null;// 工具栏
	private DesktopPanel desktopPane = null;// 桌面面板
	private JPanel statePanel = null;// 状态面板
	private JLabel stateLabel = null;// 选定窗体状态标签
	private JLabel nameLabel = null;// “系统名称”标签
	private JLabel nowDateLabel = null;// “当前日期”标签
	private JSeparator jSeparator1 = null;
	private static JLabel czyStateLabel = null;// “操作员”标签
	private JSeparator jSeparator2 = null;
 
	/**
	 * 程序主方法，运行程序的入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		SplashScreen splashScreen = SplashScreen.getSplashScreen();// 创建闪现屏幕对创建闪现屏幕对象
		JFrame login = new LoginInterface();// 登录窗体
		if (splashScreen != null) {// 闪现屏幕对象不为空
			try {
				login.setDefaultCloseOperation(EXIT_ON_CLOSE);// 设置登录窗体的关闭方式
				Thread.sleep(3000);
			} catch (InterruptedException e) {
			}
		}
		login.setVisible(true);// 使登录窗体可见
	}

	/**
	 * 初始化窗体菜单栏的方法
	 * 
	 * @return JMenuBar
	 */
	protected MenuBar getFrameMenuBar() {
		if (frameMenuBar == null) {// 菜单栏对象为空
			frameMenuBar = new MenuBar(getDesktopPane(), getStateLabel());// 创建菜单栏对象
		}
		return frameMenuBar;
	}

	/**
	 * 初始化桌面面板的方法
	 * 
	 * @return JDesktopPane
	 */
	private DesktopPanel getDesktopPane() {
		if (desktopPane == null) {// 桌面面板对象为空
			desktopPane = new DesktopPanel();// 创建桌面面板对象
		}
		return desktopPane;
	}

	/**
	 * 初始化状态面板的方法
	 * 
	 * @return JPanel
	 */
	private JPanel getStatePanel() {
		if (statePanel == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 3;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 6;
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 5;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints11.gridy = 0;
			nowDateLabel = new JLabel();// “当前日期”标签
			Date now = new Date();// 创建Date对象
			nowDateLabel.setText(String.format("%tF", now));
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 7;
			gridBagConstraints2.weightx = 0.0;
			gridBagConstraints2.fill = GridBagConstraints.NONE;
			gridBagConstraints2.gridy = 0;
			nameLabel = new JLabel("药品进销存管理系统   ");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 4;
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridy = 0;
			statePanel = new JPanel();// 状态面板
			statePanel.setLayout(new GridBagLayout());
			statePanel.setBorder(BorderFactory.createBevelBorder(RAISED));
			statePanel.add(getStateLabel(), gridBagConstraints);
			statePanel.add(getJSeparator(), gridBagConstraints1);
			statePanel.add(nameLabel, gridBagConstraints2);
			statePanel.add(getJSeparator1(), gridBagConstraints3);
			statePanel.add(nowDateLabel, gridBagConstraints11);
			statePanel.add(getCzyStateLabel(), gridBagConstraints4);
			statePanel.add(getJSeparator2(), gridBagConstraints6);
		}
		return statePanel;
	}

	public static JLabel getCzyStateLabel() {// 获得“操作员”标签的方法
		if (czyStateLabel == null) {
			czyStateLabel = new JLabel("操作员：");
		}
		return czyStateLabel;
	}

	public JLabel getStateLabel() {// 获得选定窗体状态标签
		if (stateLabel == null) {
			stateLabel = new JLabel();
			
		}
		return stateLabel;
	}

	private JSeparator getJSeparator() {
		JSeparator jSeparator = new JSeparator();
		jSeparator.setOrientation(JSeparator.VERTICAL);
		return jSeparator;
	}

	private JSeparator getJSeparator1() {
		if (jSeparator1 == null) {
			jSeparator1 = new JSeparator();
			jSeparator1.setOrientation(SwingConstants.VERTICAL);
		}
		return jSeparator1;
	}

	private JSeparator getJSeparator2() {
		if (jSeparator2 == null) {
			jSeparator2 = new JSeparator();
			jSeparator2.setOrientation(SwingConstants.VERTICAL);
		}
		return jSeparator2;
	}

	public MainFrame() {
		super(); 
		initialize();
	}

	private void initialize() {// 初始化主窗体
		this.setSize(1000, 800);
		this.setJMenuBar(getFrameMenuBar());
		this.setContentPane(getFrameContentPane());
		this.setTitle("药品进销存管理系统");
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        //设置窗口居中
        this.setLocation(screenWidth/2-1000/2, screenHeight/2-800/2);//设置窗口居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private JPanel getFrameContentPane() {// 获得内容面板
		if (frameContentPane == null) {
			frameContentPane = new JPanel();// 创建内容面板
			frameContentPane.setLayout(new BorderLayout());
			frameContentPane.add(getStatePanel(), SOUTH);
			frameContentPane.add(getDesktopPane(), CENTER);
		}
		return frameContentPane;
	}

}
