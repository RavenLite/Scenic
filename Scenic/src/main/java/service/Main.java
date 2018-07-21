package service;
import service.graph.Graph;

/**
 * @author Raven
 * 项目入口之一（另外一个入口是ScenicApplication.java）
 * 运行本入口可以进入命令行交互界面
 */
public class Main {
	public static void main(String[] args) {
		Graph myGraph = new Graph();
		myGraph.iniAll();
		Menu.showMenu(myGraph);
	}
}
