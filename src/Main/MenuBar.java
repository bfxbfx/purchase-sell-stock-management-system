package Main;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import jframe.*;

public class MenuBar extends JMenuBar {// 菜单栏

	/**
	 * （进货管理）菜单
	 */
	private JMenu jinhuo_Menu = null;

	/**
	 * （进货单）菜单项，位于（进货管理）菜单内
	 */
	private JMenuItem jinhuoItem = null;

	/**
	 * （进货退货）菜单项，位于（进货管理）菜单内
	 */
	private JMenuItem jinhuo_tuihuoItem = null;

	/**
	 * （销售管理）菜单
	 */
	private JMenu xiaoshou_Menu = null;

	/**
	 * （库存管理）菜单
	 */
	private JMenu kucun_Menu = null;

	/**
	 * （信息查询）菜单
	 */
	private JMenu xinxi_chaxunMenu = null;

	/**
	 * （基本资料）菜单
	 */
	private JMenu jiben_ziliaoMenu = null;



	/**
	 * （窗口）菜单
	 */
	private JMenu chuang_kouMenu = null;



	
	/**
	 * （销售单）菜单项，位于（销售管理）菜单内
	 */
	private JMenuItem xiaoshou_danItem = null;

	/**
	 * （销售退货）菜单项，位于（销售管理）菜单内
	 */
	private JMenuItem xiaoshou_tuihuoItem = null;

	/**
	 * （库存盘点）菜单项，位于（库存管理）菜单内
	 */
	private JMenuItem kucun_pandianItem = null;

	/**
	 * （价格调整）菜单项，位于（库存管理）菜单内
	 */
	private JMenuItem chukuItem = null;

	/**
	 * （销售查询）菜单项，位于（信息查询）菜单内
	 */
	private JMenuItem xiaoshou_chaxunItem = null;

	/**
	 * （商品查询）菜单项，位于（信息查询）菜单内
	 */
	private JMenuItem shangpin_chaxunItem = null;

	
	/**
	 * （商品管理）菜单项，位于（基本资料）菜单内
	 */
	private JMenuItem shangpin_guanliItem = null;

	/**
	 * （客户管理）菜单项，位于（基本资料）菜单内
	 */
	private JMenuItem kehu_guanliItem = null;

	/**
	 * （供应商管理）菜单项，位于（基本资料）菜单内
	 */
	private JMenuItem gys_guanliItem = null;
	
	//发货菜单
	private JMenu Send_Menu = null;

	private JMenuItem SendItem = null;
	
	private JMenuItem SendQueryItem = null;
	
	//采购菜单 
	private JMenu Buy_Menu = null;

	private JMenuItem BuyItem = null;
	
	private JMenuItem BuyQueryItem = null;
	
	private JMenuItem PlanBuyItem = null;
	
	//入库和出库
	private JMenu In_Menu = null;
	private JMenuItem InItem = null;
	private JMenuItem InQueryItem = null;
	private JMenuItem OutItem = null;
	private JMenuItem OutQueryItem = null;
	
	private JMenu Out_Menu = null;


	
	/**
	 * （退出）菜单项，位于（系统维护）菜单内
	 */
	private JMenuItem exitItem = null;

	/**
	 * （窗口平铺）菜单项，位于（窗口）菜单内
	 */
	private JMenuItem pingpuItem = null;

	/**
	 * 容纳内部窗体的桌面面板
	 */
	private JDesktopPane desktopPanel = null;

	/**
	 * 内部窗体的集合
	 */
	private Map<JMenuItem, JInternalFrame> iFrames = null;

	/**
	 * 内部窗体的位置坐标
	 */
	private int nextFrameX, nextFrameY;

	
	/**
	 * 状态栏的内部窗体提示标签
	 */
	private JLabel stateLabel = null;

	/**
	 * 默认的构造方法
	 * 
	 */
	private MenuBar() {
	}

	public MenuBar(JDesktopPane desktopPanel, JLabel label) {
		super();
		iFrames = new HashMap<JMenuItem, JInternalFrame>();
		this.desktopPanel = desktopPanel;
		this.stateLabel = label;
		initialize();
	}

	/**
	 * 初始化菜单栏界面的方法
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(600, 24));
		add(getBuy_Menu());
		add(getIn_Menu());
		add(getOut_Menu());
		add(getXiaoshou_Menu());
		add(getSend_Menu());
		add(getKucun_Menu());
		add(getXinxi_chaxunMenu());
		//add(getJiben_ziliaoMenu());
		
		
	}


	//采购
	public JMenu getBuy_Menu() {
		if (Buy_Menu == null) {
			Buy_Menu = new JMenu();
			if(!Global.userID.equals("buy"))Buy_Menu.setVisible(false);
			Buy_Menu.setText("采购管理");
			
			Buy_Menu.add(getBuyItem());
			Buy_Menu.add(getPlanBuyItem());
			Buy_Menu.add(getBuyQueryItem());
			
		}
		return Buy_Menu;
	}

	
	public JMenuItem getBuyItem() {
		if (BuyItem == null) {
			BuyItem = new JMenuItem();
			BuyItem.setText("采购单");
			BuyItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			BuyItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Global.planbuylist=null;
					createIFrame(BuyItem, BuyBill.class);
				}
			});
		}
		return BuyItem;
	}
	
	public JMenuItem getBuyQueryItem() {
		if (BuyQueryItem == null) {
			BuyQueryItem = new JMenuItem();
			BuyQueryItem.setText("采购单查询");
			BuyQueryItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			BuyQueryItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(BuyQueryItem, BuyQuery.class);
				}
			});
		}
		return BuyQueryItem;
	}
	
	public JMenuItem getPlanBuyItem() {
		if (PlanBuyItem == null) {
			PlanBuyItem = new JMenuItem();
			PlanBuyItem.setText("采购计划单查询");
			PlanBuyItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			PlanBuyItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(PlanBuyItem, PlanBuyMain.class);
				}
			});
		}
		return PlanBuyItem;
	}
	
	public JMenu getIn_Menu() {
		if (In_Menu == null) {
			In_Menu = new JMenu();
			if(!Global.userID.equals("stock"))In_Menu.setVisible(false);
			In_Menu.setText("入库管理");
			
			In_Menu.add(getInItem());
			In_Menu.add(getInQueryItem());
			
		}
		return In_Menu;
	}
	
	public JMenu getOut_Menu() {
		if (Out_Menu == null) {
			Out_Menu = new JMenu();
			if(!Global.userID.equals("stock"))Out_Menu.setVisible(false);
			Out_Menu.setText("出库管理");
			
			Out_Menu.add(getOutItem());
			Out_Menu.add(getOutQueryItem());
			
		}
		return Out_Menu;
	}
	
	/**
	 * 初始化（进货单）菜单项的方法 该方法定义菜单项打开进货单窗口,并使窗口处于被选择状态
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getInItem() {
		if (InItem == null) {
			InItem = new JMenuItem();
			InItem.setText("药品入库");
			InItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			InItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(InItem, StockInMain.class);
				}
			});
		}
		return InItem;
	}
	
	public JMenuItem getInQueryItem() {
		if (InQueryItem == null) {
			InQueryItem = new JMenuItem();
			InQueryItem.setText("入库单查询");
			InQueryItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			InQueryItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(InQueryItem, InQuery.class);
				}
			});
		}
		return InQueryItem;
	}
	
	public JMenuItem getOutQueryItem() {
		if (OutQueryItem == null) {
			OutQueryItem = new JMenuItem();
			OutQueryItem.setText("出库单查询");
			OutQueryItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			OutQueryItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(OutQueryItem, OutQuery.class);
				}
			});
		}
		return OutQueryItem;
	}

	

	/**
	 * 初始化（销售管理）菜单的方法，该方法定义菜单项打开内部窗体，并使窗体处于已选择状态。
	 * 
	 * @return javax.swing.JMenu
	 */
	public JMenu getXiaoshou_Menu() {
		if (xiaoshou_Menu == null) {
			xiaoshou_Menu = new JMenu();
			
			if(!Global.userID.equals("sale"))xiaoshou_Menu.setVisible(false);
			xiaoshou_Menu.setText("销售管理");
			xiaoshou_Menu.add(getXiaoshou_danItem());
			xiaoshou_Menu.add(getXiaoshou_chaxunItem());
			
		}
		return xiaoshou_Menu;
	}
	
	public JMenu getSend_Menu() {
		if (Send_Menu == null) {
			Send_Menu = new JMenu();
			
			if(!Global.userID.equals("sale"))Send_Menu.setVisible(false);
			Send_Menu.setText("发货管理");
			Send_Menu.add(getSendItem());
			Send_Menu.add(getSendQueryItem());
			
		}
		return Send_Menu;
	}
	


	/**
	 * 初始化（库存管理）菜单的方法
	 * 
	 * @return javax.swing.JMenu
	 */
	public JMenu getKucun_Menu() {
		if (kucun_Menu == null) {
			kucun_Menu = new JMenu();
			if(!Global.userID.equals("stock"))kucun_Menu.setVisible(false);
			kucun_Menu.setText("库存管理");
			
			kucun_Menu.add(getKucun_pandianItem());
		}
		return kucun_Menu;
	}

	/**
	 * 初始化（信息查询）菜单的方法
	 * 
	 * @return javax.swing.JMenu
	 */
	public JMenu getXinxi_chaxunMenu() {
		if (xinxi_chaxunMenu == null) {
			xinxi_chaxunMenu = new JMenu();
			xinxi_chaxunMenu.setText("信息查询");
			
			xinxi_chaxunMenu.add(getYaopin_chaxunItem());
			xinxi_chaxunMenu.addSeparator();
		}
		return xinxi_chaxunMenu;
	}




	
	
	

	
	/**
	 * 初始化（销售单）菜单项的方法
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getXiaoshou_danItem() {
		if (xiaoshou_danItem == null) {
			xiaoshou_danItem = new JMenuItem();
			xiaoshou_danItem.setText("销售单");
			xiaoshou_danItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			xiaoshou_danItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					createIFrame(xiaoshou_danItem, SaleBill.class);
				}
			});
		}
		return xiaoshou_danItem;
	}


	/**
	 * 初始化（库存盘点）菜单项的方法
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getKucun_pandianItem() {
		if (kucun_pandianItem == null) {
			kucun_pandianItem = new JMenuItem();
			kucun_pandianItem.setText("库存盘点");
			kucun_pandianItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			kucun_pandianItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Global.flag=2;
					createIFrame(kucun_pandianItem, YpQuery.class);
				}
			});
		}
		return kucun_pandianItem;
	}

	/**
	 * 初始化（价格调整）菜单项的方法
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getOutItem() {
		if (OutItem == null) {
			OutItem = new JMenuItem();
			OutItem.setText("药品出库");
			OutItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			OutItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					createIFrame(OutItem, StockOutMain.class);
				}
			});
		}
		return OutItem;
	}

	/**
	 * 初始化（销售查询）菜单项的方法
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getXiaoshou_chaxunItem() {
		if (xiaoshou_chaxunItem == null) {
			xiaoshou_chaxunItem = new JMenuItem();
			xiaoshou_chaxunItem.setText("销售查询");
			xiaoshou_chaxunItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			xiaoshou_chaxunItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					createIFrame(xiaoshou_chaxunItem, SaleQuery.class);
				} 
			});
		}
		return xiaoshou_chaxunItem;
	}

	/**
	 * 初始化（商品查询）菜单项的方法

	 */
	public JMenuItem getYaopin_chaxunItem() {
		if (shangpin_chaxunItem == null) {
			shangpin_chaxunItem = new JMenuItem();
			shangpin_chaxunItem.setText("药品查询");
			shangpin_chaxunItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			shangpin_chaxunItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Global.flag=1;
					createIFrame(shangpin_chaxunItem, YpQuery.class);
				}
			});
		}
		return shangpin_chaxunItem;
	}

	

	/**
	 * 初始化（商品管理）菜单项的方法
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getShangpin_guanliItem() {
		if (shangpin_guanliItem == null) {
			shangpin_guanliItem = new JMenuItem();
			shangpin_guanliItem.setText("商品资料管理");
			shangpin_guanliItem.setIcon(new ImageIcon(getClass().getResource("/res/icon/shangpin_guanli.png")));
			shangpin_guanliItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//createIFrame(shangpin_guanliItem, ShangPinGuanLi.class);
				}
			});
		}
		return shangpin_guanliItem;
	}

	/**
	 * 初始化（客户资料管理）菜单项的方法

	 */
	public JMenuItem getKehu_guanliItem() {
		if (kehu_guanliItem == null) {
			kehu_guanliItem = new JMenuItem();
			kehu_guanliItem.setText("客户资料管理");
			//kehu_guanliItem.setIcon(new ImageIcon(getClass().getResource("/res/icon/kehu_guanli.png")));
			kehu_guanliItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//createIFrame(kehu_guanliItem, KeHuGuanLi.class);
				}
			});
		}
		return kehu_guanliItem;
	}

	/**
	 * 初始化（供应商管理）菜单项的方法

	 */
	public JMenuItem getGys_guanliItem() {
		if (gys_guanliItem == null) {
			gys_guanliItem = new JMenuItem();
			gys_guanliItem.setText("供应商资料管理");
			//gys_guanliItem.setIcon(new ImageIcon(getClass().getResource("/res/icon/gys_guanli.png")));
			gys_guanliItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//createIFrame(gys_guanliItem, GysGuanLi.class);
				}
			});
		}
		return gys_guanliItem;
	}

//发货与发货单查询
	public JMenuItem getSendItem() {
		if (SendItem == null) {
			SendItem = new JMenuItem();
			SendItem.setText("发货");
			SendItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			SendItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					createIFrame(SendItem, SendMain.class);
				}
			});
		}
		return SendItem;
	}
	
	public JMenuItem getSendQueryItem() {
		if (SendQueryItem == null) {
			SendQueryItem = new JMenuItem();
			SendQueryItem.setText("发货单查询");
			SendQueryItem.setIcon(new ImageIcon(getClass().getResource("/res/itemicon.png")));
			SendQueryItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					createIFrame(SendQueryItem, SendQuery.class);
				}
			});
		}
		return SendQueryItem;
	}

	

	/**
	 * 初始化（退出系统）菜单项的方法
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getExitItem() {
		if (exitItem == null) {
			exitItem = new JMenuItem();
			exitItem.setText("退出系统");
			
			exitItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitItem;
	}

	/**
	 * 初始化（窗口平铺）菜单项的方法
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getPingpuItem() {
		if (pingpuItem == null) {
			pingpuItem = new JMenuItem();
			pingpuItem.setText("窗口层叠");
			pingpuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JInternalFrame[] allFrames = desktopPanel.getAllFrames();
					int x = 0, y = 0;
					for (JInternalFrame iFrame : allFrames) {
						iFrame.setLocation(x, y);
						try {
							iFrame.setSelected(true);
						} catch (PropertyVetoException e1) {
							e1.printStackTrace();
						}
						int frameH = iFrame.getPreferredSize().height;
						int panelH = iFrame.getContentPane().getPreferredSize().height;
						int fSpacing = frameH - panelH;
						x += fSpacing;
						y += fSpacing;
						if (x + getWidth() / 2 > desktopPanel.getWidth())
							x = 0;
						if (y + getHeight() / 2 > desktopPanel.getHeight())
							y = 0;
					}
				}
			});
		}
		return pingpuItem;
	}

	/**
	 * 创建内部窗体的方法，该方法使用反射技术获取内部窗体的构造方法，从而创建内部窗体。
	 * 
	 * @param item：激活该内部窗体的菜单项
	 * @param clazz：内部窗体的Class对象
	 */
	private JInternalFrame createIFrame(JMenuItem item, Class clazz) {
		Constructor constructor = clazz.getConstructors()[0];
		JInternalFrame iFrame = iFrames.get(item);
		try {
			if (iFrame == null || iFrame.isClosed()) {
				iFrame = (JInternalFrame) constructor.newInstance(new Object[] {});
				iFrames.put(item, iFrame);
				iFrame.setFrameIcon(item.getIcon());
				iFrame.setLocation(nextFrameX, nextFrameY);
				int frameH = iFrame.getPreferredSize().height;
				int panelH = iFrame.getContentPane().getPreferredSize().height;
				int fSpacing = frameH - panelH;
				nextFrameX += fSpacing;
				nextFrameY += fSpacing;
				if (nextFrameX + iFrame.getWidth() > desktopPanel.getWidth())
					nextFrameX = 0;
				if (nextFrameY + iFrame.getHeight() > desktopPanel.getHeight())
					nextFrameY = 0;
				desktopPanel.add(iFrame);
				iFrame.setResizable(false);
				iFrame.setMaximizable(false);
				iFrame.setVisible(true);
			}
			iFrame.setSelected(true); 
			stateLabel.setText(iFrame.getTitle());
			iFrame.addInternalFrameListener(new InternalFrameAdapter() {
				public void internalFrameActivated(InternalFrameEvent e) {
					super.internalFrameActivated(e);
					JInternalFrame frame = e.getInternalFrame();
					stateLabel.setText(frame.getTitle());
				}

				public void internalFrameDeactivated(InternalFrameEvent e) {
					stateLabel.setText(" ");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iFrame;
	}

	
	





}
