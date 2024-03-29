package fernuni.propra.internal_data_model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import fernuni.propra.internal_data_model.LineSegment;
import fernuni.propra.internal_data_model.Point;

@RunWith(Parameterized.class)
public class LineSegmentTestParameterized {

	@Parameter(0)
	public LineSegment lp1;
	@Parameter(1)
	public boolean result1;

	// creates the test data
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { new LineSegment(new Point(0, 0), new Point(1, 0)), true },
				{ new LineSegment(new Point(1, 0), new Point(1, 1)), false },
				{ new LineSegment(new Point(1, 1), new Point(0, 1)), true },
				{ new LineSegment(new Point(0, 0), new Point(0, 0)), false } };
		return Arrays.asList(data);
	}

	@Test
	public void testIsHorizontalParametrized() {
		// Act
		boolean isHorizontal = lp1.isHorizontal();

		// Assert
		assertTrue(isHorizontal == result1);
		;
	}

}
