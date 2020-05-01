package fernuni.propra.main;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



public class MainTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUseCase_D() {
		//Arrange
		String[] commandLineParameters = new String[] {"r=d", 
				"if=\"/Users/alex/Desktop/Programmierpraktikum/ProPra2020_workspace/Test_Component/instances/validationInstances/Selbsttest_20b.xml\""};
		String[] commandLineParameters2 = new String[] {"r=d", 
		"if=\"instances/validationInstances/Selbsttest_20b.xml\""};
		
		//Act
		Main.main(commandLineParameters);
		Main.main(commandLineParameters2);
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Assert
	}
	
	@Ignore
	@Test
	public void testUseCase_SD() {
		//Arrange
		String[] commandLineParameters = new String[] {"if=\"instances/validationInstances/Selbsttest_100b3.xml\"", "r=sd", "l=100" };
		
		//Act
		Main.main(commandLineParameters);
		
		try {
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Test
	public void testUseCase_V() {
		// Arrange
		String[] commandLineParameters = new String[] {"if=\"instances/validationInstances/Selbsttest_20a_incomplete.xml\"", "r=v" };
		
		//Act
		Main.main(commandLineParameters);
		
		//Assert
	}

}