package com.edu.beans;

import java.io.Serializable;

public class Product implements Serializable{//�����л�
	private int pid; //���
	private String pno; //��Ʒ���
	private String pname;//��Ʒ����
	private double price;//�۸�
	private int stock;//���
	private String pdesc;//����
	
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