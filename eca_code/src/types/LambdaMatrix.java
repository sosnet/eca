package types;

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

public class LambdaMatrix extends TreeSet<Line> {

  private PointSetSet colliear = null;
  private List<GridPoint> kh = null;
  private List<GridPoint> sorted = null;
  private int[][] matrix;
  private int dimenstion = 0;

  public LambdaMatrix() {
    super();
    colliear = new PointSetSet();
    kh = new LinkedList<GridPoint>();
    // setSorted(new LinkedList<GridPoint>());
  }

  private void relable() {
    int i = 0;
    GridPoint p0 = null;
    for (Line l : this) {
      if (l.p1.compareTo(p0) != 0) {
        p0 = l.p1;
        l.p1.setName(i);
        ++i;
      }
    }
  }

  @Override
  public String toString() {
    StringBuffer buf = new StringBuffer();

    // // header
    // GridPoint p0 = null;
    // buf.append("\t");
    // for (Line l : this) {
    //
    // if (l.p1.compareTo(p0) != 0) {
    // p0 = l.p1;
    // buf.append("[" + l.p1 + "]").append("\t");
    // }
    // }
    //
    // p0 = null;
    // for (Line l : this) {
    //
    // if (l.p1.compareTo(p0) != 0) {
    // buf.append("\n");
    // p0 = l.p1;
    // buf.append("[" + l.p1 + "]").append("\t");
    // }
    // buf.append(" ").append(l.left < 0 ? "-" : l.left).append("\t");
    // }
    //
    // buf.append("\nnow using real matrix representation:\n");
    buf.append("     ");
    for (int a = 0; a < dimenstion; ++a)
      buf.append(String.format("[%02d] ", a));
    for (int a = 0; a < dimenstion; ++a) {
      buf.append(String.format("\n[%02d]  ", a));
      for (int b = 0; b < dimenstion; ++b) {
        buf.append(((matrix[a][b] != -1) ? (String.format("%2d   ",
            matrix[a][b])) : ("--   ")));
      }
    }

    return buf.toString();

  }

  public PointSetSet getCollinearPoints() {
    return colliear;
  }

  public boolean setCollinearPoints(PointSetSet coL) {
    // check for colinearity
    boolean mergeOnce = false;

    this.colliear = new PointSetSet();

    for (PointSet pointSet : coL) {
      boolean merged = false;
      for (PointSet pointSet2 : coL) {
        if (pointSet.equals(pointSet2))
          continue;
        PointSet cut = new PointSet(pointSet);
        cut.retainAll(pointSet2);
        if (cut.size() > 1) {
          merged = true;
          mergeOnce = true;
          cut.addAll(pointSet2);
          cut.addAll(pointSet);
          // pointSet2.addAll(pointSet);
          this.colliear.add(cut);
        }
      }
      if (!merged) {
        this.colliear.add(pointSet);
      }
    }
    return mergeOnce;

  }

  // this is not correct, maybe
  public boolean isSmaller(LambdaMatrix m) throws Exception {
    if (m.size() != this.size()) {
      throw new Exception("Fucking Error: m: " + m.size() + " this: "
          + this.size());
    }
    Line[] line1Array = new Line[m.size()];
    m.toArray(line1Array);
    Line[] line2Array = new Line[this.size()];
    this.toArray(line2Array);
    for (int i = 0; i < this.size(); i++) {
      if (line1Array[i].left > line2Array[i].left) {
        return true;
      } else if (line1Array[i].left < line2Array[i].left)
        return false;
    }
    return false;
  }

  public List<GridPoint> getKh() {
    return kh;
  }

  public void setKh(List<GridPoint> kh) {
    this.kh = kh;
  }

  public void convolute() {
    relable();
    dimenstion = (int) Math.sqrt(size());
    matrix = new int[dimenstion][dimenstion];
    // StringBuffer buf = new StringBuffer();

    // header
    GridPoint p0 = null;
    // buf.append("\t");
    for (Line l : this) {

      if (l.p1.compareTo(p0) != 0) {
        p0 = l.p1;
        // buf.append("[" + l.p1 + "]").append("\t");
      }
    }

    p0 = null;
    int a = -1, b = 0;
    for (Line l : this) {

      if (l.p1.compareTo(p0) != 0) {
        b = 0;
        ++a;
        // buf.append("\n");
        p0 = l.p1;
        // buf.append("[" + l.p1 + "]").append("\t");
      }
      matrix[a][b] = l.left < 0 ? -1 : l.left;
      ++b;
      // buf.append(" ").append(l.left < 0 ? "-" : l.left).append("\t");
    }

  }

  public int getDimenstion() {
    return dimenstion;
  }

  public int get(int p1, int p2) {
    if (p1 < 0 || p2 < 0) {
      System.err.println("p1: " + p1 + ", p2: " + p2);
      return -1;
    }

    return matrix[p1][p2];
  }

  public boolean equals(Object o) {
    if (o == null || !(o instanceof LambdaMatrix))
      return false;
    LambdaMatrix mat = (LambdaMatrix) o;
    if (mat.getDimenstion() != getDimenstion())
      return false;
    for (int a = 0; a < dimenstion; ++a) {
      for (int b = 0; b < dimenstion; ++b) {
        if (mat.get(a, b) != get(a, b))
          return false;
      }
    }
    return true;
  }

  private void calcCollCandidates(int p, Map<Integer, Set<Integer>> points) {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < matrix.length; ++i) {
      if (i == p)
        continue;
      int t = matrix[p][i] + matrix[i][p];
      if (t != matrix.length - 2)
        set.add(i);
    }
    if (set.size() > 0) {
      set.add(p);
      // if (!points.containsValue(set))
      points.put(p, set);
    }
  }

  private Set<Set<Integer>> calculateColl(Map<Integer, Set<Integer>> points) {
    Set<Set<Integer>> coll = new HashSet<>();
    for (int p : points.keySet()) {
      for (int i : points.get(p)) {
        if (i == p)
          continue;
        if (points.get(i) != null) {
          Set<Integer> temp = new HashSet<>();
          temp.addAll(points.get(p));
          temp.retainAll(points.get(i));
          coll.add(temp);
        }
      }
    }
    return coll;
  }

  public Set<GridPoint> realize() throws Exception {
    // JPL.setNativeLibraryDir("/usr/lib/swipl-6.2.6/lib/x86_64-linux");
    // JPL.setNativeLibraryName("jpl");
    jpl.JPL.loadNativeLibrary();
    Query clpf = new Query("use_module(library(clpfd))");// , new Term[] { new
                                                         // Compound("library",
    // new Term[] { new Atom("clpfd)") }) });
    Query load = new Query("consult", new Term[] { new Atom(
        ComputationConstants.PATH_PROLOG_DB) });

    System.out.println("consult "
        + (clpf.oneSolution() != null ? "succeeded" : "failed"));
    System.out.println("consult "
        + (load.oneSolution() != null ? "succeeded" : "failed"));
    HashMap<Integer, Set<Integer>> cc = new HashMap<>();
    for (int i = 0; i < matrix.length; ++i) {
      calcCollCandidates(i, cc);
    }
    Set<Set<Integer>> coll = calculateColl(cc);
    System.out.println(coll);

    Set<String> orientations = new HashSet<>();
    Set<String> mathematica = new HashSet<>();
    for(int p = 0; p< matrix.length; p++) {
    mathematica.add("P" + p + "x <= max"); mathematica.add("P" + p + "y <= max");
    mathematica.add("P" + p + "x >= min"); mathematica.add("P" + p + "y >= min");
    }
    Set<Set<Integer>> triples = new HashSet<>();
    for (Set<Integer> s : coll) {
      for (int p1 : s) {
        for (int p2 : s) {
          for (int p3 : s) {
            if (p1 != p2 && p1 != p3 && p2 != p3) {
              Set<Integer> triple = new HashSet<>();
              triple.add(p1);
              triple.add(p2);
              triple.add(p3);
              if (triples.add(triple)) {
//                orientations.add("isColl(P" + p1 + ",P" + p2 + ",P" + p3 + ")");
                mathematica.add("orientation[P" + p1 + ", " +
                							"P" + p2 + ", " +
                							"P" + p3 + "] == 0");
              }
            }
          }
        }
      }
    }
    System.out.println(triples);

    for (int outer = 0; outer < matrix.length; ++outer) {
      for (int inner = outer; inner < matrix.length; ++inner) {
    	  if(inner == outer)
    		  continue;
    	  mathematica.add("unique[P" + outer + ", P" + inner + "]");
        
    	  for (int i = 0; i < matrix.length; ++i) {
          if (inner == i || outer == i)
            continue;

          Set<Integer> s = new HashSet<>();
          s.add(inner);
          s.add(outer);
          s.add(i);
          if (triples.contains(s))
            continue;

          int orientation = matrix[i][inner] - matrix[outer][inner];
          // int orientation2 = matrix[inner][outer] - matrix[inner][i];
          
          String p1, p2, p3;
          p1 = ("P" + outer);
          p2 = ("P" + inner);
          p3 = ("P" + i);
          
          if (orientation < 0) {
//            orientations.add("isLeft(" + p1 + "," + p2 + "," + p3 + ")");
            mathematica.add("orientation[" + p1 + ", " +
					p2 + ", " +
					p3 + " ] < 0");
          } else if (orientation > 0) {
//            orientations.add("isLeft(" + p2 + "," + p1 + "," + p3 + ")");
            mathematica.add("orientation[" + p1 + ", " + 
					p2 + ", " +
					p3 + "] > 0");
//            mathematica.add("orientation[" + p2 + "x, " + p2 + "y, " +
//					p1 + "x, " + p1 + "y, " +
//					p3 + "x, " + p3 + "y] < 0");
          }
        }
      }
    }

    StringBuilder mBuf = new StringBuilder();
    StringBuilder mVars = new StringBuilder();
    StringBuilder mSolution = new StringBuilder();
    
    mBuf.append("\n\n\norientation[{Ax_, Ay_}, {Bx_, By_}, {Cx_, Cy_}] := Ax*By + Bx*Cy + Cx*Ay - Cx*By - Bx*Ay - Ax*Cy;\n");
    mBuf.append("unique[{P1x_, P1y_}, {P2x_, P2y_}] := (P1x != P2x || P1y != P2y);\n\n");
    
    mBuf.append("min = 0;\n max = 20;\n");
    
    for (int p = 0; p < matrix.length; p++)
    	mBuf.append("P" + p + " = {P" + p + "x, P" + p + "y};\n");
    
    mBuf.append("\nResolve[{");
    boolean first = true;
    for (String o : mathematica) {
    	if(first)
    		first = false;
    	else
    		mBuf.append(", \n");
    	
        mBuf.append(o);
    }
    first = true;
    mBuf.append("}, ");
    
    mVars.append("{");
    String tmp = "%";
    for (int p = 0; p < matrix.length; p++) {
    	
    	if(first)
    		first = false;
    	else
    		mVars.append(", \n");
    
    	mVars.append("P" + p + "x, P" + p + "y");
    	mSolution.append("{P" + p + "x, P" + p + "y} /. ").append(tmp).append("\n");
    	tmp += "%";
    }
    mVars.append("}");
    mBuf.append(mVars.toString()).append(", Integers];\n\n");
    mBuf.append("FindInstance[%, ").append(mVars.toString()).append(", Integers];\n\n");
    mBuf.append(mSolution.toString()).append("\n\n\n");
    System.out.println(mBuf.toString());
	return null;
    
  }
}
