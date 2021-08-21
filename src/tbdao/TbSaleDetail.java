package tbdao;

public class TbSaleDetail implements java.io.Serializable {// 销售明细（实现序列化接口）

	private Integer id;// 流水号
	private String tbSellMain;// 销售主表
	private String ypid;// 商品编号
	private Double dj;// 销售单价
	private Integer sl;// 销售数量
	private String ypname; 
	private String ypplace;
	private String ypunit;
	private String ypspec;

	public TbSaleDetail() {// 缺省构造函数
	}

	public TbSaleDetail(String tbSellMain, String spid, Double dj, Integer sl) {// 完整构造函数
		this.tbSellMain = tbSellMain;
		this.ypid = spid;
		this.dj = dj;
		this.sl = sl;
	}

	// 使用Getters and Setters方法将销售明细类的私有属性封装起来
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTbSellMain() {
		return this.tbSellMain;
	}

	public void setTbSellMain(String tbSellMain) {
		this.tbSellMain = tbSellMain;
	}

	public String getYpid() {
		return this.ypid;
	}

	public void setYpid(String ypid) {
		this.ypid = ypid;
	}

	public Double getDj() {
		return this.dj;
	}

	public void setDj(Double dj) {
		this.dj = dj;
	}

	public Integer getSl() {
		return this.sl;
	}

	public void setSl(Integer sl) {
		this.sl = sl;
	}
	public String getName() {
		return this.ypname;
	}

	public void setName(String ypname) {
		this.ypname = ypname;
	}
	public String getUnit() {
		return this.ypunit;
	}

	public void setUnit(String ypunit) {
		this.ypunit = ypunit;
	}
	public String getSpec() {
		return this.ypspec;
	}

	public void setSpec(String ypspec) {
		this.ypspec = ypspec;
	}
	public String getPlace() {
		return this.ypplace;
	}

	public void setPlace(String ypplace) {
		this.ypplace = ypplace;
	}
}