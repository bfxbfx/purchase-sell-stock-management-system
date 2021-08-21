package dao;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import Main.Global;
import Main.Item;
//import com.lzw.Item;
//import Dao.model.TbGysinfo;
//import com.lzw.dao.model.TbJsr;
//import com.lzw.dao.model.TbKhinfo;
//import com.lzw.dao.model.TbKucun;
//import com.lzw.dao.model.TbRkthDetail;
//import com.lzw.dao.model.TbRkthMain;
//import com.lzw.dao.model.TbRukuDetail;
//import com.lzw.dao.model.TbRukuMain;
//import com.lzw.dao.model.TbSellDetail;
//import com.lzw.dao.model.TbSellMain;
import tbdao.TbYpinfo;
import tbdao.TbKhinfo;
import tbdao.TbSale;
import tbdao.TbSaleDetail;
//import com.lzw.dao.model.TbXsthDetail;
//import com.lzw.dao.model.TbXsthMain;
import Main.MenuBar;


public class Dao {

	protected static String dbClassName = "com.mysql.jdbc.Driver";// MySQL数据库驱动类的名称
	protected static String dbUrl = "jdbc:mysql://localhost:3306/bfxmis?useUnicode=true&characterEncoding=UTF-8";// 访问MySQL数据库的路径
	protected static String dbUser = "root";// 访问MySQL数据库的用户名
	protected static String dbPwd = "150018";// 访问MySQL数据库的密码
	protected static String dbName = "bfxmis";// 访问MySQL数据库中的实例(db_database28)
	protected static String second = null;//
	public static Connection conn = null;// MySQL数据库的连接对象

	static {// 静态初始化Dao类
		try {
			if (conn == null) {
				Class.forName(dbClassName).getDeclaredConstructor().newInstance();// 实例化MySQL数据库的驱动
				conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);// 连接MySQL数据库
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "请将MySQL的JDBC驱动包复制到lib文件夹中。");// 捕获异常后，弹出提示框
			System.exit(-1);// 系统停止运行
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Dao() {
	}




	// 读取药品信息
	public static TbYpinfo getYpInfo(Item item) {
		String where = "ypname='" + item.getName() + "'";
		if (item.getId() != null)
			where = "id='" + item.getId() + "'";
		ResultSet rs = findForResultSet("select * from tb_ypinfo where "
				+ where);
		TbYpinfo ypInfo = new TbYpinfo();
		try {
			if (rs.next()) {
				ypInfo.setYpid(rs.getString("ypid").trim());
				ypInfo.setQuantity(rs.getString("quantity").trim());
				ypInfo.setPlace(rs.getString("place").trim());
				ypInfo.setUnit(rs.getString("unit").trim());
				ypInfo.setSpec(rs.getString("spec").trim());
				ypInfo.setSupplyname(rs.getString("supplyname").trim());
				ypInfo.setSaleprice(rs.getString("saleprice").trim());
				if(Global.flag==2)ypInfo.setSaleprice(rs.getString("supprice").trim());
				
				ypInfo.setYpname(rs.getString("ypname").trim());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ypInfo;
	}
	
	// 获取所有药品信息
			public static List getYpInfos() {
				List list = findForList("select * from tb_ypinfo");
				return list;
			}

			
			
	// 采购时获取所有药品信息
	public static List getBuyYpInfos() {
		List list = findForList("select * from tb_ypinfo");
		return list;
	}
	
	
	// 采购时读取药品信息
		public static TbYpinfo getBuyYpInfo(Item item) {
			String where = "ypname='" + item.getName() + "'";
			if (item.getId() != null)
				where = "id='" + item.getId() + "'";
			ResultSet rs = findForResultSet("select * from tb_ypinfo where "
					+ where);
			TbYpinfo ypInfo = new TbYpinfo();
			try {
				if (rs.next()) {
					ypInfo.setYpid(rs.getString("ypid").trim());
					ypInfo.setQuantity(rs.getString("quantity").trim());
					ypInfo.setPlace(rs.getString("place").trim());
					ypInfo.setUnit(rs.getString("unit").trim());
					ypInfo.setSpec(rs.getString("spec").trim());
					ypInfo.setSupplyname(rs.getString("supplyname").trim());
					ypInfo.setSaleprice(rs.getString("supprice").trim());
					
					ypInfo.setYpname(rs.getString("ypname").trim());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ypInfo;
		}
	//获取药品余量
	public static int getYpquantity(String str) {
		int num=0;
		ResultSet rs = findForResultSet("select * from tb_ypinfo where ypid = '"
				+ str + " '");
		try {
			if (rs.next()) {
				num=Integer.valueOf(rs.getString("quantity").trim());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}
		

	public static ResultSet findForResultSet(String sql) {
		if (conn == null)
			return null;
		long time = System.currentTimeMillis();
		ResultSet rs = null;
		try {
			Statement stmt = null;
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			second = ((System.currentTimeMillis() - time) / 1000d) + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
//
	// 添加数据
	public static boolean insert(String sql) {
		boolean result = false;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 更新数据
	public static int update(String sql) {
		int result = 0;
		try {
			Statement stmt = conn.createStatement();
			result = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
//
	public static List findForList(String sql) {
		List<List> list = new ArrayList<List>();
		ResultSet rs = findForResultSet(sql);
		
	
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int colCount = metaData.getColumnCount();
			while (rs.next()) {
				List<String> row = new ArrayList<String>();
				for (int i = 1; i <= colCount; i++) {
					String str = rs.getString(i);
					if (str != null && !str.isEmpty())
						str = str.trim();
					row.add(str);
				}
				list.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 读取所有客户信息
	public static List getKhInfos() {
		List list = findForList("select id,khname from tb_khinfo");
		return list;
	}

	// 读取所有供应商信息
	public static List getSupplyInfos() {
		List list = findForList("select id,supplyname from tb_supplyinfo");
		return list;
	}

	// 读取客户信息
	public static TbKhinfo getKhInfo(Item item) {
		String where = "khname='" + item.getName() + "'";
		if (item.getId() != null)
			where = "id='" + item.getId() + "'";
		TbKhinfo info = new TbKhinfo();
		ResultSet set = findForResultSet("select * from tb_khinfo where "
				+ where);
		try {
			if (set.next()) {
				info.setId(set.getString("id").trim());
				info.setKhname(set.getString("khname").trim());
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return info;
	}

	


	// 执行指定查询
	public static ResultSet query(String QueryStr) {
		ResultSet set = findForResultSet(QueryStr);
		return set;
	}

	// 执行删除
	public static int delete(String sql) {
		return update(sql);
	}





	












	

	// 获取销售主表最大ID
	public static String getSellMainMaxId(Date date) {
		return getMainTypeTableMaxId(date, "tb_sale", "XS", "sellID");
	}
	


	// 在事务中添加销售信息
	public static boolean insertSellInfo(TbSale sellMain) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			// 添加销售主表记录
			insert("insert into tb_sale values('" + sellMain.getSellId()
					+ "','" + sellMain.getKhname()
					+ "','" + sellMain.getXsdate()
					+ "','" + sellMain.getJe() 
					+ "','" + sellMain.getJsr()
					+ "','" + sellMain.getJsfs()
					+ "','" + sellMain.getCzy()
					+ "','待出库"
					+ "')");
			Set<TbSaleDetail> rkDetails = sellMain.getTbSaleDetails();
			for (Iterator<TbSaleDetail> iter = rkDetails.iterator(); iter
					.hasNext();) {
				TbSaleDetail details = iter.next();
				// 添加销售详细表记录
				insert("insert into tb_sale_detail values('"
						+ sellMain.getSellId() 
						+ "','" + details.getName()
						+ "','" + details.getYpid()
						+ "','" + details.getPlace()
						+ "','" + details.getUnit()
						+ "','" + details.getSpec()
						+ "'," + details.getDj() 
						+ "," + details.getSl() 
						+ ")");
			
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	// 获取出库主表最大ID
	public static String getOutMainMaxId(Date date) {
		return getMainTypeTableMaxId(date, "tb_out", "CK", "sellID");
	}
	
	// 在事务中添加出库信息
		public static boolean insertOutInfo(TbSale sellMain) {
			try {
				boolean autoCommit = conn.getAutoCommit();
				conn.setAutoCommit(false);
				// 添加出库主表记录
				insert("insert into tb_out values('" + sellMain.getSellId()
						+ "','" + sellMain.getKhname()
						+ "','" + sellMain.getXsdate()
						+ "','" + sellMain.getJe() 
						+ "','" + sellMain.getJsr()
						+ "','" + sellMain.getJsfs()
						+ "','" + sellMain.getCzy()
						+ "')");
				update("update tb_sale set state = '已出库，待发货' " 
						+ " where sellID='" + Global.xs_number + "'");
				Set<TbSaleDetail> rkDetails = sellMain.getTbSaleDetails();
				for (Iterator<TbSaleDetail> iter = rkDetails.iterator(); iter
						.hasNext();) {
					TbSaleDetail details = iter.next();
					// 添加出库详细表记录
					insert("insert into tb_out_detail values('"
							+ sellMain.getSellId() 
							+ "','" + details.getName()
							+ "','" + details.getYpid()
							+ "','" + details.getPlace()
							+ "','" + details.getUnit()
							+ "','" + details.getSpec()
							+ "'," + details.getDj() 
							+ "," + details.getSl() 
							+ ")");
					update("update tb_ypinfo set quantity = quantity - " + details.getSl()
					+ " where ypid='" + details.getYpid() + "'");
					
					
				
				}
				conn.commit();
				conn.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		// 获取发货主表最大ID
		public static String getFHMainMaxId(Date date) {
			return getMainTypeTableMaxId(date, "tb_send", "FH", "sellID");
		}
		
		// 在事务中添加发货信息
			public static boolean insertFHInfo(TbSale sellMain) {
				try {
					boolean autoCommit = conn.getAutoCommit();
					conn.setAutoCommit(false);
					// 添加发货主表记录
					insert("insert into tb_send values('" + sellMain.getSellId()
							+ "','" + sellMain.getKhname()
							+ "','" + sellMain.getXsdate()
							+ "','" + sellMain.getJe() 
							+ "','" + sellMain.getJsr()
							+ "','" + sellMain.getJsfs()
							+ "','" + sellMain.getCzy()
							+ "')");
					update("update tb_sale set state = '已发货' " 
							+ " where sellID='" + Global.xs_number + "'");
					Set<TbSaleDetail> rkDetails = sellMain.getTbSaleDetails();
					for (Iterator<TbSaleDetail> iter = rkDetails.iterator(); iter
							.hasNext();) {
						TbSaleDetail details = iter.next();
						// 添加发货详细表记录
						insert("insert into tb_send_detail values('"
								+ sellMain.getSellId() 
								+ "','" + details.getName()
								+ "','" + details.getYpid()
								+ "','" + details.getPlace()
								+ "','" + details.getUnit()
								+ "','" + details.getSpec()
								+ "'," + details.getDj() 
								+ "," + details.getSl() 
								+ ")");
						
						
					
					}
					conn.commit();
					conn.setAutoCommit(autoCommit);
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
			
			
			// 获取采购主表最大ID
			public static String getBuyMainMaxId(Date date) {
				return getMainTypeTableMaxId(date, "tb_buy", "CG", "sellID");
			}
			


			// 在事务中添加销售信息
			public static boolean insertBuyInfo(TbSale sellMain) {
				try {
					boolean autoCommit = conn.getAutoCommit();
					conn.setAutoCommit(false);
					// 添加销售主表记录
					insert("insert into tb_buy values('" + sellMain.getSellId()
							+ "','" + sellMain.getKhname()
							+ "','" + sellMain.getXsdate()
							+ "','" + sellMain.getJe() 
							+ "','" + sellMain.getJsr()
							+ "','" + sellMain.getJsfs()
							+ "','" + sellMain.getCzy()
							+ "','待入库"
							+ "')");
					Set<TbSaleDetail> rkDetails = sellMain.getTbSaleDetails();
					for (Iterator<TbSaleDetail> iter = rkDetails.iterator(); iter
							.hasNext();) {
						TbSaleDetail details = iter.next();
						// 添加销售详细表记录
						insert("insert into tb_buy_detail values('"
								+ sellMain.getSellId() 
								+ "','" + details.getName()
								+ "','" + details.getYpid()
								+ "','" + details.getPlace()
								+ "','" + details.getUnit()
								+ "','" + details.getSpec()
								+ "'," + details.getDj() 
								+ "," + details.getSl() 
								+ ")");
					
					}
					conn.commit();
					conn.setAutoCommit(autoCommit);
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
			
			
			
			// 获取入库主表最大ID
						public static String getInMainMaxId(Date date) {
							return getMainTypeTableMaxId(date, "tb_in", "RK", "sellID");
						}
						


						
						// 在事务中添加销售信息
						public static boolean insertInInfo(TbSale sellMain) {
							try {
								boolean autoCommit = conn.getAutoCommit();
								conn.setAutoCommit(false);
								// 添加销售主表记录
								insert("insert into tb_in values('" + sellMain.getSellId()
										+ "','" + sellMain.getKhname()
										+ "','" + sellMain.getXsdate()
										+ "','" + sellMain.getJe() 
										+ "','" + sellMain.getJsr()
										+ "','" + sellMain.getJsfs()
										+ "','" + sellMain.getCzy()
										+ "')");
								update("update tb_buy set state = '已入库' " 
										+ " where sellID='" + Global.xs_number + "'");
								Set<TbSaleDetail> rkDetails = sellMain.getTbSaleDetails();
								for (Iterator<TbSaleDetail> iter = rkDetails.iterator(); iter
										.hasNext();) {
									TbSaleDetail details = iter.next();
									// 添加销售详细表记录
									insert("insert into tb_in_detail values('"
											+ sellMain.getSellId() 
											+ "','" + details.getName()
											+ "','" + details.getYpid()
											+ "','" + details.getPlace()
											+ "','" + details.getUnit()
											+ "','" + details.getSpec()
											+ "'," + details.getDj() 
											+ "," + details.getSl() 
											+ ")");
									update("update tb_ypinfo set quantity = quantity + " + details.getSl()
									+ " where ypid='" + details.getYpid() + "'");
								
								}
								conn.commit();
								conn.setAutoCommit(autoCommit);
							} catch (SQLException e) {
								e.printStackTrace();
								return false;
							}
							return true;
						}						
	// 获取更类主表最大ID
	private static String getMainTypeTableMaxId(Date date, String table,
			String idChar, String idName) {
		String dateStr = date.toString().replace("-", "");
		String id = idChar + dateStr;
		String sql = "select max(" + idName + ") from " + table + " where "
				+ idName + " like '" + id + "%'";
		ResultSet set = query(sql);
		String baseId = null;
		try {
			if (set.next())
				baseId = set.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		baseId = baseId == null ? "000" : baseId.substring(baseId.length() - 3);
		int idNum = Integer.parseInt(baseId) + 1;
		id += String.format("%03d", idNum);
		return id;
	}
	// 获取采购计划主表最大ID
				public static String getPlanBuyMainMaxId(Date date) {
					return getMainTypeTableMaxId(date, "tb_planbuy", "PCG", "sellID");
				}
	// 在事务中添加采购计划单信息
	public static boolean insertPlanBuyInfo(TbSale sellMain) {
		try {
			boolean autoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false);
			// 添加出库主表记录
			insert("insert into tb_planbuy values('" + sellMain.getSellId()
					
					+ "','" + sellMain.getXsdate()
					+ "','" + sellMain.getJe() 
					+ "','" + sellMain.getJsr()
					+ "','" + sellMain.getJsfs()
					+ "','" + sellMain.getCzy()
					+"','待处理"
					+ "')");
			update("update tb_sale set state = '仓库缺货，待补货' " 
					+ " where sellID='" + Global.xs_number + "'");
			Set<TbSaleDetail> rkDetails = sellMain.getTbSaleDetails();
			for (Iterator<TbSaleDetail> iter = rkDetails.iterator(); iter
					.hasNext();) {
				TbSaleDetail details = iter.next();
				// 添加出库详细表记录
				insert("insert into tb_planbuy_detail values('"
						+ sellMain.getSellId() 
						+ "','" + details.getName()
						+ "','" + details.getYpid()
						+ "','" + details.getPlace()
						+ "','" + details.getUnit()
						+ "','" + details.getSpec()
						+ "'," + details.getDj() 
						+ "," + details.getSl() 
						+ ")");
				
				
			
			}
			conn.commit();
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}





	// 获取入库单的最大ID，即最大入库票号
	public static String getRuKuMainMaxId(Date date) {
		return getMainTypeTableMaxId(date, "tb_ruku_main", "RK", "rkid");
	}


	// 读取销售单详情信息
	public static TbYpinfo getXSInfo(Item item,String str) {
		String where = null;
		if (item.getId() != null)
			where = "ypid='" + item.getId() + "'";
		ResultSet rs = findForResultSet("select * from tb_sale_detail where "
				+ "sellID = '"+str+"' and "+where);
		TbYpinfo ypInfo = new TbYpinfo();
		try {
			if (rs.next()) {
				ypInfo.setYpid(rs.getString("ypid").trim());
				ypInfo.setQuantity(rs.getString("sl").trim());
				
				ypInfo.setPlace(rs.getString("place").trim());
				ypInfo.setUnit(rs.getString("unit").trim());
				ypInfo.setSpec(rs.getString("spec").trim());
				ypInfo.setSaleprice(rs.getString("dj").trim());
				ypInfo.setYpname(rs.getString("ypname").trim());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ypInfo;
	}
	
	public static List getXSInfos() {
		List list = findForList("select * from tb_sale_detail");
		return list;
	}
	
	// 读取发货单详情信息
		public static TbYpinfo getFHInfo(Item item,String str) {
			String where = null;
			if (item.getId() != null)
				where = "ypid='" + item.getId() + "'";
			ResultSet rs = findForResultSet("select * from tb_send_detail where "
					+ "sellID = '"+str+"' and "+where);
			TbYpinfo ypInfo = new TbYpinfo();
			try {
				if (rs.next()) {
					ypInfo.setYpid(rs.getString("ypid").trim());
					ypInfo.setQuantity(rs.getString("sl").trim());
					
					ypInfo.setPlace(rs.getString("place").trim());
					ypInfo.setUnit(rs.getString("unit").trim());
					ypInfo.setSpec(rs.getString("spec").trim());
					ypInfo.setSaleprice(rs.getString("dj").trim());
					ypInfo.setYpname(rs.getString("ypname").trim());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ypInfo;
		}
		
		public static List getFHInfos() {
			List list = findForList("select * from tb_send_detail");
			return list;
		}
	
	
		
		// 读取出库单详情信息
				public static TbYpinfo getCKInfo(Item item,String str) {
					String where = null;
					if (item.getId() != null)
						where = "ypid='" + item.getId() + "'";
					ResultSet rs = findForResultSet("select * from tb_out_detail where "
							+ "sellID = '"+str+"' and "+where);
					TbYpinfo ypInfo = new TbYpinfo();
					try {
						if (rs.next()) {
							ypInfo.setYpid(rs.getString("ypid").trim());
							ypInfo.setQuantity(rs.getString("sl").trim());
							
							ypInfo.setPlace(rs.getString("place").trim());
							ypInfo.setUnit(rs.getString("unit").trim());
							ypInfo.setSpec(rs.getString("spec").trim());
							ypInfo.setSaleprice(rs.getString("dj").trim());
							ypInfo.setYpname(rs.getString("ypname").trim());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ypInfo;
				}
				
				public static List getCKInfos() {
					List list = findForList("select * from tb_out_detail");
					return list;
				}
		// 读取入库单详情信息
				public static TbYpinfo getRKInfo(Item item,String str) {
					String where = null;
					if (item.getId() != null)
						where = "ypid='" + item.getId() + "'";
					ResultSet rs = findForResultSet("select * from tb_in_detail where "
							+ "sellID = '"+str+"' and "+where);
					TbYpinfo ypInfo = new TbYpinfo();
					try {
						if (rs.next()) {
							ypInfo.setYpid(rs.getString("ypid").trim());
							ypInfo.setQuantity(rs.getString("sl").trim());
							
							ypInfo.setPlace(rs.getString("place").trim());
							ypInfo.setUnit(rs.getString("unit").trim());
							ypInfo.setSpec(rs.getString("spec").trim());
							ypInfo.setSaleprice(rs.getString("dj").trim());
							ypInfo.setYpname(rs.getString("ypname").trim());
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return ypInfo;
				}
				
				public static List getRKInfos() {
					List list = findForList("select * from tb_in_detail");
					return list;
				}
	// 读取采购计划单详情信息
		public static TbYpinfo getPlanBuyInfo(Item item,String str) {
			String where = "ypname='" + item.getName() + "'";
			if (item.getId() != null)
				where = "ypid='" + item.getId() + "'";
			ResultSet rs = findForResultSet("select * from tb_planbuy_detail where "
					+ "sellID = '"+str+"' and "+where);
			TbYpinfo ypInfo = new TbYpinfo();
			try {
				if (rs.next()) {
					ypInfo.setYpid(rs.getString("ypid").trim());
					ypInfo.setQuantity(rs.getString("sl").trim());
					ypInfo.setPlace(rs.getString("place").trim());
					ypInfo.setUnit(rs.getString("unit").trim());
					ypInfo.setSpec(rs.getString("spec").trim());
					ypInfo.setSaleprice(rs.getString("dj").trim());
					ypInfo.setYpname(rs.getString("ypname").trim());
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			return ypInfo;
		}
		
		public static List getBuyInfos() {
			List list = findForList("select * from tb_buy_detail");
			return list;
		}
		
		//读取采购单详情
		public static TbYpinfo getBuyInfo(Item item,String str) {
			String where = "ypname='" + item.getName() + "'";
			if (item.getId() != null)
				where = "ypid='" + item.getId() + "'";
			ResultSet rs = findForResultSet("select * from tb_buy_detail where "
					+ "sellID = '"+str+"' and "+where);
			TbYpinfo ypInfo = new TbYpinfo();
			try {
				if (rs.next()) {
					ypInfo.setYpid(rs.getString("ypid").trim());
					ypInfo.setQuantity(rs.getString("sl").trim());
					ypInfo.setPlace(rs.getString("place").trim());
					ypInfo.setUnit(rs.getString("unit").trim());
					ypInfo.setSpec(rs.getString("spec").trim());
					ypInfo.setSaleprice(rs.getString("dj").trim());
					ypInfo.setYpname(rs.getString("ypname").trim());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return ypInfo;
		}
		
		public static List getPlanBuyInfos() {
			List list = findForList("select * from tb_planbuy_detail");
			return list;
		}
	

	// 验证登录
	public static boolean checkLogin(String userStr, String passStr)
			throws SQLException {
		ResultSet rs = findForResultSet("select * from tb_userinfo where user='"
				+ userStr + "'");
		
		
		try {
			if(rs.next()) {
				Global.userID=rs.getString("identity").trim();
				Global.username=rs.getString("user").trim();
			}
				
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		//JOptionPane.showMessageDialog(null, "You input is "+Global.userID, Global.userID, JOptionPane.PLAIN_MESSAGE);
		
		rs = findForResultSet("select * from tb_userinfo where user='"
				+ userStr + "' and password='" + passStr + "'");
		if (rs == null)
			return false;
		
		return rs.next();
	}

}
