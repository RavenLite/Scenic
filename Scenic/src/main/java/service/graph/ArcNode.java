package service.graph;

import service.datastructure.myArrayList;

/*
 * ArcNode:景点类
 * 包含景点的基本信息和它拥有的全部路径
 */
public class ArcNode {
	private String name;// 景点名称
	private myArrayList<VNode> adjNodes = new myArrayList<VNode>();
	private int degree;// 景点的度
	private String des;// 景点简介
	private String welcome;// 景点欢迎度
	private String rest;// 有无休息区
	private String toilet;// 有无公厕

	// Constructors
	public ArcNode() {
		
	}
	
	public ArcNode(String name) {
		this.name = name;
	}

	public ArcNode(String name, String des, String welcome, String rest, String toilet) {
		this.name = name;
		this.des = des;
		this.welcome = welcome;
		this.rest = rest;
		this.toilet = toilet;
	}

	public void updateDegree() {
		this.degree = this.adjNodes.size();
	}

	// Getters and Setters
	public myArrayList<VNode> getNodes() {
		return this.adjNodes;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDegree() {
		return this.degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public String getDes() {
		return this.des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getWelcome() {
		return this.welcome;
	}

	public void setWelcome(String welcome) {
		this.welcome = welcome;
	}

	public String getRest() {
		return this.rest;
	}

	public void setRest(String rest) {
		this.rest = rest;
	}

	public String getToilet() {
		return this.toilet;
	}

	public void setToilet(String toilet) {
		this.toilet = toilet;
	}
}
