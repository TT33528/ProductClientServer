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
	 * @param op：发送给服务器的请求类型
	 * @param obj：需要发送给服务器的数据
	 * @return Object：收到的服务器发送过来的数据
	 */
	public Object show(String op, Object obj) {
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			//将Socket的输入输出流包装成对象流，以便将对象序列化之后传输或将序列化的对象反序列化成对象
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			//输出数据
			out.writeUTF(op);//发送请求类型给服务器
			out.writeObject(obj);//将序列化后的数据发送给服务器
			
			//读取数据
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
	
	//上传文件
	public Object upload(String op, String file) {
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			//将Socket的输入输出流包装成对象流，以便将对象序列化之后传输或将序列化的对象反序列化成对象
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			//输出数据
			out.writeUTF(op);//发送请求类型给服务器
			
			int lastIndexOf = file.lastIndexOf(File.separator);//找到路径中的最后一个"\"
			String filename = file.substring(lastIndexOf + 1);// 截取出文件名
			//将文件名写出给服务器
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
			return "上传成功";
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//下载文件
	public Object download(String op, String file) {
		try {
			Socket socket = new Socket("127.0.0.1", 6666);
			//将Socket的输入输出流包装成对象流，以便将对象序列化之后传输或将序列化的对象反序列化成对象
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			//输出数据
			out.writeUTF(op);//发送请求类型给服务器
			
			int lastIndexOf = file.lastIndexOf(File.separator);//找到路径中的最后一个"\"
			String filename = file.substring(lastIndexOf + 1);// 截取出文件名
			//将文件名写出给服务器
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
			return "下载成功";
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