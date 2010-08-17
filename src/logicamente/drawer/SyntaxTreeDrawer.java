package logicamente.drawer;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logicamente.formulas.AtomicFormula;
import logicamente.formulas.CompositeFormula;
import logicamente.formulas.Formula;
import logicamente.parser.ParseResult;
import logicamente.parser.Parser;

public class SyntaxTreeDrawer extends JFrame implements ActionListener {

	private static final long serialVersionUID = 4393288660576735767L;

	private int width;
	private int height;
	private Parser parser;
	private Formula formula;
	private DrawingGrid grid;
	private Map<String, String> connectiveSymbolsMap;

	private JPanel drawingPanel;

	public static void main(String[] args) {
		String formula = "";

		// if (args.length > 0) {
		// formula = args[0];
		SyntaxTreeDrawer std = new SyntaxTreeDrawer();
		// std.setFormula(formula);
		std.setVisible(true);
		// } else {
		// showCommandLineComments();
		// }
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
		System.out
				.println("java -jar logicamente-utfpr.jar 'A->B->C->D->E->F->G'");
		// std.setFormula("A->B->C->D->E->F->G");
		System.out
				.println("java -jar logicamente-utfpr.jar '(!A->B->!!C)->D->E->F->G'");
		// std.setFormula("(!A->B->!!C)->D->E->F->G");
		// std.setFormula("(!A&B|!!C)->D->(E1&(E2&E3))->(F1&F2&F3)->G");
		System.out.println();
		System.out
				.println("More information at http://github.com/adolfont/Logicamente-UTFPR/");
		System.exit(0);
	}

	public SyntaxTreeDrawer() {
		parser = new Parser();
		this.width = 700;
		this.height = 500;

		this.setBounds(300, 100, width, height);
		setTitle("Logicamente-UTFPR - Syntax Tree Drawer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// The symbols came from
		// http://en.wikipedia.org/wiki/List_of_logic_symbols
		connectiveSymbolsMap = new HashMap<String, String>();
		connectiveSymbolsMap.put(Formula.NOT, "¬");
		connectiveSymbolsMap.put(Formula.AND, "∧");
		connectiveSymbolsMap.put(Formula.OR, "∨");
		connectiveSymbolsMap.put(Formula.IMPLIES, "→");

		drawScreen();

		setVisible(true);
	}

	private JPanel topPanel;
	private JLabel errorPanel;

	private void drawScreen() {

		if (drawingPanel == null) {
			drawingPanel = new JPanel();
		}

		JLabel inputFormulaLabel = new JLabel();
		inputFormulaLabel.setFont(inputFormulaLabel.getFont().deriveFont(
				(float) 30.0));
		JTextField inputFormulaTextField = new JTextField(30);
		inputFormulaTextField.setFont(inputFormulaTextField.getFont()
				.deriveFont((float) 30.0));
		inputFormulaTextField.addActionListener(this);
		inputFormulaLabel.setText("Formula: ");
		inputFormulaTextField.setText("");

		getContentPane().setLayout(new BorderLayout());

		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(inputFormulaLabel);
		topPanel.add(inputFormulaTextField);

		getContentPane().add(topPanel, BorderLayout.PAGE_START);
		getContentPane().add(drawingPanel, BorderLayout.CENTER);
		drawingPanel.setBounds(0, 0, 500, 500);

		errorPanel = new JLabel("", JLabel.CENTER);
		errorPanel.setFont(errorPanel.getFont().deriveFont((float) 30.0));

		getContentPane().add(errorPanel, BorderLayout.PAGE_END);

		// pack();
	}

	public void setFormula(String string) {
		ParseResult result = parser.parse(string);
		// System.out.println(result.parseCorrect());
		if (result.parseCorrect()) {
			formula = result.getFormula();
			errorPanel.setText("");
		} else {
			formula = null;
			grid.clear();
			errorPanel.setText("Syntax error");
		}
	}

	public void paint(Graphics g) {
		if (formula != null) {
			paintSyntaxTree();
			topPanel.repaint();
		} else {
			errorPanel.repaint();
		}
	}

	private void paintSyntaxTree() {
		grid = new DrawingGrid(drawingPanel.getGraphics());
		drawingPanel.getGraphics()
				.clearRect(0, 0, drawingPanel.getBounds().width,
						drawingPanel.getBounds().height);

		grid.setBounds(drawingPanel.getBounds().width,
				drawingPanel.getBounds().height);
		grid.setNodeDiameter(50);
		grid.setGrid(formula.getComplexity() - formula.getNegationDegree(),
				formula.getHeight() + 1);
		
		// DEBUG ONLY
		grid.drawGridLines();

		drawSyntaxTree(0, 0, 1, 1, formula);
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
		// TODO testar com !(A&!B), !!(A&B), A->!!(A&B)
		System.out.print(formula);
		System.out.println("    X is " + x);
		int newX = calculateX_For_A_NotNode(formula, x);
		System.out.println("    should be " + newX);

		// TODO TENTAR TROCAR AS DUAS ABAIXO PELAS MAIS ABAIXO
		grid.drawNode(x, y, getConnectiveSymbol(((CompositeFormula) formula)
				.getConnective()));
		drawSyntaxTree(x, y, x, y + 1, left);

		// TODO VERSAO ABAIXO - NEGACAO ABAIXO DE NEGACAO DA PROBLEMA
		// grid.drawNode(newX, y, getConnectiveSymbol(((CompositeFormula)
		// formula)
		// .getConnective()));

		// if (left instanceof AtomicFormula) {
		// drawSyntaxTree(newX, y, x, y + 1, left);
		// } else if (left instanceof CompositeFormula
		// && ((CompositeFormula) left).getConnective() == Formula.NOT) {
		// drawNotNode_in_SyntaxTree(newX, y, newX, y + 1, left,
		// ((CompositeFormula) left).getLeftFormula());
		// } else {
		// drawSyntaxTree(newX, y, x, y + 1, left);
		// }

	}

	private int calculateX_For_A_NotNode(Formula notFormula, int x) {
		Formula f = notFormula;
		while (true) {
			if ((f instanceof CompositeFormula)
					&& (((CompositeFormula) f).getConnective() == Formula.NOT)) {
				f = ((CompositeFormula) f).getLeftFormula();
			} else {
				break;
			}
		}

		// A || B&&C
		// A || (B&&C)

		System.out.println("Resulting f: " + f);
		if (f instanceof CompositeFormula) {
			return x
					+ ((CompositeFormula) f).getLeftFormula().getComplexity()
					- ((CompositeFormula) f).getLeftFormula()
							.getNegationDegree();
		} else {
			return x;
		}
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

	@Override
	public void actionPerformed(ActionEvent e) {
		setFormula(e.getActionCommand());
		repaint();
	}

	public static int getLeftSize(Formula f) {
		if (f instanceof AtomicFormula) {
			return 0;
		} else {
			CompositeFormula cf = (CompositeFormula) f;

			if (cf.getConnective() == Formula.NOT) {
				return getLeftSize(cf.getLeftFormula());
			}

			return cf.getLeftFormula().getComplexity()
					- cf.getLeftFormula().getNegationDegree();
		}
	}

}
