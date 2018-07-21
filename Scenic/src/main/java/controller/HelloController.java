/**
 * 
 */
package controller;

import java.util.Set;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ScenicApplication;

import service.FileManager;
import service.algorithm.myHamilton;
import service.datastructure.myArrayList;
import service.datastructure.myQueue;
import service.datastructure.myStack;
import service.graph.ArcNode;
import service.graph.VNode;
import service.parking.Car;

/*
 * HelloController:Springboot框架重要组成部分，为网站请求数据提供接口
 */
@RestController
@CrossOrigin("http://localhost:8080")
public class HelloController {

	// 返回全部景点信息
	@RequestMapping(value = "/listallnodes", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public myArrayList<ArcNode> GetNodesController() {
		return ScenicApplication.getGraph().getNodes();
	}

	// 返回全部路线信息
	@RequestMapping(value = "/listalledges", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public myArrayList<VNode> GetEdgesController() {
		return ScenicApplication.getGraph().getEdges();
	}

	// 返回邻接矩阵
	@RequestMapping(value = "/getAdjMatrix", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public int[][] MatrixController() {
		return ScenicApplication.getGraph().getAdjMatrix();
	}

	// 返回全部景点名称
	@RequestMapping(value = "/getNodeSet", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public Set<String> NodesSetController() {
		return ScenicApplication.getGraph().getNodeSet();
	}

	// 搜索
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public myArrayList<String> SearchController(@RequestParam("search") String search) {
		return ScenicApplication.getGraph().search(search);
	}

	// 欢迎度排行
	@RequestMapping(value = "/sortWelcome", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public myArrayList<String[]> SortWelcomeController() {
		return ScenicApplication.getGraph().sort_welcome();
	}

	// 岔路数排行
	@RequestMapping(value = "/sortDegree", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public myArrayList<String[]> SortDegreeController() {
		return ScenicApplication.getGraph().sort_degree();
	}

	// 管理员登录验证
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public boolean SearchController(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		return (username.equals("admin") && password.equals("admin"));
	}

	// 新增景点
	@RequestMapping(value = "/addNode", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public void AddNodeController(@RequestParam("name") String name, @RequestParam("des") String des,
			@RequestParam("welcome") String welcome, @RequestParam("rest") String rest,
			@RequestParam("toilet") String toilet) {
		FileManager file = new FileManager();
		file.addNode(ScenicApplication.getGraph(), name, des, welcome, rest, toilet);
	}

	// 删除景点
	@RequestMapping(value = "/removeNode", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public void RemoveNodeController(@RequestParam("name") String name) {
		FileManager file = new FileManager();
		file.removeNode(ScenicApplication.getGraph(), name, 0);
		;
	}

	// 新增路线
	@RequestMapping(value = "/addEdge", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public void AddEdgeController(@RequestParam("name1") String name1, @RequestParam("name2") String name2,
			@RequestParam("dis") int dis) {
		FileManager file = new FileManager();
		file.addEdge(ScenicApplication.getGraph(), name1, name2, dis, 0);
		;
	}

	// 删除路线
	@RequestMapping(value = "/removeEdge", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public void RemoveEdgeController(@RequestParam("name3") String name3, @RequestParam("name4") String name4) {
		FileManager file = new FileManager();
		file.removeEdge(ScenicApplication.getGraph(), name3, name4, 0);
	}

	// 删除路线
	@RequestMapping(value = "/findShortest", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public Object[] FindShortestController(@RequestParam("name1") String name1, @RequestParam("name2") String name2) {
		return ScenicApplication.getGraph().findShortest(name1, name2, 0);
	}

	// 浏览导游图
	@RequestMapping(value = "/tourgraph", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public String TourGraphController(@RequestParam("name") String name) {
		String temp = "";
		// temp += ScenicApplication.getGraph().findShortest(name, "花卉园", 0);
		temp += myHamilton.findCircle(ScenicApplication.getGraph().getAdjMatrix(), ScenicApplication.getGraph());
		return temp;
	}

	// 获取停车场内汽车
	@RequestMapping(value = "/getenterstack", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public myStack<Car> GetEnterController() {
		return ScenicApplication.getParking().getEnterStack();
	}

	// 获取停车场内汽车
	@RequestMapping(value = "/gettempparking", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public myQueue GetTempParkingController() {
		return ScenicApplication.getParking().getTempParking();
	}

	// 汽车进场
	@RequestMapping(value = "/enterstack", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public String enterStackController(@RequestParam("num") String num, @RequestParam("arvTime") String arvTime) {
		System.out.println("进场程序开启1");
		return ScenicApplication.getParking().carEnter(Integer.parseInt(num), arvTime, 0);
	}
	
	// 汽车出场
	@RequestMapping(value = "/exitstack", method = RequestMethod.GET)
	@CrossOrigin("http://localhost:8080")
	public String exitStackController(@RequestParam("num") String num, @RequestParam("levTime") String levTime) {
		System.out.println("出场程序开启1");
		return ScenicApplication.getParking().carExit(Integer.parseInt(num), levTime, 0);
	}
}
