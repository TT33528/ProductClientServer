package com.edu.utils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbHelper {

	//执行增加，删除，修改语句
	//sql：待执行的sql，args：可变参数，传递给占位符的实际参数值
	public static int update(String sql, Object...args) {
		int res = 0;
		PreparedStatement pstmt = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(args != null) {//如果不为空，表示有占位符?需要填充
				for (int i = 0; i < args.length; i++) {//遍历整个参数args
					//设置占位符？的值
					pstmt.setObject(i +1, args[i]);//不管参数是什么类型，统一当成Object类型处理
				}
			}
			res = pstmt.executeUpdate();//增删改都用同一个方法执行
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, pstmt, conn);//关闭资源
		}
		return res;
	}
	
	//方法作用：获取数据库的一条记录，并把它封装成一个任意类型的JavaBean对象返回
	/**
	 * 
	 * @param clazz：对应任意类型JavaBean的Class对象
	 * @param sql：查询的sql语句，只能返回一条记录
	 * @param args：传递给占位符的实参
	 * @return T：返回任意类型的JavaBean对象
	 */
	public static <T> T getBean(Class<T> clazz, String sql, Object... args) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T t = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(args != null) {//如果不为空，表示有占位符?需要填充
				for (int i = 0; i < args.length; i++) {//遍历整个参数args
					//设置占位符？的值
					pstmt.setObject(i +1, args[i]);//不管参数是什么类型，统一当成Object类型处理
				}
			}
			rs = pstmt.executeQuery();//执行查询语句
			//获取结果集的ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取列的总数
			int columnCount = rsmd.getColumnCount();
			if(rs.next()) {//此时只查询到了一条数据
				//此时我们在其中一条记录里
				//反射产生bean对象
				t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {//从0到列的总数进行遍历
					//获取数据表表当前行的对应位置的字段的名称
					String columnLabel = rsmd.getColumnLabel(i + 1);
					//获取数据表当前行的对应位置的字段的值
					Object columnValue = rs.getObject(i + 1);
					//通过反射的机制获取JavaBean的属性Field（注意：此时要求数据库表的字段名和JavaBean的属性要同名）
					//那么反射才能有作用
					Field field = clazz.getDeclaredField(columnLabel);
					//设置field可进入
					field.setAccessible(true);
					//给field赋值
					field.set(t, columnValue);
				}//通过这个循环之后就会给JavaBean的各个属性都赋上值
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);//关闭资源
		}
		return t;//将赋了值的Javabean返回
	}
	
	//通过传递一个Javabean的反射源Class以及sql语句和赋值给占位符的args参数，获取一个Javabean组成的集合
	/**
	 * 
	 * @param clazz：对应任意类型JavaBean的Class对象
	 * @param sql：查询的sql语句，返回多条记录
	 * @param args：传递给占位符的实参
	 * @return List<T>：返回任意类型的JavaBean对象组成的集合
	 */
	public static <T> List<T> getList(Class<T> clazz, String sql, Object... args){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		Connection conn = ConnectionFactory.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(args != null) {//如果不为空，表示有占位符?需要填充
				for (int i = 0; i < args.length; i++) {//遍历整个参数args
					//设置占位符？的值
					pstmt.setObject(i +1, args[i]);//不管参数是什么类型，统一当成Object类型处理
				}
			}
			rs = pstmt.executeQuery();//执行查询语句
			//获取结果集的ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取列的总数
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {//遍历结果集
				//此时我们在其中一条记录里
				//反射产生bean对象
				T t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {//从0到列的总数进行遍历
					//获取数据表表当前行的对应位置的字段的名称
					String columnLabel = rsmd.getColumnLabel(i + 1);
					//获取数据表当前行的对应位置的字段的值
					Object columnValue = rs.getObject(i + 1);
					//通过反射的机制获取JavaBean的属性Field（注意：此时要求数据库表的字段名和JavaBean的属性要同名）
					//那么反射才能有作用
					Field field = clazz.getDeclaredField(columnLabel);
					//设置field可进入
					field.setAccessible(true);
					//给field赋值
					field.set(t, columnValue);
				}
				list.add(t);//将设置了各个字段值的JavaBean添加到集合list中
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(rs, pstmt, conn);//关闭资源
		}
		return list;
	}
	//关闭资源
	public static void close(ResultSet rs,Statement stmt, Connection conn) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}