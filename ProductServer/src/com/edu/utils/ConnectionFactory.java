package com.edu.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	//在类加载器加载ConnectionFactory类时初始化静态成员变量connectionFactory一次，然后调用其私有构造函数完成mysql驱动注册
	private static ConnectionFactory connectionFactory = new ConnectionFactory();//单例的
	
	private ConnectionFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");//加载并注册驱动
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