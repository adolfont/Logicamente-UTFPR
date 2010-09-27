package logicamente.drawer;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

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
	private JPanel infoPanel;
	private JLabel errorPanel;
	private JPanel formulaInfoPanel;
	private JTextField complexityTextField;
	private JTextField heightTextField;
	private JTextField negativesTextField;

	private javax.swing.JLabel inputFormulaLabel;
	private javax.swing.JTextField inputFormulaTextField;

	private javax.swing.JButton btnAnd;
	private javax.swing.JButton btnImplies;
	private javax.swing.JButton btnNot;
	private javax.swing.JButton btnOr;

	private boolean showGridLines = false;

	public static void main(String[] args) {
		SyntaxTreeDrawer std = new SyntaxTreeDrawer();
		// System.out.println(args.length);
		if (args.length >= 1) {
			initFromCommandLine(args, std);
		}
	}

	private static void initFromCommandLine(String[] args, SyntaxTreeDrawer std) {
		String formula;
		if (args.length == 1) {
			formula = args[0];
			std = new SyntaxTreeDrawer();
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

	private void btnClick(String st) {
		inputFormulaTextField.setText(inputFormulaTextField.getText() + st);
		inputFormulaTextField.grabFocus();
	}

	private void drawScreen() {

		if (drawingPanel == null) {
			drawingPanel = new JPanel();
		}

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		topPanel = new JPanel();

		includeFormulaInputArea();
		addConnectiveButtons();
		setTopAndDrawingPanelProperties();
		includeFormulaInfoPanel();
		includeTopLevelInfoPanel();

		JPanel topLevelPanel = createComplexLayout();
		getContentPane().add(topLevelPanel, BorderLayout.CENTER);
		getContentPane().add(infoPanel, BorderLayout.SOUTH);
	}

	private void setTopAndDrawingPanelProperties() {
		topPanel.setLayout(new GridLayout(2, 1));
		topPanel.add(inputFormulaLabel);
		topPanel.add(inputFormulaTextField);
		topPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		drawingPanel.setBounds(0, 0, 500, 500);
	}

	private void includeTopLevelInfoPanel() {
		infoPanel = new JPanel();
		errorPanel = new JLabel("", JLabel.CENTER);
		errorPanel.setFont(infoPanel.getFont().deriveFont((float) 30.0));
		infoPanel.setLayout(new GridLayout(2, 1));
		infoPanel.add(errorPanel);
		infoPanel.add(formulaInfoPanel);
	}

	private JPanel createComplexLayout() {
		GroupLayout topPanelLayout = new GroupLayout(topPanel);
		topPanel.setLayout(topPanelLayout);
		topPanelLayout.setHorizontalGroup(topPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				topPanelLayout.createSequentialGroup().addComponent(
						inputFormulaLabel)
						.addContainerGap(551, Short.MAX_VALUE)).addComponent(
				inputFormulaTextField, GroupLayout.DEFAULT_SIZE, 632,
				Short.MAX_VALUE).addGroup(
				topPanelLayout.createSequentialGroup().addGap(191, 191, 191)
						.addComponent(btnImplies, GroupLayout.DEFAULT_SIZE, 54,
								Short.MAX_VALUE).addPreferredGap(
								ComponentPlacement.RELATED).addComponent(
								btnAnd, GroupLayout.DEFAULT_SIZE, 54,
								Short.MAX_VALUE).addPreferredGap(
								ComponentPlacement.RELATED).addComponent(btnOr,
								GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnNot, GroupLayout.DEFAULT_SIZE, 54,
								Short.MAX_VALUE).addContainerGap(203,
								Short.MAX_VALUE)));
		topPanelLayout.setVerticalGroup(topPanelLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				topPanelLayout.createSequentialGroup().addComponent(
						inputFormulaLabel).addPreferredGap(
						ComponentPlacement.RELATED).addComponent(
						inputFormulaTextField, GroupLayout.PREFERRED_SIZE, 42,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						ComponentPlacement.RELATED).addGroup(
						topPanelLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNot,
										GroupLayout.PREFERRED_SIZE, 35,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnOr,
										GroupLayout.PREFERRED_SIZE, 35,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnAnd,
										GroupLayout.PREFERRED_SIZE, 35,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(btnImplies,
										GroupLayout.PREFERRED_SIZE, 35,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		drawingPanel.setBorder(BorderFactory
				.createLineBorder(new java.awt.Color(0, 0, 0)));

		GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(
				drawingPanel);
		drawingPanel.setLayout(drawingPanelLayout);

		JPanel topLevelPanel = new JPanel();
		GroupLayout layout = new GroupLayout(topLevelPanel);
		topLevelPanel.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addComponent(topPanel,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE).addComponent(drawingPanel,
				GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(topPanel,
						GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE).addPreferredGap(
						LayoutStyle.ComponentPlacement.RELATED).addComponent(
						drawingPanel, GroupLayout.DEFAULT_SIZE,
						GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		return topLevelPanel;
	}

	private void addConnectiveButtons() {

		btnAnd = new JButton();
		btnOr = new JButton();
		btnNot = new JButton();
		btnImplies = new JButton();

		btnImplies.setToolTipText("Implies");
		btnAnd.setToolTipText("And");
		btnOr.setToolTipText("Or");
		btnNot.setToolTipText("Not");

//		btnAnd.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnAnd.setText(connectiveSymbolsMap.get(Formula.AND));
		btnAnd.setAlignmentY(0.0F);
		btnAnd.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		btnAnd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				btnClick(Formula.AND);
			}
		});

//		btnOr.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnOr.setText(connectiveSymbolsMap.get(Formula.OR));
		btnOr.setAlignmentY(0.0F);
		btnOr.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		btnOr.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				btnClick(Formula.OR);
			}
		});

//		btnNot.setFont(new java.awt.Font("Tahoma", 1, 11));
		btnNot.setText(connectiveSymbolsMap.get(Formula.NOT));
		btnNot.setAlignmentY(0.0F);
		btnNot.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		btnNot.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				btnClick(Formula.NOT);
			}
		});

//		btnImplies.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		btnImplies.setText(connectiveSymbolsMap.get(Formula.IMPLIES));
		btnImplies.setAlignmentY(0.0F);
		btnImplies.setBorder(javax.swing.BorderFactory.createEtchedBorder());
		btnImplies.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				btnClick(Formula.IMPLIES);
			}
		});
	}

	private void includeFormulaInputArea() {
		inputFormulaLabel = new JLabel();
		// inputFormulaLabel.setFont(inputFormulaLabel.getFont().deriveFont((float)
		// 30.0));
		inputFormulaLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
		inputFormulaLabel.setText("Fórmula: ");

		inputFormulaTextField = new JTextField(30);
		// inputFormulaTextField.setFont(inputFormulaTextField.getFont().deriveFont((float)
		// 30.0));
		inputFormulaTextField.setFont(new java.awt.Font("Verdana", 1, 24));
		inputFormulaTextField.addActionListener(this);
		inputFormulaTextField.setText("");
		inputFormulaTextField
				.setToolTipText("Press Enter key to generate a tree!");
	}

	private void includeFormulaInfoPanel() {
		formulaInfoPanel = new JPanel();
		formulaInfoPanel.setLayout(new GridLayout(1, 6));
		JLabel negativesLabel = new JLabel("Negações: ", JLabel.CENTER);
		negativesTextField = new JTextField(4);
		negativesTextField.setEditable(false);
		JLabel heightLabel = new JLabel("Altura: ", JLabel.CENTER);
		heightTextField = new JTextField(4);
		heightTextField.setEditable(false);
		JLabel complexityLabel = new JLabel("Complexidade: ", JLabel.CENTER);
		complexityTextField = new JTextField(4);
		complexityTextField.setEditable(false);
		formulaInfoPanel.add(negativesLabel);
		formulaInfoPanel.add(negativesTextField);
		formulaInfoPanel.add(heightLabel);
		formulaInfoPanel.add(heightTextField);
		formulaInfoPanel.add(complexityLabel);
		formulaInfoPanel.add(complexityTextField);
	}

	public void setFormula(String string) {
		ParseResult result = parser.parse(string);
		// System.out.println(result.parseCorrect());
		if (result.parseCorrect()) {
			formula = result.getFormula();
			// System.out.println(formula);
			errorPanel.setText("");
		} else {
			formula = null;
			if (grid != null)
				grid.clear();
			errorPanel.setText("Syntax error");
		}
	}

	private void showInfoValues() {
		if (formula != null) {
			complexityTextField.setText("" + formula.getComplexity());
			heightTextField.setText("" + formula.getHeight());
			negativesTextField.setText("" + formula.getNegationDegree());
		} else {
			complexityTextField.setText("");
			heightTextField.setText("");
			negativesTextField.setText("");
		}
	}

	public void paint(Graphics g) {
		if (formula != null) {
			paintSyntaxTree();
		} else {
			drawingPanel.repaint();
		}
		showInfoValues();
		topPanel.repaint();
		infoPanel.repaint();
		errorPanel.repaint();
		formulaInfoPanel.repaint();

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

	@Override
	public void actionPerformed(ActionEvent e) {
		setFormula(e.getActionCommand());
		repaint();
	}

}
