package logicamente.drawer;


public class GridNode {

	private int x;
	private int y;
	private String symbol;
	private GridNode parent;

	public GridNode(int x, int y, String symbol) {
		this.x = x;
		this.y = y;
		this.symbol = symbol;
		parent = null;
	}

	public void setParent(GridNode parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		String result = symbol + " (" + x + "," + y + ")";
		if (parent != null)
			result += " (" + parent.toString() + ")";
		return result;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getSymbol() {
		return symbol;
	}

	public GridNode getParent() {
		return parent;
	}

}
