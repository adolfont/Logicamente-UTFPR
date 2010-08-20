package logicamente.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class DrawingGrid {

	private Graphics graphics;
	private int width;
	private int height;
	private int gridWidth;
	private int gridHeight;
	private int nodeDiameter;
	private int cellWidth;
	private int cellHeight;
	private Color fillColor;
	private Color foreColor;
	private List<GridNode> nodes;
	private GridNode root;

	public DrawingGrid(Graphics graphics) {
		this.graphics = graphics;
		this.width = 100;
		this.height = 100;
		this.gridWidth = 10;
		this.gridHeight = 10;
		this.fillColor = Color.WHITE;
		this.foreColor = Color.BLACK;
		nodes = new ArrayList<GridNode>();
	}

	public void setBounds(int width, int height) {
		this.width = width;
		this.height = height;
		calculateCellDimensions();
	}

	public void setGrid(int gridWidth, int gridHeight) {
		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		calculateCellDimensions();
	}

	private void calculateCellDimensions() {
		this.cellWidth = width / gridWidth;
		this.cellHeight = height / gridHeight;

		if (nodeDiameter > cellWidth) {
			nodeDiameter = cellWidth;
		}
		if (nodeDiameter > cellHeight) {
			nodeDiameter = cellHeight;
		}

		if (graphics.getFontMetrics().getHeight() > cellWidth
				|| graphics.getFontMetrics().getHeight() > cellHeight) {
			// TODO o que faz?
		}

		if (nodeDiameter / 2 - 4 > 5) {
			graphics.setFont(graphics.getFont().deriveFont(
					(float) ((nodeDiameter / 2) - 4)));
		}
		// if (nodeDiameter/2-4 > 11) {
		// graphics.setFont(new Font("Dialog", Font.BOLD, nodeDiameter/2-4));
		// }
	}

	public void setNodeDiameter(int nodeDiameter) {
		this.nodeDiameter = nodeDiameter;
	}

	public void drawNode(int x, int y, String aString) {
		graphics.setColor(fillColor);
		graphics.fillOval(calculateX(x), calculateY(y), nodeDiameter,
				nodeDiameter);
		graphics.setColor(foreColor);
		graphics.drawOval(calculateX(x), calculateY(y), nodeDiameter,
				nodeDiameter);

		int stringWidth = graphics.getFontMetrics(graphics.getFont())
				.stringWidth(aString.toString());
		int stringHeight = graphics.getFontMetrics(graphics.getFont())
				.getHeight() - 3; // -3 because it was not centralized

		graphics.drawString(aString, calculateXHalfDiameter(x) - stringWidth
				/ 2, calculateLineY(y) + stringHeight / 2);
	}

	private int calculateX(int x) {
		return (x - 1) * cellWidth + cellWidth / 2 - nodeDiameter / 2;
	}

	private int calculateY(int y) {
		return (y - 1) * cellHeight + cellHeight / 2 - nodeDiameter / 2;
	}

	public void drawGridLines() {
		for (int i = 0; i <= width; i++) {
			graphics.drawLine(i * cellWidth, 0, i * cellWidth, height);
		}
		for (int i = 0; i <= height; i++) {
			graphics.drawLine(0, i * cellHeight, width, i * cellHeight);
		}
	}

	public void drawLine(int x1, int y1, int x2, int y2) {

		int x1Actual = (x1 - 1) * cellWidth + cellWidth / 2;
		int x2Actual = (x2 - 1) * cellWidth + cellWidth / 2;
		int y1Actual = (y1 - 1) * cellHeight + cellHeight / 2;
		int y2Actual = (y2 - 1) * cellHeight + cellHeight / 2;

		// Code adapted from http://forums.sun.com/thread.jspa?threadID=528037
		double phi = Math.atan2(y2Actual - y1Actual, x2Actual - x1Actual);
		double xx1 = x1Actual + (Math.cos(phi) * nodeDiameter + 1) / 2;
		double yy1 = y1Actual + (Math.sin(phi) * nodeDiameter + 1) / 2;
		double xx2 = x2Actual - (Math.cos(phi) * nodeDiameter - 1) / 2;
		double yy2 = y2Actual - (Math.sin(phi) * nodeDiameter - 1) / 2;

		// draw connection
		graphics.setColor(foreColor);
		// ((Graphics2D) graphics).draw(new Line2D.Double(xx1, yy1, xx2, yy2));
		graphics.drawLine((int) xx1, (int) yy1, (int) xx2, (int) yy2);

	}

	private int calculateXHalfDiameter(int x1) {
		return calculateX(x1) + nodeDiameter / 2;
	}

	private int calculateLineY(int y1) {
		return calculateY(y1) + nodeDiameter / 2;
	}

	public void clear() {
		graphics.clearRect(0, 0, width, height);
	}

	public void add(GridNode node) {
		if (root == null) {
			root = node;
		}
		nodes.add(node);
	}

	public List<GridNode> getNodes() {
		return nodes;
	}

	public GridNode getRootNode() {
		return root;
	}

	public void drawTree() {
		for (GridNode node: nodes){
			drawNode(node.getX(), node.getY(), node.getSymbol());
			if (node.getParent()!=null){
				drawLine(node.getX(), node.getY(), node.getParent().getX(), node.getParent().getY());
			}
		}
	}

}
