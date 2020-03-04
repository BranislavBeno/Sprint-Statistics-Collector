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
import com.issue.entity.Feature;
import com.issue.entity.Sprint;
import com.issue.entity.Story;
import com.issue.iface.Dao2Output;
import com.issue.iface.FeatureDao;
import com.issue.iface.SprintDao;
import com.issue.iface.StoryDao;
import com.issue.repository.EngineerDaoImpl;
import com.issue.repository.SprintDaoImpl;
import com.issue.repository.TeamDaoImpl;

/**
 * The Class SprintsTest.
 *
 * @author branislav.beno
 */
class SprintsTest {

	/**
	 * Test private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Sprints> clazz = Sprints.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test negative team list extraction with false properties.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeTeamListExtractionWithFalseProperties() throws IOException, InterruptedException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative1_application.properties");

		// Get sprints
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(globalParams);

		assertThat(sprints).isNotNull();
	}

	/**
	 * Test negative team list extraction with false authentication.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeTeamListExtractionWithFalseAuthentication() throws IOException, InterruptedException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_negative2_application.properties");

		// Get sprints
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(globalParams);

		assertThat(sprints.getAll().size()).isEqualTo(0);
	}

	/**
	 * Test positive sprint list extraction.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveSprintListExtraction() throws IOException, InterruptedException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Get sprints
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(globalParams);

		assertThat(sprints.getAll().size()).isEqualTo(4);
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

		// Get features
		FeatureDao<String, Feature> features = Features.createFeaturesRepo(globalParams);

		// Get Json string
		String jsonString = StoriesTest.readFileContent("src/test/resources/CompleteSprint52Black.json");

		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Initialize sprints repository
		SprintDao<String, Sprint> sprintsRepo = new SprintDaoImpl();

		// Initialize sprint object
		Sprint sprint = new Sprint("Sprint 0");

		// Count story points from stories refined for particular sprint
		sprint.setRefinedStoryPoints(Utils.countFeatureFocus(features, stories));

		// Save refined sprint analysis
		sprintsRepo.save(sprint);

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, new TeamDaoImpl(), sprintsRepo,
				new EngineerDaoImpl());

		assertThat(Files.exists(Paths.get(xlsxOutput.provideOutputFileName()))).isTrue();
	}
}
