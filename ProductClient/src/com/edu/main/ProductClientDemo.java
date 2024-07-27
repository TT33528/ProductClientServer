package com.edu.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.edu.beans.Product;
import com.edu.beans.User;

public class ProductClientDemo {

	private static Scanner scanner = new Scanner(System.in);
	private static ProductClient client = new ProductClient();
	
	public static void main(String[] args) {
		System.out.println("\t\t\t\t欢迎使用产品信息管理系统\t\t\t\t");
		mainmenu:
		while(true) {
			System.out.println("=======请选择 1 注册 2 登陆=======");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:
					register();
					break;
				case 2:
					boolean login = login();
					if(login)
						break mainmenu;
					else
						break;
				default:
					break;
			}
		}
		System.out.println("拜拜");
	}
	
	public static void register() {
		System.out.println("请输入姓名：");
		String name = scanner.next();
		System.out.println("请输入密码：");
		String pwd = scanner.next();
		User user = new User(name, pwd);
		String msg = (String)client.show("register", user);
		System.out.println(msg);
	}
	
	public static boolean login() {
		System.out.println("请输入姓名：");
		String name = scanner.next();
		System.out.println("请输入密码：");
		String pwd = scanner.next();
		User user = new User(name, pwd);
		boolean login = (boolean)client.show("login", user);
		if(!login) {
			System.out.println("用户名密码错误，请重新登陆");
			return false;
		}
		submenu:
		while(true) {
			if(!login)
				break;
			System.out.println("欢迎回来：" + user.getName());
			System.out.println("请选择功能序号：");
			System.out.println("1.查看所有产品信息 2.增加产品信息\n\r" +
								"3.修改产品信息 4.删除产品信息 5.多条件查询产品信息\n\r" +
								"6.搜索产品信息 7.统计 8.导出 9.上传文件 10.下载文件 11.退出");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:{
						List<Product> products1 = (List<Product>)client.show("listProducts", null);
						displayProducts(products1);
					}
					System.out.println("-----------------");
					break;
				case 2:
					System.out.println("请输入产品编号：");
					String pno = scanner.next();
					System.out.println("请输入产品名称：");
					String pname = scanner.next();
					System.out.println("请输入产品价格：");
					double price = scanner.nextDouble();
					System.out.println("请输入产品库存：");
					int stock = scanner.nextInt();
					System.out.println("请输入产品描述：");
					String pdesc = scanner.next();
					Product product = new Product(pno, pname, price, stock, pdesc);
					boolean res = (boolean)client.show("saveProduct", product);
					if(res) {
						System.out.println("保存产品成功");
					}else {
						System.out.println("保存产品失败");
					}
					System.out.println("-----------------");
					break;
				case 3:
					System.out.println("请输入要修改的产品ID：");
					int pid = scanner.nextInt();
					System.out.println("请输入新的产品编号：");
					pno = scanner.next();
					System.out.println("请输入新的产品名称：");
					pname = scanner.next();
					System.out.println("请输入新的产品价格：");
					price = scanner.nextDouble();
					System.out.println("请输入新的产品库存：");
					stock = scanner.nextInt();
					System.out.println("请输入新的产品描述：");
					pdesc = scanner.next();
					Product updatedProduct = new Product(pid,pno, pname, price, stock, pdesc);
					boolean updateRes = (boolean)client.show("updateProduct", updatedProduct);
					if(updateRes) {
						System.out.println("更新产品成功");
					}else {
						System.out.println("更新产品失败");
					}
					System.out.println("-----------------");
					break;
				case 4:
					System.out.println("请输入要删除的产品ID：");
					pid = scanner.nextInt();
					boolean deleteRes = (boolean)client.show("deleteProduct", pid);
					if(deleteRes) {
						System.out.println("删除产品成功");
					}else {
						System.out.println("删除产品失败");
					}
					System.out.println("-----------------");
					break;
				case 5:
				    Map<String, Object> searchParams = new HashMap<>();
				    System.out.println("输入搜索条件（跳过按Enter键）:");
				    scanner.nextLine(); 
				    System.out.print("产品名称包含: ");
				    String pname1 = scanner.nextLine();
				    if (!pname1.isEmpty()) {
				        searchParams.put("pname", pname1);
				    }
				    System.out.print("最低价格: ");
				    String minPrice = scanner.nextLine();
				    if (!minPrice.isEmpty()) {
				        searchParams.put("minPrice", Double.parseDouble(minPrice));
				    }
				    System.out.print("最高价格: ");
				    String maxPrice = scanner.nextLine();
				    if (!maxPrice.isEmpty()) {
				        searchParams.put("maxPrice", Double.parseDouble(maxPrice));
				    }
				    System.out.print("最低库存: ");
				    String minStock = scanner.nextLine();
				    if (!minStock.isEmpty()) {
				        searchParams.put("minStock", Integer.parseInt(minStock));
				    }
				    System.out.print("最高库存: ");
				    String maxStock = scanner.nextLine();
				    if (!maxStock.isEmpty()) {
				        searchParams.put("maxStock", Integer.parseInt(maxStock));
				    }
				    List<Product> products2 = (List<Product>) client.show("multiSearch", searchParams);
				    displayProducts(products2);
					System.out.println("-----------------");
					break;
				case 6:
					System.out.print("请输入要搜索的产品名称: ");
				    String keyword = scanner.next();
				    List<Product> searchResults = (List<Product>) client.show("search", keyword);
				    displayProducts(searchResults);
					System.out.println("-----------------");
					break;
				case 7:
					System.out.println("请选择要统计的内容：");
				    System.out.println("1. 统计产品总数");
				    System.out.println("2. 统计产品平均价格");
				    int statChoice = scanner.nextInt();

				    switch (statChoice) {
				        case 1: {
				            int totalCount = (int) client.show("statistics", "totalProducts");
				            System.out.println("产品总数为：" + totalCount);
				            break;
				        }
				        case 2: {
				            double avgPrice = (double) client.show("statistics", "avgPrice");
				            System.out.println("产品平均价格为：" + avgPrice);
				            break;
				        }
				        default:
				            System.out.println("无效的选择");
				            break;
				    }
				    System.out.println("-----------------");
					break;
				case 8:{
					String fPath = (String) client.show("export", "fPath");
					System.out.println("数据已成功导出到：" + fPath);
					}
					System.out.println("-----------------");
					break;
				case 9:{
					System.out.println("请输入你要上传文件的完整路径，例如：d:\\workspace\\user.jpg");
					String file = scanner.next();
					String resp = (String)client.upload("upload", file);
					System.out.println(resp);
					}
					System.out.println("-----------------");
					break;
				case 10:{
					System.out.println("请输入你要下载文件的完整路径，例如：d:\\workspace\\user.jpg");
					String file = scanner.next();
					String resp = (String)client.download("download", file);
					System.out.println(resp);
					}
					System.out.println("-----------------");
					break;
				case 11:
					System.out.println("感谢使用，再见！");
				    scanner.close();
				    System.exit(0);
					break;
				default:
					break;
			}
		}
		return true;
	}

	public static void displayProducts(List<Product> products) {
	    if (products == null || products.isEmpty()) {
	        System.out.println("没有找到符合条件的产品。");
	        return;
	    }
	    System.out.println("产品序号\t产品编号\t产品名称\t产品价格\t产品库存\t产品描述");
	    for (Product product1 : products) {
	    	System.out.println(product1.getPid()+"\t"+product1.getPno()+"\t"+product1.getPname()+
					"\t"+product1.getPrice()+"\t"+product1.getStock()+"\t"+product1.getPdesc());
		}
	}
	
	
}