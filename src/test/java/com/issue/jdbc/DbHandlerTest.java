/**
 * 
 */
package com.issue.jdbc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.issue.entity.Feature;
import com.issue.entity.Story;
import com.issue.entity.Team;
import com.issue.iface.FeatureDao;
import com.issue.iface.StoryDao;
import com.issue.repository.FeatureDaoImpl;
import com.issue.utils.Features;
import com.issue.utils.Stories;
import com.issue.utils.Teams;
import com.issue.utils.Utils;
import com.issue.jdbc.DbHandler;

/**
 * @author benito
 *
 */
class DbHandlerTest {

	private static String readAll(final Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		int c;
		while ((c = reader.read()) != -1) {
			sb.append((char) c);
		}
		return String.valueOf(sb);
	}

	private static String readFileContent(final String filePath) throws IOException {
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

	@Test
	void test() throws IOException, SQLException {
		// Create empty features list
		FeatureDao<String, Feature> features = new FeatureDaoImpl();

		// Get Json string
		String featuresJson = readFileContent("src/test/resources/features.json");
		// Extract features from json
		features.saveAll(Features.extractFeatures(featuresJson).getAll());

		// Get Json string
		String storiesJson = readFileContent("src/test/resources/CompleteSprint.json");
		// extract stories from json
		StoryDao<Story> stories = Stories.extractStories(storiesJson);

		Team team = new Team("Red");

		// Summarize story points from stories finished within sprint(s)
		team.setFinishedStoryPoints(Utils.countFeatureFocus(features, stories));

		// Summarize story points planned for sprint on begin of sprint
		team.setOnBeginPlannedStoryPointsSum(team.getFinishedStoryPointsSum());

		// Summarize story points planned for sprint on end of sprint
		team.setOnEndPlannedStoryPointsSum(team.getOnBeginPlannedStoryPointsSum());

		// Collect team members participating on sprint(s)
		Teams.collectTeamMembers(stories, team);

		String tableName = team.getTeamName().toString().toLowerCase() + "_team";

		String sprintLabel = "Sprint 1";

		DbHandler.insertIntoDB(tableName, sprintLabel, team);
	}
}
