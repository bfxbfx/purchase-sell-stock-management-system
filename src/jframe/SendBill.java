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

import Main.*;
import dao.Dao;
import jframe.StockOutMain;
import tbdao.*;

public class SendBill extends JFrame {// 销售单内部窗体

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
	

	public SendBill() {// 销售单内部窗体的构造方法
		super();// 调用父类JInternalFrame的构造器
//		setMaximizable(true);// 可以最大化
//		setIconifiable(true);// 可以图标化
//		setClosable(true);// 可以关闭销售单内部窗体
		getContentPane().setLayout(new GridBagLayout());// 设置销售单内部窗体的布局为网格布局
		getContentPane().setBackground(new Color(204,204,204));
		setTitle("发货单");// 设置销售单内部窗体的标题
		setBounds(0, 0, 700, 350);// 设置销售单内部窗体的位置和宽高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
		this.setLocation(screenWidth/2-700/2, screenHeight/2-350/2);//设置窗口居中显示
		// “销售票号”标签和“销售票号”文本框


		setupComponet(new JLabel("发货单号："), 0,0,1,0,false);
		
		setupComponet(piaoHao, 1, 0, 1, 80, true);
		// “客户”标签和“客户”下拉列表
		setupComponet(new JLabel("               客户："), 2, 0, 1, 0, false);
		setupComponet(new JLabel(Global.xs_KHname), 3, 0, 1, 80, true);
		
		
		// “结算方式”标签和“结算方式”下拉列表
		setupComponet(new JLabel("结算方式："), 0, 1, 1, 0, false);
		setupComponet(new JLabel(Global.xs_jsfs), 1, 1, 1, 1, true);

		// “销售时间”标签和“销售时间”文本框
		setupComponet(new JLabel("               发货时间："), 2, 1, 1, 0, false);
		sellDate.setFocusable(false);
		setupComponet(sellDate, 3, 1, 1, 1, true);
		// “经手人”标签和“经手人”下拉列表
		setupComponet(new JLabel("经手人："+Global.xs_jsr), 4, 1, 1, 0, false);
		
		// 表格模型
		table = new JTable();
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);// 不自动调整列的宽度，使用滚动条
		initTable();
		// 添加事件完成品种数量、货品总数、合计金额的计算
		
		JScrollPane scrollPanel = new JScrollPane(table);
		scrollPanel.setPreferredSize(new Dimension(380, 200));
		setupComponet(scrollPanel, 0, 2, 6, 1, true);
		

		
		// “操作人员”标签和“操作人员”文本框
		setupComponet(new JLabel("操作人员："), 0, 4, 1, 0, false);
		
		czy.setFocusable(false);
		czy.setText(MainFrame.getCzyStateLabel().getText());
		setupComponet(czy, 1, 4, 1, 1, true);

		
		// “出库”按钮
		final JButton tjButton = new JButton("   发货   ");
		
		tjButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String jeStr = Global.xs_jr;// 合计金额
				String jsfsStr = Global.xs_jsfs;// 结算方式
				String jsrStr = Global.username;// 经手人
				String czyStr = czy.getText();// 操作员
				DateFormat ddtf = DateFormat.getDateTimeInstance();  
				xssjDate = new Date();
				//String rkDate = ddtf.format(xssjDae);// 销售时间
				
				DateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   //创建一个格式化日期对象
				String punchTime = simpleDateFormat.format(xssjDate);  

				
				String id = piaoHao.getText();// 票号
				String kehuName = Global.xs_KHname;// 供应商名字
				TbSale sellMain = new TbSale(id, jeStr,  kehuName, punchTime, czyStr, jsrStr, jsfsStr);// 销售主表
				Set<TbSaleDetail> set = sellMain.getTbSaleDetails();// 获得销售明细的集合
				int rows = table.getRowCount();
				for (int i = 0; i < rows; i++) {
					//TbYpinfo ypinfo = (TbYpinfo) table.getValueAt(i, 1);
					String ypname=(String)table.getValueAt(i, 1);
					String ypid=(String)table.getValueAt(i, 0);
					String ypplace=(String)table.getValueAt(i, 5);
					String ypunit=(String)table.getValueAt(i, 3);
					String ypspec=(String)table.getValueAt(i, 2);
					String djStr = (String) table.getValueAt(i, 6);
					String slStr = (String) table.getValueAt(i, 4);
					 
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
					
					set.add(detail);// 把销售明细添加到销售明细的集合中
				}
				boolean rs = Dao.insertFHInfo(sellMain);// 添加销售信息
				if (rs) {
					JOptionPane.showMessageDialog(SendBill.this, "发货完成");
					dispose();

					hjje.setText("0");
				}
				
					
				
			}
		});
		setupComponet(tjButton, 2, 4, 1, 1, false);
		
		//JOptionPane.showMessageDialog(null, "友情提示"); 
		// 添加窗体监听器，完成初始化
		//addWindowListener(new initTasks());
		initTasks();
		

	}


	// 初始化表格
	private void initTable() {
		String[] columnNames = {  "药品编号", "药品名称",  "规格","单位", "数量","生产厂家","单价" };
		final DefaultTableModel dftm = (DefaultTableModel) table.getModel();
		dftm.setColumnIdentifiers(columnNames);
		TableColumn column = table.getColumnModel().getColumn(0);
DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
		
		r.setHorizontalAlignment(JLabel.CENTER);   //表格数据居中
		table.setDefaultRenderer(Object.class, r);
		List list =null;
		list = searchInfo(list);
		updateTable(list, dftm);

	}



	// 设置组件位置并添加到容器中
	private void setupComponet(JComponent component, int gridx, int gridy, int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;

		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		gridBagConstrains.insets = new Insets(5,5, 5, 5);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}





	// 窗体的初始化任务
	private void initTasks()  {
		
			
			initTimeField();
			initPiaoHao();
		


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
	
	private void initPiaoHao() {
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		String maxId = Dao.getFHMainMaxId(date);
		piaoHao.setText(maxId);
	}


	// 根据商品下拉列表，更新表格当前行的内容
	private void updateTable(List list, final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++)
			dftm.removeRow(0);
		Iterator iterator = list.iterator();
		TbYpinfo ypInfo;// 商品信息
		while (iterator.hasNext()) {
			List info = (List) iterator.next();
			Item item = new Item();
			item.setId((String) info.get(2));
			item.setName((String) info.get(1));
			ypInfo = Dao.getXSInfo(item,Global.xs_number);
			Vector rowData = new Vector();
			//JOptionPane.showMessageDialog(null, "You input is "+ypInfo.getYpname().trim(), ypInfo.getYpname().trim(), JOptionPane.PLAIN_MESSAGE);
			rowData.add(ypInfo.getYpid().trim());// 商品编号
			rowData.add(ypInfo.getYpname().trim());// 商品名
			rowData.add(ypInfo.getSpec());// 商品规格
			
			rowData.add(ypInfo.getUnit());// 商品计量单位
			
			
			rowData.add(ypInfo.getQuantity());// 数量
			rowData.add(ypInfo.getPlace());// 产地
			rowData.add(ypInfo.getSaleprice());// 单价
			dftm.addRow(rowData);// 向表格对象添加行数据（商品信息）
		}
	}

//获得执行SQL语句后相应的结果集
private List searchInfo(List list) {
	String sql = "select * from tb_sale_detail where ";
	
			
			list = Dao.findForList(sql + "sellID='" + Global.xs_number + "'");
	return list;
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
