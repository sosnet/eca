package businesslogic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jpl.Atom;
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
    for (int i = 0; i < lm.getDimenstion(); ++i) {
      calcCollCandidates(i, cc);
    }
    Set<Set<Integer>> coll = calculateColl(cc);
    System.out.println(coll);

    Set<String> orientations = new HashSet<>();
    Set<String> mathematica = new HashSet<>();
    for(int p = 0; p< lm.getDimenstion(); p++) {
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

    for (int outer = 0; outer < lm.getDimenstion(); ++outer) {
      for (int inner = outer; inner < lm.getDimenstion(); ++inner) {
        if(inner == outer)
          continue;
        mathematica.add("unique[P" + outer + ", P" + inner + "]");
        
        for (int i = 0; i < lm.getDimenstion(); ++i) {
          if (inner == i || outer == i)
            continue;

          Set<Integer> s = new HashSet<>();
          s.add(inner);
          s.add(outer);
          s.add(i);
          if (triples.contains(s))
            continue;

          int orientation = lm.get(i, inner) - lm.get(outer, inner);
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
//          p1 + "x, " + p1 + "y, " +
//          p3 + "x, " + p3 + "y] < 0");
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
    
    for (int p = 0; p < lm.getDimenstion(); p++)
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
    for (int p = 0; p < lm.getDimenstion(); p++) {
      
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
