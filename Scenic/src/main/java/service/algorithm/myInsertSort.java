/**
 * 
 */package service.algorithm;

import service.datastructure.myArrayList;

/*
 * myInsertSort:利用插入排序对景点按照欢迎度进行排序
 */
public class myInsertSort {
	/**
	 * sort_welcome辅助函数，实现了插入排序
	 * 
	 * @param result 全部景点信息
	 * @return 排序结果
	 */
	public static myArrayList<String[]> InsertSort(myArrayList<String[]> result) {
		int n = result.size();// 总数
		int i;

		for (i = 1; i < n; i++) {
			insert(result, i);
		}
		return result;
	}

	/**
	 * InsertSort辅助函数
	 * 
	 * @param result 全部景点信息
	 * @param i 循环次数
	 */
	private static void insert(myArrayList<String[]> result, int i) {
		String[] target = new String[5];
		System.arraycopy(result.get(i), 0, target, 0, 5);

		int j = i;
		while (Integer.parseInt(result.get(j - 1)[2]) > Integer.parseInt(target[2])) {
			System.arraycopy(result.get(j - 1), 0, result.get(j), 0, 5);
			j--;
			if (j == 0)
				break;
		}
		System.arraycopy(target, 0, result.get(j), 0, 5);
	}

}
