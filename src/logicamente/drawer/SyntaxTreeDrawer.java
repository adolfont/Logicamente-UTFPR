package logicamente.drawer;

import java.awt.Graphics;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import logicamente.formulas.AtomicFormula;
import logicamente.formulas.CompositeFormula;
import logicamente.formulas.Formula;
import logicamente.parser.Parser;

public class SyntaxTreeDrawer extends JFrame implements WindowStateListener {

	private static final long serialVersionUID = 4393288660576735767L;

	private int x;
	private int y;
	private int width;
	private int height;
	private int cellWidth;
	private int cellHeight;

	private int leftBorder = 3;
	private int topBorder = 24;

	private int diameter;

	private Parser parser;
	private Formula formula;
	private byte[][] screen;

	private DrawingGrid grid;

	public SyntaxTreeDrawer() {
		this.x = 250;
		this.y = 50;
		parser = new Parser();
		this.width = 700;
		this.height = 500;
		this.diameter = 60;

		this.setBounds(100, 100, width, height);
		setTitle("Syntax Tree Painter");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {
		SyntaxTreeDrawer std = new SyntaxTreeDrawer();

		// std.setBounds(x, y, width, height); // opcionalmente fora

		// alguns testes
		std.setFormula("A");
		// std.setFormula("A->B");
		// std.setFormula("A->B->C");
		// std.setFormula("A->B->C->D->E->F->G");
		std.setFormula("(!A->B->!!C)->D->E->F->G");

		std.setVisible(true);
	}

	public void setFormula(String string) {
		formula = parser.parse(string).getFormula();
	}

	public void paint(Graphics g) {
		// drawTree(formula);
		// drawSyntaxTree(formula);

		g.clearRect(0, 0, getBounds().width, getBounds().height);

		grid = new DrawingGrid(getContentPane().getGraphics());

		grid.setBounds(getContentPane().getBounds().width, getContentPane()
				.getBounds().height);
		grid.setNodeDiameter(50);
		grid.setGrid(formula.getComplexity() - formula.getNegationDegree(),
				formula.getHeight() + 1);
		grid.drawGridLines();

		drawSyntaxTreeV3(0, 0, 1, 1, formula);
	}

	private void drawSyntaxTreeV3(int xPrev, int yPrev, int x, int y,
			Formula formula) {
		if (formula instanceof AtomicFormula) {
			if (xPrev != 0) {
				grid.drawLine(xPrev, yPrev, x, y);
			}
			grid.drawNode(x, y, formula.toString());
		} else {

			Formula left = (((CompositeFormula) formula).getLeftFormula());

			if (((CompositeFormula) formula).getConnective() == Formula.NOT) {
				if (xPrev != 0) {
					grid.drawLine(xPrev, yPrev, x, y);
				}
				grid.drawNode(x, y, ((CompositeFormula) formula)
						.getConnective().toString());
				drawSyntaxTreeV3(x, y, x, y + 1, left);
			} else {

				Formula right = (((CompositeFormula) formula).getRightFormula());
				if (xPrev != 0) {
					grid.drawLine(xPrev, yPrev, x + left.getComplexity()
							- left.getNegationDegree(), y);
				}
				grid
						.drawNode(x + left.getComplexity()
								- left.getNegationDegree(), y,
								((CompositeFormula) formula).getConnective()
										.toString());
				drawSyntaxTreeV3(x + left.getComplexity()
						- left.getNegationDegree(), y, x, y + 1, left);
				if (right != null)
					drawSyntaxTreeV3(x + left.getComplexity()
							- left.getNegationDegree(), y, x
							+ left.getComplexity() - left.getNegationDegree()
							+ 1, y + 1, right);
			}
		}

	}

	@Override
	public void windowStateChanged(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
