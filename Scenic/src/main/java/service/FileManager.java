package service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import service.graph.ArcNode;
import service.graph.Graph;
import service.graph.VNode;

/*
 * FileManager:用于处理和文件相关的操作（增删景点、增删路径）
 */
public class FileManager {
	private static String nodeFile = "./src/main/java/service/node.dat";// 景点信息
	private static String edgeFile = "./src/main/java/service/edge.dat";// 路径信息

	/* 新增景点 */
	public void addNode(Graph myGraph) {
		System.out.println("请依次输入新景点的名称、描述、欢迎度、有(1)无(0)休息区、有(1)无(0)公厕");
		System.out.println("(每个项目请换行)");
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String newName = scan.nextLine();
		String newDes = scan.nextLine();
		String newWelcome = scan.nextLine();
		String newRest = scan.nextLine();
		String newToilet = scan.nextLine();

		ArcNode addedNode = new ArcNode(newName, newDes, newWelcome, newRest, newToilet);
		myGraph.getNodes().add(addedNode);
		myGraph.showAll();
		System.out.println("[成功增加1个景点]");
		rewriteFile1(myGraph);
	}

	public void addNode(Graph myGraph, String newName, String newDes, String newWelcome, String newRest,
			String newToilet) {

		ArcNode addedNode = new ArcNode(newName, newDes, newWelcome, newRest, newToilet);
		myGraph.getNodes().add(addedNode);
		myGraph.showAll();
		System.out.println("[成功增加1个景点]");
		rewriteFile1(myGraph);
	}

	/* 删除景点 */
	public void removeNode(Graph myGraph, String name, int mode) {
		String oldName = "";
		if (mode == 1) {
			System.out.println("请输入您要删除的景点的名称");
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			oldName = scan.nextLine();
		} else if (mode == 0) {
			oldName = name;
		}

		/* 更改景点信息 */
		for (int i = 0; i < myGraph.getNodes().size(); i++) {
			if (myGraph.getNodes().get(i).getName().equals(oldName)) {
				myGraph.getNodes().remove(i);
			}
			myGraph.getNodes().get(i).updateDegree();// 更新景点的度
		}
		myGraph.showAll();
		rewriteFile1(myGraph);

		/* 更改路径信息 */
		// 更改每个景点拥有的路径集合
		for (int i = 0; i < myGraph.getNodes().size(); i++) {
			for (int j = 0; j < myGraph.getNodes().get(i).getNodes().size(); j++) {
				if (myGraph.getNodes().get(i).getNodes().get(j).getNextName().equals(oldName)) {
					myGraph.getNodes().get(i).getNodes().remove(j);
				}
			}
		}
		// 更改Graph拥有的路径集合
		for (int i = 0; i < myGraph.getEdges().size(); i++) {
			if (myGraph.getEdges().get(i).getPreName().equals(oldName)
					|| myGraph.getEdges().get(i).getNextName().equals(oldName)) {
				myGraph.getEdges().remove(i);
			}
		}
		myGraph.showAllEdges();
		rewriteFile2(myGraph);
		System.out.println("[成功删除1个景点]");
	}

	/* 增加路径 */
	public void addEdge(Graph myGraph, String name1, String name2, int dis0, int mode) {
		String newPreName = "";
		String newNextName = "";
		int newDis = 0;
		if (mode == 1) {
			// 输入路径基本信息
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("请输入新路径的景点1名称");
			newPreName = scan.nextLine();
			System.out.println("请输入新路径的景点2名称");
			newNextName = scan.nextLine();
			System.out.println("请输入新路径的距离权值");
			newDis = scan.nextInt();
		} else if (mode == 0) {
			newPreName = name1;
			newNextName = name2;
			newDis = dis0;
		}

		// 创建新VNode并修改Graph的edges属性
		VNode addedEdge = new VNode(newPreName, newNextName, newDis);
		myGraph.getEdges().add(addedEdge);

		// 修改相关景点的adjNodes属性
		for (int i = 0; i < myGraph.getNodes().size(); i++) {
			if (myGraph.getNodes().get(i).getName().equals(newNextName)) {
				myGraph.getNodes().get(i).getNodes().add(new VNode(newPreName, newDis, myGraph.getNodeSet()));
			}
			if (myGraph.getNodes().get(i).getName().equals(newPreName)) {
				myGraph.getNodes().get(i).getNodes().add(new VNode(newNextName, newDis, myGraph.getNodeSet()));
			}
			myGraph.getNodes().get(i).updateDegree();// 更新景点的度
		}
		myGraph.showAllEdges();
		System.out.println("[成功增加1个景点]");
		rewriteFile2(myGraph);
		myGraph.setAdjMatrix(myGraph.iniAdjMat(myGraph.getNodeSet()));// 更新邻接矩阵
	}

	/* 删除路径 */
	public void removeEdge(Graph myGraph, String name3, String name4, int mode) {
		String oldPreName = "";
		String oldNextName = "";
		if (mode == 1) {
			// 输入路径基本信息
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("请输入欲删除路径的景点1名称");
			oldPreName = scan.nextLine();
			System.out.println("请输入欲删除路径的景点2名称");
			oldNextName = scan.nextLine();
		} else if (mode == 0) {
			oldPreName = name3;
			oldNextName = name4;
		}

		// 从Graph的edges属性中删除该路径
		for (int i = 0; i < myGraph.getEdges().size(); i++) {
			if (myGraph.getEdges().get(i).getPreName().equals(oldPreName)
					&& myGraph.getEdges().get(i).getNextName().equals(oldNextName)) {
				myGraph.getEdges().remove(i);
			}
			if (myGraph.getEdges().get(i).getPreName().equals(oldNextName)
					&& myGraph.getEdges().get(i).getNextName().equals(oldPreName)) {
				myGraph.getEdges().remove(i);
			}
		}

		// 修改相关景点的adjNodes属性
		for (int i = 0; i < myGraph.getNodes().size(); i++) {
			for (int j = 0; j < myGraph.getNodes().get(i).getNodes().size(); j++) {
				if (myGraph.getNodes().get(i).getName().equals(oldPreName)
						&& myGraph.getNodes().get(i).getNodes().get(j).getNextName().equals(oldNextName)) {
					myGraph.getNodes().get(i).getNodes().remove(j);
				}
				if (myGraph.getNodes().get(i).getName().equals(oldNextName)
						&& myGraph.getNodes().get(i).getNodes().get(j).getNextName().equals(oldPreName)) {
					myGraph.getNodes().get(i).getNodes().remove(j);
				}
			}
			myGraph.getNodes().get(i).updateDegree();// 更新景点的度
		}
		myGraph.showAllEdges();
		System.out.println("[成功删除1个景点]");
		rewriteFile2(myGraph);
		myGraph.setAdjMatrix(myGraph.iniAdjMat(myGraph.getNodeSet()));// 更新邻接矩阵
	}

	private static void rewriteFile1(Graph myGraph) {
		try {
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(nodeFile)), "UTF-8"));
			for (int i = 0; i < myGraph.getNodes().size(); i++) {
				out.write(myGraph.getNodes().get(i).getName() + "——" + myGraph.getNodes().get(i).getDes() + "——"
						+ myGraph.getNodes().get(i).getWelcome() + "——" + myGraph.getNodes().get(i).getRest() + "——"
						+ myGraph.getNodes().get(i).getToilet());
				out.newLine(); // 注意\n不一定在各种计算机上都能产生换行的效果
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail");
		}
	}

	/**/
	private static void rewriteFile2(Graph myGraph) {
		try {
			BufferedWriter out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(new File(edgeFile)), "UTF-8"));
			for (int i = 0; i < myGraph.getEdges().size(); i++) {
				out.write(myGraph.getEdges().get(i).getPreName() + "——" + myGraph.getEdges().get(i).getNextName() + "——"
						+ myGraph.getEdges().get(i).getDis());
				out.newLine(); // 注意\n不一定在各种计算机上都能产生换行的效果
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Fail");
		}
	}
}
