package service.datastructure;

/*
 * myStack：动手实现的Stack类
 * 两个停车场栈，用于存储停车场内的车辆和因避让而回退车辆
 */
public class myStack<E> {

	private Object[] array;
	private int currentSize;
	private int maxSize;

	public myStack(int size) {
		this.array = new Object[size];
		this.maxSize = size;
		this.currentSize = -1;
	}

	/*压入栈底*/
	public void push(E item) {
		array[++currentSize] = item;
	}

	public boolean isEmpty() {
		return currentSize == -1;
	}

	public boolean isFull() {
		return currentSize == (maxSize - 1);
	}

	public int getSize() {
		return this.currentSize;
	}

	/*返回栈顶元素*/
	@SuppressWarnings("unchecked")
	public E pop() {

		return (E) array[currentSize--];
	}
}
