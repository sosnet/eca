package datatypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import jpl.Atom;
import jpl.Query;
import jpl.Term;
import businesslogic.conf.ComputationConstants;

public class LambdaMatrix {

  private int[][] matrix;
  private int gridsize;

  public LambdaMatrix(int dimension, int gridsize) {
    matrix = new int[dimension][dimension];
    this.gridsize = gridsize;
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();

    buf.append("     ");
    int dimension = matrix.length;
    for (int a = 0; a < dimension; ++a)
      buf.append(String.format("[%02d] ", a));
    for (int a = 0; a < dimension; ++a) {
      buf.append(String.format("\n[%02d]  ", a));
      for (int b = 0; b < dimension; ++b) {
        buf.append(((matrix[a][b] != -1) ? (String.format("%2d   ",
            matrix[a][b])) : ("--   ")));
      }
    }

    return buf.toString();

  }

//  public PointSetSet getCollinearPoints() {
//    return colliear;
//  }

//  public boolean setCollinearPoints(PointSetSet coL) {
//    // check for colinearity
//    boolean mergeOnce = false;
//
//    this.colliear = new PointSetSet();
//
//    for (PointSet pointSet : coL) {
//      boolean merged = false;
//      for (PointSet pointSet2 : coL) {
//        if (pointSet.equals(pointSet2))
//          continue;
//        PointSet cut = new PointSet(pointSet);
//        cut.retainAll(pointSet2);
//        if (cut.size() > 1) {
//          merged = true;
//          mergeOnce = true;
//          cut.addAll(pointSet2);
//          cut.addAll(pointSet);
//          // pointSet2.addAll(pointSet);
//          this.colliear.add(cut);
//        }
//      }
//      if (!merged) {
//        this.colliear.add(pointSet);
//      }
//    }
//    return mergeOnce;
//
//  }

  // this is not correct, maybe
//  public boolean isSmaller(LambdaMatrix m) throws Exception {
//    if (m.size() != this.size()) {
//      throw new Exception("Fucking Error: m: " + m.size() + " this: "
//          + this.size());
//    }
//    Line[] line1Array = new Line[m.size()];
//    m.toArray(line1Array);
//    Line[] line2Array = new Line[this.size()];
//    this.toArray(line2Array);
//    for (int i = 0; i < this.size(); i++) {
//      if (line1Array[i].left > line2Array[i].left) {
//        return true;
//      } else if (line1Array[i].left < line2Array[i].left)
//        return false;
//    }
//    return false;
//  }

//  public List<GridPoint> getKh() {
//    return kh;
//  }
//
//  public void setKh(List<GridPoint> kh) {
//    this.kh = kh;
//  }
//
//  public void convolute() {
//    relable();
//    dimenstion = (int) Math.sqrt(size());
//    matrix = new int[dimenstion][dimenstion];
//    // StringBuffer buf = new StringBuffer();
//
//    // header
//    GridPoint p0 = null;
//    // buf.append("\t");
//    for (Line l : this) {
//
//      if (l.p1.compareTo(p0) != 0) {
//        p0 = l.p1;
//        // buf.append("[" + l.p1 + "]").append("\t");
//      }
//    }
//
//    p0 = null;
//    int a = -1, b = 0;
//    for (Line l : this) {
//
//      if (l.p1.compareTo(p0) != 0) {
//        b = 0;
//        ++a;
//        // buf.append("\n");
//        p0 = l.p1;
//        // buf.append("[" + l.p1 + "]").append("\t");
//      }
//      matrix[a][b] = l.left < 0 ? -1 : l.left;
//      ++b;
//      // buf.append(" ").append(l.left < 0 ? "-" : l.left).append("\t");
//    }
//
//  }

  public int getDimenstion() {
    return matrix.length;
  }

  public int get(int p1, int p2) {
    if (p1 < 0 || p2 < 0) {
      System.err.println("p1: " + p1 + ", p2: " + p2);
      return -1;
    }

    return matrix[p1][p2];
  }
  
  public void set (int p1, int p2, int value) {
    matrix[p1][p2] = value;
  }

  public int getGridsize() {
    return gridsize;
  }

//  public boolean equals(Object o) {
//    if (o == null || !(o instanceof LambdaMatrix))
//      return false;
//    LambdaMatrix mat = (LambdaMatrix) o;
//    if (mat.getDimenstion() != getDimenstion())
//      return false;
//    for (int a = 0; a < dimenstion; ++a) {
//      for (int b = 0; b < dimenstion; ++b) {
//        if (mat.get(a, b) != get(a, b))
//          return false;
//      }
//    }
//    return true;
//  }

  
  
  }
