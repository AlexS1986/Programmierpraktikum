package fernuni.propra.internal_data_model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ LineSegmentTest.class, LineSegmentTestParameterized.class, PointTest.class, RoomTest.class,
		WallTest.class })
public class InternalDataModelTests {

}
