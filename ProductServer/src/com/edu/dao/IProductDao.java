package com.edu.dao;

import java.util.List;

import com.edu.beans.Product;

public interface IProductDao {
	List<Product> getProducts();//��ȡ���в�Ʒ
	int saveProduct(Product product);//����
	int updateProduct(Product product);//�޸�
	int deleteProductById(int pid);//����pidɾ��
	int deleteAll();//ɾ������
}