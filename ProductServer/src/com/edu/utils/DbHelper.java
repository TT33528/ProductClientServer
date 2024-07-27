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

	//ִ�����ӣ�ɾ�����޸����
	//sql����ִ�е�sql��args���ɱ���������ݸ�ռλ����ʵ�ʲ���ֵ
	public static int update(String sql, Object...args) {
		int res = 0;
		PreparedStatement pstmt = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(args != null) {//�����Ϊ�գ���ʾ��ռλ��?��Ҫ���
				for (int i = 0; i < args.length; i++) {//������������args
					//����ռλ������ֵ
					pstmt.setObject(i +1, args[i]);//���ܲ�����ʲô���ͣ�ͳһ����Object���ʹ���
				}
			}
			res = pstmt.executeUpdate();//��ɾ�Ķ���ͬһ������ִ��
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close(null, pstmt, conn);//�ر���Դ
		}
		return res;
	}
	
	//�������ã���ȡ���ݿ��һ����¼����������װ��һ���������͵�JavaBean���󷵻�
	/**
	 * 
	 * @param clazz����Ӧ��������JavaBean��Class����
	 * @param sql����ѯ��sql��䣬ֻ�ܷ���һ����¼
	 * @param args�����ݸ�ռλ����ʵ��
	 * @return T�������������͵�JavaBean����
	 */
	public static <T> T getBean(Class<T> clazz, String sql, Object... args) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		T t = null;
		Connection conn = ConnectionFactory.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(args != null) {//�����Ϊ�գ���ʾ��ռλ��?��Ҫ���
				for (int i = 0; i < args.length; i++) {//������������args
					//����ռλ������ֵ
					pstmt.setObject(i +1, args[i]);//���ܲ�����ʲô���ͣ�ͳһ����Object���ʹ���
				}
			}
			rs = pstmt.executeQuery();//ִ�в�ѯ���
			//��ȡ�������ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//��ȡ�е�����
			int columnCount = rsmd.getColumnCount();
			if(rs.next()) {//��ʱֻ��ѯ����һ������
				//��ʱ����������һ����¼��
				//�������bean����
				t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {//��0���е��������б���
					//��ȡ���ݱ��ǰ�еĶ�Ӧλ�õ��ֶε�����
					String columnLabel = rsmd.getColumnLabel(i + 1);
					//��ȡ���ݱ�ǰ�еĶ�Ӧλ�õ��ֶε�ֵ
					Object columnValue = rs.getObject(i + 1);
					//ͨ������Ļ��ƻ�ȡJavaBean������Field��ע�⣺��ʱҪ�����ݿ����ֶ�����JavaBean������Ҫͬ����
					//��ô�������������
					Field field = clazz.getDeclaredField(columnLabel);
					//����field�ɽ���
					field.setAccessible(true);
					//��field��ֵ
					field.set(t, columnValue);
				}//ͨ�����ѭ��֮��ͻ��JavaBean�ĸ������Զ�����ֵ
				
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
			close(rs, pstmt, conn);//�ر���Դ
		}
		return t;//������ֵ��Javabean����
	}
	
	//ͨ������һ��Javabean�ķ���ԴClass�Լ�sql���͸�ֵ��ռλ����args��������ȡһ��Javabean��ɵļ���
	/**
	 * 
	 * @param clazz����Ӧ��������JavaBean��Class����
	 * @param sql����ѯ��sql��䣬���ض�����¼
	 * @param args�����ݸ�ռλ����ʵ��
	 * @return List<T>�������������͵�JavaBean������ɵļ���
	 */
	public static <T> List<T> getList(Class<T> clazz, String sql, Object... args){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<T>();
		Connection conn = ConnectionFactory.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(args != null) {//�����Ϊ�գ���ʾ��ռλ��?��Ҫ���
				for (int i = 0; i < args.length; i++) {//������������args
					//����ռλ������ֵ
					pstmt.setObject(i +1, args[i]);//���ܲ�����ʲô���ͣ�ͳһ����Object���ʹ���
				}
			}
			rs = pstmt.executeQuery();//ִ�в�ѯ���
			//��ȡ�������ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			//��ȡ�е�����
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {//���������
				//��ʱ����������һ����¼��
				//�������bean����
				T t = clazz.newInstance();
				for (int i = 0; i < columnCount; i++) {//��0���е��������б���
					//��ȡ���ݱ��ǰ�еĶ�Ӧλ�õ��ֶε�����
					String columnLabel = rsmd.getColumnLabel(i + 1);
					//��ȡ���ݱ�ǰ�еĶ�Ӧλ�õ��ֶε�ֵ
					Object columnValue = rs.getObject(i + 1);
					//ͨ������Ļ��ƻ�ȡJavaBean������Field��ע�⣺��ʱҪ�����ݿ����ֶ�����JavaBean������Ҫͬ����
					//��ô�������������
					Field field = clazz.getDeclaredField(columnLabel);
					//����field�ɽ���
					field.setAccessible(true);
					//��field��ֵ
					field.set(t, columnValue);
				}
				list.add(t);//�������˸����ֶ�ֵ��JavaBean��ӵ�����list��
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
			close(rs, pstmt, conn);//�ر���Դ
		}
		return list;
	}
	//�ر���Դ
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