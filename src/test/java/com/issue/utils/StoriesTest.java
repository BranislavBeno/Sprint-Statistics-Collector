package com.issue.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.issue.entity.Feature;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.enums.FeatureScope;
import com.issue.iface.FeatureDao;
import com.issue.iface.StoryDao;
import com.issue.repository.FeatureDaoImpl;

/**
 * The Class StoriesTest.
 */
class StoriesTest {

	/**
	 * Read all.
	 *
	 * @param reader the reader
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static String readAll(final Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		return String.valueOf(sb);
	}

	/**
	 * Read file content.
	 *
	 * @param filePath the file path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String readFileContent(final String filePath) throws IOException {
		String content = null;

		// Proceed only when file name is not null
		if (filePath != null) {
			File file = new File(filePath);
			try (Reader fileReader = new FileReader(file)) {
				content = readAll(fileReader);
			}
		}

		return content;
	}

	/**
	 * Test private constructors for code coverage.
	 *
	 * @throws NoSuchMethodException the no such method exception
	 */
	@Test
	void testPrivateConstructorsForCodeCoverage() throws NoSuchMethodException {
		Class<Stories> clazz = Stories.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			constructor.setAccessible(true);
			assertThrows(InvocationTargetException.class, constructor::newInstance);
		}
	}

	/**
	 * Test negative story parsing with null json strings.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeStoryParsingWithNullJsonStrings() throws IOException, InterruptedException {
		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();

		// Extract features from json
		features.saveAll(Features.extractFeatures(null).getAll());

		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(null);

		// Get finished story points
		Map<FeatureScope, Integer> finishedStoryPoints = Utils.countFeatureFocus(features, stories);

		// Get finished story points count
		int finishedStoryPointsCount = Stories.summarizeStoryPoints(finishedStoryPoints);

		assertThat(finishedStoryPoints.get(FeatureScope.BASIC)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.ADVANCED)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.COMMERCIAL)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.FUTURE)).isEqualTo(0);
		assertThat(finishedStoryPointsCount).isEqualTo(0);
	}

	/**
	 * Test negative story parsing with null features json string.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeStoryParsingWithNullFeaturesJsonString() throws IOException, InterruptedException {
		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();

		// Extract features from json
		features.saveAll(Features.extractFeatures(null).getAll());

		// Get Json string
		String storiesJson = StoriesTest.readFileContent("src/test/resources/CompleteSprint.json");
		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(storiesJson);

		// Get finished story points
		Map<FeatureScope, Integer> finishedStoryPoints = Utils.countFeatureFocus(features, stories);

		// Get finished story points count
		int finishedStoryPointsCount = Stories.summarizeStoryPoints(finishedStoryPoints);

		assertThat(finishedStoryPoints.get(FeatureScope.BASIC)).isEqualTo(55);
		assertThat(finishedStoryPoints.get(FeatureScope.ADVANCED)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.COMMERCIAL)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.FUTURE)).isEqualTo(0);
		assertThat(finishedStoryPointsCount).isEqualTo(55);
	}

	/**
	 * Test negative story parsing with false json strings.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testNegativeStoryParsingWithFalseJsonStrings() throws IOException, InterruptedException {
		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();

		// Extract features from json
		features.saveAll(Features.extractFeatures("{}").getAll());

		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories("{}");

		// Get finished story points
		Map<FeatureScope, Integer> finishedStoryPoints = Utils.countFeatureFocus(features, stories);

		// Get finished story points count
		int finishedStoryPointsCount = Stories.summarizeStoryPoints(finishedStoryPoints);

		assertThat(finishedStoryPoints.get(FeatureScope.BASIC)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.ADVANCED)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.COMMERCIAL)).isEqualTo(0);
		assertThat(finishedStoryPoints.get(FeatureScope.FUTURE)).isEqualTo(0);
		assertThat(finishedStoryPointsCount).isEqualTo(0);
	}

	/**
	 * Test positive story parsing.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	@DisplayName("Test whether parsing stories from json input is passing")
	void testPositiveStoryParsing() throws IOException, InterruptedException {
		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();

		// Get Json string
		String featuresJson = StoriesTest.readFileContent("src/test/resources/features.json");
		// Extract features from json
		features.saveAll(Features.extractFeatures(featuresJson).getAll());

		// Get Json string
		String storiesJson = StoriesTest.readFileContent("src/test/resources/CompleteSprint.json");
		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(storiesJson);

		// Get finished story points
		Map<FeatureScope, Integer> finishedStoryPoints = Utils.countFeatureFocus(features, stories);

		// Get finished story points count
		int finishedStoryPointsCount = Stories.summarizeStoryPoints(finishedStoryPoints);

		assertThat(finishedStoryPoints.get(FeatureScope.BASIC)).isEqualTo(40);
		assertThat(finishedStoryPoints.get(FeatureScope.ADVANCED)).isEqualTo(6);
		assertThat(finishedStoryPoints.get(FeatureScope.COMMERCIAL)).isEqualTo(6);
		assertThat(finishedStoryPoints.get(FeatureScope.FUTURE)).isEqualTo(3);
		assertThat(finishedStoryPointsCount).isEqualTo(55);
	}

	/**
	 * Test positive team mebers counting.
	 *
	 * @throws IOException          Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 */
	@Test
	void testPositiveTeamMebersCounting() throws IOException, InterruptedException {
		// Get Json string
		String storiesJson = StoriesTest.readFileContent("src/test/resources/CompleteSprint.json");
		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(storiesJson);

		// Create new team
		Team team = new Team("Banana");

		// Get team member count
		Teams.collectTeamMembers(stories, team);

		assertThat(team.getTeamMemberCount()).isEqualTo(7);
	}

	/**
	 * Test stories extraction.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testStoriesExtraction() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/CompleteSprint.json");
		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		assertThat(stories.getAll().size()).isEqualTo(17);
		assertThat(stories.getAll().get(0).getEpic().orElse("")).isEqualTo("ISSUE-465");
		assertThat(stories.getAll().get(2).getStoryPoints().orElse(null)).isEqualTo(3);
	}

	/**
	 * Test no stories extraction.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testNoStoriesExtraction() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/CompleteOutsideSprint.json");
		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		assertThat(stories.getAll().size()).isEqualTo(0);
		assertThat(stories.getAll()).isEmpty();
	}

	/**
	 * Test stories extraction from non existing file.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testStoriesExtractionFromNonExistingFile() throws IOException {
		// Assert non existing file
		assertThrows(FileNotFoundException.class, () -> readFileContent("src/test/resources/CompleteInSprint.json"));
	}

	/**
	 * Test null json stories extraction.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testNullJsonStoriesExtraction() throws IOException {
		// Get Json string
		String jsonString = readFileContent(null);
		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		assertThat(stories.getAll().size()).isEqualTo(0);
		assertThat(stories.getAll()).isEmpty();
	}

	/**
	 * Test not closed high prior stories counting.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testNotClosedHighPriorStoriesCounting() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/CompleteSprint.json");

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize not closed high prior stories count
		int count = Stories.summarizeNotClosedHighPriorStoriesCount(stories);

		assertThat(count).isEqualTo(2);
	}

	/**
	 * Test not closed high prior stories counting from null json.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testNotClosedHighPriorStoriesCountingFromNullJson() throws IOException {
		// Get stories from null json
		StoryDao<Story> stories = Stories.extractStories(null);

		// Summarize not closed high prior stories count
		int count = Stories.summarizeNotClosedHighPriorStoriesCount(stories);

		assertThat(count).isEqualTo(0);
	}

	/**
	 * Test finished bugs SP summming.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testFinishedBugsSPSummming() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/CompleteSprint.json");

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize story points from finished bugfix stories
		int count = Teams.countFinishedBugsSPSum(stories);

		assertThat(count).isEqualTo(15);
	}

	/**
	 * Test finished bugs SP summming from null json.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testFinishedBugsSPSummmingFromNullJson() throws IOException {
		// Get stories from null json
		String jsonString = readFileContent(null);

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize story points from finished bugfix stories
		int count = Teams.countFinishedBugsSPSum(stories);

		assertThat(count).isEqualTo(0);
	}

	/**
	 * Test time estimation summing.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testTimeEstimationSumming() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/CompleteSprint.json");

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize time estimation within stories repo
		long count = Teams.summarizeTimeEstimation(stories);

		assertThat(count).isEqualTo(384);
	}

	/**
	 * Test time estimation summing from null json.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testTimeEstimationSummingFromNullJson() throws IOException {
		// Get stories from null json
		String jsonString = readFileContent(null);

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize time estimation within stories repo
		long count = Teams.summarizeTimeEstimation(stories);

		assertThat(count).isEqualTo(0);
	}

	/**
	 * Test time spent summing.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testTimeSpentSumming() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/CompleteSprint.json");

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize time spent within stories repository
		long count = Teams.summarizeTimeSpent(stories);

		assertThat(count).isEqualTo(369);
	}

	/**
	 * Test time spent summing from null json.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testTimeSpentSummingFromNullJson() throws IOException {
		// Get stories from null json
		String jsonString = readFileContent(null);

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize time estimation within stories repo
		long count = Teams.summarizeTimeSpent(stories);

		assertThat(count).isEqualTo(0);
	}

	/**
	 * Test in progress story points summing.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testInProgressStoryPointsSumming() throws IOException {
		// Get Json string
		String jsonString = readFileContent("src/test/resources/IncompleteSprint.json");

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize stories in progress status
		int count = Stories.summarizeInProgressStoryPoints(stories);

		assertThat(count).isEqualTo(3);
	}

	/**
	 * Test in progress story points summing from null json.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	void testInProgressStoryPointsSummingFromNullJson() throws IOException {
		// Get stories from null json
		String jsonString = readFileContent(null);

		// Extract stories from json
		StoryDao<Story> stories = Stories.extractStories(jsonString);

		// Summarize stories in progress status
		int count = Stories.summarizeInProgressStoryPoints(stories);

		assertThat(count).isEqualTo(0);
	}
}
