package service.parking;

/*
 * Car:车的相关信息类
 */
public class Car {

	private Car next;// 指向下一辆车的引用
	private int number; // 车牌号
	private String arriveTime; // 到达时间

	public Car(int num, String time) {
		this.number = num;
		this.arriveTime = time;
		this.next = null;

	}

	public Car getNext() {
		return next;
	}

	public void setNext(Car next) {
		this.next = next;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

}
