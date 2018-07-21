package service.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * myArrayList:动手实现的ArrayList类
 * 实现了Java泛型，应用于存储景点ArcNode，路线VNode等
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class myArrayList<T> extends ArrayList {
	private Object[] elementData;// 存放Object的数组
	private int size;// ArrayList大小

	public myArrayList() {
		this(10);// 如果不指定大小，默认大小为10
	}

	public myArrayList(int initSize) {
		if (initSize < 0) {
			throw new IllegalArgumentException("IllegalArgument:" + initSize);// 参数不合法，抛出异常
		}
		elementData = new Object[initSize];
	}

	public void add(T obj) {// 在末尾条件元素
		checkCapacity(size + 1);// 插入一个元素，至少需要size+1大小的空间
		elementData[this.size++] = obj;
	}

	public void add(int index, T obj) {// 在index处插入元素obj
		RangeCheck(index);
		checkCapacity(size + 1);// 插入一个元素，至少需要size+1大小的空间
		System.arraycopy(elementData, index, elementData, index + 1, size - index);// 将index后的元素都后移一个位置
		elementData[index] = obj;
		size++;
	}

	private void checkCapacity(int needCapacity) {// 检查ArrayList开辟的空间是否足够，如果不足则进行扩容
		if (needCapacity > elementData.length) {// 空间不足，扩容
			Object oldelementData[] = elementData;
			int newSize = this.size * 2 + 1;// 扩容的空间
			elementData = new Object[newSize];
			elementData = Arrays.copyOf(oldelementData, newSize);
		}
	}

	private void RangeCheck(int index) {// 检查索引是否合法
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("IllegalArgument" + index);// 出界，抛出异常
		}
	}

	@SuppressWarnings("unchecked")
	public T get(int index) {// 根据索引返回对象
		RangeCheck(index);
		return (T) elementData[index];
	}

	@SuppressWarnings("unchecked")
	public T set(int index, T obj) {// 将index位置置为obj
		RangeCheck(index);
		T oldvalue = (T) elementData[index];
		elementData[index] = obj;// 设置新值
		return oldvalue;// 返回旧值
	}

	@SuppressWarnings("unchecked")
	public T remove(int index) {// 删除index处的元素
		RangeCheck(index);
		T oldValue = (T) elementData[index];
		int moveNum = size - index - 1;
		if (moveNum > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, moveNum);
		}
		elementData[--size] = null;// 让垃圾回收器回收
		return oldValue;// 返回旧值
	}

	public boolean remove(T obj) {// 删除等于obj的元素，成功返回true，失败返回false
		if (obj == null) {
			for (int i = 0; i < size; i++) {
				if (elementData[i] == null) {
					fastremove(i);
					return true;
				}
			}
		} else {
			for (int i = 0; i < size; i++) {
				if (obj.equals(elementData[i])) {// obj不为null,一定要在前，否则使用elementData[i]调用equals()可能导致控制在异常
					fastremove(i);
					return true;
				}
			}
		}

		return false;
	}

	public void fastremove(int index) {
		int moveNum = size - index - 1;
		if (moveNum > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, moveNum);
		}
		elementData[--size] = null;// 让垃圾回收器回收
	}

	public int size() {
		return this.size;
	}

	public int length() {
		return elementData.length;
	}
}
