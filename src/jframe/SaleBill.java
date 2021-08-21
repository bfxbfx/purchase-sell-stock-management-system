package jframe;

import java.awt.*;
import java.awt.desktop.AboutEvent;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import Main.Item;
import tbdao.TbKhinfo;

import Main.*;
import dao.Dao;
import tbdao.*;

public class SaleBill extends JInternalFrame {// 销售单内部窗体

	private final JTable table;// 表格模型
	private final JTextField sellDate = new JTextField();// “销售时间”文本框
	private JComboBox jsr = null;// “经手人”下拉列表
	private final JComboBox jsfs = new JComboBox();// “结算方式”下拉列表
	
	private final JComboBox kehu = new JComboBox();// “客户”下拉列表
	private final JTextField piaoHao = new JTextField();// “销售票号”文本框
	
	private final JTextField hjje = new JTextField("0");// “合计金额”文本框
	private final JTextField czy = new JTextField();// “操作员”文本框
	private Date xssjDate;// “销售日期”
	private JComboBox sp;// “商品”下拉列表

	public SaleBill() {// 销售单内部窗体的构造方法
		super();// 调用父类JInternalFrame的构造器
		setMaximizable(true);// 可以最大化
		setIconifiable(true);// 可以图标化
		setClosable(true);// 可以关闭销售单内部窗体
		getContentPane().setLayout(new GridBagLayout());// 设置销售单内部窗体的布局为网格布局 
		getContentPane().setBackground(new Color(212,234,252));
		setTitle("销售单");// 设置销售单内部窗体的标题
		setBounds(0, 0, 800, 600);// 设置销售单内部窗体的位置和宽高
		// “销售票号”标签和“销售票号”文本框


		setupComponet(new JLabel("销售票号："), 0,0,1,0,false);
		
		piaoHao.setFocusable(false);
		setupComponet(piaoHao, 1, 0, 1, 80, true);
		// “客户”标签和“客户”下拉列表
		setupComponet(new JLabel("               客户："), 2, 0, 1, 0, false);
		kehu.setPreferredSize(new Dimension(100, 21));
		kehu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doKhSelectAction();// “客户”下拉列表的选择事件
			}
		});
		setupComponet(kehu, 3, 0, 1, 80, true);
		
		// “结算方式”标签和“结算方式”下拉列表
		setupComponet(new JLabel("结算方式："), 0, 1, 1, 0, false);
		jsfs.addItem("现金");
		jsfs.addItem("支票");
		jsfs.addItem("移动支付");
		jsfs.setEditable(true);
		setupComponet(jsfs, 1, 1, 1, 1, true);
		// “销售时间”标签和“销售时间”文本框
		setupComponet(new JLabel("               销售时间："), 2, 1, 1, 0, false);
		sellDate.setFocusable(false);
		setupComponet(sellDate, 3, 1, 1, 1, true);
		// “经手人”标签和“经手人”下拉列表
		setupComponet(new JLabel("经手人："+Global.username), 4, 1, 1, 0, false);
		// “商品”下拉列表
		sp = new JComboBox();
		sp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TbYpinfo info = (TbYpinfo) sp.getSelectedItem();
				if (info != null && info.getYpid() != null) {
					updateTable();// 如果选择有效就更新表格
				}
			}
		});
		// 表格模型
		table = new JTable();
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 不自动调整列的宽度，使用滚动条
		initTable();
		// 添加事件完成品种数量、货品总数、合计金额的计算
		table.addContainerListener(new computeInfo());
		JScrollPane scrollPanel = new JScrollPane(table);
		scrollPanel.setPreferredSize(new Dimension(380, 200));
		setupComponet(scrollPanel, 0, 2, 6, 1, true);
		
		// “合计金额”标签和“合计金额”文本框
		setupComponet(new JLabel("合计金额："), 0, 4, 1, 0, false);
		hjje.setFocusable(false);
		setupComponet(hjje, 1, 4, 1, 1, true);
		
		// “操作人员”标签和“操作人员”文本框
		setupComponet(new JLabel("               操作人员："), 2, 4, 1, 0, false);
		czy.setFocusable(false);
		czy.setText(MainFrame.getCzyStateLabel().getText());
		setupComponet(czy, 3, 4, 1, 1, true);

		// “添加”按钮
		JButton tjButton = new JButton("添加药品");
		tjButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 初始化票号
				initPiaoHao();
				// 结束表格中没有编写的单元
				stopTableCellEditing();
				// 如果表格中还包含空行，就再添加新行
				for (int i = 0; i < table.getRowCount(); i++) {
					
					if (table.getValueAt(i, 0) == null)
						return;
				}
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Vector());
			}
		});
		setupComponet(tjButton, 2, 3, 1, 1, false);

		// “销售”按钮
		JButton sellButton = new JButton("销售");
		sellButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopTableCellEditing();// 结束表格中没有编写的单元
				clearEmptyRow();// 清除空行

				String jeStr = hjje.getText();// 合计金额
				String jsfsStr = jsfs.getSelectedItem().toString();// 结算方式
				String jsrStr = Global.username;// 经手人
				String czyStr = czy.getText();// 操作员
				DateFormat ddtf = DateFormat.getDateTimeInstance();  
				xssjDate = new Date();
				//String rkDate = ddtf.format(xssjDae);// 销售时间
				
				DateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //创建一个格式化日期对象
				String punchTime = simpleDateFormat.format(xssjDate);  

				
				String id = piaoHao.getText();// 票号
				String kehuName = kehu.getSelectedItem().toString();// 供应商名字

				if (table.getRowCount() <= 0) {
					JOptionPane.showMessageDialog(SaleBill.this, "填加销售药品");
					return;
				}
				TbSale sellMain = new TbSale(id, jeStr,  kehuName, punchTime, czyStr, jsrStr, jsfsStr);// 销售主表
				Set<TbSaleDetail> set = sellMain.getTbSaleDetails();// 获得销售明细的集合
				int rows = table.getRowCount();
				for (int i = 0; i < rows; i++) {
					//TbYpinfo ypinfo = (TbYpinfo) table.getValueAt(i, 1);
					String ypname=(String)table.getValueAt(i, 0);
					String ypid=(String)table.getValueAt(i, 1);
					String ypplace=(String)table.getValueAt(i, 2);
					String ypunit=(String)table.getValueAt(i, 3);
					String ypspec=(String)table.getValueAt(i, 4);
					String djStr = (String) table.getValueAt(i, 5);
					String slStr = (String) table.getValueAt(i, 6);
					 
					Double dj = Double.valueOf(djStr);
					Integer sl = Integer.valueOf(slStr);
					TbSaleDetail detail = new TbSaleDetail();// 销售明细
					detail.setYpid(ypid);// 流水号
					detail.setTbSellMain(sellMain.getSellId());// 销售主表
					detail.setDj(dj);// 销售单价
					detail.setSl(sl);// 销售数量
					detail.setName(ypname);
					detail.setPlace(ypplace);
					detail.setUnit(ypunit);
					detail.setSpec(ypspec);
					//System.out.print(sl);
					
					set.add(detail);// 把销售明细添加到销售明细的集合中
				}
				boolean rs = Dao.insertSellInfo(sellMain);// 添加销售信息
				if (rs) {
					JOptionPane.showMessageDialog(SaleBill.this, "销售完成");
					DefaultTableModel dftm = new DefaultTableModel();
					table.setModel(dftm);
					initTable();

					hjje.setText("0");
				}
			}
		});
		setupComponet(sellButton, 2, 5, 1, 100, false);
		// 添加窗体监听器，完成初始化
		addInternalFrameListener(new initTasks());
		

	}
	// 初始化表格
	private void initTable() {
		String[] columnNames = { "药品名称", "药品编号", "生产厂家", "单位", "规格", "单价", "数量" };
		((DefaultTableModel) table.getModel()).setColumnIdentifiers(columnNames);
		TableColumn column = table.getColumnModel().getColumn(0);
		final DefaultCellEditor editor = new DefaultCellEditor(sp);
		//editor.setClickCountToStart(2);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
		
		r.setHorizontalAlignment(JLabel.CENTER);   //表格数据居中
		table.setDefaultRenderer(Object.class, r);
		column.setCellEditor(editor);
	}

	// 初始化商品下拉列表
	private void initYpBox() {
		List list = new ArrayList();
		ResultSet set = Dao.query(" select * from tb_ypinfo" + " where id in (select id from tb_ypinfo where quantity>0)");
		sp.removeAllItems();
		sp.addItem(new TbYpinfo());
		for (int i = 0; table != null && i < table.getRowCount(); i++) {
			TbYpinfo tmpInfo = (TbYpinfo) table.getValueAt(i, 0);
			if (tmpInfo != null && tmpInfo.getYpid() != null)
				list.add(tmpInfo.getYpid());
		}
		try {
			while (set.next()) {
				TbYpinfo ypinfo = new TbYpinfo();
				ypinfo.setYpid(set.getString("ypid").trim());
				// 如果表格中以存在同样商品，商品下拉框中就不再包含该商品
				if (list.contains(ypinfo.getYpid()))
					continue;
				ypinfo.setYpname(set.getString("ypname").trim());
				ypinfo.setPlace(set.getString("place").trim());
				ypinfo.setUnit(set.getString("unit").trim());
				ypinfo.setSpec(set.getString("spec").trim());
				ypinfo.setSaleprice(set.getString("saleprice").trim());
				ypinfo.setQuantity(set.getString("quantity").trim());
				ypinfo.setSupplyname(set.getString("supplyname").trim());
				
				sp.addItem(ypinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 设置组件位置并添加到容器中
	private void setupComponet(JComponent component, int gridx, int gridy, int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		gridBagConstrains.weightx=2;
		gridBagConstrains.weighty=2;
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		gridBagConstrains.insets = new Insets(5,5, 5, 5);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}



	// 计算品种数量、货品总数、合计金额
	private final class computeInfo implements ContainerListener {
		public void componentRemoved(ContainerEvent e) {

			// 计算代码
			int rows = table.getRowCount();
			int count = 0;
			double money = 0.0;
			
			
			// 计算货品总数和金额
			for (int i = 0; i < rows; i++) {
				String column6 = (String) table.getValueAt(i, 6);
				String column5 = (String) table.getValueAt(i, 5);
				int c6 = (column6 == null || column6.isEmpty()) ? 0 : Integer.valueOf(column6);
				Double c5 = (column5 == null || column5.isEmpty()) ? 0 : Double.valueOf(column5);
				count += c6;
				money += c5 * c6;
			}
			DecimalFormat df = new DecimalFormat("0.00");
			 df.setRoundingMode(RoundingMode.HALF_UP);
			hjje.setText(df.format(money));
		}

		public void componentAdded(ContainerEvent e) {
		}
	}

	// 窗体的初始化任务
	private final class initTasks extends InternalFrameAdapter {
		public void internalFrameActivated(InternalFrameEvent e) {
			super.internalFrameActivated(e);
			initTimeField();
			initKehuField();
			initPiaoHao();
			initYpBox();
		}
		// 初始化客户字段
		private void initKehuField() {
			List gysInfos = Dao.getKhInfos();
			for (Iterator iter = gysInfos.iterator(); iter.hasNext();) {
				List list = (List) iter.next();
				Item item = new Item();
				item.setId(list.get(0).toString().trim());
				item.setName(list.get(1).toString().trim());
				kehu.addItem(item);
			}
			doKhSelectAction();
		}
		
		// 启动销售时间线程
		private void initTimeField() {
			new Thread(new Runnable() {
				public void run() {
					try {
						while (true) {
							
							DateFormat ddtf = DateFormat.getDateTimeInstance();  
							xssjDate = new Date();
							sellDate.setText(ddtf.format(xssjDate));
							Thread.sleep(100);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	// 初始化“销售票号”
	private void initPiaoHao() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		String maxId = Dao.getSellMainMaxId(date);
		piaoHao.setText(maxId);
	}

	// 根据商品下拉列表，更新表格当前行的内容
	private synchronized void updateTable() {
		TbYpinfo ypinfo = (TbYpinfo) sp.getSelectedItem();
		Item item = new Item();
		item.setId(ypinfo.getYpid());
		TbYpinfo kucun = Dao.getYpInfo(item);
		int row = table.getSelectedRow();
		if (row >= 0 && ypinfo != null) {
			table.setValueAt(ypinfo.getYpid(), row, 1);
			table.setValueAt(ypinfo.getYpname(), row, 0);
			table.setValueAt(ypinfo.getUnit(), row, 3);
			table.setValueAt(ypinfo.getSpec(), row, 4);
			table.setValueAt(ypinfo.getSaleprice() + "", row, 5);
			table.setValueAt(ypinfo.getQuantity() + "", row, 6);
			table.setValueAt(ypinfo.getSupplyname(), row, 2);

			table.editCellAt(row, 6);
		}
	}
	private void doKhSelectAction() {
		Item item = (Item) kehu.getSelectedItem();
		TbKhinfo khInfo = Dao.getKhInfo(item);
		
	}
	// 清除空行
	private synchronized void clearEmptyRow() {
		DefaultTableModel dftm = (DefaultTableModel) table.getModel();
		for (int i = 0; i < table.getRowCount(); i++) {
			String info2 = (String)table.getValueAt(i, 0);
			if (info2 == null || info2 == null || info2.isEmpty()) {
				dftm.removeRow(i);
			}
		}
	}

	// 停止表格单元的编辑
	private void stopTableCellEditing() {
		TableCellEditor cellEditor = table.getCellEditor();
		if (cellEditor != null)
			cellEditor.stopCellEditing();
	}
}
