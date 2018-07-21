package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import service.graph.Graph;
import service.parking.Parking;
/**
 * @author Raven
 * 项目入口之一（另外一个入口是Main.java）
 * 运行本入口可开启Springboot服务器，从浏览器进入网页交互界面
 */
@SpringBootApplication
@ComponentScan(basePackages = { "Controller" })
public class ScenicApplication {

	public static Graph myGraph;
	public static Parking parking;

	// 初始化操作，初始化景区信息和停车场信息
	public static void main(String[] args) {
		Graph myGraph = new Graph();
		setGraph(myGraph);
		myGraph.iniAll();
		Parking parking = new Parking(2, 5);
		setParking(parking);
		SpringApplication.run(ScenicApplication.class, args);
	}

	private static void setGraph(Graph myGraph) {
		ScenicApplication.myGraph = myGraph;
	}

	public static Graph getGraph() {
		return ScenicApplication.myGraph;
	}

	public static Parking getParking() {
		return ScenicApplication.parking;
	}

	private static void setParking(Parking parking) {
		ScenicApplication.parking = parking;
	}
}
