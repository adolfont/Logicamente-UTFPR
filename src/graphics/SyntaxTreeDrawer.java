package graphics;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

import logicamente.formulas.Formula;
import logicamente.parser.Parser;

public class SyntaxTreeDrawer extends JFrame {

	public SyntaxTreeDrawer() {
	}

	public static void main(String[] args) {
		Parser parser = new Parser();
		SyntaxTreeDrawer std = new SyntaxTreeDrawer();

		std.setBounds(100, 100, 500, 300);
		std.setTitle("Syntax Tree Painter");
		
		Formula f = parser.parse("A->B").getFormula();
		

		std.setVisible(true);
		std.drawTree(f);
	}

	private void drawTree(Formula f) {
		// percorrer a fórmula e ir desenhando
	
		this.getGraphics().drawOval(100, 100, 50, 50);
		this.getGraphics().drawString("A", 130, 130);
		this.getGraphics().drawLine(130, 130, 180, 180);
		this.repaint();
	}

	public void paint(Graphics g) {
	}

}
