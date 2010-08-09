package logicamente.drawer;

import java.awt.Graphics;

import javax.swing.JFrame;

import logicamente.formulas.AtomicFormula;
import logicamente.formulas.CompositeFormula;
import logicamente.formulas.Formula;
import logicamente.parser.Parser;

public class SyntaxTreeDrawer extends JFrame {

	private int x;
	private int y;

	private Parser parser;
	private Formula formula;

	public SyntaxTreeDrawer() {
		this.x = 250;
		this.y = 50;
		parser = new Parser();
	}

	public static void main(String[] args) {
		SyntaxTreeDrawer std = new SyntaxTreeDrawer();

		std.setBounds(100, 100, 500, 500);
		std.setTitle("Syntax Tree Painter");

		
// 		alguns testes
//		std.setFormula("A->B");
//		std.setFormula("A->B->C->D->E->F->G"); // esse funciona
		std.setFormula("(A->B->C)->D->E->F->G"); // esse dá erro: fica encavalado!

		std.setVisible(true);
}

	private void setFormula(String string) {
		formula = parser.parse(string).getFormula();
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

	public void paint(Graphics g) {
		drawTree(formula);
	}

}
