package cn.jxy.model;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {

	private Integer id;
	private String name;
	private Double price;
	private String remark;
	private Date date;
	private String pic;
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price
				+ ", remark=" + remark + ", date=" + date + ", pic=" + pic
				+ "]";
	}

	public Product() {
		super();
	}

	public Product(String name, Double price, String remark) {
		super();
		this.name = name;
		this.price = price;
		this.remark = remark;
	}
	
	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
