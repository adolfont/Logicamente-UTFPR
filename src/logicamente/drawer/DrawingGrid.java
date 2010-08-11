package logicamente.drawer;

import java.awt.Graphics;

public class DrawingGrid {

	private Graphics graphics;
	private int width;
	private int height;
	private int gridWidth;
	private int gridHeight;
	private int nodeDiameter;
	private int cellWidth;
	private int cellHeight;

	public DrawingGrid(Graphics graphics) {
		this.graphics = graphics;
		this.width = 100;
		this.height = 100;
		this.gridWidth = 10;
		this.gridHeight = 10;
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
			System.out.println("NÃO PODE DIMINUIR MAIS!");
		}

	}

	public void setNodeDiameter(int nodeDiameter) {
		this.nodeDiameter = nodeDiameter;
	}

	public void drawNode(int x, int y, String aString) {

		graphics.drawOval(calculateX(x), calculateY(y), nodeDiameter,
				nodeDiameter);

		int stringWidth = graphics.getFontMetrics(graphics.getFont())
				.stringWidth(aString.toString());
		int stringHeight = graphics.getFontMetrics(graphics.getFont())
				.getHeight();

		graphics.drawString(aString, calculateLineX(x) - stringWidth / 2,
				calculateLineY(y) + stringHeight / 2);
	}

	private int calculateX(int x) {
		return (x - 1) * cellWidth + cellWidth / 2 - nodeDiameter / 2;
	}

	private int calculateY(int y) {
		return (y - 1) * cellHeight + cellHeight / 2 - nodeDiameter / 2;
	}

	public void drawGridLines() {

		for (int i = 1; i <= width; i++) {
			graphics.drawLine(i * cellWidth, 0, i * cellWidth, height);
		}
		for (int i = 1; i <= height; i++) {
			graphics.drawLine(0, i * cellHeight, width, i * cellHeight);
		}
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		// y1 == y2
		// x1 > x2
		// y1 > y2

		if (x1 == x2) {
			if (y1 > y2) {
				int aux = y2;
				y2 = y1;
				y1 = aux;
			}
			int x = calculateLineX(x1);
			int ySup = calculateLineY(y1) + nodeDiameter / 2;
			int yInf = calculateLineY(y2) - nodeDiameter / 2;
			graphics.drawLine(x, ySup, x, yInf);
		} else {

			if (y1 == y2) {
				if (x1 > x2) {
					int aux = x2;
					x2 = x1;
					x1 = aux;
				}
				int y = calculateLineY(y1);
				int xLeft = calculateLineX(x1) + nodeDiameter / 2;
				int xRight = calculateLineX(x2) - nodeDiameter / 2;
				graphics.drawLine(xLeft, y, xRight, y);

			} else {
				if (x1 > x2) {
					drawLine(x2, y2, x1, y1);
				} else {
					// x1 < x2 & y1 > y2
					graphics.drawLine(calculateLineXLeft(x1),
							calculateLineY(y1), calculateLineXRight(x2),
							calculateLineY(y2));
				}

			}
		}
	}

	private int calculateLineX(int x1) {
		return calculateX(x1) + nodeDiameter / 2;
	}

	private int calculateLineXLeft(int x) {
		return calculateX(x) + nodeDiameter;
	}

	private int calculateLineXRight(int x) {
		return calculateX(x);
	}

	private int calculateLineY(int y1) {
		return calculateY(y1) + nodeDiameter / 2;
	}

}
