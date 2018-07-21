package service.graph;

import java.util.Iterator;
import java.util.Set;

/*
 * VNode:路径类
 * 包含路径的基本信息
 * 为VNode提供了两种构造器分别满足Graph类和ArcNode类的不同需求
 * Graph需要的路径需要包含两端端点的信息
 * ArcNode因为已知一个端点，所以只需要包含另外一个端点的信息，另外为了查找方便，还存储了
 * 另外一个端点在景点列表中的位置
 */
public class VNode {
	private int preIndex;
	private String preName;
	private int nextIndex;// 指向的节点的位置
	private String nextName;// 下一个节点的名字
	private int dis;// 距离

	public VNode(String preName, String nextName, int dis) {
		this.preName = preName;
		this.nextName = nextName;
		this.dis = dis;
	}

	public VNode(String name, int dis, Set<String> nodeSet) {
		this.nextName = name;
		this.dis = dis;

		int i = 0;
		Iterator<String> it = nodeSet.iterator();
		while (it.hasNext()) {
			if (it.next().equals(name)) {
				setIndex(i);
			}
			i++;
		}
	}

	public int getIndex() {
		return this.nextIndex;
	}

	public void setIndex(int index) {
		this.nextIndex = index;
	}

	public int getDis() {
		return this.dis;
	}

	public void setDis(int dis) {
		this.dis = dis;
	}

	public String getNextName() {
		return this.nextName;
	}

	public void setNextName(String name) {
		this.nextName = name;
	}

	public String getPreName() {
		return this.preName;
	}

	public void setPreName(String name) {
		this.preName = name;
	}

	public int getPreIndex() {
		return this.preIndex;
	}

	public void setPreIndex(int Index) {
		this.preIndex = Index;
	}
}
