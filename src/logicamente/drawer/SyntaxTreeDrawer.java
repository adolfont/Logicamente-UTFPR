package logicamente.drawer;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
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
	private JPanel topPanel;
	private JLabel errorPanel;

	private boolean showGridLines = true;

	public static void main(String[] args) {
		SyntaxTreeDrawer std = new SyntaxTreeDrawer();
		if (args.length > 1) {
			initFromCommandLine(args, std);
		}
	}

	private static void initFromCommandLine(String[] args, SyntaxTreeDrawer std2) {
		String formula;
		if (args.length == 1) {
			formula = args[0];
			SyntaxTreeDrawer std = new SyntaxTreeDrawer();
			std.setFormula(formula);
		} else {
			showCommandLineComments();
		}
	}

	private static void showCommandLineComments() {
		System.out
				.println("Usage: java -jar logicamente-utfpr.jar '<formula>'");
		System.out.println();
		System.out.println("Some examples:");
		System.out.println("java -jar logicamente-utfpr.jar 'A'");
		System.out.println("java -jar logicamente-utfpr.jar 'A->B'");
		System.out.println("java -jar logicamente-utfpr.jar 'A->B->C'");
		System.out
				.println("java -jar logicamente-utfpr.jar 'A->B->C->D->E->F->G'");
		System.out
				.println("java -jar logicamente-utfpr.jar '(!A->B->!!C)->D->E->F->G'");
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

	public void calculatePositionOfTreeNodes() {
		grid = new DrawingGrid(drawingPanel.getGraphics());
		drawingPanel.getGraphics()
				.clearRect(0, 0, drawingPanel.getBounds().width,
						drawingPanel.getBounds().height);

		grid.setBounds(drawingPanel.getBounds().width,
				drawingPanel.getBounds().height);
		grid.setNodeDiameter(50);
		grid.setGrid(formula.getComplexity() - formula.getNegationDegree(),
				formula.getHeight() + 1);

		if (showGridLines)
			grid.drawGridLines();

		Interval xInterval = new Interval(1, formula.getComplexity()
				- formula.getNegationDegree());
		drawSyntaxTree(null, formula, xInterval, 1);
	}

	private void paintSyntaxTree() {
		calculatePositionOfTreeNodes();
		grid.drawTree();
	}

	private void drawSyntaxTree(GridNode parent, Formula formula,
			Interval xInterval, int y) {
		int x = xInterval.getXLeft() + getLeftSize(formula);
		GridNode currentNode = new GridNode(x, y, getCurrentSymbol(formula));
		currentNode.setParent(parent);
		grid.add(currentNode);

		drawSyntaxTree_leftSubformula(currentNode, formula, xInterval, x, y);
		drawSyntaxTree_rightSubformula(currentNode, formula, xInterval, x, y);
	}

	private void drawSyntaxTree_leftSubformula(GridNode parentNode,
			Formula formula, Interval interval, int x, int y) {
		// poe na esquerda
		if (formula instanceof CompositeFormula) {
			CompositeFormula cf = (CompositeFormula) formula;
			String connective = cf.getConnective();

			if (connective == Formula.NOT) {
				// xLeft = o mesmo
				// xRight= o mesmo
			} else {
				// xLeft = o mesmo
				// xRight = x onde foi colocado o conectivo - 1
				interval.setXRight(x - 1);
			}

			drawSyntaxTree(parentNode, cf.getLeftFormula(), interval, y + 1);
		} else {
			// nao faz nada. Já fez o que tinha de fazer antes
		}
	}

	private void drawSyntaxTree_rightSubformula(GridNode parentNode,
			Formula formula, Interval interval, int x, int y) {
		// poe na direita
		if (formula instanceof CompositeFormula) {
			CompositeFormula cf = (CompositeFormula) formula;
			String connective = cf.getConnective();

			if (connective == Formula.NOT) {
				// ja fez o que tinha de fazer
			} else {
				// xLeft = x onde foi colocado o conectivo + 1
				interval.setXLeft(x + 1);
				// xRight = o mesmo

				drawSyntaxTree(parentNode, cf.getRightFormula(), interval,
						y + 1);
			}
		} else {
			// nao faz nada. Já fez o que tinha de fazer antes
		}
	}

	private String getCurrentSymbol(Formula formula) {
		if (formula instanceof AtomicFormula) {
			return formula.toString();
		} else {
			return getConnectiveSymbol(((CompositeFormula) formula)
					.getConnective());
		}
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

	public List<GridNode> getTreeNodes() {
		return grid.getNodes();
	}

}
