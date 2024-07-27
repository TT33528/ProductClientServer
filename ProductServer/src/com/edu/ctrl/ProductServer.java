package com.edu.ctrl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLEngineResult.Status;

import com.edu.beans.Product;
import com.edu.beans.User;
import com.edu.service.IProductService;
import com.edu.service.IUserService;
import com.edu.service.impl.ProductServiceImpl;
import com.edu.service.impl.UserServiceImpl;
import com.edu.utils.ConnectionFactory;

public class ProductServer {
	private IUserService userService = new UserServiceImpl();
	private IProductService productService = new ProductServiceImpl();
	
	public void exec() {
		try {
			//创建动态线程池，适合小并发量，
			ExecutorService executorService = Executors.newCachedThreadPool();
			ServerSocket serverSocket = new ServerSocket(6666);
			System.out.println("服务器在6666端口号上监听...");
			while(true) {
				Socket socket = serverSocket.accept();//来一个客户端连接就创建一个线程来处理它的请求
				executorService.execute(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							//获取Socket的输入输出流，转换为对象流，即进行序列化和反序列来收发数据
							ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
							ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
							//读取客户端发送的请求的类型
							String op = in.readUTF();
							switch (op) {
								case "register":{
									//注册
									User user = (User)in.readObject();//读取客户端发送的序列化的对象
									userService.saveUser(user);
									out.writeObject("注册用户成功");
									out.flush();//刷新缓冲
									}
									break;
								case "login": {
									//登陆
									User user = (User)in.readObject();
									boolean login = userService.login(user);
									out.writeObject(login);//将验证信息写入给客户端
									out.flush();
								}
								break;
								case "saveProduct":{
									//保存商品
									Product product = (Product)in.readObject();
									int res = productService.saveProduct(product);
									if(res > 0) {
										out.writeObject(true);
									}else {
										out.writeObject(false);
									}
									out.flush();
								}
								break;
								case "listProducts":{
									//显示所有商品
									List<Product> products = productService.getProducts();
									out.writeObject(products);//将获取的所有产品信息写给客户端
									out.flush();
								}
								break;
								case "updateProduct":{
									//更新产品
									Product product = (Product)in.readObject();
									int res = productService.updateProduct(product);
									if(res > 0) {
										out.writeObject(true);
									}else {
										out.writeObject(false);
									}
									out.flush();
								}
								break;
								case "deleteProduct":{
									//删除产品
									int productId  = (int)in.readObject();
									int res = productService.deleteProductById(productId);
									if(res > 0) {
										out.writeObject(true);
									}else {
										out.writeObject(false);
									}
									out.flush();
								}
								break;
								case "multiSearch":{
									//多条件查询
									try {
								        @SuppressWarnings("unchecked")
										Map<String, Object> searchParams = (Map<String, Object>) in.readObject();
								        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM product WHERE 1=1");
								        if (searchParams.containsKey("pname")) {
								            queryBuilder.append(" AND pname LIKE ?");
								        }
								        if (searchParams.containsKey("minPrice")) {
								            queryBuilder.append(" AND price >= ?");
								        }
								        if (searchParams.containsKey("maxPrice")) {
								            queryBuilder.append(" AND price <= ?");
								        }
								        if (searchParams.containsKey("minStock")) {
								            queryBuilder.append(" AND stock >= ?");
								        }
								        if (searchParams.containsKey("maxStock")) {
								            queryBuilder.append(" AND stock <= ?");
								        }

								        String query = queryBuilder.toString();
								        PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(query);
								        int index = 1;
								        if (searchParams.containsKey("pname")) {
								            stmt.setString(index++, "%" + searchParams.get("pname") + "%");
								        }
								        if (searchParams.containsKey("minPrice")) {
								            stmt.setDouble(index++, (Double) searchParams.get("minPrice"));
								        }
								        if (searchParams.containsKey("maxPrice")) {
								            stmt.setDouble(index++, (Double) searchParams.get("maxPrice"));
								        }
								        if (searchParams.containsKey("minStock")) {
								            stmt.setInt(index++, (Integer) searchParams.get("minStock"));
								        }
								        if (searchParams.containsKey("maxStock")) {
								            stmt.setInt(index++, (Integer) searchParams.get("maxStock"));
								        }

								        // Execute the query and collect the results
								        ResultSet rs = stmt.executeQuery();
								        List<Product> products = new ArrayList<>();
								        while (rs.next()) {
								            Product product = new Product(
								                rs.getInt("pid"),
								                rs.getString("pno"),
								                rs.getString("pname"),
								                rs.getDouble("price"),
								                rs.getInt("stock"),
								                rs.getString("pdesc")
								            );
								            products.add(product);
								        }
								        out.writeObject(products);
								        out.flush();
								    } catch (SQLException | IOException | ClassNotFoundException e) {
								        e.printStackTrace();
								    }
								}
								break;
								case "search":{
									//搜索产品信息
									try {
								        String keyword = (String) in.readObject();
								        String query = "SELECT * FROM product WHERE pname LIKE ?";
								        PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(query);
								        stmt.setString(1, "%" + keyword + "%");
								        ResultSet rs = stmt.executeQuery();
								        List<Product> products = new ArrayList<>();
								        while (rs.next()) {
								            Product product = new Product(
								                rs.getInt("pid"),
								                rs.getString("pno"),
								                rs.getString("pname"),
								                rs.getDouble("price"),
								                rs.getInt("stock"),
								                rs.getString("pdesc")
								            );
								            products.add(product);
								        }
								        out.writeObject(products);
								        out.flush();
								    } catch (SQLException | IOException | ClassNotFoundException e) {
								        e.printStackTrace();
								    }
								}
								break;
								case "statistics":{
									//统计
									String statType = (String) in.readObject();

								    switch (statType) {
								        case "totalProducts": {
								        	Connection conn = null;
								        	Statement stmt = null;
								        	ResultSet rs = null;								        	
								        	conn = ConnectionFactory.getConnection();
								        	stmt = conn.createStatement();
								        	String sql = "SELECT COUNT(*) AS total FROM product";
								        	rs = stmt.executeQuery(sql);
								        	List<Integer> resultList = new ArrayList<>();
								        	while (rs.next()) {
								                int totalCount = rs.getInt("total");
								                resultList.add(totalCount);
								            }
								            int totalCount = resultList.get(0);
								            out.writeObject(totalCount); 
								            out.flush();
								            break;
								        }
								        case "avgPrice": {
								        	Connection conn = null;
								        	Statement stmt = null;
								        	ResultSet rs = null;								        	
								        	conn = ConnectionFactory.getConnection();
								        	stmt = conn.createStatement();
								        	String sql = "SELECT AVG(price) AS avg_price FROM product";
								        	rs = stmt.executeQuery(sql);
								        	double averagePrice = 0.0;
								        	while (rs.next()) {
								        		averagePrice = rs.getDouble("avg_price");								                
								            }
								            out.writeObject(averagePrice); 
								            out.flush();
								            break;
								        }
								        default:					            
								            break;
								    }
								    out.flush();
								}
								break;
								case "export":{
									//导出
									String dir = System.getProperty("user.dir");
							        String fileName = "exported_data.txt";
							        File file = new File(dir + File.separator + fileName);
							        List<Product> products = productService.getProducts();
							        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
							            for (Product product : products) {
							                writer.write(product.toString()); 
							                writer.newLine();
							            }
							        }
							        String fPath = file.getAbsolutePath();
							        out.writeObject(fPath);
							        out.flush();
								}
								break;
								case "upload":{
									//上传
									String filename = (String)in.readObject();//从客户端读取上传文件的名字
									BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
									String dir = System.getProperty("user.dir");//获得当前工程的工作目录
									BufferedOutputStream bos = new BufferedOutputStream(
											new FileOutputStream(dir + File.separator + filename));
									byte[] buf = new byte[1024];
									int len = 0;
									while((len = bis.read(buf)) != -1) {
										bos.write(buf, 0, len);
									}
									bis.close();
									bos.close();
								}
								break;
								case "download":{
									//下载
									String filename = (String) in.readObject();
						            BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
						            String dir = System.getProperty("user.dir");
						            File downloadDir = new File(dir, "download");
						            if (!downloadDir.exists()) {
						                if (downloadDir.mkdir()) {
						                    //System.out.println("下载目录已创建: " + downloadDir.getAbsolutePath());
						                } else {
						                    //System.out.println("无法创建下载目录");
						                }
						            }
						            BufferedOutputStream bos = new BufferedOutputStream(
						                    new FileOutputStream(new File(downloadDir, filename))
						            );
						            byte[] buf = new byte[1024];
						            int len;
						            while ((len = bis.read(buf)) != -1) {
						                bos.write(buf, 0, len);
						            }
						            bis.close();
						            bos.close();
								}
								break;
								default:
									break;
								}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}