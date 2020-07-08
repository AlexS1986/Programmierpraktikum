package fernuni.propra.file_processing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import fernuni.propra.internal_data_model.LineSegmentTest;
import fernuni.propra.internal_data_model.LineSegmentTestParameterized;
import fernuni.propra.internal_data_model.PointTest;

@RunWith(Suite.class)
@SuiteClasses({ FilePersistenceTest.class, LineSegmentTest.class, LineSegmentTestParameterized.class, PointTest.class })
public class FileProcessingTests {
}
