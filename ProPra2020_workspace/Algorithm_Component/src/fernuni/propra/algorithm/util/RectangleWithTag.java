package fernuni.propra.algorithm.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import fernuni.propra.internal_data_model.Point;

public class RectangleWithTag extends Rectangle{

	HashSet<Integer> tags = new HashSet<Integer>();
	
	public RectangleWithTag(Point p1, Point p2, List<Integer> initialTags) {
		super(p1,p2);
		
		if (initialTags != null) {
			for (Integer tag : initialTags) {
				tags.add(tag);
			}
		}

	}
	
	public RectangleWithTag(Rectangle rectangle, List<Integer> initialTags) {
		super(rectangle.getP1(),rectangle.getP3());
		
		if (initialTags != null) {
			for (Integer tag : initialTags) {
				tags.add(tag);
			}
		}
	}
	
	public RectangleWithTag(Rectangle rectangle, HashSet<Integer> initialTags) {
		super(rectangle.getP1(),rectangle.getP3());
		if (initialTags != null) {
			tags = initialTags;
		}
	}
	
	public boolean containsTag(Integer tag) {
		return tags.contains(tag);
	}

	public void addTag(Integer tag) {
		tags.add(tag);
	}
	
	public void addTags(Iterator<Integer> tagIterator) {
		while(tagIterator.hasNext()) {
			this.tags.add(tagIterator.next());
		}
	}
	
	public Iterator<Integer> getTagIterator() {
		return tags.iterator();
	}
	
	public boolean tagsOfOtherRectangleAreSubsetOfThisTags(RectangleWithTag other) {
		boolean subset = true;
		Iterator<Integer> tagsOfOther = other.getTagIterator();
		while(tagsOfOther.hasNext()) {
			if (!tags.contains(tagsOfOther.next())) {
				subset = false;
				break;
			}
		}
		return subset;
	} 
	
	public HashSet<Integer> getCopyOfTags() {
		HashSet<Integer> outHashSet = new HashSet<Integer>();
		
		for(Integer tag : tags) {
			outHashSet.add(tag);
		}
		
		return outHashSet;
	}
	
	private int getSumOfTags() {
		int result = 0;
		for (Integer tag: tags) {
			result = result + tag;
		}
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if(!(o instanceof RectangleWithTag)) {
			return false;
		}
		RectangleWithTag r = (RectangleWithTag) o;
		return getP1().isEqual(r.getP1()) && getP2().isEqual(r.getP2()) 
				&& getP3().isEqual(r.getP3()) && getP4().isEqual(r.getP4()) && getSumOfTags() == r.getSumOfTags() ;
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + getP1().hashCode();
		result = 31 * result + getP3().hashCode();
		result = 31 * result + getSumOfTags();
		return result;
	}
	
}