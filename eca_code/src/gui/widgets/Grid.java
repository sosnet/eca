package gui.widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import types.GridPoint;
import types.LambdaMatrix;
import businesslogic.Computer;

public class Grid extends JPanel {

	// private BufferedImage canvas;
	private LinkedHashSet<GridPoint> points;
	private LambdaMatrix matrix;
	private Point outline = null;
	private LambdaMatrix constructionMatrix;
	private boolean constructionMode;

	public Grid() {
		super();
		this.points = new LinkedHashSet<GridPoint>();
		this.matrix = new LambdaMatrix();
		this.constructionMode = false;

	}

	public void setPoints(Set<GridPoint> points) throws Exception {

		this.points.clear();
		this.points.addAll(points);
		if (!constructionMode)
			this.matrix = Computer.fingerprint(new TreeSet<>(points));
	}

	public void paint(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(new Color(100, 100, 100));
		for (int x = 0; x <= getWidth() / 30; ++x) {
			g.drawString("" + x, t(x) + 1, 14);
			g.drawLine(t(x), 0, t(x), getHeight());
		}
		for (int y = 0; y <= getHeight() / 30; ++y) {
			g.drawString("" + y, 16, t(y) - 1);
			g.drawLine(0, t(y), getWidth(), t(y));
		}
		if (constructionMode) {

			GridPoint[] pnts = new GridPoint[points.size()];
			points.toArray(pnts);

			for (int outer = 0; outer < pnts.length; ++outer) {
				GridPoint a = pnts[outer];
				for (int inner = outer; inner < pnts.length; ++inner) {
					GridPoint b = pnts[inner];
					if ((a.x == b.x)
							&& (a.y == b.y)
							|| pnts.length >= constructionMatrix
									.getDimenstion())
						continue;

					g.setColor(Color.BLUE);

					double k = 0.0;
					double ys = 0.0;

					if (a.x == b.x) {
						k = Math.PI / 2;
						g.drawLine(t(a.x), 0, t(a.x), 2000);
					} else {
						k = (double) (b.y - a.y) / (double) (b.x - a.x);
						ys = 1000.0 * k;
						g.drawLine(t(a.x) - 1000, (int) (t(a.y) - ys),
								t(a.x) + 1000, (int) (t(a.y) + ys));
					}

					g.setColor(new Color(0, 0, 200, 80));
					Graphics2D g2 = (Graphics2D) g;
					g.translate(t(a.x), t(a.y));
					g2.rotate(Math.atan(k));

					int leftright = 0; // 0 or -4000
					int orientation = 0;

					orientation = constructionMatrix.get(a.getName(),
							b.getName())
							- constructionMatrix.get(a.getName(), pnts.length);

					System.out.println("Orientation for p[" + pnts.length
							+ "]: m[" + a.getName() + ", " + b.getName()
							+ "] - m[" + a.getName() + ", " + pnts.length
							+ "] = "
							+ constructionMatrix.get(a.getName(), b.getName())
							+ " - "
							+ constructionMatrix.get(a.getName(), pnts.length)
							+ " = " + orientation + " --> "
							+ (orientation < 0 ? "links" : "rechts") + " von "
							+ b.getName() + "-" + a.getName());

					// check orientation
					if (constructionMatrix.get(a.getName(), pnts.length)
							+ constructionMatrix.get(pnts.length, a.getName()) != constructionMatrix
							.getDimenstion() - 2
							&& constructionMatrix.get(b.getName(), pnts.length)
									+ constructionMatrix.get(pnts.length,
											b.getName()) != constructionMatrix
									.getDimenstion() - 2) {
						// TODO: This is a colliniar point to points a and b, so
						// the only valid set is ON the line a-b
						System.out.println("p[" + pnts.length
								+ "] is collinear to p[" + a.getName()
								+ "] and p[" + b.getName() + "]");
						// workaround for doing nothing
						leftright = -1;
					}

					else if (orientation == 0) {
						// invert orientation calculation

						orientation = -(constructionMatrix.get(
								pnts.length,
								a.getName()
										- constructionMatrix.get(b.getName(),
												a.getName())));
						System.out
								.println("We had a orientation of 0, so we change the calculation. New orientation is "
										+ orientation);

						System.out.println("Orientation for p["
								+ pnts.length
								+ "]: m["
								+ pnts.length
								+ ", "
								+ a.getName()
								+ "] - m["
								+ b.getName()
								+ ", "
								+ a.getName()
								+ "] = "
								+ constructionMatrix.get(pnts.length,
										a.getName())
								+ " - "
								+ constructionMatrix.get(b.getName(),
										a.getName()) + " = " + orientation
								+ " --> "
								+ (orientation < 0 ? "links" : "rechts")
								+ " von " + b.getName() + "-" + a.getName());

						// delete this, because at the moment, we are not
						// handling it.... maybe the alternattive calculation is
						// wrong

						
						if(orientation < 0)
							leftright = 0;
						else if (orientation > 0)
							leftright = -4000;
						else {
							leftright = -1;
							System.err.println("Error");
						}
						
						boolean donothing = false;
						
						if(donothing)
							leftright = -1;

					}

					else if (orientation < 0) {
						leftright = 0;
						System.out.println("left");
					} else if (orientation > 0) {
						leftright = -4000;
					} else {
						System.out.println("ERROR collinear");
						leftright = -1;
						// continue;
					}

					if (leftright != -1)
						g2.fillRect(-2000, leftright, 4000, 4000);

					g2.rotate(Math.atan(-k));
					g.translate(-t(a.x), -t(a.y));

				}

			}
		}
		g.setColor(new Color(255, 0, 0));
		for (Set<GridPoint> s : matrix.getCollinearPoints()) {
			GridPoint tmp = null;
			for (GridPoint p : s) {
				if (tmp != null) {
					g.drawLine(t(tmp.x), t(tmp.y), t(p.x), t(p.y));
				}
				tmp = p;

			}
		}

		g.setColor(new Color(0, 200, 0));
		Point prev = null;
		for (Point p : matrix.getKh()) {
			if (prev != null) {
				g.drawLine(t(prev.x), t(prev.y), t(p.x), t(p.y));
			}
			prev = p;
		}

		g.setColor(new Color(255, 0, 0));
		for (Set<GridPoint> s : matrix.getCollinearPoints()) {
			GridPoint tmp = null;
			for (GridPoint p : s) {
				if (tmp != null) {
					g.drawLine(t(tmp.x), t(tmp.y), t(p.x), t(p.y));
				}
				tmp = p;
			}
		}

		for (GridPoint p : this.points) {
			// System.out.println("P");
			if (p.isHighlighted()) {
				g.setColor(Color.WHITE);
				g.fillOval(t(p.x) - 7, t(p.y) - 7, 15, 15);
				g.setColor(Color.BLACK);
				g.drawOval(t(p.x) - 8, t(p.y) - 8, 16, 16);
				g.drawOval(t(p.x) - 9, t(p.y) - 9, 18, 18);
			}
			g.setColor(new Color(0, 80, 0));
			g.fillOval(t(p.x) - 5, t(p.y) - 5, 11, 11);
			g.drawString(String.valueOf("[" + p.getName()) + "]", t(p.x) + 8,
					t(p.y) - 5);

		}
		if (outline != null) {
			g.setColor(Color.BLUE);
			g.drawOval((int) outline.getX() - 6, (int) outline.getY() - 6, 12,
					12);
		}

	}

	private int t(int p) {
		return p * 30 + 15;
	}

	public int getX(int x) {
		return (x) / 30;
	}

	public int getY(int y) {
		return (y) / 30;
	}

	public LambdaMatrix getLambdaMatrix() {
		return matrix;
	}

	public void drawOutline(int x, int y) {
		outline = new Point(x, y);
		repaint();

	}

	public void clearOutline() {
		outline = null;
	}

	public LambdaMatrix getConstructionMatrix() {
		return constructionMatrix;
	}

	public void setConstructionMatrix(LambdaMatrix constructionMatrix) {
		this.constructionMatrix = constructionMatrix;
	}

	public boolean isConstructionMode() {
		return constructionMode;
	}

	public void setConstructionMode(boolean constructionMode) {
		this.constructionMode = constructionMode;
	}

};
