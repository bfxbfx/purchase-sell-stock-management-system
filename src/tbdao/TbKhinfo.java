package tbdao;

public class TbKhinfo implements java.io.Serializable {// 客户信息（实现序列化接口）

	private String id;// 客户编号
	private String khname;// 客户名称


	public TbKhinfo() {// 缺省构造函数
	}

	public TbKhinfo(String id) {// 最小构造函数(主键)
		this.id = id;
	}

	public TbKhinfo(String id, String khname) {// 完整构造函数
		this.id = id;
		this.khname = khname;

	}
	
	// 使用Getters and Setters方法将客户信息类的私有属性封装起来
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKhname() {
		return this.khname;
	}

	public void setKhname(String khname) {
		this.khname = khname;
	}
}

	