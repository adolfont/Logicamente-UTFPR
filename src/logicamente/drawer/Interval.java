package logicamente.drawer;

public class Interval {

	private int xLeft, xRight;

	public Interval(int xLeft, int xRight) {
		this.xLeft = xLeft;
		this.xRight = xRight;
	}

	public int getXLeft() {
		return xLeft;
	}

	public void setXLeft(int left) {
		xLeft = left;
	}

	public int getXRight() {
		return xRight;
	}

	public void setXRight(int right) {
		xRight = right;
	}

}
