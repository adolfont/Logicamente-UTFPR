package logicamente.drawer;

import java.awt.Graphics;

import javax.swing.JFrame;

import logicamente.formulas.AtomicFormula;
import logicamente.formulas.CompositeFormula;
import logicamente.formulas.Formula;
import logicamente.parser.Parser;

public class SyntaxTreeDrawer extends JFrame {

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
//		std.setFormula("A");
//		 std.setFormula("A->B");
		 std.setFormula("A->B->C->D->E->F->G"); // esse funciona
		// std.setFormula("(A->B->C)->D->E->F->G"); // esse dá erro: fica
		// encavalado!

		std.setVisible(true);
	}

	public void setFormula(String string) {
		formula = parser.parse(string).getFormula();
		generateScreen();
	}

	public void paint(Graphics g) {
		// drawTree(formula);
//		drawSyntaxTree(formula);
		
		g.clearRect(0, 0, getBounds().width, getBounds().height);
		
		grid = new DrawingGrid(getContentPane().getGraphics());
		
		grid.setBounds(getContentPane().getBounds().width, getContentPane().getBounds().height);
		grid.setNodeDiameter(50);
		grid.setGrid(formula.getComplexity(), formula.getHeight()+1);
		grid.drawGridLines();
		
		drawSyntaxTreeV3(1, 1, formula);
	}

	private void drawSyntaxTreeV3(int x, int y, Formula formula) {
		if (formula instanceof AtomicFormula){
			grid.drawNode(x, y, formula.toString());
		}
		else{
			Formula left = (((CompositeFormula) formula).getLeftFormula());
			Formula right = (((CompositeFormula) formula).getRightFormula());
			
			grid.drawNode(x+left.getComplexity(), y, ((CompositeFormula)formula).getConnective().toString());
			drawSyntaxTreeV3(x, y+1, left);
			if (right !=null) drawSyntaxTreeV3(x+left.getComplexity()+1, y+1, right);
		}
		
	}

	private void drawSyntaxTree(Formula formula2) {
		generateScreen();
	}

	private void generateScreen() {
		screen = new byte[formula.getHeight() + 1][formula.getComplexity()];

		for (int i = 0; i < formula.getHeight() + 1; i++) {
			for (int j = 0; j < formula.getComplexity(); j++) {
				screen[i][j] = 0;
			}
		}
		if (this.getGraphics() != null)
			recursivelyFillScreen(formula);
	}

	private void recursivelyFillScreen(Formula formula) {

		cellWidth = width / formula.getComplexity();
		cellHeight = height / (formula.getHeight() + 1);
		
		
		int baseX, baseY;

		if (formula instanceof AtomicFormula) {
			int formulaStringWidth = getFontMetrics(getFont()).stringWidth(
					formula.toString());
			int formulaStringHeight = getFontMetrics(getFont()).getHeight();

			getGraphics().drawOval(leftBorder, topBorder, diameter, diameter);
			getGraphics().drawString(formula.toString(),
					leftBorder + diameter / 2 - formulaStringWidth / 2,
					topBorder + diameter / 2 + formulaStringHeight / 2);
			
			baseX = leftBorder + diameter / 2 - formulaStringWidth / 2;
			baseY = topBorder + diameter / 2 + formulaStringHeight / 2;
		} else if (formula instanceof CompositeFormula) {
			int formulaStringWidth = getFontMetrics(getFont()).stringWidth(
					((CompositeFormula) formula).getConnective().toString());
			int formulaStringHeight = getFontMetrics(getFont()).getHeight();

			Formula left = (((CompositeFormula) formula).getLeftFormula());
			Formula right = (((CompositeFormula) formula).getRightFormula());

			int x = left.getComplexity()*cellWidth;
			
			getGraphics().drawOval(leftBorder + x, topBorder, diameter, diameter);
			getGraphics().drawString(
					((CompositeFormula) formula).getConnective().toString(),
					x+ leftBorder + diameter / 2 - formulaStringWidth / 2,
					topBorder + diameter / 2 + formulaStringHeight / 2);
			
			baseX = x+ leftBorder + diameter / 2 - formulaStringWidth / 2;
			baseY = topBorder + diameter / 2 + formulaStringHeight / 2;

			System.out.println(baseX + "  " + baseY);
			recursivelyFillScreen2(baseX,baseY,left);
			if (right != null) recursivelyFillScreen2(baseX,baseY,right);
		}
	}

	private void recursivelyFillScreen2(int originalBaseX, int originalBaseY, Formula formula) {

		int baseX, baseY;

		if (formula instanceof AtomicFormula) {
			int formulaStringWidth = getFontMetrics(getFont()).stringWidth(
					formula.toString());
			int formulaStringHeight = getFontMetrics(getFont()).getHeight();

			getGraphics().drawOval(leftBorder, topBorder, diameter, diameter);
			getGraphics().drawString(formula.toString(),
					leftBorder + diameter / 2 - formulaStringWidth / 2,
					topBorder + diameter / 2 + formulaStringHeight / 2);
			
			baseX = leftBorder + diameter / 2 - formulaStringWidth / 2;
			baseY = topBorder + diameter / 2 + formulaStringHeight / 2;
		} else if (formula instanceof CompositeFormula) {
			int formulaStringWidth = getFontMetrics(getFont()).stringWidth(
					((CompositeFormula) formula).getConnective().toString());
			int formulaStringHeight = getFontMetrics(getFont()).getHeight();

			Formula left = (((CompositeFormula) formula).getLeftFormula());
			Formula right = (((CompositeFormula) formula).getRightFormula());

			int x = left.getComplexity()*cellWidth;
			
			getGraphics().drawOval(leftBorder + x, topBorder, diameter, diameter);
			getGraphics().drawString(
					((CompositeFormula) formula).getConnective().toString(),
					x+ leftBorder + diameter / 2 - formulaStringWidth / 2,
					topBorder + diameter / 2 + formulaStringHeight / 2);
			
			baseX = x+ leftBorder + diameter / 2 - formulaStringWidth / 2;
			baseY = topBorder + diameter / 2 + formulaStringHeight / 2;

			System.out.println(baseX + "  " + baseY);
			recursivelyFillScreen2(baseX,baseY,left);
			if (right != null) recursivelyFillScreen2(baseX,baseY,right);
		}
		
	}

	private void recursivelyFillScreen(int line, int startColumn,
			Formula formulaPiece) {

		if (formulaPiece instanceof CompositeFormula) {
			Formula left = (((CompositeFormula) formulaPiece).getLeftFormula());
			Formula right = (((CompositeFormula) formulaPiece)
					.getRightFormula());

			int column = startColumn + left.getComplexity();

			screen[line][column] = 1;

			// TODO Add node connection
			// (line,column), (line + 1, ??)
			drawNode(line, column, ((CompositeFormula) formulaPiece)
					.getConnective());

			recursivelyFillScreen(line + 1, startColumn, left);
			if (right != null) {
				// TODO Add node connection
				// (line,column), (line + 1, ??)
				recursivelyFillScreen(line + 1, column + 1, right);
			}
		} else {
			drawNode(line, startColumn, formulaPiece.toString());
			screen[line][startColumn] = 1;
		}
	}

	private void drawNode(int line, int column, String connective) {
		int horSize = Math.min(80, this.width / formula.getComplexity());
		int verSize = Math.min(80, this.height / (formula.getHeight() + 1));
		int x = 20 + column * horSize;
		int y = 110 + line * verSize;

		System.out.println(horSize + " " + verSize);
		if (getGraphics() != null) {

			System.out.println(x + "  " + y + "  " + connective);

			this.getGraphics().drawOval(x, y, diameter, diameter);
			this.getGraphics().drawString(connective, x + 25, y + 35);
			this.getGraphics().drawLine(x, y, x, y);
		}
	}

	public byte[][] getScreen() {
		return screen;
	}

	private void drawTree(Formula f) {
		if (f instanceof CompositeFormula) {
			if (((CompositeFormula) f).getLeftFormula() != null) {
				this.getGraphics().drawLine(this.x - 7, this.y + 5,
						this.x - 35, this.y + 15);
				this.x -= 50;
				this.y += 30;
				this.drawTree(((CompositeFormula) f).getLeftFormula());
				this.x += 50;
				this.y -= 30;
			}
			if (((CompositeFormula) f).getRightFormula() != null) {
				this.getGraphics().drawLine(this.x + 16, this.y + 5,
						this.x + 40, this.y + 15);
				this.x += 50;
				this.y += 30;
				this.drawTree(((CompositeFormula) f).getRightFormula());
				this.x -= 50;
				this.y -= 30;
			}
			this.getGraphics().drawOval(this.x - 12, this.y - 20, 30, 30);
			this.getGraphics().drawString(
					((CompositeFormula) f).getConnective(), this.x, this.y);
		} else if (f instanceof AtomicFormula) {
			this.getGraphics().drawOval(this.x - 12, this.y - 20, 30, 30);
			this.getGraphics().drawString(((AtomicFormula) f).toString(),
					this.x, this.y);

		} else {
			this.getGraphics().drawString("erro", 2, 2);
			return;
		}
		this.repaint();
	}

}
