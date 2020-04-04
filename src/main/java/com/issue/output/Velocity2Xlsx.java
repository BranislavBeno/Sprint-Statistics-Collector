/**
 * 
 */
package com.issue.output;

import java.util.List;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.issue.entity.Team;
import com.issue.iface.TeamDao;
import com.issue.utils.OutputCreators;

/**
 * The Class Velocity2Xlsx.
 */
public class Velocity2Xlsx {

	/** The logger. */
	private static Logger logger = LogManager.getLogger(Velocity2Xlsx.class);

	/**
	 * Utility classes should not have public constructors.
	 */
	private Velocity2Xlsx() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Creates worksheet with velocity.
	 *
	 * @param workbook the workbook
	 * @param dao      the dao
	 * @param sheetIdx the sheet idx
	 */
	public static void createWorksheet(Workbook workbook, final TeamDao<String, Team> dao, final int sheetIdx) {
		// Worksheet name
		String sheetName = "Velocity";

		// Create sheet
		Sheet sheet = workbook.createSheet(sheetName);

		// Create column with captions
		OutputCreators.createCaptionColumn(workbook, sheetIdx,
				List.of("", "Sprint", "Dev #", "Sprint begin SP", "Planned SP", "Finished SP"));

		// Initialize column
		int colIdx = 1;
		for (Team team : dao.getAll().values()) {
			// Row 0 - Team name
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, 0, team.getTeamName());

			// Row 1 - Sprint label
			OutputCreators.writeHeaderCell(workbook, sheetIdx, colIdx, 1, team.getSprintLabel());

			// Row 2 - Team member count
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 2, team.getTeamMemberCount());

			// Row 3 - Story points planned on sprint begin
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 3, team.getOnBeginPlannedStoryPointsSum());

			// Row 4 - Story points planned on sprint end
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 4, team.getOnEndPlannedStoryPointsSum());

			// Row 5 - Finished story points
			OutputCreators.writeCell(workbook, sheetIdx, colIdx, 5, team.getFinishedStoryPointsSum());

			colIdx++;
		}

		// Autosize columns
		IntStream.range(0, dao.getAll().size() + 1).forEach(sheet::autoSizeColumn);

		logger.info("Worksheet {} created successfully.", sheetName);
	}
}
