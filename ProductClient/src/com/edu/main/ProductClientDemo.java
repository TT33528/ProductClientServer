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
		System.out.println("\t\t\t\t��ӭʹ�ò�Ʒ��Ϣ����ϵͳ\t\t\t\t");
		mainmenu:
		while(true) {
			System.out.println("=======��ѡ�� 1 ע�� 2 ��½=======");
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
		System.out.println("�ݰ�");
	}
	
	public static void register() {
		System.out.println("������������");
		String name = scanner.next();
		System.out.println("���������룺");
		String pwd = scanner.next();
		User user = new User(name, pwd);
		String msg = (String)client.show("register", user);
		System.out.println(msg);
	}
	
	public static boolean login() {
		System.out.println("������������");
		String name = scanner.next();
		System.out.println("���������룺");
		String pwd = scanner.next();
		User user = new User(name, pwd);
		boolean login = (boolean)client.show("login", user);
		if(!login) {
			System.out.println("�û���������������µ�½");
			return false;
		}
		submenu:
		while(true) {
			if(!login)
				break;
			System.out.println("��ӭ������" + user.getName());
			System.out.println("��ѡ������ţ�");
			System.out.println("1.�鿴���в�Ʒ��Ϣ 2.���Ӳ�Ʒ��Ϣ\n\r" +
								"3.�޸Ĳ�Ʒ��Ϣ 4.ɾ����Ʒ��Ϣ 5.��������ѯ��Ʒ��Ϣ\n\r" +
								"6.������Ʒ��Ϣ 7.ͳ�� 8.���� 9.�ϴ��ļ� 10.�����ļ� 11.�˳�");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1:{
						List<Product> products1 = (List<Product>)client.show("listProducts", null);
						displayProducts(products1);
					}
					System.out.println("-----------------");
					break;
				case 2:
					System.out.println("�������Ʒ��ţ�");
					String pno = scanner.next();
					System.out.println("�������Ʒ���ƣ�");
					String pname = scanner.next();
					System.out.println("�������Ʒ�۸�");
					double price = scanner.nextDouble();
					System.out.println("�������Ʒ��棺");
					int stock = scanner.nextInt();
					System.out.println("�������Ʒ������");
					String pdesc = scanner.next();
					Product product = new Product(pno, pname, price, stock, pdesc);
					boolean res = (boolean)client.show("saveProduct", product);
					if(res) {
						System.out.println("�����Ʒ�ɹ�");
					}else {
						System.out.println("�����Ʒʧ��");
					}
					System.out.println("-----------------");
					break;
				case 3:
					System.out.println("������Ҫ�޸ĵĲ�ƷID��");
					int pid = scanner.nextInt();
					System.out.println("�������µĲ�Ʒ��ţ�");
					pno = scanner.next();
					System.out.println("�������µĲ�Ʒ���ƣ�");
					pname = scanner.next();
					System.out.println("�������µĲ�Ʒ�۸�");
					price = scanner.nextDouble();
					System.out.println("�������µĲ�Ʒ��棺");
					stock = scanner.nextInt();
					System.out.println("�������µĲ�Ʒ������");
					pdesc = scanner.next();
					Product updatedProduct = new Product(pid,pno, pname, price, stock, pdesc);
					boolean updateRes = (boolean)client.show("updateProduct", updatedProduct);
					if(updateRes) {
						System.out.println("���²�Ʒ�ɹ�");
					}else {
						System.out.println("���²�Ʒʧ��");
					}
					System.out.println("-----------------");
					break;
				case 4:
					System.out.println("������Ҫɾ���Ĳ�ƷID��");
					pid = scanner.nextInt();
					boolean deleteRes = (boolean)client.show("deleteProduct", pid);
					if(deleteRes) {
						System.out.println("ɾ����Ʒ�ɹ�");
					}else {
						System.out.println("ɾ����Ʒʧ��");
					}
					System.out.println("-----------------");
					break;
				case 5:
				    Map<String, Object> searchParams = new HashMap<>();
				    System.out.println("��������������������Enter����:");
				    scanner.nextLine(); 
				    System.out.print("��Ʒ���ư���: ");
				    String pname1 = scanner.nextLine();
				    if (!pname1.isEmpty()) {
				        searchParams.put("pname", pname1);
				    }
				    System.out.print("��ͼ۸�: ");
				    String minPrice = scanner.nextLine();
				    if (!minPrice.isEmpty()) {
				        searchParams.put("minPrice", Double.parseDouble(minPrice));
				    }
				    System.out.print("��߼۸�: ");
				    String maxPrice = scanner.nextLine();
				    if (!maxPrice.isEmpty()) {
				        searchParams.put("maxPrice", Double.parseDouble(maxPrice));
				    }
				    System.out.print("��Ϳ��: ");
				    String minStock = scanner.nextLine();
				    if (!minStock.isEmpty()) {
				        searchParams.put("minStock", Integer.parseInt(minStock));
				    }
				    System.out.print("��߿��: ");
				    String maxStock = scanner.nextLine();
				    if (!maxStock.isEmpty()) {
				        searchParams.put("maxStock", Integer.parseInt(maxStock));
				    }
				    List<Product> products2 = (List<Product>) client.show("multiSearch", searchParams);
				    displayProducts(products2);
					System.out.println("-----------------");
					break;
				case 6:
					System.out.print("������Ҫ�����Ĳ�Ʒ����: ");
				    String keyword = scanner.next();
				    List<Product> searchResults = (List<Product>) client.show("search", keyword);
				    displayProducts(searchResults);
					System.out.println("-----------------");
					break;
				case 7:
					System.out.println("��ѡ��Ҫͳ�Ƶ����ݣ�");
				    System.out.println("1. ͳ�Ʋ�Ʒ����");
				    System.out.println("2. ͳ�Ʋ�Ʒƽ���۸�");
				    int statChoice = scanner.nextInt();

				    switch (statChoice) {
				        case 1: {
				            int totalCount = (int) client.show("statistics", "totalProducts");
				            System.out.println("��Ʒ����Ϊ��" + totalCount);
				            break;
				        }
				        case 2: {
				            double avgPrice = (double) client.show("statistics", "avgPrice");
				            System.out.println("��Ʒƽ���۸�Ϊ��" + avgPrice);
				            break;
				        }
				        default:
				            System.out.println("��Ч��ѡ��");
				            break;
				    }
				    System.out.println("-----------------");
					break;
				case 8:{
					String fPath = (String) client.show("export", "fPath");
					System.out.println("�����ѳɹ���������" + fPath);
					}
					System.out.println("-----------------");
					break;
				case 9:{
					System.out.println("��������Ҫ�ϴ��ļ�������·�������磺d:\\workspace\\user.jpg");
					String file = scanner.next();
					String resp = (String)client.upload("upload", file);
					System.out.println(resp);
					}
					System.out.println("-----------------");
					break;
				case 10:{
					System.out.println("��������Ҫ�����ļ�������·�������磺d:\\workspace\\user.jpg");
					String file = scanner.next();
					String resp = (String)client.download("download", file);
					System.out.println(resp);
					}
					System.out.println("-----------------");
					break;
				case 11:
					System.out.println("��лʹ�ã��ټ���");
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
	        System.out.println("û���ҵ����������Ĳ�Ʒ��");
	        return;
	    }
	    System.out.println("��Ʒ���\t��Ʒ���\t��Ʒ����\t��Ʒ�۸�\t��Ʒ���\t��Ʒ����");
	    for (Product product1 : products) {
	    	System.out.println(product1.getPid()+"\t"+product1.getPno()+"\t"+product1.getPname()+
					"\t"+product1.getPrice()+"\t"+product1.getStock()+"\t"+product1.getPdesc());
		}
	}
	
	
}