package types;

public class Line implements Comparable {
	public GridPoint p1;
	public GridPoint p2;
	public int left;

	public Line(GridPoint a, GridPoint b) {
		p1 = a;
		p2 = b;
		left = 0;
	}

	@Override
	public int compareTo(Object arg0) {
		if (arg0 == null)
			return 0;
		Line l = (Line) arg0;
		int c = Integer.compare(p1.getName(), l.p1.getName());
		if (c == 0)
			return Integer.compare(p2.getName(), l.p2.getName());
		return c;
	}

	public double orientation(GridPoint p) {
		GridPoint a = p1;
		GridPoint b = p2;
		double ret = a.x * b.y + b.x * p.y + p.x * a.y - p.x * b.y - b.x * a.y
				- a.x * p.y;
		return ret;
	}

	@Override
	public String toString() {
		return p1.getName() + " " + p1.x + " " + p1.y + ", " + p2.getName()
				+ " " + p2.x + " " + p2.y + " Points left: " + left;
	}

}
