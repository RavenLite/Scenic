package service.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import service.algorithm.*;
import service.datastructure.myArrayList;

/*
 * Graph:图类，存储图内全部节点（景点）和边（路径）
 * 另外还包含非常有用的邻接矩阵
 */
public class Graph {
	private static myArrayList<ArcNode> nodes;// 节点列表
	private static myArrayList<VNode> edges;// 边列表
	private int V;// 节点数
	private int E;// 边数
	private int[][] adjMatrix;// 邻接矩阵
	private static Set<String> nodeSet = null;// 景点名称列表

	/* 初始化操作 */
	public void iniAll() {
		String[] srcData = readIn();// 从edge.dat文件读取边数据，每行作为数组的一项
		String[] srcData2 = readNode();// 从node.dat文件读取景点数据，每行作为数组的一项
		edges = iniEdges(srcData);// 初始化图里的边
		Graph.nodeSet = findNode(srcData2);// 分析文件内容，找到全部景点的名字
		this.setV(nodeSet.size());// 设置图的V
		nodes = iniArc(nodeSet);// 设置图的节点列表，nodes内元素顺序与nodeSet是一致的，也与下面的邻接矩阵adjMatrix内元素顺序是一致的
		for (int i = 0; i < nodes.size(); i++) {
			findNext(nodes.get(i), srcData, nodeSet);
		} // 设置景点包含的边信息
		adjMatrix = iniAdjMat(nodeSet);// 设置邻接矩阵
		moreInfo();// 丰富景点信息
		System.out.println("---初始化完成---");
	}

	public Object[] findShortest(String name1, String name2, int mode) {
		String preName = "";
		String nextName = "";
		if(mode == 1) {
			// 输入欲查找路径基本信息
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("请输入您要查找的路径景点1");
			preName = scan.nextLine();
			System.out.println("请输入您要查找的路径景点2");
			nextName = scan.nextLine();
		}else if(mode == 0) {
			preName = name1;
			nextName = name2;
		}

		myDijkstra myDijkstra = new myDijkstra();
		Object[] temp = myDijkstra.run(adjMatrix, nodeSet, preName, nextName);
		
		return temp;

	}

	/**
	 * 展示当前全部景点信息
	 */
	public void showAll() {
		myArrayList<String> result = new myArrayList<String>();
		int i = 0;
		while (i < nodes.size()) {
			result.add(nodes.get(i).getName());
			result.add(nodes.get(i).getDes());
			result.add(nodes.get(i).getWelcome());
			result.add(nodes.get(i).getRest());
			result.add(nodes.get(i).getToilet());
			i++;
		}
		System.out.println("Total:" + i);
		System.out.println("[当前共有" + result.size() / 5 + "个景点]");
		System.out.println("序号" + "\t" + "名称" + "\t" + "欢迎度" + "\t" + "休息区" + "\t" + "公厕" + "\t" + "简介" + "\t");
		for (int j = 0; j < result.size(); j += 5) {
			System.out.print("No." + (j + 5) / 5 + "\t");
			System.out.print(result.get(j) + "\t");
			System.out.print(result.get(j + 2) + "\t");
			System.out.print((result.get(j + 3).equals("0") ? "无" : "有") + "\t");// 三元运算式
			System.out.print((result.get(j + 4).equals("0") ? "无" : "有") + "\t");
			System.out.print(result.get(j + 1) + "\t");
			System.out.println();
		}
	}

	/**
	 * 搜索功能，用到了字符串的indexOf函数，判断景点名称和内容中是否存在关键词
	 * state变量用来标记当前景点是否已经被加入过，避免因为名称和简介同时含有关键词而被重复添加
	 * 
	 * @param 关键词内容
	 * @return 搜索结果
	 */
	public myArrayList<String> search(String item) {
		myArrayList<String> result = new myArrayList<String>();// 返回结果
		int i = 0;

		while (i < nodes.size()) {
			int state = 0;// 避免因为名称和简介里都有关键字而重复打印
			if (nodes.get(i).getName().indexOf(item) != -1) {
				result.add(nodes.get(i).getName());
				result.add(nodes.get(i).getDes());
				result.add(nodes.get(i).getWelcome());
				result.add(nodes.get(i).getRest());
				result.add(nodes.get(i).getToilet());
				state = 1;
			}
			if (nodes.get(i).getDes().indexOf(item) != -1 && state == 0) {
				result.add(nodes.get(i).getName());
				result.add(nodes.get(i).getDes());
				result.add(nodes.get(i).getWelcome());
				result.add(nodes.get(i).getRest());
				result.add(nodes.get(i).getToilet());
			}
			i++;
		}

		return result;
	}

	/**
	 * 排序功能-根据欢迎度排序-插入排序算法
	 * 
	 * @return result 排序结果
	 */
	public myArrayList<String[]> sort_welcome() {
		myArrayList<String[]> result = new myArrayList<String[]>();// 返回结果

		int i = 0;
		while (i < nodes.size()) {
			String[] tempNode = new String[5];
			tempNode[0] = nodes.get(i).getName();
			tempNode[1] = nodes.get(i).getDes();
			tempNode[2] = nodes.get(i).getWelcome();
			tempNode[3] = nodes.get(i).getRest();
			tempNode[4] = nodes.get(i).getToilet();
			result.add(tempNode);
			i++;
		}
		result = myInsertSort.InsertSort(result);
		return result;
	}

	/**
	 * 排序功能-根据岔路数（景点度）排序-快速排序算法
	 * 
	 * @return result 排序结果
	 */
	public myArrayList<String[]> sort_degree() {
		myArrayList<String[]> result = new myArrayList<String[]>();// 返回结果

		int i = 0;
		while (i < nodes.size()) {
			String[] tempNode = new String[6];
			tempNode[0] = nodes.get(i).getName();
			tempNode[1] = nodes.get(i).getDes();
			tempNode[2] = nodes.get(i).getWelcome();
			tempNode[3] = nodes.get(i).getRest();
			tempNode[4] = nodes.get(i).getToilet();
			tempNode[5] = String.valueOf(nodes.get(i).getDegree());
			result.add(tempNode);
			i++;
		}
		result = myQuickSort.QuickSort(result);
		return result;
	}

	/* 读取节点文件 */
	private static String[] readNode() {
		File firstFile = new File("./src/main/java/service/node.dat");
		String[] srcData = new String[20];
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(firstFile), "utf-8"));
			String line = "";
			int i = 0;
			while ((line = in.readLine()) != null) {
				// System.out.println(line);
				srcData[i] = line;
				i++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("file is not fond");
		} catch (IOException e) {
			System.out.println("Read or write Exceptioned");
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return srcData;
	}

	// 根据node.dat文件里的内容补全景点的信息
	private static void moreInfo() {
		String[] srcData = readNode();

		int i = 0;
		while (i < nodes.size()) {
			String[] tempString = null;
			tempString = srcData[i].split("——");
			ArcNode currentNode = getNode(tempString[0]);
			currentNode.setName(tempString[0]);
			currentNode.setDes(tempString[1]);
			currentNode.setWelcome(tempString[2]);
			currentNode.setRest(tempString[3]);
			currentNode.setToilet(tempString[4]);
			i++;
		}
	}

	// moreInfo()的辅助函数：定位当前景点
	private static ArcNode getNode(String name) {
		int i = 0;
		while (i < nodes.size()) {
			if (nodes.get(i).getName().equals(name)) {
				return nodes.get(i);
			}
			i++;
		}
		return null;
	}

	// 打印邻接矩阵的外部接口
	public void printMatrix() {
		print(this.adjMatrix, nodeSet);
	}

	/* 打印邻接矩阵 */
	private static void print(int[][] adjMatrix, Set<String> nodeSet) {
		/* 打印标题栏 */
		System.out.print("\t");
		Iterator<String> it = nodeSet.iterator();
		while (it.hasNext()) {
			System.out.print(it.next() + "\t");
		}
		System.out.println();
		Object[] tempArr = nodeSet.toArray();// 转变为数组方便下面使用

		/* 打印内容 */
		for (int i = 0; i < nodes.size(); i++) {
			System.out.print(tempArr[i] + "\t");
			for (int j = 0; j < nodes.size(); j++) {
				System.out.print(adjMatrix[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/* 初始化邻接矩阵 */
	public int[][] iniAdjMat(Set<String> nodeSet) {
		int size = nodeSet.size();
		int[][] adjMatrix = new int[size][size];

		/* 设置没有连通边的点之间的距离32767 */
		for (int i = 0; i < nodeSet.size(); i++) {
			for (int j = 0; j < nodeSet.size(); j++) {
				adjMatrix[i][j] = 32767;
			}
		}

		/* 设置与之间的距离0 */
		for (int i = 0; i < nodeSet.size(); i++) {
			for (int j = 0; j < nodeSet.size(); j++) {
				if (i == j)
					adjMatrix[i][j] = 0;
			}
		}

		/* 设置有连通边的点之间的距离 */
		for (int i = 0; i < nodeSet.size(); i++) {
			for (int j = 0; j < nodes.get(i).getNodes().size(); j++) {
				adjMatrix[i][nodes.get(i).getNodes().get(j).getIndex()] = nodes.get(i).getNodes().get(j).getDis();
			}
		}
		return adjMatrix;
	}

	/* 找到每一个景点的连通边 */
	private static void findNext(ArcNode tempNode, String[] srcData, Set<String> nodeSet) {
		int i = 0;
		while (srcData[i] != null) {
			String[] tempString = null;
			tempString = srcData[i].split("——");
			if (tempNode.getName().equals(tempString[0])) {
				tempNode.getNodes().add(new VNode(tempString[1], Integer.parseInt(tempString[2]), nodeSet));
			}
			if (tempNode.getName().equals(tempString[1])) {
				tempNode.getNodes().add(new VNode(tempString[0], Integer.parseInt(tempString[2]), nodeSet));
			}
			i++;
		}
		tempNode.setDegree(tempNode.getNodes().size());// 设置结点的度
	}

	/* 根据文件初始化 ArcNode 的 ArrayList */
	private static myArrayList<ArcNode> iniArc(Set<String> nodeSet) {
		myArrayList<ArcNode> ArcList = new myArrayList<ArcNode>();
		Iterator<String> it = nodeSet.iterator();
		while (it.hasNext()) {
			ArcList.add(new ArcNode(it.next()));
		}
		return ArcList;
	}

	/* 根据文件初始化 Graph 的 edges */
	private static myArrayList<VNode> iniEdges(String[] srcData) {
		myArrayList<VNode> VList = new myArrayList<VNode>();
		int i = 0;
		while (srcData[i] != null) {
			String[] tempString = new String[2];
			tempString = srcData[i].split("——");
			VNode tempEdge = new VNode(tempString[0], tempString[1], Integer.parseInt(tempString[2]));
			VList.add(tempEdge);
			i++;
		}
		return VList;
	}

	/* 展示所有边的外部接口 */
	public void showAllEdges() {
		printEdges();
	}

	/* 展示所有边 */
	private static void printEdges() {
		System.out.println("[有" + edges.size() + "项符合结果]");
		System.out.println("序号" + "\t" + "景点1" + "\t" + "景点2" + "\t" + "距离");
		for (int i = 0; i < edges.size(); i++) {
			System.out.print("No." + (i + 1) + "\t" + edges.get(i).getPreName() + "\t" + edges.get(i).getNextName()
					+ "\t" + edges.get(i).getDis());
			System.out.println();
		}
	}

	/* 读入文件 */
	private static String[] readIn() {
		File firstFile = new File("./src/main/java/service/edge.dat");
		String[] srcData = new String[20];
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(firstFile), "UTF-8"));
			String line = "";
			int i = 0;
			while ((line = in.readLine()) != null) {
				// System.out.println(line);
				srcData[i] = line;
				i++;
			}
		} catch (FileNotFoundException e) {
			System.out.println("file is not fond");
		} catch (IOException e) {
			System.out.println("Read or write Exceptioned");
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return srcData;
	}

	/**
	 * 提取找到所有景点
	 * 
	 * @param srcData
	 */
	private static Set<String> findNode(String[] srcData) {
		int i = 0;
		Set<String> nodeSet = new HashSet<String>();
		while (srcData[i] != null) {
			String[] tempString = null;
			tempString = srcData[i].split("——");
			nodeSet.add(tempString[0]);
			i++;
		}
		return nodeSet;
	}

	public int getV() {
		return this.V;
	}

	public void setV(int V) {
		this.V = V;
	}
	
	public int getE() {
		return this.E;
	}

	public void setE(int E) {
		this.E = E;
	}

	public myArrayList<ArcNode> getNodes() {
		return Graph.nodes;
	}

	public myArrayList<VNode> getEdges() {
		return Graph.edges;
	}

	public Set<String> getNodeSet() {
		return Graph.nodeSet;
	}

	public void setAdjMatrix(int[][] adjMatrix) {
		this.adjMatrix = adjMatrix;
	}
	
	public int[][] getAdjMatrix(){
		return this.adjMatrix;
	}

}
