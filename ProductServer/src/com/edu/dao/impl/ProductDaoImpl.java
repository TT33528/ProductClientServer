package com.edu.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.edu.beans.Product;
import com.edu.dao.IProductDao;
import com.edu.utils.ConnectionFactory;
import com.edu.utils.DbHelper;

public class ProductDaoImpl implements IProductDao{

	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		String sql = "select * from product";
		List<Product> list = DbHelper.getList(Product.class, sql, null);
		return list;
	}

	@Override
	public int saveProduct(Product product) {
		// TODO Auto-generated method stub
		String sql = "insert into product(pno,pname,price,stock,pdesc) value(?,?,?,?,?)";
		int res = DbHelper.update(sql, product.getPno(),product.getPname(),product.getPrice(),product.getStock(),product.getPdesc());
		return res;
	}

	@Override
	public int updateProduct(Product product) {
		// TODO Auto-generated method stub
		String sql = "update product set pno=?,pname=?,price=?,stock=?,pdesc=? where pid=?";
		int res = DbHelper.update(sql, product.getPno(),product.getPname(),product.getPrice(),product.getStock(),product.getPdesc(),product.getPid());
		return res;
	}

	@Override
	public int deleteProductById(int pid) {
		// TODO Auto-generated method stub
		String sql = "delete from product where pid=?";
		int res = DbHelper.update(sql, pid);
		return res;
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		String sql = "delete from product";
		int res = DbHelper.update(sql, null);
		return res;
	}

}