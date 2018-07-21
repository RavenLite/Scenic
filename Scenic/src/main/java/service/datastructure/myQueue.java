package service.datastructure;

import service.parking.Car;

/*
 * myQueue：动手实现的Queue类
 * 便道的队列，里面的元素为car类
 */
public class myQueue {

	private Car rear;// 队列尾指针
	private Car front;// 队列头指针
	private int currentSize;// 当前队列大小

	// 进队列
	public void enQueue(Car item) {
		this.currentSize++;
		if (front == null) {
			front = rear = item;
		} else {
			rear.setNext(item);
			rear = rear.getNext();
		}
	}

	// 出队列
	public Car deQueue() {
		this.currentSize--;
		Car car = front;
		front = car.getNext();
		return car;
	}

	public int getSize() {
		return this.currentSize;
	}

	public boolean isEmpty() {

		return front == null;
	}

}
