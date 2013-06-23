package types;

import java.util.Random;

public class GridPoint extends java.awt.Point implements Comparable {

  private int name;
  private boolean highlighted;

  public GridPoint(){
	  
  }
  
  public GridPoint(int x, int y) {
    super(x, y);
    this.name = 0;
    highlighted = false;
  }

  // public GridPoint(int n, int name) {
  // super();
  // highlighted = false;
  // this.name = name;
  // Random rn = new Random();
  // int x = (Math.abs(rn.nextInt()) % n) + 1;
  // int y = (Math.abs(rn.nextInt()) % n) + 1;
  // // System.out.println(x + " " + y);
  // this.setLocation(x, y);
  // }

  @Override
  public int compareTo(Object o) {
    if (o == null)
      return -2;
    GridPoint p = (GridPoint) o;
    Integer x = new Integer(p.x);
    Integer y = new Integer(p.y);

    if (this.x == p.x)
      return y.compareTo(this.y);
    return x.compareTo(this.x);
  }

  public int getName() {
    return name;
  }

  public void setName(int name) {
    this.name = name;
  }

  public String toString() {
    return String.format("%2d", name);
  }

  public void setHighlighted(boolean highlighted) {
    this.highlighted = highlighted;
  }

  public boolean isHighlighted() {
    return highlighted;
  }

  @Override
  public boolean equals(Object o) {
    if ((o != null) && (o instanceof GridPoint)) {
      return (x == ((GridPoint) o).getX()) && (y == ((GridPoint) o).getY());
    }
    return false;

  }
}
