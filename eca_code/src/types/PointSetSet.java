package types;

import java.util.TreeSet;

public class PointSetSet extends TreeSet<PointSet> implements Comparable {
	
	@Override
	public boolean equals(Object o) {
		if ((o != null) && (o instanceof PointSetSet)) {
			//PointSet other = (PointSet) o;
			PointSetSet oth = ((PointSetSet) o);
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
		if ((o != null) && (o instanceof PointSetSet)) {
			PointSetSet oth = ((PointSetSet) o);
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

	@Override
	public boolean contains(Object o) {
		for( PointSet ps : this ){
			if(ps.equals(o)) return true;
		}
		return false;
	}

	@Override
	public boolean add(PointSet e) {
		if( this.contains(e) ) return false;
		return super.add(e);
	}
	
	

}
