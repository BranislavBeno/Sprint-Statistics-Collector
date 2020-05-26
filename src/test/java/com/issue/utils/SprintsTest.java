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
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Feature;
import com.issue.entity.Sprint;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.Dao2Output;
import com.issue.iface.FeatureDao;
import com.issue.iface.SprintDao;
import com.issue.iface.StoryDao;
import com.issue.iface.TeamDao;
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
	 * Test sprint list extraction with null team repository.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testSprintListExtractionWithNullTeamRepository() throws IOException, InterruptedException {
		// Get sprints
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(null);

		assertThat(sprints).isNotNull();
	}

	/**
	 * Test sprint list extraction with empty team repository.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testSprintListExtractionWithEmptyTeamRepository() throws IOException, InterruptedException {
		// Create team repository
		TeamDao<String, Team> teams = new TeamDaoImpl();

		// Get sprints
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(teams);

		assertThat(sprints).isNotNull();
	}

	/**
	 * Test sprint list extraction with non empty team repository containing initial team.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testSprintListExtractionWithNonEmptyTeamRepositoryContainingInitialTeam()
			throws IOException, InterruptedException {
		// Create new team
		Team team = new Team("Apple", "First");

		// Create team repository
		TeamDao<String, Team> teams = new TeamDaoImpl();
		teams.save(team);

		// Get sprints
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(teams);

		assertThat(sprints.getAll().size()).isEqualTo(0);
	}

	/**
	 * Test sprint list extraction with non empty team repository containing necessary featured team.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testSprintListExtractionWithNonEmptyTeamRepositoryContainingNecessaryFeaturedTeam()
			throws IOException, InterruptedException {
		// Create map of refined story points
		Map<FeatureScope, Integer> map = new EnumMap<>(FeatureScope.class);
		map.put(FeatureScope.BASIC, 7);
		map.put(FeatureScope.ADVANCED, 5);
		map.put(FeatureScope.COMMERCIAL, 3);
		map.put(FeatureScope.FUTURE, 1);

		// Create sprint
		Sprint sprint = new Sprint("Test sprint");
		sprint.setRefinedStoryPoints(map);

		// Create sprint repository
		SprintDao<String, Sprint> refinedStoryPoints = new SprintDaoImpl();
		refinedStoryPoints.save(sprint);

		// Create first team
		Team team1 = new Team("Apple", "Test sprint");
		// Add refined story points
		team1.setRefinedStoryPoints(refinedStoryPoints);

		// Create team repository
		TeamDao<String, Team> teams = new TeamDaoImpl();
		// Add team to repository
		teams.save(team1);

		// Create second team
		Team team2 = new Team("Banana", "Test sprint");
		// Add refined story points
		team2.setRefinedStoryPoints(refinedStoryPoints);

		// Add team to repository
		teams.save(team2);

		// Get sprints
		SprintDao<String, Sprint> sprints = Sprints.createSprintRepo(teams);

		assertThat(sprints.getAll().size()).isEqualTo(1);
	}

	/**
	 * Test positive xlsx output file exists.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
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
		String jsonString = StoriesTest.readFileContent("src/test/resources/CompleteSprint.json");

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
