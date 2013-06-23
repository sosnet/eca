package businesslogic;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import types.GridPoint;
import types.LambdaMatrix;
import types.Line;
import types.PointSet;
import types.PointSetSet;

public class Computer {

  public static LambdaMatrix fingerprint(SortedSet<GridPoint> grid) throws Exception {
    //Set<Set<GridPoint>> mergedCoL = new HashSet<Set<GridPoint>>();
	  //Set<Set<GridPoint>> coL = new HashSet<Set<GridPoint>>();
	  PointSetSet coL = new PointSetSet();
	  
	  final Set<Set<GridPoint>> coLF;// = new HashSet<Set<GridPoint>>();

    for (GridPoint p : grid)
      System.out.println(" x: " + p.x + " y: " + p.y);
    List<GridPoint> kH = new LinkedList<GridPoint>();

    List<GridPoint> gridAsList = new LinkedList<GridPoint>();
    gridAsList.addAll(grid);
    try{
    kH = GrahamScan.getConvexHull(gridAsList);
    }catch(Exception e){}
    List<GridPoint> sorted = new LinkedList<GridPoint>();

    //GridPoint fpPoint = null;
    LambdaMatrix fingerprint = new LambdaMatrix();

    // create all possible labels (lambda matrices)
    for (GridPoint khp : kH) {
    	coL.clear();
      grid = GrahamScan.getSortedPointSet(gridAsList, khp);
      sorted.clear();
      int c = 0;
      for (Point tmp : grid) {
        GridPoint mP = (GridPoint) tmp;
        mP.setName(c++);
        sorted.add(mP);
      }

      LambdaMatrix lines = new LambdaMatrix();
      

      for (GridPoint p1 : sorted) {
        for (GridPoint p2 : sorted) {
          Line l = new Line(p1, p2);
          if (p1.equals(p2)) {
            l.left = -1;
            lines.add(l);
            continue;
          }

          for (GridPoint p3 : sorted) {
            if (p2.equals(p3))
              continue;
            if (p1.equals(p3))
              continue;
            // calculate orientation
            double o = l.orientation(p3);
            if (o < 0)
              l.left++;
            if (o == 0) {
              // System.out.println("colinar");
              PointSet pointSet = new PointSet();
              pointSet.add(p1);
              pointSet.add(p2);
              pointSet.add(p3);
//              if(!coL.isEmpty())
//            	  System.out.println("First: " +  coL.first() + " equals " + pointSet + "? " + pointSet.equals(coL.first()));
//              System.out.println("PointPointSet: " + coL + " contains: " + pointSet + " ? " + coL.contains(pointSet));
              coL.add(pointSet);
            }
          }
          lines.add(l);
        }
      }


      System.out.println("\n\nLambda Matrix:");
      System.out.println(lines.toString());

      if (fingerprint.isEmpty() || lines.isSmaller(fingerprint)) {
        fingerprint = lines;
      }
    }

    System.out.println("Size Col: " + coL.size());

    fingerprint.setCollinearPoints(coL);
    while(fingerprint.setCollinearPoints(fingerprint.getCollinearPoints()));
    fingerprint.setKh(kH);
    fingerprint.convolute();
    System.out.println("\n\nFingerprint:");
    System.out.println(fingerprint.toString());
    
    return fingerprint;

  }
}
