package com.edu.service;

import java.util.List;

import com.edu.beans.Product;

public interface IProductService {
	List<Product> getProducts();//获取所有产品
	int saveProduct(Product product);//保存
	int updateProduct(Product product);//修改
	int deleteProductById(int pid);//根据pid删除
	int deleteAll();//删除所有
}