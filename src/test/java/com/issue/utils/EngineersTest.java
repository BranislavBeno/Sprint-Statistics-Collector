/**
 * 
 */
package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Engineer;
import com.issue.entity.Story;
import com.issue.iface.Dao2Output;
import com.issue.iface.EngineerDao;
import com.issue.iface.StoryDao;
import com.issue.repository.SprintDaoImpl;
import com.issue.repository.TeamDaoImpl;

/**
 * The Class EngineersTest.
 *
 * @author branislav.beno
 */
class EngineersTest {

	/**
	 * Test private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Engineers> clazz = Engineers.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test negative engineer list extraction with false properties.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeEngineerListExtractionWithFalseProperties() throws IOException, InterruptedException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		// Get engineers
		EngineerDao<String, Engineer> engineers = Engineers.createEngineersRepo(globalParams);

		assertThat(engineers).isNotNull();
	}

	/**
	 * Test negative engineer list extraction with false authentication.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeEngineerListExtractionWithFalseAuthentication() throws IOException, InterruptedException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Get engineers
		EngineerDao<String, Engineer> engineers = Engineers.createEngineersRepo(globalParams);

		assertThat(engineers.getAll().size()).isEqualTo(0);
	}

	/**
	 * Test positive engineer list extraction.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveEngineerListExtraction() throws IOException, InterruptedException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Get engineers
		EngineerDao<String, Engineer> engineers = Engineers.createEngineersRepo(globalParams);

		assertThat(engineers.getAll().size()).isEqualTo(0);
	}

	/**
	 * Test positive xlsx output file exists.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveXlsxOutputFileExists() throws IOException, InterruptedException {
		// Provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");
		// Set output file
		globalParams.setOutputFileName4Xlsx("sprints.xlsx");

		// Get Json string
		String jsonString = StoriesTest.readFileContent("src/test/resources/CompleteSprint52Black.json");

		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Collect engineers with their finished story points
		EngineerDao<String, Engineer> engineers = Engineers.collectFinished4Engineers(stories);

		// Add engineers with their not finished story points
		engineers.saveAll(Engineers.collectNotFinished4Engineers(stories).getAll());

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, new TeamDaoImpl(), new SprintDaoImpl(),
				engineers);

		assertThat(engineers.getAll().size()).isEqualTo(7);
		assertThat(Files.exists(Paths.get(xlsxOutput.provideOutputFileName()))).isTrue();
	}
}
