package com.edu.service.impl;

import java.util.List;

import com.edu.beans.Product;
import com.edu.dao.IProductDao;
import com.edu.dao.impl.ProductDaoImpl;
import com.edu.service.IProductService;

public class ProductServiceImpl implements IProductService{
	private IProductDao productDao = new ProductDaoImpl();
	
	@Override
	public List<Product> getProducts() {
		// TODO Auto-generated method stub
		return productDao.getProducts();
	}

	@Override
	public int saveProduct(Product product) {
		// TODO Auto-generated method stub
		return productDao.saveProduct(product);
	}

	@Override
	public int updateProduct(Product product) {
		// TODO Auto-generated method stub
		return productDao.updateProduct(product);
	}

	@Override
	public int deleteProductById(int pid) {
		// TODO Auto-generated method stub
		return productDao.deleteProductById(pid);
	}

	@Override
	public int deleteAll() {
		// TODO Auto-generated method stub
		return productDao.deleteAll();
	}
	
	

}