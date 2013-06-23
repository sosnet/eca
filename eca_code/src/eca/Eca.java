package eca;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import types.Line;
import types.GridPoint;

public class Eca extends JFrame {
//
//	private class Grid extends JPanel {
//
//		private BufferedImage canvas;
//		private Set<GridPoint> points;
//		private Set<Set<GridPoint>> colinear;
//		private List<GridPoint> kH;
//
//		public Grid(Collection points, Set<Set<GridPoint>> colinear, Collection kH) {
//			super();
//			this.points = new TreeSet<GridPoint>();
//			this.points.addAll(points);
//			this.colinear = colinear;
//			this.kH = new LinkedList<GridPoint>();
//			this.kH.addAll(kH);
//
//		}
//
//		public void paint(Graphics g) {
//			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
//					RenderingHints.VALUE_ANTIALIAS_ON);
//			g.clearRect(0, 0, 800, 600);
//			g.setColor(new Color(255, 255, 255));
//			g.fillRect(0, 0, 800, 600);
//			g.setColor(new Color(100, 100, 100));
//			for (int x = 0; x < 27; ++x) {
//				g.drawString("" + x, x * 30 + 16, 14);
//				g.drawLine(x * 30 + 15, 0, x * 30 + 15, 600);
//			}
//			for (int y = 0; y < 20; ++y) {
//				g.drawString("" + y, 16, y * 30 + 14);
//				g.drawLine(0, y * 30 + 15, 800, y * 30 + 15);
//			}
//			g.setColor(new Color(255, 0, 0));
//			for (Set<GridPoint> s : colinear) {
//				GridPoint tmp = null;
//				for (GridPoint p : s) {
//					if (tmp != null) {
//						g.drawLine(tmp.x * 30 + 15, tmp.y * 30 + 15,
//								p.x * 30 + 15, p.y * 30 + 15);
//					}
//					tmp = p;
//
//				}
//			}
//
//			g.setColor(new Color(0, 255, 0));
//			GridPoint prev = null;
//			for (GridPoint p : kH) {
//				if (prev != null) {
//					g.drawLine(prev.x * 30 + 15, prev.y * 30 + 15,
//							p.x * 30 + 15, p.y * 30 + 15);
//				}
//				prev = p;
//			}
//
//			g.setColor(new Color(0, 80, 0));
//			for (GridPoint p : this.points) {
//				// System.out.println("P");
//				g.fillOval(p.x * 30 + 10, p.y * 30 + 10, 11, 11);
//				g.drawString(p.getName(), p.x * 30 + 23, p.y * 30 + 10);
//			}
//
//		}
//
//	};
//
//	private Grid grid;
//
//	public Eca(Collection<GridPoint> points, Set<Set<GridPoint>> mergedCoL,
//			Collection<GridPoint> kH) {
//		super();
//		grid = new Grid(points, mergedCoL, kH);
//		this.getContentPane().add(grid, BorderLayout.CENTER);
//		this.pack();
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
//
//	public static void main(String[] args) {
//		Set<GridPoint> grid = new TreeSet<GridPoint>();
//		Set<Set<GridPoint>> mergedCoL = null;
//		//dont change this
//		int n = 18;
//		int s = 15;
//		int num = 0;
//		// create random point set
//		while (grid.size() < s) {
//			grid.add(new GridPoint(n, "[" + num++ + "]"));
//		}
//
//		for (GridPoint p : grid)
//			System.out.println(" x: " + p.x + " y: " + p.y);
//		List<GridPoint> kH = new LinkedList<GridPoint>();
//
//		List<GridPoint> gridAsList = new LinkedList<GridPoint>();
//		gridAsList.addAll(grid);
//		kH = GrahamScan.getConvexHull(gridAsList);
//		List<GridPoint> sorted = new LinkedList<GridPoint>();
//
//		GridPoint fpPoint = null;
//		Set<Line> fingerprint = new TreeSet<Line>();
//
//		// create all possible labels (lambda matrices)
//		for (GridPoint khp : kH) {
//			grid = GrahamScan.getSortedPointSet(gridAsList, khp);
//			sorted.clear();
//			int c = 0;
//			for (GridPoint tmp : grid) {
//				GridPoint mP = (GridPoint) tmp;
//				mP.setName(String.valueOf(c++));
//				sorted.add(mP);
//			}
//
//			Set<Line> lines = new TreeSet<Line>();
//			List<Set<GridPoint>> coL = new LinkedList<Set<GridPoint>>();
//
//			for (GridPoint p1 : sorted) {
//				for (GridPoint p2 : sorted) {
//					Line l = new Line(p1, p2);
//					if (p1.equals(p2)) {
//						l.left = -1;
//						lines.add(l);
//						continue;
//					}
//
//					for (GridPoint p3 : sorted) {
//						if (p2.equals(p3))
//							continue;
//						if (p1.equals(p3))
//							continue;
//						// calculate orientation
//						int o = orientation(l, p3);
//						if (o > 0)
//							l.left++;
//						if (o == 0) {
//							// System.out.println("colinar");
//							Set<GridPoint> pointSet = new HashSet<GridPoint>();
//							pointSet.add(p1);
//							pointSet.add(p2);
//							pointSet.add(p3);
//							coL.add(pointSet);
//						}
//					}
//					lines.add(l);
//				}
//			}
//
//			// check for colinearity
//			mergedCoL = new HashSet<Set<GridPoint>>();
//			for (Set<GridPoint> pointSet : coL) {
//				boolean merged = false;
//				for (Set<GridPoint> pointSet2 : coL) {
//					Set<GridPoint> cut = new HashSet<GridPoint>(pointSet);
//					cut.retainAll(pointSet2);
//					if (cut.size() > 1) {
//						merged = true;
//						pointSet2.addAll(pointSet);
//						mergedCoL.add(pointSet2);
//					}
//				}
//				if (!merged) {
//					mergedCoL.add(pointSet);
//				}
//			}
//
//			System.out.println("\n\nLambda Matrix:");
//			printLambda(lines);
////			System.out.println("\n\nColinear points:");
////			for (Set<MyPoint> sp : mergedCoL) {
////				System.out.print("{");
////				for (MyPoint p : sp) {
////					System.out.print(p.getName() + ", ");
////				}
////				System.out.print("}");
////				System.out.println("");
////			}
//
//			if (fpPoint == null || isBigger(lines, fingerprint)) {
//				fingerprint = lines;
//				fpPoint = khp;
//			}
//		}
//		
//		System.out.println("\n\nFingerprint:");
//		printLambda(fingerprint);
//
//		
//		Eca vis = new Eca(sorted, mergedCoL, kH);
//
//		vis.setBounds(0, 0, 800, 600);
//		vis.setVisible(true);
//	}
//
//	
//	private static boolean isBigger(Set<Line> lines1, Set<Line> lines2) {
//		//System.out.println("lines1: " + lines1);
//		if(lines1.size() != lines2.size()){
//			System.out.println("Fucking Error: lines1: " + lines1.size() + " Lines2: " + lines2.size());
//			return false;
//		}
//		Line[] line1Array = (Line[]) lines1.toArray(new Line[lines1.size()]);
//		Line[] line2Array = (Line[]) lines2.toArray(new Line[lines1.size()]);
//		for( int i = 0; i < lines1.size(); i++ ){
////			System.out.println(line1Array[i]);
////			System.out.println(line2Array[i]);
//			if(line1Array[i].left > line2Array[i].left){
//				return true;
//			}
//			else if(line1Array[i].left < line2Array[i].left)
//				return false;
//		}
//		return false;
//	}
//
//	private static int orientation(Line l, GridPoint c) {
//		GridPoint a = l.p1;
//		GridPoint b = l.p2;
//		return a.x * b.y + b.x * c.y + c.x * a.y - c.x * b.y - b.x * a.y - a.x
//				* c.y;
//	}
//
//	static private void printLambda(Set<Line> lines) {
//		String n = "";
//		// header
//		System.out.print("\t");
//		for (Line l : lines) {
//			String n2 = l.p1.getName();
//			if (n.compareTo(n2) != 0) {
//				n = n2;
//				System.out.print(n2 + "\t");
//			}
//		}
//		n = "";
//		for (Line l : lines) {
//			String n2 = l.p1.getName();
//			if (n.compareTo(n2) != 0) {
//				System.out.println("");
//				n = n2;
//				System.out.print(n2 + "\t");
//			}
//			System.out.print(l.left + "\t");
//		}
//
//	}

}
