/**
 * 
 */
package service.algorithm;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/*
 * myDijkstra:利用迪杰克斯拉算法实现最短路径的搜索
 */
public class myDijkstra {
	private static String pathTip = "-->";// 路径标记
	private static Map<String, Object> pathInfoMap = new LinkedHashMap<String, Object>(); // 路径和距离信息

	public Object[] run(int[][] adjMatrix, Set<String> nodeSet, String preName, String nextName) {
		/* 将景点集合转变为数组，方便后续操作 */
		String[] point = new String[nodeSet.size()];
		Iterator<String> it = nodeSet.iterator();
		int i = 0;
		while (it.hasNext()) {
			point[i] = it.next();
			i++;
		}
		/* 开始获取最短路信息 */
		for (int start = 0; start < point.length; start++) {
			getPathInfo(adjMatrix, start, point);
		}
		/* 打印输出最短路径 */
		Object[] temp = printPathInfo(preName, nextName);
		return temp;

	}

	public static void getPathInfo(int[][] adjMatrix, int start, String[] point) {
		// 接受一个有向图的权重矩阵，和一个起点编号start（从0编号，顶点存在数组中）
		int n = adjMatrix.length; // 顶点个数
		int[] shortPath = new int[n]; // 保存start到其他各点的最短路径
		String[] path = new String[n]; // 保存start到其他各点最短路径的字符串表示
		for (int i = 0; i < n; i++) {
			path[i] = new String(point[start] + pathTip + point[i]);
		}
		int[] visited = new int[n]; // 标记当前该顶点的最短路径是否已经求出,1表示已求出
		// 初始化，第一个顶点已经求出
		shortPath[start] = 0;
		visited[start] = 1;
		for (int count = 1; count < n; count++) { // 要加入n-1个顶点
			int k = -1; // 选出一个距离初始顶点start最近的未标记顶点
			int dmin = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				if (visited[i] == 0 && adjMatrix[start][i] < dmin) {
					dmin = adjMatrix[start][i];
					k = i;
				}
			}
			// 将新选出的顶点标记为已求出最短路径，且到start的最短路径就是dmin
			shortPath[k] = dmin;
			visited[k] = 1;
			// 以k为中间点，修正从start到未访问各点的距离
			for (int i = 0; i < n; i++) {
				if (visited[i] == 0 && adjMatrix[start][k] + adjMatrix[k][i] < adjMatrix[start][i]) {
					adjMatrix[start][i] = adjMatrix[start][k] + adjMatrix[k][i];
					path[i] = path[k] + pathTip + point[i];
				}
			}
		}
		for (int i = 0; i < n; i++) {
			Object[] objects = new Object[2];
			objects[0] = path[i];
			objects[1] = shortPath[i];
			pathInfoMap.put(point[start] + pathTip + point[i], objects);
		}

	}

	/**
	 * 打印路径信息和距离
	 */
	private static Object[] printPathInfo(String preName, String nextName) {
		Object[] temp = new String[2];
		for (Entry<String, Object> entry : pathInfoMap.entrySet()) {
			String key = entry.getKey();
			Object[] objects = (Object[]) entry.getValue();
			if (key.indexOf(preName) != -1 && key.indexOf(nextName) != -1
					&& key.indexOf(preName) < key.indexOf(nextName)) {
				System.out.println(key + ":" + objects[0] + "  路径长度：" + objects[1]);
				temp[0] = objects[0];
				temp[1] = objects[1].toString();
			}
		}
		pathInfoMap.clear();// 清空map
		return temp;
	}
}
