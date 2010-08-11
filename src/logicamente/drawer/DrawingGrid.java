package logicamente.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

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

	public DrawingGrid(Graphics graphics) {
		this.graphics = graphics;
		this.width = 100;
		this.height = 100;
		this.gridWidth = 10;
		this.gridHeight = 10;
		this.fillColor = Color.WHITE;
		this.foreColor = Color.BLACK;
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

		graphics.setColor(fillColor);
		graphics.fillOval(calculateX(x), calculateY(y), nodeDiameter,
				nodeDiameter);
		graphics.setColor(foreColor);
		graphics.drawOval(calculateX(x), calculateY(y), nodeDiameter,
				nodeDiameter);

		int stringWidth = graphics.getFontMetrics(graphics.getFont())
				.stringWidth(aString.toString());
		int stringHeight = graphics.getFontMetrics(graphics.getFont())
				.getHeight();

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

		for (int i = 1; i <= width; i++) {
			graphics.drawLine(i * cellWidth, 0, i * cellWidth, height);
		}
		for (int i = 1; i <= height; i++) {
			graphics.drawLine(0, i * cellHeight, width, i * cellHeight);
		}
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		
		
		
		if (x1 > x2) {
			drawLine(x2, y2, x1, y1);
		} else {
			int x1Actual = (x1 - 1) * cellWidth + cellWidth/2;
			int x2Actual = (x2 - 1) * cellWidth + cellWidth/2;
			int y1Actual = (y1 - 1) * cellHeight+ cellHeight/2;
			int y2Actual = (y2 - 1) * cellHeight+ cellHeight/2;
			
			double phi = Math.atan2(y2Actual - y1Actual, x2Actual - x1Actual);                    
			double xx1 = x1Actual  + (Math.cos(phi) * nodeDiameter + 1)/2;                    
			double yy1 = y1Actual  + (Math.sin(phi) * nodeDiameter + 1)/2;                    
			double xx2 = x2Actual  - (Math.cos(phi) * nodeDiameter - 1)/2;                    
			double yy2 = y2Actual  - (Math.sin(phi) * nodeDiameter - 1)/2;

			//draw connection
			graphics.setColor(foreColor); 
			((Graphics2D) graphics).draw(new Line2D.Double(xx1, yy1, xx2, yy2));

			
			
			if (x1 < x2) {
//				graphics.drawLine(x1Actual,y1Actual,x2Actual,y2Actual);
				float m = (y2Actual - y1Actual)
						/ ((float) (x2Actual - x1Actual));
				
				// (x-x1Actual)^2 + (y-y2Actual)^2 = (nodeDiameter/2)^2
				int x1New = x1Actual + nodeDiameter / 2;
				int x2New = x2Actual - nodeDiameter / 2;
//				 graphics.drawLine(x1New,y1Actual,x2New,y2Actual);
				int y1New = (int) (y1Actual + m * (x1New - x1Actual));
				int y2New = (int) (y1Actual + m * (x2New - x1Actual));
//				graphics.drawLine(x1New, y1New, x2New, y2New);
			} else {
				if (y1Actual > y2Actual) {
					int aux = y1Actual;
					y1Actual = y2Actual;
					y2Actual = aux;
				}
//				graphics.drawLine(x1Actual, y1Actual  + nodeDiameter/2,
//						x2Actual, y2Actual - nodeDiameter/2);
			}
		}
	}

	public void drawLine_old(int x1, int y1, int x2, int y2) {
		if (x1 == x2) {
			if (y1 > y2) {
				int aux = y2;
				y2 = y1;
				y1 = aux;
			}
			int x = calculateXHalfDiameter(x1);
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
				int xLeft = calculateXHalfDiameter(x1) + nodeDiameter / 2;
				int xRight = calculateXHalfDiameter(x2) - nodeDiameter / 2;
				graphics.drawLine(xLeft, y, xRight, y);

			} else {
				if (x1 > x2) {
					drawLine(x2, y2, x1, y1);
				} else {
					if (y1 < y2) {
						graphics.drawLine(calculateLineXDiameter(x1)
								- nodeDiameter / 2, calculateLineY(y1)
								+ nodeDiameter / 2, calculateXHalfDiameter(x2),
								calculateLineY(y2) - nodeDiameter / 2);
					} else {
						graphics.drawLine(calculateLineXDiameter(x1)
								- nodeDiameter / 2, calculateLineY(y1)
								- nodeDiameter / 2, calculateXHalfDiameter(x2),
								calculateLineY(y2) + nodeDiameter / 2);
					}
				}
			}
		}
	}

	private int calculateXHalfDiameter(int x1) {
		return calculateX(x1) + nodeDiameter / 2;
	}

	private int calculateLineXDiameter(int x) {
		return calculateX(x) + nodeDiameter;
	}

	private int calculateLineY(int y1) {
		return calculateY(y1) + nodeDiameter / 2;
	}
	
}
