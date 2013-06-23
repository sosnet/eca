package businesslogic;

import java.util.LinkedList;
import java.util.List;

import types.GridPoint;
import types.Line;


@Deprecated
public class FingerprintDrawer {

  GridPoint center;
  GridPoint bottom;
  GridPoint top;
  int n = 0;
  int dim = 0;
  int s = 0;
  private List<GridPoint> points = null;
  
  public FingerprintDrawer(int n, int s) {
    this.n = n;
    this.dim = (Math.round(n/2) - 1);
    this.s = s;
    this.center = new GridPoint((int) Math.round(n/2),(int) Math.round(n/2));
    this.top = new GridPoint((int) (this.center.getX()), ((int) (this.center.getY()) + this.dim));
    this.bottom = new GridPoint((int) (this.center.getX()), ((int) (this.center.getY()) - this.dim));
    points = new LinkedList<GridPoint>();
  }
  
  public int initConvexHull() {
    if (n < 3) return -1;
    
    int axes = 0;
    int degree = 0;
    int i = 0;
    for(int k = n; k >= 1; k = k - 2) {
      Line l = new Line(top, bottom);
      while(true) {
        if (!points.contains(l.p1) && !points.contains(l.p2)){
          System.out.println("k: "+ k + " : will add " + l.p1 + " and " + l.p2 + "to initGraph");
          points.add(l.p1);
          points.add(l.p2);
          break;
        } 
      }
      axes++;
      
    }
    return 0;
    
  }
  
}
