package logicamente.drawer;

import java.awt.Graphics;

import javax.swing.JFrame;

public class DrawingGridQuasiTest extends JFrame {

	public static DrawingGrid dg;

	public static void main(String[] args) {
		DrawingGridQuasiTest dgqt = new DrawingGridQuasiTest();
		dgqt.setBounds(100, 100, 500, 300);
		dgqt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dgqt.setUndecorated(false);
		dgqt.setVisible(true);

		dg = new DrawingGrid(dgqt.getContentPane().getGraphics());
		dg.setBounds(dgqt.getContentPane().getBounds().width, dgqt.getContentPane().getBounds().height);
		dg.setGrid(5, 3);
		dg.setNodeDiameter(50);
	}

	public void paint(Graphics g) {

		dg.drawGridLines();

		dg.drawNode(1, 2, "AAA");
		dg.drawNode(3, 2, "BB");
		dg.drawNode(5, 1, "C1");
		dg.drawNode(1, 3, "X");
		dg.drawNode(5, 3, "Y");
		
		dg.drawLine(1,2,5,1);
//		dg.drawLine(5,1,1,2);

//		dg.drawLine(3,2,5,3);
		dg.drawLine(5,3,3,2);

		// mesma linha
//		dg.drawLine(1,2,3,2);
		dg.drawLine(3,2,1,2);
		
		// mesma coluna
		dg.drawLine(1,2,1,3);
//		dg.drawLine(1,3,1,2);
		
		
//		dg.drawLine(1,1,1,2);
	}
}
