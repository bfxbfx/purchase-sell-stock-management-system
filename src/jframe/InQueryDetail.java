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
import Main.*;
import dao.Dao;
import tbdao.*;

public class InQueryDetail extends JFrame {// 销售单内部窗体

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
	

	public InQueryDetail() {// 销售单内部窗体的构造方法
		super();// 调用父类JInternalFrame的构造器
//		setMaximizable(true);// 可以最大化
//		setIconifiable(true);// 可以图标化
//		setClosable(true);// 可以关闭销售单内部窗体
		getContentPane().setLayout(new GridBagLayout());// 设置销售单内部窗体的布局为网格布局
		getContentPane().setBackground(new Color(255,245,238));
		
		setTitle("入库单据详情");// 设置销售单内部窗体的标题
		setBounds(0, 0, 700, 350);// 设置销售单内部窗体的位置和宽高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
		this.setLocation(screenWidth/2-700/2, screenHeight/2-350/2);//设置窗口居中显示
		// “销售票号”标签和“销售票号”文本框


		setupComponet(new JLabel("入库单号："), 0,0,1,0,false);
		
		setupComponet(new JLabel(Global.xs_number), 1, 0, 1, 80, true);
		// “客户”标签和“客户”下拉列表
		setupComponet(new JLabel("               供应商："), 2, 0, 1, 0, false);
		setupComponet(new JLabel(Global.xs_KHname), 3, 0, 1, 80, true);
		
		
		// “结算方式”标签和“结算方式”下拉列表
		setupComponet(new JLabel("结算方式："), 0, 1, 1, 0, false);
		setupComponet(new JLabel(Global.xs_jsfs), 1, 1, 1, 1, true);

		// “销售时间”标签和“销售时间”文本框
		setupComponet(new JLabel("               入库时间："), 2, 1, 1, 0, false);
		setupComponet(new JLabel(Global.xs_date), 3, 1, 1, 1, true);
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
		
		// “合计金额”标签和“合计金额”文本框
		setupComponet(new JLabel("合计金额："), 0, 4, 1, 0, false);
		setupComponet(new JLabel(Global.xs_jr), 1, 4, 1, 1, true);
		
		// “操作人员”标签和“操作人员”文本框
		setupComponet(new JLabel("               操作人员："), 2, 4, 1, 0, false);
		
		setupComponet(new JLabel(Global.xs_czy), 3, 4, 1, 1, true);

		

		//JOptionPane.showMessageDialog(null, "友情提示"); 
		// 添加窗体监听器，完成初始化
		//addInternalFrameListener(new initTasks());
		

	}


	// 初始化表格
	private void initTable() {
		String[] columnNames = {  "药品编号", "药品名称",  "规格","单位", "数量","生产厂家","进价" };
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
	private final class initTasks extends InternalFrameAdapter {
		public void internalFrameActivated(InternalFrameEvent e) {
			super.internalFrameActivated(e);
			
		}

		

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
			ypInfo = Dao.getRKInfo(item,Global.xs_number);
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
	String sql = "select * from tb_in_detail where ";
	
			
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
