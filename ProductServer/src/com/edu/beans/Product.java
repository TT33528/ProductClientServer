package com.edu.beans;

import java.io.Serializable;

public class Product implements Serializable{//可序列化
	private int pid; //编号
	private String pno; //产品编号
	private String pname;//产品名称
	private double price;//价格
	private int stock;//库存
	private String pdesc;//描述
	
	public Product() {
		// TODO Auto-generated constructor stub
	}

	public Product(String pno, String pname, double price, int stock, String pdesc) {
		super();
		this.pno = pno;
		this.pname = pname;
		this.price = price;
		this.stock = stock;
		this.pdesc = pdesc;
	}

	public Product(int pid, String pno, String pname, double price, int stock, String pdesc) {
		super();
		this.pid = pid;
		this.pno = pno;
		this.pname = pname;
		this.price = price;
		this.stock = stock;
		this.pdesc = pdesc;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pno=" + pno + ", pname=" + pname + ", price=" + price + ", stock=" + stock
				+ ", pdesc=" + pdesc + "]";
	}
}