package jframe;

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import Main.Global;
import dao.Dao;
import tbdao.TbJsr;


public class SaleQuery extends JInternalFrame{// 销售查询内部窗体
	
	private JButton queryButton;// “查询”按钮
	private JTextField endDate;// “终止日期”文本框
	private JTextField startDate;// “起始日期”文本框
	private JTable table;// 表格模型
	private JTextField content;// “条件内容”文本框
	private JComboBox operation;// “条件运算”下拉列表
	private JComboBox condition;// “条件名称”下拉列表
	private TbJsr user;// 经手人信息
	private DefaultTableModel dftm;// 表格模型的实例化对象 
	private JCheckBox selectDate;// “指定查询日期”复选框
	

	
	public SaleQuery() {// 销售查询内部窗体的构造方法
		// 打开销售查询内部窗体时，设置“起始日期”和“终止日期”
		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameActivated(final InternalFrameEvent e) {
				//Calendar cd = Calendar.getInstance();
				
				java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
				endDate.setText(date.toString());
//				date.setMonth(0);
//				date.setDate(1);
				startDate.setText(date.toString());
			}
		});
		setIconifiable(true);// 可以图标化
		setClosable(true);// 可以关闭
		setTitle("销售信息查询");// 设置销售查询内部窗体的标题
		getContentPane().setLayout(new GridBagLayout());// 设置销售查询内部窗体的布局是网格布局
		setBounds(0, 0, 800, 600);// 设置销售查询内部窗体的位置和宽高 
		// “选择查询条件”标签和“条件名称”下拉列表
		setupComponet(new JLabel(" 选择查询条件："), 0, 0, 1, 1, false);
		condition = new JComboBox();
		condition.setModel(new DefaultComboBoxModel(new String[] {"客户全称", "销售票号", "经手人", "金额不高于", "金额不低于"}));
		setupComponet(condition, 1, 0, 1, 10, true);
		
		// “条件内容”文本框
		content = new JTextField();
		content.addKeyListener(new KeyAdapter() {
			public void keyReleased(final KeyEvent e) {
				if(e.getKeyCode()==10) {
					queryButton.doClick();
				}
			}
		});
		setupComponet(content, 2, 0, 1, 100, true);
		// “查询”按钮
		queryButton = new JButton();
		queryButton.addActionListener(new QueryAction());
		setupComponet(queryButton, 1, 3, 1, 1, false);
		queryButton.setText("  查询  ");
		// “指定查询日期”复选框
		selectDate = new JCheckBox();
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.ipadx = 10;
		gridBagConstraints_7.anchor = GridBagConstraints.EAST;
		gridBagConstraints_7.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 0;
		
		getContentPane().add(selectDate, gridBagConstraints_7);
		// “指定查询日期    从”标签
		final JLabel label_1 = new JLabel();
		label_1.setText("是否指定查询日期：     从");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();

		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 1;
		getContentPane().add(label_1, gridBagConstraints_8);
		// “起始日期”文本框
		startDate = new JTextField();
		startDate.setPreferredSize(new Dimension(100,21));
		setupComponet(startDate, 2, 1, 1, 1, true);
		// “到”文本框
		final JLabel label_dao = new JLabel();
		label_dao.setText("到      ");
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.anchor = GridBagConstraints.EAST;
		gridBagConstraints_9.gridy = 2;
		gridBagConstraints_9.gridx = 1;
		getContentPane().add(label_dao, gridBagConstraints_9);
		// “终止日期”文本框
		endDate = new JTextField();
		endDate.setPreferredSize(new Dimension(100,21));
		setupComponet(endDate, 2, 2, 1, 1, true);
		
		
		// “为了美观的空字符文本框”
		final JLabel label_eee = new JLabel();
		label_eee.setText("                ");
		final GridBagConstraints gridBagConstraints_e = new GridBagConstraints();
		gridBagConstraints_e.anchor = GridBagConstraints.EAST;
		gridBagConstraints_e.gridy = 2;
		gridBagConstraints_e.gridx = 4;
		getContentPane().add(label_eee, gridBagConstraints_e);
		
		// “为了美观的空字符文本框”
		final JLabel label_qqq = new JLabel();
		label_qqq.setText("                ");
		final GridBagConstraints gridBagConstraints_q = new GridBagConstraints();
		gridBagConstraints_q.anchor = GridBagConstraints.EAST;
		gridBagConstraints_q.gridy = 2;
		gridBagConstraints_q.gridx = 5;
		getContentPane().add(label_qqq, gridBagConstraints_q);
		// “显示全部数据”按钮
		final JButton showAllButton = new JButton();
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				content.setText("");
				List list=Dao.findForList("select * from tb_sale");
				Iterator iterator=list.iterator();
				updateTable(iterator);
			}
		});
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(0, 0, 0, 10);
		gridBagConstraints_5.gridy = 3;
		gridBagConstraints_5.gridx = 2;
		getContentPane().add(showAllButton, gridBagConstraints_5);
		showAllButton.setFont(new Font("", Font.PLAIN, 12));
		showAllButton.setText("显示全部销售单");
		// 滚动面板
		final JScrollPane scrollPane = new JScrollPane();
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_6.insets = new Insets(0, 10, 5, 10);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 9;
		gridBagConstraints_6.gridy = 4;
		gridBagConstraints_6.gridx = 0;
		getContentPane().add(scrollPane, gridBagConstraints_6);
		// 表格模型
		table = new JTable();
		table.setEnabled(false);
		//table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		dftm = (DefaultTableModel) table.getModel();
		String[] tableHeads = new String[]{"   销售票号", "   客户全称", "      销售日期","    金额","  经手人", "   结算方式","   操作员","   状态"};
		dftm.setColumnIdentifiers(tableHeads);
		TableColumn column = table.getColumnModel().getColumn(2);//0是代表的第一列
		column.setPreferredWidth(130);
		
		column = table.getColumnModel().getColumn(7);//0是代表的第一列
		column.setPreferredWidth(100);
		
		column = table.getColumnModel().getColumn(4);//0是代表的第一列
		column.setPreferredWidth(50);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();   
		
		r.setHorizontalAlignment(JLabel.CENTER);   //表格数据居中
		table.setDefaultRenderer(Object.class, r);
		scrollPane.setViewportView(table);
		
		//双击监听器
		table.addMouseListener(new MouseAdapter(){ 

		      public void mouseClicked(MouseEvent e) { 
		    	  if(e.getClickCount() == 2) {
		    		   int row = ((JTable)e.getSource()).rowAtPoint(e.getPoint()); //获得行位置 
		    	  		//content.setText(column); 
		    	  		Global.xs_number=(String) table.getValueAt(row, 0);
		    	  		Global.xs_KHname=(String) table.getValueAt(row, 1);
		    	  		Global.xs_date=(String) table.getValueAt(row, 2);
		    	  		Global.xs_jr=(String) table.getValueAt(row, 3);
		    	  		Global.xs_jsr=(String) table.getValueAt(row, 4);
		    	  		Global.xs_jsfs=(String) table.getValueAt(row, 5);
		    	  		Global.xs_czy=(String) table.getValueAt(row, 6);
		    	  		
		    	  		JFrame inFrm = new SaleDetail(); 
		    	  		inFrm.setVisible(true);
		    	  		
		    	  		//this.add(XiaoShouXiangQin.class);
		    	  		 
		    	  }

		    	  else return; 
		      }} );
	}
	// 更新表格数据
	private void updateTable(Iterator iterator) {
		int rowCount=dftm.getRowCount();
		for(int i=0;i<rowCount;i++) {
			dftm.removeRow(0);
		}
		while(iterator.hasNext()) {
			Vector vector=new Vector();
			List view=(List) iterator.next();
			vector.addAll(view);
			dftm.addRow(vector);
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
		gridBagConstrains.insets = new Insets(5, 5, 5,5);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}
	
	
	// 条件查询
	private final class QueryAction implements ActionListener {
		public void actionPerformed(final ActionEvent e) {
			boolean selDate = selectDate.isSelected();
			if(content.getText().equals("")) {
				JOptionPane.showMessageDialog(getContentPane(), "请输入查询内容！");
				return;
			}
			if(selDate) {
				if(startDate.getText()==null||startDate.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "请输入查询的开始日期！");
					return;
				}
				if(endDate.getText()==null||endDate.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "请输入查询的结束日期！");
					return;
				}
			}
			List list=null;// 结果集
			String con = null;
			if(condition.getSelectedIndex() == 0)con ="khname ";
			else if(condition.getSelectedIndex() == 1)con ="sellID ";
			else if(condition.getSelectedIndex() == 2)con ="jsr ";

			String opstr = " = " ;
			if(condition.getSelectedIndex() == 3) {
				con="je ";
				opstr = " <= " ;
			}
			if(condition.getSelectedIndex() == 4) {
				con="je ";
				opstr = " >= " ;
			}
			String cont = content.getText();
			list = Dao.findForList("select * from tb_sale where "
					+ con
					+ opstr
					+ "'"+cont+"'"
					+ (selDate ? " and xsdate>'" + startDate.getText()
							+ "' and xsdate<='" + endDate.getText()+" 23:59:59'" : ""));// 执行拼接的SQL语句后获得的结果集
			Iterator iterator = list.iterator();// 与结果集list相应的迭代器
			updateTable(iterator);
		}
	}

}