/**
 * 
 */
package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.issue.configuration.GlobalParams;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.Dao2Output;
import com.issue.iface.TeamDao;
import com.issue.repository.EngineerDaoImpl;
import com.issue.repository.SprintDaoImpl;
import com.issue.repository.TeamDaoImpl;

/**
 * The Class TeamsTest.
 *
 * @author branislav.beno
 */
class TeamsTest {

	/**
	 * Test private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Teams> clazz = Teams.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test negative team list extraction with non existing properties file.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeTeamListExtractionWithNonExistingPropertiesFile() throws IOException, InterruptedException {
		// Test non existing properties file
		assertThrows(FileNotFoundException.class,
				() -> Utils.provideGlobalParams("src/test/resources/application.properties"));
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

		// Get teams
		TeamDao<String, Team> teams = Teams.createTeamRepo(globalParams);

		assertThat(globalParams.getOutputFileName4Xlsx()).isEqualTo("null.xlsx");
		assertThat(teams).isNull();
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
		// Get teams
		TeamDao<String, Team> teams = Teams.createTeamRepo(globalParams);

		assertThat(globalParams.getOutputFileName4Xlsx()).isEqualTo("null.xlsx");
		assertThat(teams.getAll().size()).isEqualTo(0);
	}

	/**
	 * Test positive team list extraction.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveTeamListExtraction() throws IOException, InterruptedException {
		// provide global parameters
		GlobalParams globalParams = Utils
				.provideGlobalParams("src/test/resources/test_positive_application.properties");

		// Get teams
		TeamDao<String, Team> teams = Teams.createTeamRepo(globalParams);

		assertThat(globalParams.isXlsxOutput()).isTrue();
		assertThat(globalParams.getOutputFileName4Xlsx()).isEqualTo("Test.xlsx");
		assertThat(globalParams.getSprintStart().getYear()).isEqualTo(2020);
		assertThat(globalParams.getSprintEnd().getDayOfMonth()).isEqualTo(20);
		assertThat(teams.getAll().size()).isEqualTo(2);
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

		globalParams.setOutputFileName4Xlsx("teams.xlsx");

		Team team = new Team("Pear", globalParams.getSprintLabel());
		team.addTeamMembers(Set.of("John Doe"));
		Map<FeatureScope, Integer> storyPoints = new EnumMap<>(FeatureScope.class);
		for (FeatureScope scope : FeatureScope.values())
			storyPoints.put(scope, 0);
		team.setFinishedStoryPoints(storyPoints);

		TeamDao<String, Team> teamDao = new TeamDaoImpl();
		teamDao.save(team);

		// Create XLSX output
		Dao2Output xlsxOutput = OutputCreators.createXlsxOutput(globalParams, teamDao, new SprintDaoImpl(),
				new EngineerDaoImpl());

		assertThat(Files.exists(Paths.get(xlsxOutput.provideOutputFileName()))).isTrue();
	}
}
