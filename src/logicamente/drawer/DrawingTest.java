package logicamente.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawingTest {
	public static void main(String[] args) {
		CirclesAndArrows circles = new CirclesAndArrows();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(circles.connector.getUIPanel(), "North");
		f.getContentPane().add(circles);
		f.setSize(400, 400);
		f.setLocation(200, 200);
		f.setVisible(true);
	}
}

class CirclesAndArrows extends JPanel {
	List circleList;
	final double DIA;
	boolean showLines;
	Connector connector;

	public CirclesAndArrows() {
		circleList = new ArrayList();
		DIA = 20;
		showLines = false;
		setBackground(Color.white);
		connector = new Connector(this);
		addMouseListener(connector);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		for (int j = 0; j < circleList.size(); j++)
			g2.draw((Shape) circleList.get(j));
		if (showLines) {
			Ellipse2D.Double e1, e2;
			double x1, y1, x2, y2, phi;
			for (int j = 0; j < circleList.size(); j++) {
				e1 = (Ellipse2D.Double) circleList.get(j);
				for (int k = j + 1; k < circleList.size(); k++) {
					e2 = (Ellipse2D.Double) circleList.get(k);
					phi = Math.atan2(e2.y - e1.y, e2.x - e1.x);
					x1 = e1.x + DIA / 2 + (Math.cos(phi) * DIA + 1) / 2;
					y1 = e1.y + DIA / 2 + (Math.sin(phi) * DIA + 1) / 2;
					x2 = e2.x + DIA / 2 - (Math.cos(phi) * DIA - 1) / 2;
					y2 = e2.y + DIA / 2 - (Math.sin(phi) * DIA - 1) / 2;
					g2.draw(new Line2D.Double(x1, y1, x2, y2));
				}
			}
		}
	}

	public void setCircle(Point p) {
		Ellipse2D.Double e = new Ellipse2D.Double(p.x - DIA / 2, p.y - DIA / 2,
				DIA, DIA);
		circleList.add(e);
		repaint();
	}

	public void setShowLines(boolean show) {
		showLines = show;
		repaint();
	}

	public void clear() {
		circleList.clear();
		repaint();
	}
}

class Connector extends MouseAdapter {
	CirclesAndArrows circles;

	public Connector(CirclesAndArrows caa) {
		circles = caa;
	}

	public void mousePressed(MouseEvent e) {
		circles.setCircle(e.getPoint());
	}

	public JPanel getUIPanel() {
		JButton clear = new JButton("clear");
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				circles.clear();
			}
		});
		final JCheckBox connect = new JCheckBox("connect circles");
		connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				circles.setShowLines(connect.isSelected());
			}
		});
		JPanel panel = new JPanel();
		panel.add(clear);
		panel.add(connect);
		return panel;
	}
}
