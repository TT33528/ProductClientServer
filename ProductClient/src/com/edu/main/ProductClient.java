package com.edu.main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.edu.beans.Product;

public class ProductClient {
	/**
	 * 
	 * @param op�����͸�����������������
	 * @param obj����Ҫ���͸�������������
	 * @return Object���յ��ķ��������͹���������
	 */
	public Object show(String op, Object obj) {
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			//��Socket�������������װ�ɶ��������Ա㽫�������л�֮��������л��Ķ������л��ɶ���
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			//�������
			out.writeUTF(op);//�����������͸�������
			out.writeObject(obj);//�����л�������ݷ��͸�������
			
			//��ȡ����
			Object o = in.readObject();
			in.close();
			out.close();
			return o;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//�ϴ��ļ�
	public Object upload(String op, String file) {
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			//��Socket�������������װ�ɶ��������Ա㽫�������л�֮��������л��Ķ������л��ɶ���
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			//�������
			out.writeUTF(op);//�����������͸�������
			
			int lastIndexOf = file.lastIndexOf(File.separator);//�ҵ�·���е����һ��"\"
			String filename = file.substring(lastIndexOf + 1);// ��ȡ���ļ���
			//���ļ���д����������
			out.writeObject(filename);
			InputStream input = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(input);
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bis.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			bis.close();
			bos.flush();
			in.close();
			out.close();
			return "�ϴ��ɹ�";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//�����ļ�
	public Object download(String op, String file) {
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			//��Socket�������������װ�ɶ��������Ա㽫�������л�֮��������л��Ķ������л��ɶ���
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			//�������
			out.writeUTF(op);//�����������͸�������
			
			int lastIndexOf = file.lastIndexOf(File.separator);//�ҵ�·���е����һ��"\"
			String filename = file.substring(lastIndexOf + 1);// ��ȡ���ļ���
			//���ļ���д����������
			out.writeObject(filename);
			InputStream input = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(input);
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bis.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			bis.close();
			bos.flush();
			in.close();
			out.close();
			return "���سɹ�";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}