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
import jpl.Compound;
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
    Set<Set<Integer>> triples = new HashSet<>();
    for (Set<Integer> s : coll) {
      // if (s.size() > 3) {
      for (int p1 : s) {
        for (int p2 : s) {
          for (int p3 : s) {
            if (p1 != p2 && p1 != p3 && p2 != p3) {
              Set<Integer> triple = new HashSet<>();
              triple.add(p1);
              triple.add(p2);
              triple.add(p3);
              if (triples.add(triple))
                orientations.add("isColl(P" + p1 + ",P" + p2 + ",P" + p3 + ")");
            }
          }
        }
      }
      // } else{
      //
      // triples.add(s);
      // }
    }
    System.out.println(triples);

    for (int outer = 0; outer < matrix.length; ++outer) {
      for (int inner = outer; inner < matrix.length; ++inner) {
        for (int i = 0; i < matrix.length; ++i) {
          if (inner == outer || inner == i || outer == i)
            continue;

          Set<Integer> s = new HashSet<>();
          s.add(inner);
          s.add(outer);
          s.add(i);
          if (triples.contains(s))
            continue;

          int orientation = matrix[i][inner] - matrix[outer][inner];
          // int orientation2 = matrix[inner][outer] - matrix[inner][i];
          int priority = 0;
          String p1;
          // if (inner == 0) {
          // ++priority;
          // p1 = "p(5,1)";
          // } else if (inner == 1) {
          // p1 = "p(10,1)";
          // ++priority;
          // } else
          p1 = ("P" + outer);
          String p2;
          // if (outer == 0) {
          // p2 = "p(5,1)";
          // ++priority;
          // } else if (outer == 1) {
          // p2 = "p(10,1)";
          // ++priority;
          // } else
          p2 = ("P" + inner);
          int pos = priority == 2 ? 0 : priority == 1 ? orientations.size() / 2
              : orientations.size();
          // if (orientation != orientation2)
          // ;// orientations.add(pos, "isColl(" + p1 + "," + p2 + ",P" + i +
          // // ")");
          // else
          if (orientation < 0) {
            orientations.add("isLeft(" + p1 + "," + p2 + ",P" + i + ")");
          } else if (orientation > 0) {
            orientations.add("isLeft(" + p2 + "," + p1 + ",P" + i + ")");
          }
          // else
          // orientations.add(pos, "isColl(" + p1 + "," + p2 + ",P" + i + ")");
        }
      }
    }
    StringBuilder qBuf = new StringBuilder();
    int i = 0;
    for (String o : orientations) {
      qBuf.append(o).append(i == orientations.size() - 1 ? "." : ",");
      ++i;
    }
    Query lambda = new Query(qBuf.toString());
    System.out.println("Querying: \n" + qBuf.toString());
    // lambda.query();

    lambda.goal();
    Hashtable solution = lambda.oneSolution();

    if (solution != null) {
      System.out.println("REALIZATION:");
      Set<GridPoint> gridpoints = new HashSet<>();
      for (Object o : solution.keySet()) {
        String pointstring = solution.get(o).toString();
        StringTokenizer st = new StringTokenizer(pointstring, "p(, )");
        GridPoint p = new GridPoint(Integer.parseInt(st.nextToken()),
            Integer.parseInt(st.nextToken().trim()));
        p.setName(Integer.parseInt(o.toString().split("P")[1]));
        System.out.println(o + ": " + p.getX() + ", " + p.getY());
        gridpoints.add(p);
      }
      return gridpoints;
    } else
      throw new Exception("no solution found: " + qBuf.toString());

  }
}
