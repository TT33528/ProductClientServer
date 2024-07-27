package com.edu.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//�������������ConnectionFactory��ʱ��ʼ����̬��Ա����connectionFactoryһ�Σ�Ȼ�������˽�й��캯�����mysql����ע��
	private static ConnectionFactory connectionFactory = new ConnectionFactory();//������
	
	private ConnectionFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");//���ز�ע������
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		String url = "jdbc:mysql://localhost:3306/mall?characterEncoding=utf-8&useUnicode=true&useSSL=true&serverTimezone=UTC";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, "root", "123456");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
}