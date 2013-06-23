package types;

import java.util.TreeSet;

public class PointSet extends TreeSet<GridPoint> implements Comparable {
	
	public PointSet(PointSet pointSet) {
		super(pointSet);
	}

	public PointSet() {
		super();
	}

	@Override
	public boolean equals(Object o) {
		if ((o != null) && (o instanceof PointSet)) {
			//PointSet other = (PointSet) o;
			PointSet oth = ((PointSet) o);
			if(this.size() != oth.size()) return false;
			
			GridPoint[] other = oth.toArray(new GridPoint[oth.size()]);
			GridPoint[] me = this.toArray(new GridPoint[this.size()]);
			for(int i = 0; i < this.size(); ++i){
				if(other[i] == null){
					return false;
				}
				
				if(!me[i].equals(other[i])) return false;
			}
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Object o) {
		if ((o != null) && (o instanceof PointSet)) {
			PointSet oth = ((PointSet) o);
			if(this.size() <= oth.size()) return 1;
			
			GridPoint[] other = oth.toArray(new GridPoint[oth.size()]);
			GridPoint[] me = this.toArray(new GridPoint[this.size()]);
			for(int i = 0; i < this.size(); ++i){
				if(other[i] == null){
					return -1;
				}
				int c = me[i].compareTo(other[i]);
				if( c != 0) return 1;
			}
			return 0;
		}
		return -1;
	}

}
