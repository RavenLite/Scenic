package service;

import java.util.Scanner;

import service.algorithm.myHamilton;
import service.datastructure.myArrayList;
import service.graph.Graph;
import service.parking.Parking;

/*
 * Menu:菜单类，服务于通过命令行进入管理系统
 * 因为本系统有命令行和网站两种交互方式并行，所以在调用相关方法时会用int mode 这一变量
 * 确定是哪种交互模式。mode为0时代表网站交互，为1时代表命令行交互。
 */
public class Menu {
	public static void showMenu(Graph myGraph) {
		while (true) {
			System.out.println("===================================");
			System.out.println("      欢迎使用景区信息管理系统           ");
			System.out.println("        ***选择菜单***             ");
			System.out.println("===================================");
			System.out.println("1.浏览景区景点分布图");
			System.out.println("2.景点查找");
			System.out.println("3.景点排序");
			System.out.println("4.寻找最短路径");
			System.out.println("5.展示全部景点");
			System.out.println("6.展示全部线路");
			System.out.println("7.展示导游图");
			System.out.println("8.停车场管理系统");
			System.out.println("9.管理员登录");
			System.out.println("0.退出系统");
			System.out.println("===================================");

			System.out.println("请输入您的选择：");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			dealChoice(choice, myGraph);
		}
	}

	/*处理菜单操作*/
	private static void dealChoice(int choice, Graph myGraph) {
		switch (choice) {
		case 1:
			ch1_ini(myGraph);
			break;
		case 2:
			ch2_search(myGraph);
			break;
		case 3:
			ch3_sort(myGraph);
			break;
		case 4:
			ch4_shortest(myGraph);
			break;
		case 5:
			ch5_showAll(myGraph);
			break;
		case 6:
			ch6_showAllEdges(myGraph);
			break;
		case 7:
			ch7_showTourGraph(myGraph);
			break;
		case 8:
			ch8_parking(myGraph);
			break;
		case 9:
			ch9_managerMenu(myGraph);
			break;
		case 0:
			System.exit(0);
			break;
		default:
			System.out.println("输入错误，请重新输入。");
			clearConsole();
			break;
		}
	}

	private static void ch1_ini(Graph myGraph) {
		myGraph.printMatrix();
		System.out.println("===================================");
		clearConsole();
	}

	private static void ch2_search(Graph myGraph) {
		System.out.println("请输入查找内容：");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String item = scan.nextLine();
		myArrayList<String> result = myGraph.search(item);
		System.out.println("[有" + result.size() / 5 + "项符合结果]");
		System.out.println("序号" + "\t" + "名称" + "\t" + "欢迎度" + "\t" + "休息区" + "\t" + "公厕" + "\t" + "简介" + "\t");
		for (int i = 0; i < result.size(); i += 5) {
			System.out.print("No." + (i + 5) / 5 + "\t");
			System.out.print(result.get(i) + "\t");
			System.out.print(result.get(i + 2) + "\t");
			System.out.print((result.get(i + 3).equals("0") ? "无" : "有") + "\t");// 三元运算式
			System.out.print((result.get(i + 4).equals("0") ? "无" : "有") + "\t");
			System.out.print(result.get(i + 1) + "\t");
			System.out.println();
		}
		System.out.println("===================================");
		clearConsole();
	}

	private static void ch3_sort(Graph myGraph) {
		System.out.println("———————————————————————————————————");
		System.out.println("              排序方式                       ");
		System.out.println("———————————————————————————————————");
		System.out.println("1.欢迎度排序(插入排序)              ");
		System.out.println("2.岔路数排序(快速排序)              ");
		System.out.println("3.返回上级                                             ");
		System.out.println("———————————————————————————————————");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		System.out.println("———————————————————————————————————");
		switch (choice) {
		case 1:
			myArrayList<String[]> result1 = myGraph.sort_welcome();
			System.out.println("[有" + result1.size() + "项符合结果]");
			System.out.println("序号" + "\t" + "名称" + "\t" + "欢迎度" + "\t" + "休息区" + "\t" + "公厕" + "\t" + "简介" + "\t");
			for (int i = result1.size() - 1; i > -1; i--) {
				System.out.print("No." + (result1.size() - i) + "\t");
				System.out.print(result1.get(i)[0] + "\t");
				System.out.print(result1.get(i)[2] + "\t");
				System.out.print((result1.get(i)[3].equals("0") ? "无" : "有") + "\t");// 三元运算式
				System.out.print((result1.get(i)[4].equals("0") ? "无" : "有") + "\t");
				System.out.print(result1.get(i)[1] + "\t");
				System.out.println();
			}
			break;
		case 2:
			myArrayList<String[]> result2 = myGraph.sort_degree();
			System.out.println("[有" + result2.size() + "项符合结果]");
			System.out.println(
					"序号" + "\t" + "名称" + "\t" + "岔路数" + "\t" + "欢迎度" + "\t" + "休息区" + "\t" + "公厕" + "\t" + "简介" + "\t");
			for (int i = result2.size() - 1; i > -1; i--) {
				System.out.print("No." + (result2.size() - i) + "\t");
				System.out.print(result2.get(i)[0] + "\t");
				System.out.print(result2.get(i)[5] + "\t");
				System.out.print(result2.get(i)[2] + "\t");
				System.out.print((result2.get(i)[3].equals("0") ? "无" : "有") + "\t");// 三元运算式
				System.out.print((result2.get(i)[4].equals("0") ? "无" : "有") + "\t");
				System.out.print(result2.get(i)[1] + "\t");
				System.out.println();
			}
			break;
		case 3:
			clearConsole();
			showMenu(myGraph);
			break;
		default:
			System.out.println("输入错误，请重新输入。");
			clearConsole();
			break;
		}
		System.out.println("———————————————————————————————————");
		clearConsole();
	}

	private static void ch4_shortest(Graph myGraph) {
		myGraph.findShortest("", "", 1);
		clearConsole();
	}

	private static void ch5_showAll(Graph myGraph) {
		myGraph.showAll();
		clearConsole();
	}

	private static void ch6_showAllEdges(Graph myGraph) {
		myGraph.showAllEdges();
		clearConsole();
	}

	private static void ch7_showTourGraph(Graph myGraph) {
		myHamilton.findCircle(myGraph.getAdjMatrix(), myGraph);
		clearConsole();
	}

	private static void ch8_parking(Graph myGraph) {
		Parking parking = new Parking(2, 5);
		while (true) {
			System.out.println("===================================");
			System.out.println("       欢迎使用停车场管理系统           ");
			System.out.println("        ***管理员菜单***             ");
			System.out.println("===================================");
			System.out.println("1.汽车进场");
			System.out.println("2.汽车出车场");
			System.out.println("3.自动模拟");
			System.out.println("0.返回上层");
			System.out.println("===================================");

			System.out.println("请输入您的选择：");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				parking.carEnter(0, "", 1);
				break;
			case 2:
				parking.carExit(0, "", 1);
				break;
			case 3:

				break;
			case 0:
				showMenu(myGraph);
				clearConsole();
				break;
			default:
				System.out.println("输入错误，请重新输入。");
				clearConsole();
				break;
			}
		}
	}

	private static void ch9_managerMenu(Graph myGraph) {
		while (true) {
			System.out.println("===================================");
			System.out.println("      欢迎使用景区信息管理系统           ");
			System.out.println("        ***管理员菜单***             ");
			System.out.println("===================================");
			System.out.println("1.新增景点");
			System.out.println("2.删除景点");
			System.out.println("3.新增路径");
			System.out.println("4.删除路径");
			System.out.println("5.发布通知公告");
			System.out.println("0.返回上层");
			System.out.println("===================================");

			System.out.println("请输入您的选择：");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			int choice = scan.nextInt();
			dealManager(choice, myGraph);
		}
	}

	private static void dealManager(int choice, Graph myGraph) {
		FileManager file = new FileManager();
		switch (choice) {
		case 1:
			file.addNode(myGraph);
			break;
		case 2:
			file.removeNode(myGraph, "", 1);
			break;
		case 3:
			file.addEdge(myGraph, "", "", 0, 1);
			break;
		case 4:
			file.removeEdge(myGraph, "", "", 1);
			break;
		case 5:
			break;
		case 0:
			showMenu(myGraph);
			clearConsole();
			break;
		default:
			clearConsole();
			System.out.println("输入错误，请重新输入。");
			break;
		}
	}

	/* 三行空行，调用使console界面更友好 */
	private static void clearConsole() {
		for (int i = 0; i <= 3; i++) {
			System.out.println();
		}
	}
}
