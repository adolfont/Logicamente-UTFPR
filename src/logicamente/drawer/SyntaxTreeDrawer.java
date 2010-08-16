package logicamente.drawer;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import logicamente.formulas.AtomicFormula;
import logicamente.formulas.CompositeFormula;
import logicamente.formulas.Formula;
import logicamente.parser.ParseResult;
import logicamente.parser.Parser;

public class SyntaxTreeDrawer extends JFrame {

	private static final long serialVersionUID = 4393288660576735767L;

	private int width;
	private int height;
	private Parser parser;
	private Formula formula;
	private DrawingGrid grid;
	private Map<String, String> connectiveSymbolsMap;

	public static void main(String[] args) {
		String formula;
		if (args.length > 0) {
			formula = args[0];
			SyntaxTreeDrawer std = new SyntaxTreeDrawer();
			std.setFormula(formula);
			std.setVisible(true);
		} else {
			showCommandLineComments();
		}
	}

	private static void showCommandLineComments() {
		System.out
				.println("Usage: java -jar logicamente-utfpr.jar '<formula>'");
		System.out.println();
		System.out.println("Some examples:");
		// alguns testes
		System.out.println("java -jar logicamente-utfpr.jar 'A'");
		// std.setFormula("A");
		System.out.println("java -jar logicamente-utfpr.jar 'A->B'");
		// std.setFormula("A->B");
		System.out.println("java -jar logicamente-utfpr.jar 'A->B->C'");
		// std.setFormula("A->B->C");
		System.out.println("java -jar logicamente-utfpr.jar 'A->B->C->D->E->F->G'");
		// std.setFormula("A->B->C->D->E->F->G");
		System.out.println("java -jar logicamente-utfpr.jar '(!A->B->!!C)->D->E->F->G'");
		// std.setFormula("(!A->B->!!C)->D->E->F->G");
		// std.setFormula("(!A&B|!!C)->D->(E1&(E2&E3))->(F1&F2&F3)->G");
		System.out.println();
		System.out.println("More information at http://github.com/adolfont/Logicamente-UTFPR/");
		System.exit(0);
	}

	public SyntaxTreeDrawer() {
		parser = new Parser();
		this.width = 700;
		this.height = 500;

		this.setBounds(100, 100, width, height);
		setTitle("Logicamente-UTFPR - Syntax Tree Drawer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// The symbols came from
		// http://en.wikipedia.org/wiki/List_of_logic_symbols
		connectiveSymbolsMap = new HashMap<String, String>();
		connectiveSymbolsMap.put(Formula.NOT, "¬");
		connectiveSymbolsMap.put(Formula.AND, "∧");
		connectiveSymbolsMap.put(Formula.OR, "∨");
		connectiveSymbolsMap.put(Formula.IMPLIES, "→");
	}

	public void setFormula(String string) {
		ParseResult result = parser.parse(string);
		if (result.parseCorrect()) {
			formula = result.getFormula();
		}
	}

	public void paint(Graphics g) {
		if (formula != null) {
			g.clearRect(0, 0, getBounds().width, getBounds().height);

			grid = new DrawingGrid(getContentPane().getGraphics());
			grid.setBounds(getContentPane().getBounds().width, getContentPane()
					.getBounds().height);
			grid.setNodeDiameter(50);
			grid.setGrid(formula.getComplexity() - formula.getNegationDegree(),
					formula.getHeight() + 1);
			drawSyntaxTree(0, 0, 1, 1, formula);
			grid.drawGridLines();
		}
		else{
			g.drawString("Parsing error", 50, 50);
		}
	}

	private void drawSyntaxTree(int xPrev, int yPrev, int x, int y,
			Formula formula) {
		if (formula instanceof AtomicFormula) {
			if (xPrev != 0) {
				grid.drawLine(xPrev, yPrev, x, y);
			}
			grid.drawNode(x, y, formula.toString());
		} else {
			Formula left = (((CompositeFormula) formula).getLeftFormula());
			if (((CompositeFormula) formula).getConnective() == Formula.NOT) {
				drawNotNode_in_SyntaxTree(xPrev, yPrev, x, y, formula, left);
			} else {
				drawBinaryNode_in_SyntaxTree(xPrev, yPrev, x, y, formula, left);
			}
		}
	}

	private void drawNotNode_in_SyntaxTree(int xPrev, int yPrev, int x, int y,
			Formula formula, Formula left) {
		if (xPrev != 0) {
			grid.drawLine(xPrev, yPrev, x, y);
		}
		// grid.drawNode(x, y, ((CompositeFormula) formula).getConnective()
		// .toString());
		grid.drawNode(x, y, getConnectiveSymbol(((CompositeFormula) formula)
				.getConnective()));
		drawSyntaxTree(x, y, x, y + 1, left);
	}

	private void drawBinaryNode_in_SyntaxTree(int xPrev, int yPrev, int x,
			int y, Formula formula, Formula left) {
		Formula right = (((CompositeFormula) formula).getRightFormula());
		if (xPrev != 0) {
			grid.drawLine(xPrev, yPrev, x + left.getComplexity()
					- left.getNegationDegree(), y);
		}
		// grid.drawNode(x + left.getComplexity() - left.getNegationDegree(), y,
		// ((CompositeFormula) formula).getConnective().toString());
		grid.drawNode(x + left.getComplexity() - left.getNegationDegree(), y,
				getConnectiveSymbol(((CompositeFormula) formula)
						.getConnective()));

		drawSyntaxTree(x + left.getComplexity() - left.getNegationDegree(), y,
				x, y + 1, left);
		if (right != null)
			drawSyntaxTree(x + left.getComplexity() - left.getNegationDegree(),
					y, x + left.getComplexity() - left.getNegationDegree() + 1,
					y + 1, right);
	}

	private String getConnectiveSymbol(String connective) {
		return connectiveSymbolsMap.get(connective);
	}

}
