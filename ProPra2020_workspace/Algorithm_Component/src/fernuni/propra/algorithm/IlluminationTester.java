package fernuni.propra.algorithm;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import fernuni.propra.algorithm.util.RectangleWithTag;
import fernuni.propra.internal_data_model.IRoom;
import fernuni.propra.internal_data_model.Lamp;

public class IlluminationTester implements IIlluminationTester{ 
	
	public IlluminationTester() {}
	
	@Override	
	public boolean testIfRoomIsIlluminated(IRoom room, IRuntimeIlluminationTester runtimeInfo) throws IlluminationTesterException {
		try {
			IOriginalPartialRectanglesFinder originalRectanglesFinder = new OriginalPartialRectanglesFinder();
			List<RectangleWithTag> rectanglesWithTag = originalRectanglesFinder.findOriginalPartialRectangles(room, runtimeInfo);	
			HashSet<Integer> allTags = originalRectanglesFinder.getAllTags();
			
			// assign lamps to tags/rectangles
			HashSet<Integer> tagsOfAllIlluminatedLamps = new HashSet<Integer>();
			
			//List<Lamp> taggedLamps = new LinkedList<Lamp>();
			Iterator<Lamp> lampIterator = room.getLamps();
			while(lampIterator.hasNext()) {
				Lamp lamp = lampIterator.next();
				if(lamp.getOn()) {
					//taggedLamps.add(lamp);
					for(RectangleWithTag rec : rectanglesWithTag) {
						if(lamp.isInsideRectangle(rec.getP1(), rec.getP3())) {
							Iterator<Integer> tagIterator = rec.getTagIterator();
							while(tagIterator.hasNext()) {
								tagsOfAllIlluminatedLamps.add(tagIterator.next());
								//lamp.addTag(tagIterator.next());
							}
						}
					}
				}	
			}
			if(tagsOfAllIlluminatedLamps.containsAll(allTags)) {
				return true;
			} else {
				return false;
			}
			
		} catch (OriginalPartialRectanglesFinderException e) {
			throw new IlluminationTesterException(e);
		}
	}
	
	@Override
	public boolean testIfRoomIsIlluminated(Iterator<Lamp> taggedLampsIterator, HashSet<Integer> allTags, IRuntimeIlluminationTester runtimeInfo) {
		return illuminatedLampsCoverAllTags(taggedLampsIterator, allTags);
	}

	private static boolean illuminatedLampsCoverAllTags(Iterator<Lamp> taggedLampsIterator, HashSet<Integer> allTags) {
		// TODO Auto-generated method stub
		HashSet<Integer> tagsOfAllIlluminatedLamps = new HashSet<Integer>();
		while(taggedLampsIterator.hasNext()) {
			Lamp lamp = taggedLampsIterator.next();
			if (lamp.getOn()) {
				Iterator<Integer> tagIterator = lamp.iteratorTag();
				while(tagIterator.hasNext()) {
					tagsOfAllIlluminatedLamps.add(tagIterator.next());
				}
			}
		}
		if (tagsOfAllIlluminatedLamps.containsAll(allTags)) {
			return true;
		} else {
			return false;
		}	
	}

	@Override
	public boolean testIfRoomIsIlluminated(HashSet<Integer> illuminatedTags, HashSet<Integer> allTags,
			IRuntimeIlluminationTester runtimeInfo) {
		return illuminatedTags.containsAll(allTags);
	}
}