package businesslogic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import jpl.Atom;
import jpl.JPL;
import jpl.Query;
import jpl.Term;
import businesslogic.conf.ComputationConstants;
import datatypes.GridPoint;
import datatypes.LambdaMatrix;

public class CheckLambda {
  private LambdaMatrix lm = null;
  public CheckLambda(LambdaMatrix lm) {
    this.lm = lm;
    
  }
  
//  public Set<GridPoint> realize() {
//    return null;
//  }
  
  private void calcCollCandidates(int p, Map<Integer, Set<Integer>> points) {
    Set<Integer> set = new HashSet<>();
    for (int i = 0; i < lm.getDimenstion(); ++i) {
      if (i == p)
        continue;
      int t = lm.get(p, i) + lm.get(i, p);
      if (t != lm.getDimenstion() - 2)
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
    JPL.setNativeLibraryDir("/usr/lib/swipl-6.2.6/lib/x86_64-linux");
    JPL.setNativeLibraryName("jpl");
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
    for (int i = 0; i < lm.getDimenstion(); ++i) {
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

    for (int outer = 0; outer < lm.getDimenstion(); ++outer) {
      for (int inner = outer; inner < lm.getDimenstion(); ++inner) {
        for (int i = 0; i < lm.getDimenstion(); ++i) {
          if (inner == outer || inner == i || outer == i)
            continue;

          Set<Integer> s = new HashSet<>();
          s.add(inner);
          s.add(outer);
          s.add(i);
          if (triples.contains(s))
            continue;

          int orientation = lm.get(i, inner) - lm.get(outer, inner);
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
        p.setLabel(Integer.parseInt(o.toString().split("P")[1]));
        System.out.println(o + ": " + p.getX() + ", " + p.getY());
        gridpoints.add(p);
      }
      return gridpoints;
    } else
      throw new Exception("no solution found: " + qBuf.toString());

  }

  
}
