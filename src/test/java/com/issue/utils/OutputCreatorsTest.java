/**
 * 
 */
package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.iface.Dao2Output;
import com.issue.repository.EngineerDaoImpl;
import com.issue.repository.SprintDaoImpl;
import com.issue.repository.TeamDaoImpl;

/**
 * The Class OutputCreatorsTest.
 *
 * @author benito
 */
class OutputCreatorsTest {

	/**
	 * Test output creators private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testOutputCreatorsPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<OutputCreators> clazz = OutputCreators.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test negative xlsx output empty file name.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeXlsxOutputEmptyFileName() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, new TeamDaoImpl(), new SprintDaoImpl(),
				new EngineerDaoImpl());

		assertThat(xlsxOutput.provideContent()).isBlank();
	}

	/**
	 * Test negative xlsx output null global params.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeXlsxOutputNullGlobalParams() throws IOException, InterruptedException {
		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(null, new TeamDaoImpl(), new SprintDaoImpl(),
				new EngineerDaoImpl());

		assertThat(xlsxOutput).isNull();
	}

	/**
	 * Test negative xlsx output null team repo.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeXlsxOutputNullTeamRepo() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, null, new SprintDaoImpl(),
				new EngineerDaoImpl());

		assertThat(xlsxOutput).isNull();
	}

	/**
	 * Test negative xlsx output null sprint repo.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeXlsxOutputNullSprintRepo() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, new TeamDaoImpl(), null,
				new EngineerDaoImpl());

		assertThat(xlsxOutput).isNull();
	}

	/**
	 * Test negative xlsx output null engineer repo.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeXlsxOutputNullEngineerRepo() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, new TeamDaoImpl(), new SprintDaoImpl(),
				null);

		assertThat(xlsxOutput).isNull();
	}

	/**
	 * Test positive default xlsx output.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveXlsxDefaultOutput() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, new TeamDaoImpl(), new SprintDaoImpl(),
				new EngineerDaoImpl());

		assertThat(xlsxOutput).isNotNull();
	}
}
