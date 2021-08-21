package Login;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

import java.awt.Dimension;

public class LoginPanel extends JPanel {// 登录面板

	public int width, height;
	private Image img;

	public LoginPanel() {
		super();
		URL url = getClass().getResource("/res/loginbackground.jpg");
		img = new ImageIcon(url).getImage();
	}

	protected void paintComponent(Graphics g) {// 重写绘制组件方法
		super.paintComponent(g);
		g.drawImage(img, 0, 0, this);
	}

}
