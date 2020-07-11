package fernuni.propra.main;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MainTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUseCase_D() {
		// Arrange
		String[] commandLineParameters = new String[] { "r=d", "if=instances/validationInstances/Selbsttest_20b.xml" };

		// Act
		Main.main(commandLineParameters);
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
		}

		// Assert
	}
	
	@Test
	public void testUseCase_SD() {
		// Arrange
		String[] commandLineParameters = new String[] { "if=instances/validationInstances/Zufallsraum_144_solved.xml", "r=sd",
				"l=30" };

		// Act
		Main.main(commandLineParameters);
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
		}

	}
	
	@Test
	public void testUseCase_V() {
		// Arrange
		String[] commandLineParameters = new String[] {
				"if=instances/validationInstances/Selbsttest_100b3.xml", "r=vd" };

		// Act
		Main.main(commandLineParameters);

		// Assert
		System.out.println();
	}

	@Test
	public void testUseCase_VD() {
		// Arrange
		String[] commandLineParameters = new String[] {
				"if=instances/validationInstances/Selbsttest_20a_incomplete.xml", "r=vd" };

		// Act
		Main.main(commandLineParameters);

		// Assert
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
		}
	}

}
