package com.edu.service;

import java.util.List;

import com.edu.beans.Product;

public interface IProductService {
	List<Product> getProducts();//��ȡ���в�Ʒ
	int saveProduct(Product product);//����
	int updateProduct(Product product);//�޸�
	int deleteProductById(int pid);//����pidɾ��
	int deleteAll();//ɾ������
}